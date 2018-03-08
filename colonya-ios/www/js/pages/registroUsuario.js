/**
    Script:         buscador.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          16/09/2015
    Descripción:    Módulo con las funciones y eventos que se manejan dentro del registro de usuario
*/
var registroUsuario = {};

registroUsuario = (function () {
    'use strict';
    var user;
    if (localStorage.getItem("usuarioActual") !== null) {
        user = JSON.parse(localStorage.getItem("usuarioActual")).name;
        //alert("userRegistroUsuario:    "+user);
    }

    var dni;
    var numUsuariosRegistrado = 0;
    var listUsers = [];
    var usuarioCifrado;
    var userSelect;

    //////////////////////////////////////////////
    // métodos privados
    //////////////////////////////////////////////
    var cargarCadenas = function () {
        utils.insertarIdioma(function () {
            $("p.lng-registro-cuerpo-1").text($.i18n.prop('registroUsuario-ningunUsuario-1'));
            $("p.lng-registro-cuerpo-1-largo").text($.i18n.prop('registroUsuario-ningunUsuario-1-largo'));
            $("p.lng-registro-cuerpo-2").html($.i18n.prop('registroUsuario-ningunUsuario-2'));
            $("p.lng-registro-cuerpo-2-largo").html($.i18n.prop('registroUsuario-ningunUsuario-2-largo'));
            //$(".lng-registroUsuario-titulo").text($.i18n.prop('registroUsuario-titulo'));
            $(".lng-label-registro-variosUsuarios").text($.i18n.prop('registroUsuario-seleccioneUsuario'));
            $(".lng-label-registro-unUsuario").text($.i18n.prop('registroUsuario-unUsuario'));
            $("a.lng-boton-registro-nuevo").text($.i18n.prop('registroUsuario-nuevoUsuario'));
            $("a.lng-boton-registro-eliminarUsuario").text($.i18n.prop('registroUsuario-eliminarUsuario'));
            $("a.lng-boton-registro-aceptar").text($.i18n.prop('aceptar'));
            $("a.lng-boton-registro-eliminar").text($.i18n.prop('avisos-eliminar'));
            $(".lng-registro-AnadirUsuario").text($.i18n.prop('registroUsuario-registrarNuevoUsuario'));
            $(".lng-popupHeader-titulo").text($.i18n.prop('registroUsuario-popup-titulo'));
            $(".lng-registro-eliminar-texto-1").text($.i18n.prop('registroUsuario-popup-texto-1'));
            $(".lng-registro-eliminar-texto-2").text($.i18n.prop('registroUsuario-popup-texto-2'));
            $("a.lng-popup-eliminar-aceptar").text($.i18n.prop('aceptar'));
            $("a.lng-popup-eliminar-cancelar").text($.i18n.prop('cancelar'));
        });
    };

    var obtenerUsuarioActual = function () {
        var nombre;
        if (numUsuariosRegistrado === 1) {
            if ($("#recuadroUsuario").length === 1) {
                nombre = $("#recuadroUsuario").text().substring(0, 8);
            } else {
                nombre = JSON.parse(localStorage.getItem("usuarioActual")).name;
            }

            if (nombre === "") {
                nombre = JSON.parse(localStorage.getItem("usuarioActual")).name;
            }
            // alert(nombre);
        } else {
            nombre = $("span.selectUsuario").text().substring(0, 8);
        }
        console.log("numUsuariosRegistrado:   " + numUsuariosRegistrado);
        console.log("obtenerUsuarioActual: " + nombre);
        console.log("listUsers: " + listUsers);
        var i;

        for (i = 0; i < numUsuariosRegistrado; i++) {
            if (listUsers[i].usuario === nombre) {
                return listUsers[i];
            }
        }
    };

    var ocultarMostrar = function (ocultar, mostrar) {
        $(ocultar).hide();
        $(mostrar).show();
    };

    var comprobarClaseSubrayado = function () {
        if (numUsuariosRegistrado === 0) {
            $("#lblBuscador").removeClass("subrayadoAnadirUsuario");
        } else {
            $("#lblBuscador").addClass("subrayadoAnadirUsuario");
        }
    };

    var cargar0Usuarios = function () {
        ocultarMostrar(".lng-registro-cuerpo-1-largo, .lng-registro-cuerpo-2, .lng-registro-cuerpo-2-largo, #divActivarUsuario", ".lng-registro-cuerpo-1, #nuevoUsuario");
    };

    var cargarUnUsuario = function () {
        ocultarMostrar(".lng-registro-cuerpo-1, .lng-registro-cuerpo-2-largo, #nuevoUsuario, #selectUsuario , #variosUsuarios, #divActivarUsuario div.ui-select",
             ".lng-registro-cuerpo-1-largo, .lng-registro-cuerpo-2, #divActivarUsuario, #unUsuario, #recuadroUsuario");
    };

    var cargarVariosUsuarios = function () {
        ocultarMostrar(".lng-registro-cuerpo-1, .lng-registro-cuerpo-2, #nuevoUsuario, #unUsuario, #recuadroUsuario", 
            ".lng-registro-cuerpo-1-largo, .lng-registro-cuerpo-2-largo, #divActivarUsuario, #divActivarUsuario div.ui-select, #selectUsuario, #variosUsuarios");
    };

    var limpiarSelect = function () {
        $('#selectUsuario').find('option').remove().end();
    };

    var cargarSelect = function () {
        limpiarSelect();

        var usuarioActual = userHelper.getActiveUser();

        var option;
        var i;
        for (i = 0; i < numUsuariosRegistrado; i++) {
            if (listUsers[i].pushActivo) {
                option = "<option value=\"" + listUsers[i].usuario + " (" + $.i18n.prop('registroUsuario-AvisosActivos') + ")" + "\">" + listUsers[i].usuario + " (" + $.i18n.prop('registroUsuario-AvisosActivos') + ")" + "</option>";

                if (i !== 0 && listUsers[i].usuario === userHelper.getActiveUser().usuario) {
                    $('#selectUsuario option:first').before(option);
                } else {
                    $('#selectUsuario').append(new Option(listUsers[i].usuario + " (" + $.i18n.prop('registroUsuario-AvisosActivos') + ")", listUsers[i].usuario + " (" + $.i18n.prop('registroUsuario-AvisosActivos') + ")", false, false));
                }

                if (listUsers[i].usuario === userHelper.getActiveUser().usuario) {
                    $("span.selectUsuario").text(listUsers[i].usuario + " (" + $.i18n.prop('registroUsuario-AvisosActivos') + ")");
                }

            } else {
                option = "<option value=\"" + listUsers[i].usuario + "\">" + listUsers[i].usuario + "</option>";

                if (i !== 0 && listUsers[i].usuario === userHelper.getActiveUser().usuario) {
                    $('#selectUsuario option:first').before(option);
                } else {
                    $('#selectUsuario').append(new Option(listUsers[i].usuario, listUsers[i].usuario, false, false));
                }         
            }
        }
        
        //$('<option>').val(listUsers[0].usuario).text(listUsers[0].usuario).appendTo(select);
        var firstOption = $('#selectUsuario option:first');
        //var texto = listUsers[0].pushActivo ? listUsers[0].usuario + " (" + $.i18n.prop('registroUsuario-AvisosActivos') + ")" : listUsers[0].usuario;
        $("span.selectUsuario").text(firstOption.val());
        firstOption.prop('selected', true);
    };

    var setUsuario = function (user) {
        $("#recuadroUsuario").text(user);
        var usuario = obtenerUsuarioActual();

        if (usuario != undefined && usuario.pushActivo) {
            $("#recuadroUsuario").text(user + " (" + $.i18n.prop('registroUsuario-AvisosActivos') + ")");
        }
    };

    var setVariosUsuarios = function () {
        var usuarioActual = obtenerUsuarioActual();
        var usuarios = userHelper.getUsers();

        $.each(usuarios, function (key, value) {
            var option = value.pushActivo ? value.usuario + " (" + $.i18n.prop('registroUsuario-AvisosActivos') + ")" : value.usuario;
            if (value.usuario !== usuarioActual) {
                $("#selectUsuario").append($("<option></option>").attr("value", key).text(option));
            } else {
                $("#selectUsuario").append($('<option selected="selected"></option>').attr("value", key).text(option));
            }
        })
    }

    var addClassRedFocus = function (elemento) {
        var numChildren;
        if (elemento === "#errorRegistroUsuario") {
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

    var inputsCorrectos = function () {
        if (utils.esVacio(user) && utils.esVacio(dni)) {
            inputError("#errorRegistroUsuario", 'login-user-vacio');
            inputError("#errorRegistroDNI", 'login-dni-vacio');
            return false;
        } else if (utils.esVacio(user)) {
            inputError("#errorRegistroUsuario", 'login-user-vacio');
            return false;
        } else if (utils.esVacio(dni)) {
            inputError("#errorRegistroDNI", 'login-dni-vacio');
            return false;
        } else {
            //Comprobamos que los 6 ultimos dígitos del campo usuario son números
            var ultimos6 = user.substr(user.length - 6);
            if (!utils.esNumerico(ultimos6)) {
                inputError("#errorRegistroUsuario", 'login-dni-numerico');
                return false;
            } else {
                if (dni.length < 9) {
                    dni = utils.padCeros(dni, 9);
                }
            }
            return true;
        }
    };

    var limpiarInputs = function () {
        utils.limpiarInput("#inputRegistroUsuario");
        utils.limpiarInput("#inputRegistroDNI");
        utils.ocultarClearBtn();
        $("#btnRegistroNuevoUsuario").removeClass("btnVerde");
        $("#btnRegistroNuevoUsuario").addClass("btnGris");
    };

    var registrarUsuario = function (data) {
        numUsuariosRegistrado++;
        listUsers.push(data);
    };

    var addOption = function (user) {
        var option = "<option value=\"" + user + "\">" + user + "</option>";

        //Si es el primer usuario, lo añadimos como primero, si hay más de uno, lo incluimos antes de la primera opción
        if (numUsuariosRegistrado === 1) {
            $("#selectUsuario option").remove();
            $('#selectUsuario').append(new Option(user, user, false, false));
        } else {
            $('#selectUsuario option:first').before(option);
        }
        $('#selectUsuario option:first').prop('selected', true);
        $("span.selectUsuario").text(user);
    };

    var existeUsuario = function (usuario) {
        var i;
        for (i = 0; i < listUsers.length; i++) {
            if (listUsers[i].usuario === usuario.usuario) {
                return true;
            }
        }
        return false;
    };

    var cbAppMovil = function (res, irAPortal) {
        utils.quitarLoading();
        dataHelper.saveActiveApp(res.EE_O_AplicacionMovil);

        var usuarioSeleccionado = obtenerUsuarioActual();
        userHelper.saveActiveUser(usuarioSeleccionado);

        if (irAPortal) {

            portal.mostrar();
        }
    };

    var cbErrorAppMovil = function () {
        utils.quitarLoading();
        alerta.aviso($.i18n.prop('errorGenerico'));
    };

    var activar = function (irAPortal) {

        var usuarioSeleccionado = obtenerUsuarioActual();
        var jsonParam = {};

        if (usuarioSeleccionado === undefined || usuarioSeleccionado === null || usuarioSeleccionado === "null") {
            jsonParam.ent = JSON.parse(localStorage.getItem("active_user")).Entidad;
            jsonParam.usu = JSON.parse(localStorage.getItem("usuarioActual")).name;
            usuarioCifrado = JSON.parse(localStorage.getItem("active_user")).campoCifrado;
        } else {
            jsonParam.ent = usuarioSeleccionado.Entidad ? usuarioSeleccionado.Entidad : "9998";
            jsonParam.usu = usuarioSeleccionado.usuario;
            usuarioCifrado = usuarioSeleccionado.campoCifrado;
        }

        jsonParam.idDispositivo = localStorage.getItem("UUID");
        jsonParam.idioma = "ca";

        utils.mostrarLoading();

        if(jsonParam.ent === "undefined" || jsonParam.ent === undefined) {
            jsonParam.ent = userHelper.getGenericUser().Entidad;
        }

        if(jsonParam.usu === "undefined" || jsonParam.usu === undefined) {
            jsonParam.usu = userHelper.getGenericUser().usuario;
        }

        var datosSinCodificar = "?codigoEntidad=" + jsonParam.ent + "&usuarioBE=" + jsonParam.usu + "&idDispositivo=" + jsonParam.idDispositivo + "&idioma=" + jsonParam.idioma;

        var metodo = "EE_I_PortalPubAPP";

        var url = config.servidor + config.Aplicacion + metodo + datosSinCodificar;

        console.log("urlPortal: " + url);
        utils.mostrarLoading();

        $.ajax({

            url: url,
            dataType: 'json',
            contentType: 'application/json',
            type: "GET",
            headers: config.headers,
            success: function (data, response) {
                console.log("data: " + JSON.stringify(data));
                cbAppMovil(data, irAPortal);
            },
            error: function (response) {
                cbErrorAppMovil();
            }

        });



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
        var usuarioActual = JSON.parse(localStorage.getItem("usuarioActual"));

        if (user === undefined) {
            user = usuarioActual.name;
        }

        if (!utils.esNulo(user)) {
            if (!utils.esNumerico(user)) {
                nuevoUsuario.usuario = user.toUpperCase();
            } else {
                nuevoUsuario.usuario = user;
            }
        } else {
            nuevoUsuario.usuario = user;
        }

        var cr = nuevoUsuario.CR;

        if (cr === "1") {
            if (!existeUsuario(nuevoUsuario)) {
                JSONData.setReg(nuevoUsuario);

                userHelper.setUser(nuevoUsuario);
                userHelper.saveActiveUser(nuevoUsuario);
                userHelper.setLoginActivityPass(true);

                registrarUsuario(nuevoUsuario);
                addOption(nuevoUsuario.usuario);
                comprobarClaseSubrayado();

                checkActivationTemplate(nuevoUsuario.usuario);
            }
        } else if (cr === "40002") {
            alerta.aviso($.i18n.prop('login-40002'));
        } else if (cr === "1500") {
            alerta.aviso(nuevoUsuario.MR);
        } else {
            alerta.aviso($.i18n.prop('login-error-1') + ' ' + nuevoUsuario.CR + ' ' + $.i18n.prop('login-error-2') + ' ' + nuevoUsuario.MR);
        }
    };

    var cbErrorRegistrarApp = function (response) {
        utils.quitarLoading();
        // alert("error");
        var respuestaError = JSON.stringify(response.responseText.mensajeMostrar);
        console.log("respuestaError::::" + respuestaError);
        alerta.aviso($.i18n.prop('login-error-registro'));
    };

    var registrarApp = function (newUser, newDni) {
        var jsonParam = {};
        jsonParam.idioma = "es";
        jsonParam.codigoEntidad = "0198";
        jsonParam.usu = user;
        jsonParam.nif = dni;
        jsonParam.idDis = localStorage.getItem("UUID"); //Chequear si está así en la app anterior

        var metodo = "EE_I_RegistroAPP";
        // var datosSinCodificar = "{\"" + metodo + "\":" + JSON.stringify(jsonParam) + "}";
        var datosSinCodificar = "?usuarioBE=" + jsonParam.usu + "&idExterno=" + jsonParam.nif + "&idDispositivo=" + jsonParam.idDis;
        var url = config.servidor + config.Registro + metodo + datosSinCodificar;
        // var url = config.servidor + config.Registro + '?data=' + datosSinCodificar;
        console.log("url: " + url);

        utils.mostrarLoading();

        $.ajax({

            url: url,
            dataType: 'json',
            contentType: 'application/json',
            type: "GET",
            //processData: true,
            headers: config.headers,
            //data: JSON.stringify(jsonParam),
            success: function (data, response) {
                // console.log("data: "+JSON.stringify(data));
                // alert("reponseSuccess: "+JSON.stringify(response));
                // alert("dataSuccess: "+JSON.stringify(data));
                console.log("dataSuccess: " + JSON.stringify(data));
                // alert("urlSuccess:  " +url);
                cbRegistrarApp(data);
            },
            error: function (response) {
                // alert("data: "+JSON.stringify(data));
                // console.log("reponseError: "+JSON.stringify(response));
                // alert("reponseError: "+JSON.stringify(response));
                console.log("reponseError: " + JSON.stringify(response));
                // alert("urlError:  " +url);
                cbErrorRegistrarApp(response);
            }

        });



    };

    var nuevoUsuario = function () {

        user = $.trim($("#inputRegistroUsuario").val());
        dni = $.trim($("#inputRegistroDNI").val());

        if (inputsCorrectos()) {
            //Comprobamos que los 6 ultimos dígitos del campo usuario son números
            var ultimos6 = user.substr(user.length - 6);

            if (!utils.esNumerico(ultimos6)) {
                inputError("#inputRegistroUsuario", 'login-dni-numerico');
            } else {
                if (dni.length < 9) {
                    dni = utils.padCeros(dni, 9);
                }
                limpiarInputs();
                registrarApp(user, dni);
            }
        }
    };

    var anadirUsuario = function () {
        if (numUsuariosRegistrado > 0) {
            $("#nuevoUsuario").toggle();
            $('html, body').animate({ scrollTop: $("#anadirUsuario").offset().top }, 1000);
        }
    };

    var cerrarPopup = function () {
        $("#popup-registro-eliminar").popup().popup("close");
        if(!$(".ui-popup-container.ui-popup-active").is(":visible")) {
            $("#page-registroUsuario").css("position", "absolute");
        }
    };

    var abrirPopupEliminar = function () {
        $("#popup-registro-eliminar").popup().popup("open");
        if($(".ui-popup-container.ui-popup-active").is(":visible")) {
            $("#page-registroUsuario").css("position", "inherit");
        }
    };

    var switchUserSelector = function () {
        var usersArray = userHelper.getUsers();

        listUsers = [];
        numUsuariosRegistrado = 0;

        if (!utils.esNulo(usersArray) && usersArray.length > 0) {
            var i;

            for (i = 0; i < usersArray.length; i++) {
                var user = usersArray[i];

                if (!utils.esNulo(user)) {

                    listUsers.push(user);
                    numUsuariosRegistrado++;
                }
            }
        }
    };

    var reactivarUsuario = function () {

        var i = 0;
        var encontrado = false;
        while (i < numUsuariosRegistrado && !encontrado) {
            encontrado = userHelper.getActiveUser().usuario === listUsers[i].usuario;
            i++;
        }

        //Si no se encuentra el usuario activo, es que ha sido eliminado, y activamos al primero de la lista
        if (!encontrado) {
            if (numUsuariosRegistrado > 0) {
                userHelper.saveActiveUser(listUsers[0]);
            } else {
                userHelper.saveActiveUser(null);
            }
        }

    };

    var elementosAMostrar = function () {

        switchUserSelector();

        $("#errorRegistroUsuario").hide();
        $("#errorRegistroDNI").hide();

        //comprobar lo que mostrar/ocultar
        if (numUsuariosRegistrado === 0) {
            cargar0Usuarios();
        } else if (numUsuariosRegistrado > 0) {
            // console.log("listUsers::::   "+listUsers[0].usuario === undefined);

            if (listUsers[0].usuario === undefined) {
                setUsuario(JSON.parse(localStorage.getItem("usuarioActual")).name);
            } else {
                setUsuario(listUsers[0].usuario);             
            }
            cargarSelect();
            cargarVariosUsuarios();

            activar(false);
        } else {
            reactivarUsuario();
            cargarSelect();
            cargarVariosUsuarios();
        }

        comprobarClaseSubrayado();
    };

    var eliminarUsuario = function () {
        var user = obtenerUsuarioActual();
        userHelper.deleteUser(user);

        cerrarPopup();

        elementosAMostrar();

        var usersArray = userHelper.getUsers();

        if (utils.esNulo(usersArray) || usersArray.length === 0) {
            userHelper.removeActiveUser();
            dataHelper.removeActiveApp();
        }

    };

    var showActivePushMessageDialog = function () {
        alerta.avisoTitulo($.i18n.prop('registroUsuario-noPuedeEliminarTitulo'), $.i18n.prop('registroUsuario-noPuedeEliminarCuerpo'));
    };

    var comprobarEliminarUsuario = function () {
        var user = obtenerUsuarioActual();

        if (user.pushActivo) {
            showActivePushMessageDialog();
        } else {
            abrirPopupEliminar();
        } 
    };

    var eventosInput = function () {
        $('#inputRegistroUsuario, #inputRegistroDNI').on('input', function () {
            utils.comprobarInput(this);

            var lenUsuario = $("#inputRegistroUsuario").val().length;
            var lenDNI = $("#inputRegistroDNI").val().length;

            if (lenUsuario > 0 && lenDNI > 0) {
                $("#btnRegistroNuevoUsuario").removeClass("btnGris");
                $("#btnRegistroNuevoUsuario").addClass("btnVerde");
            } else if (lenUsuario === 0 || lenDNI === 0) {
                $("#btnRegistroNuevoUsuario").removeClass("btnVerde");
                $("#btnRegistroNuevoUsuario").addClass("btnGris");
            }
        });

        $('#inputRegistroUsuario, #inputRegistroDNI').on('focus', function () {
            if (this.id === "inputRegistroUsuario") {
                if ($("#errorRegistroUsuario").is(':visible')) {
                    $("#errorRegistroUsuario").hide();
                }
            } else {
                if ($("#errorRegistroDNI").is(':visible')) {
                    $("#errorRegistroDNI").hide();
                }
            }

            $(this).parent().removeClass('border-red-focus');
        });

        $(".ui-icon-delete").on("click", function () {
            $(this).siblings().val("");
            $(this).css("display", "none");
            $(this).parent().removeClass('border-red-focus');
            $("#errorRegistroUsuario").hide();
            $("#errorRegistroDNI").hide();
            $("#btnRegistroNuevoUsuario").removeClass("btnVerde");
            $("#btnRegistroNuevoUsuario").addClass("btnGris");
        });
    };

    var inicializarPagina = function () {
        cargarCadenas();

        elementosAMostrar();

        utils.ocultarClearBtn();

        $("#selectUsuario").bind("change", function (event, ui) {
            var valor = $("#selectUsuario").val();
            $("#divActivarUsuario span.selectUsuario").text(valor);
        });

        $("#btnRegistroNuevoUsuario").on("click", nuevoUsuario);
        $("#anadirUsuario").on("vclick", function(e) {
            e.preventDefault();
            anadirUsuario();
        });
        $("#btnRegistroAceptar").on("click", function () {
            activar(true);
        });
        $("#btnRegistroEliminarUsuario").on("click", comprobarEliminarUsuario);
        $("#btnRegistroEliminar").on("touchend", comprobarEliminarUsuario);
        $("#btnRegistroEliminarAceptar").on("click", eliminarUsuario);
        $("#btnRegistroEliminarCancelar").on("click", cerrarPopup);
        $("#atras-registroUsuario").on("vclick", portal.mostrar);
        $("#btnRegistroAceptar-1Usuario").on("click", portal.mostrar);
        $("#btnRegistroEliminar-1Usuario").on("click", comprobarEliminarUsuario);


        $("#btnAvisoNuevoUsuario, #lblBuscador").on("vclick", function (e) {
            e.preventDefault();
            $("#nuevoUsuario").addClass("anadirPadding");

        });



        eventosInput();

    };

    var iniciarElementos = function () {
        cargarCadenas();
    };

    var iniciarEventos = function () {

    };

    //////////////////////////////////////////////
    // métodos públicos
    //////////////////////////////////////////////
    var mostrar = function () {
        var usuarioRegistrado = userHelper.getUsuarioRegistrado();
        if (localStorage.getItem("list_users") != null && localStorage.getItem("active_user") === "undefined") {
            localStorage.setItem("active_user", JSON.stringify(JSON.parse(localStorage.getItem("list_users"))[0]));
        }
        setTimeout(function () { localStorage.setItem("active_app", localStorage.getItem("active_app2")); }, 500);



        var esApple = true;

        if (!config.simuleTerminal) {
            esApple = (utils.getDevice() === "iOS");
            //screen.unlockOrientation();
        }

        var logo = dataHelper.getLogoUrl();

        utils.inyectar("registroUsuario.html", "#page-registroUsuario", {
            usuarioRegistrado: usuarioRegistrado,
            esApple: esApple,
            logo: logo
        });
    };

    var checkActivationTemplate = function (user) {
        if (numUsuariosRegistrado === 1) {
            cargarUnUsuario();
            setUsuario(user);
        } else if (numUsuariosRegistrado > 1) {
            cargarVariosUsuarios();
        }
        setUsuario(user);
    };

    var inicializar = function () {
        $(document)
            .one('pageinit', '#page-registroUsuario', iniciarEventos)
            .on("pageinit", "#page-registroUsuario", iniciarElementos)
            .on("pageshow", "#page-registroUsuario", inicializarPagina)
            .on("backbutton", "#page-registroUsuario", portal.mostrar);
    };

    return {
        mostrar: mostrar,
        inicializar: inicializar,
        existeUsuario: existeUsuario,
        registrarUsuario: registrarUsuario,
        setUsuario: setUsuario,
        activar: activar,
        cbRegistrarApp: cbRegistrarApp,
        cbErrorRegistrarApp: cbErrorRegistrarApp,
        registrarApp: registrarApp,
        checkActivationTemplate: checkActivationTemplate,
        addOption: addOption
    };
} ());
