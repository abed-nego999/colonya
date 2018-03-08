/**
    Script:         login.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          22/10/2015
    Descripción:    Módulo con las funciones y eventos que se manejan dentro del login
*/
var login = {};

login = (function () {
    'use strict';
    var productos = [];
    var intentos = 0;
    var dni = "";
    var user = "";
    var jsonParam = {};

    //////////////////////////////////////////////
    // métodos privados
    //////////////////////////////////////////////
    var cargarCadenas = function () {
        utils.insertarIdioma(function () {
            $("label.lng-login-titulo").text($.i18n.prop('registroUsuario-titulo'));
            $("p.lng-login-texto-1").html($.i18n.prop('login-texto-1'));
            $("p.lng-login-texto-2").text($.i18n.prop('login-texto-2'));
            $("p.lng-login-texto-3").text($.i18n.prop('login-texto-3'));
            $(".lng-login-label").text($.i18n.prop('login-noGuardar'));
            $("a.lng-boton-aceptar").text($.i18n.prop('aceptar'));
            $("a.lng-boton-cancelar").text($.i18n.prop('cancelar'));
        });
    };

    var irAPortal = function () {

        portal.mostrar();
    };

    var setLoginAndPortal = function () {
        userHelper.setLoginActivityPass(true);
        localStorage.setItem("usuarioActual", JSON.stringify({
            name: user,
            dni: dni
        }));
        irAPortal();
    };

    var addClassRedFocus = function (elemento) {
        var numChildren;
        if (elemento === "#errorUsuario") {
            numChildren = 0;
        } else {
            numChildren = 2;
        }

        $(elemento).parent().children().each(function (idx, val) {
            if (idx === numChildren) {
                $(this).addClass('border-red-focus');
            }
        });
    };

    var inputError = function (elemento, frase) {
        addClassRedFocus(elemento);
        $(elemento).show();
        $(elemento).text($.i18n.prop(frase));
    };

    var inputsCorrectos = function (user, dni) {
        if (utils.esVacio(user) && utils.esVacio(dni)) {
            inputError("#errorUsuario", 'login-user-vacio');
            inputError("#errorDNI", 'login-dni-vacio');
            return false;
        } else if (utils.esVacio(user)) {
            inputError("#errorUsuario", 'login-user-vacio');
            return false;
        } else if (utils.esVacio(dni)) {
            inputError("#errorDNI", 'login-dni-vacio');
            return false;
        } else {
            return true;
        }
    };

    var cbRegistrarApp = function (data) {
        utils.quitarLoading();

        var nuevoUsuario = {};
        nuevoUsuario.campoCifrado = data.EE_O_RegistroApp.Respuesta.codigoLogin;
        nuevoUsuario.campoPersona = data.EE_O_RegistroApp.Respuesta.codigoPersona;
        nuevoUsuario.CR = data.EE_O_RegistroApp.codigoRetorno;
        nuevoUsuario.Entidad = data.EE_O_RegistroApp.Respuesta.codigoEntidad;
        nuevoUsuario.pushActivo = false;
        nuevoUsuario.MR = data.EE_O_RegistroApp.MR;

        if (!utils.esNulo(user)) {
            if (!utils.esNumerico(user)) {
                nuevoUsuario.usuario = user.toUpperCase();
            } else {
                nuevoUsuario.usuario = user;
            }
        } else {
            nuevoUsuario.usuario = user;
        }

        if (!utils.esNulo(dni)) {
            if (!utils.esNumerico(dni)) {
                nuevoUsuario.dni = dni.toUpperCase();
            } else {
                nuevoUsuario.dni = dni;
            }
        } else {
            nuevoUsuario.dni = dni;
        }

        var cr = nuevoUsuario.CR;

        // Si CR === 1, guardamos el usuario, el login, el registro..
        if (cr === "1") {
            if (!registroUsuario.existeUsuario(nuevoUsuario)) {
                JSONData.setReg(nuevoUsuario);

                userHelper.setUser(nuevoUsuario);
                userHelper.saveActiveUser(nuevoUsuario);
                userHelper.setLoginActivityPass(true);

                registroUsuario.registrarUsuario(nuevoUsuario);
                registroUsuario.setUsuario(nuevoUsuario.usuario);
                irAPortal();

                registroUsuario.activar(false);
            }
        } else if (cr === "40002") {
            alerta.aviso($.i18n.prop('login-40002'));
        } else if (cr === "1500") {
            alerta.aviso(nuevoUsuario.MR);
        } else {
            alerta.aviso($.i18n.prop('login-error-1') + ' ' + nuevoUsuario.CR + ' ' + $.i18n.prop('login-error-2') + ' ' + nuevoUsuario.MR);
        }
    };

    var cbErrorRegistrarApp = function () {
        utils.quitarLoading();
        alerta.aviso("El usuario o el NIF/NIE no son correctos");
        //alerta.aviso($.i18n.prop('login-error-registro'));
    };

    var registrarApp = function () {
        

        var jsonParam = {};
        jsonParam.idioma = "ca";
        jsonParam.codigoEntidad = "2056";
        jsonParam.usu = user;
        jsonParam.nif = dni;
        jsonParam.idDis = localStorage.getItem("UUID"); //Chequear si está así en la app anterior

        var metodo = "EE_I_RegistroAPP";
        var datosSinCodificar = "?usuarioBE=" + jsonParam.usu + "&idExterno=" + jsonParam.nif + "&idDispositivo=" + jsonParam.idDis;
        var url = config.servidor + config.Registro + metodo + datosSinCodificar;
        console.log("loginAppUrl:::::"+url);

        utils.mostrarLoading();

        $.ajax({

            url: url,
            dataType: 'json',
            contentType: 'application/json',
            type: "GET",
            headers: config.headers,
            success: function (data, response) {
                console.log("dataSuccess: " + JSON.stringify(data));
                cbRegistrarApp(data);
            },
            error: function (response) {
                console.log("reponseError: " + JSON.stringify(response));
                cbErrorRegistrarApp();
            }
        });
    };

    var cbUUID = function (res) {
        var id = res;

        if (id.length >= 20) {
            id = id.substring(0, 19);
        }

        registrarApp(id);
    };

    var failUUID = function (res) {
        alerta.aviso($.i18n.prop('uuid-fail'));
    };

    var login = function () {
        // Si el nº de intentos es > 3 cargamos la entidad genérica
        if (intentos > 3) {
            alerta.aviso($.i18n.prop('login-intentos'));
            irAPortal();
        } else {
            user = $.trim($("#inputUsuario").val());
            dni = $.trim($("#inputDNI").val());
            localStorage.setItem("usuarioPrimeraVez", user);
            localStorage.setItem("usuarioActual", JSON.stringify({
                name: user,
                dni: dni
            }));
            if (inputsCorrectos(user, dni)) {

                //Comprobamos que los 6 ultimos dígitos del campo usuario son números
                var ultimos6 = user.substr(user.length - 6);

                if (!utils.esNumerico(ultimos6)) {
                    inputError("#errorUsuario", 'login-dni-numerico');
                } else {
                    if (dni.length < 9) {
                        dni = utils.padCeros(dni, 9);
                    }
                    intentos++;

                    if (!config.simuleTerminal) {
                        window.plugins.uniqueDeviceID.get(cbUUID, failUUID);
                    } else {
                        registrarApp();
                    }
                }
            }
        }

    };

    var aceptar = function () {
        if ($("#checkNoGuardar").is(':checked')) {
            setLoginAndPortal();
        } else {
            login();
        }
    };

    var cancelar = function () {
        setLoginAndPortal();
    };

    var comprobarCambioBtnVerde = function () {
        var lenUsuario = $("#inputUsuario").val().length;
        var lenDNI = $("#inputDNI").val().length;

        if (lenUsuario > 0 && lenDNI > 0) {
            $("#btnLoginAceptar").removeClass("btnGris");
            $("#btnLoginAceptar").addClass("btnVerde");
        } else {
            $("#btnLoginAceptar").removeClass("btnVerde");
            $("#btnLoginAceptar").addClass("btnGris");
        }
    };

    var eventosInput = function () {
        $('#inputUsuario, #inputDNI').on('input', function () {
            utils.comprobarInput(this);
            comprobarCambioBtnVerde();
        });

        $('#inputUsuario, #inputDNI').on('focus', function () {
            if (this.id === "inputUsuario") {
                $("#errorUsuario").hide();
            } else {
                $("#errorDNI").hide();
            }

            $(this).parent().removeClass('border-red-focus');

        });

        $(".ui-icon-delete").on("click", function () {
            $(this).siblings().val("");
            $(this).css("display", "none");
            $("#btnLoginAceptar").removeClass("btnVerde");
            $("#btnLoginAceptar").addClass("btnGris");
            $(this).parent().removeClass('border-red-focus');
            $("#errorUsuario").hide();
            $("#errorDNI").hide();
        });
    };

    var iniciarElementos = function () {
        cargarCadenas();
    };

    var iniciarPagina = function () {
        utils.ocultarClearBtn();

        eventosInput();
        setTimeout(function(){
                        navigator.splashscreen.hide();
                    }, 300);

        /*$("#errorUsuario").hide();  
        $("#errorDNI").hide();*/
    };

    var iniciarEventos = function () {
        $('#btnLoginAceptar').on("vclick", aceptar);
        $('#btnLoginCancelar').on("vclick", cancelar);
    };

    //////////////////////////////////////////////
    // métodos públicos
    //////////////////////////////////////////////   
    var inicializar = function () {

        $(document)
            .one('pageinit', '#page-login', iniciarEventos)
            .on("pageinit", "#page-login", iniciarElementos)
            .on("pageshow", "#page-login", iniciarPagina);

    };

    return {
        inicializar: inicializar
    };
} ());
