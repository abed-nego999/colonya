/**
    Script:         buscador.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          16/09/2015
    Descripción:    Módulo con las funciones y eventos que se manejan dentro de avisos
*/
var avisos = {};

avisos = (function () {
    'use strict';
    var user;
    var dni;
    var numUsuariosRegistrado = 0;
    var listUsers = [];
    var usuarioCifrado;
    var mensajesSeleccionados = 0;
    var pagePaddingTop;
    var altaNuevoUsuario = false;
    var jsonParam = {};
    var clickAblePush;
    var allowActivePush = false;
    var paraActivarNuevoUsuario = false;

    //////////////////////////////////////////////
    // métodos privados
    //////////////////////////////////////////////

    var handlebars = function (elem, data) {
        var source = $(elem).html();
        var template = Handlebars.compile(source);
        var html = template(data);
        $(elem).html(html);
    };

    var cargarCadenas = function () {
        utils.insertarIdioma(function () {
            //$("label.lng-avisos-titulo").text($.i18n.prop('avisos-titulo'));
            $(".lng-avisos-cuerpo-1").text($.i18n.prop('avisos-cuerpo-1'));
            $(".lng-avisos-cuerpo-2.textoAvisos2").text($.i18n.prop('avisos-cuerpo-2'));
            $(".avisos-negrita-activar").text($.i18n.prop('avisos-negrita-activar'));
            $(".desactivar.negrita").text($.i18n.prop('avisos-negrita-desactivar'));
            $("#pageAvisoPrevioActivar .lng-avisos-cuerpo-2.textoAvisos").text($.i18n.prop('avisos-cuerpo-pulsaActivar'));
            $(".lng-label-aviso-seleccioneUsuario").text($.i18n.prop('registroUsuario-seleccioneUsuario'));
            $(".lng-avisos-cuerpo-credenciales-1").text($.i18n.prop('avisos-cuerpo-credenciales-1'));
            $(".lng-boton-aviso-nuevo").text($.i18n.prop('avisos-nuevoUsuario'));
            $(".lng-boton-aviso-activar").text($.i18n.prop('avisos-activar'));
            $(".lng-boton-aviso-credenciales-aceptar").text($.i18n.prop('aceptar'));
            $(".lng-avisos-AnadirUsuario").text($.i18n.prop('avisos-anadirUsuario'));
            $(".lng-avisos-checkbox").text($.i18n.prop('avisos-eliminar'));
            $(".lng-avisos-NoAvisos").text($.i18n.prop('avisos-noMensajes'));
            $(".lng-eliminarUno").html($.i18n.prop('avisos-eliminarUno'));
            $(".lng-eliminarVarios").html($.i18n.prop('avisos-eliminarVarios'));
            $(".lng-eliminar-aceptar").html($.i18n.prop('aceptar'));
            $(".lng-eliminar-cancelar").html($.i18n.prop('cancelar'));
            $("#altaNuevoUsuarioButton").text($.i18n.prop('avisos-altaNuevoUsuario'));
            $("#btnDesactivar").html($.i18n.prop('avisos-desactivarServicio'));
            $(".lng-popupHeader-titulo").html($.i18n.prop('registroUsuario-popup-titulo'));
            $(".lng-popupPush-nuevoUsuario").html($.i18n.prop('registroPush-popup-nuevoUsuario'));
            $(".lng-popupPush-bajaUsuario").html($.i18n.prop('registroPush-popup-bajaUsuario'));
            $(".lng-relleneCampos").html($.i18n.prop('texto-relleneCampos'));
            $(".avisos-activar-cuerpo-1").text($.i18n.prop('avisos-activar-cuerpo-1'));
            $(".avisos-activar-cuerpo-1-activar").text($.i18n.prop('avisos-activar-cuerpo-1-activar'));

            $(".lng-avisos-desactivar-cuerpo-1").html($.i18n.prop('avisos-desactivar-cuerpo-1'));
            $(".lng-boton-aviso-desactivar").text($.i18n.prop('boton-aviso-desactivar'));
        });
    };

    var cargaIframe = function (hayCabecera) {
        //Cargamos dinámicamente la altura del iframe, ya que existe un problema de cross domain

        var alturaPage = $(document).outerHeight();

        var alturaCabeceraPage = hayCabecera ? $("#headerAvisos").outerHeight() : 0;

        var alturaCabeceraContent = hayCabecera ? $("#headerContent").outerHeight() : 0;

        var alturaIframe = alturaPage - alturaCabeceraPage - alturaCabeceraContent;

        $("#iframeCredenciales").height(alturaIframe);
    };

    var mostrarMensaje = function (index) {
        alerta.avisoTitulo('Aviso: ' + push.lstPush[index].fecha, push.lstPush[index].msg, function () {
            actualizarLeido(index)
        });
    };

    var actualizarLeido = function (index) {
        var id = push.lstPush[index].id;
        push.actualizarPush(id, true);
        cargarXpull();
    };

    var actualizarTextoEliminados = function () {
        if (mensajesSeleccionados > 0) {
            $(".lng-avisos-checkbox").text($.i18n.prop('avisos-eliminar') + "(" + mensajesSeleccionados + ")");
            $(".lng-avisos-checkbox").addClass("subrayadoPushEliminar");
        } else {
            $(".lng-avisos-checkbox").text($.i18n.prop('avisos-eliminar'));
            $(".lng-avisos-checkbox").removeClass("subrayadoPushEliminar");
        }
    };

    var isAvisosActivo = function () {

        var userList = userHelper.getUsers();

        if (!utils.esNulo(userList) && userList.length > 0) {
            for (var i = 0; i < userList.length; i++) {
                if (userList[i].pushActivo) {
                    return true;
                }
            }
        }
        return false;
    };

    var crearObjetoInyectar = function () {
        var objeto = {};

        //Si estan los avisos activos para algun usuario y no le hemos dado a "alta nuevo usuario"
        if (isAvisosActivo() && !altaNuevoUsuario) {
            objeto.esListaPush = true;
            objeto.hayMensajes = push.lstPush.length > 0;
            objeto.mensajes = sortMessages(push.lstPush);
            objeto.esActivateUsuario = false;
        } else {
            objeto.esListaPush = false;
            objeto.esActivateUsuario = true;
        }
        // console.log("crearObjetoInyectar     "+JSON.stringify())
        return objeto;
    };

    var refrescarListaMensajes = function () {

        var usuarioRegistrado = userHelper.getUsuarioRegistrado();

        var obj = crearObjetoInyectar();

        var logo = dataHelper.getLogoUrl();

        var promesa = utils.inyectarRefrescar("avisos.html", "#mensajePush", {
            usuarioRegistrado: usuarioRegistrado,
            obj: obj,
            logo: logo
        });

        promesa.done(iniciarEventosMensajes);
    };

    var inyectarRefrescarAltaNuevoUsuario = function (obj) {
        var usuarioRegistrado = userHelper.getUsuarioRegistrado();

        var obj = crearObjetoInyectar();

        altaNuevoUsuario = false;
        var logo = dataHelper.getLogoUrl();

        var promesa = utils.inyectarRefrescar("avisos.html", "#sectionAvisos", {
            usuarioRegistrado: usuarioRegistrado,
            obj: obj,
            logo: logo
        });

        promesa.done(inicializarPagina);
    };

    var cerrarPopup = function () {
        $("#popup-push-eliminar").popup().popup("close");

    };

    var eliminarMensaje = function () {
        mensajesSeleccionados = 0;
        actualizarTextoEliminados();
        utils.mostrarLoading();
        $("#mensajePush :checkbox").each(function () {
            if ($(this).is(":checked")) {
                var id = +this.id.toString().split("checkEliminar")[1];
                id = push.lstPush[id].id;
                push.deletePush(id);
            }
        });

        if ($("#checkEliminar").is(":checked")) {
            $("#checkEliminar").prop("checked", false);
            $(".lng-avisos-checkbox").removeClass("subrayadoPushEliminar");
        }
    };

    var abrirPopupEliminar = function () {
        if (mensajesSeleccionados === 1) {
            $("#eliminarUno").show();
            $("#eliminarVarios").hide();
            $("#popup-push-eliminar").popup().popup("open");
        } else if (mensajesSeleccionados > 1) {
            $("#eliminarUno").hide();
            $("#eliminarVarios").show();
            $("#popup-push-eliminar").popup().popup("open");
        }

    };

    var seleccionarTodos = function () {
        var numMensajesPush = push.lstPush.length;
        var i;
        var nuevoEstado = $("#checkEliminar").is(':checked');
        mensajesSeleccionados = 0;

        for (i = 0; i < numMensajesPush; i++) {
            $("#checkEliminar" + i).prop('checked', nuevoEstado);
            if (nuevoEstado) {
                $("#checkEliminar" + i).parent().parent().addClass("fondoMensajePushSeleccionado");
            } else {
                $("#checkEliminar" + i).parent().parent().removeClass("fondoMensajePushSeleccionado");
            }
        }

        if (nuevoEstado) {
            mensajesSeleccionados = numMensajesPush;
        } else {
            mensajesSeleccionados = 0;
        }

        actualizarTextoEliminados();
    };

    var seleccionarMensaje = function (checked, padre) {

        if (checked) {
            padre.addClass("fondoMensajePushSeleccionado");
            mensajesSeleccionados++;
            if (mensajesSeleccionados === push.lstPush.length) {
                $("#checkEliminar").prop("checked", true);
            }
        } else {
            padre.removeClass("fondoMensajePushSeleccionado");
            mensajesSeleccionados -= 1;
            if ($("#checkEliminar").is(":checked")) {
                $("#checkEliminar").prop("checked", false);
            }
        }

        actualizarTextoEliminados();
    };

    var obtenerUsuarioSeleccionado = function () {
        var usuario = $("#divActivarUsuario span.selectUsuario").text();
        var lista = userHelper.getUsers();

        for (var i = 0; i < lista.length; i++) {
            if (lista[i].usuario === usuario) {
                return lista[i];
            }
        }
    };

    var cargarSelect = function () {
        var i;
        for (i = 0; i < numUsuariosRegistrado; i++) {
            $('#selectUsuario').append(new Option(listUsers[i].usuario, listUsers[i].usuario, false, false));
            if (i === 0) {
                $("span.selectUsuario").text(listUsers[i].usuario);
            }
        }
    };

    var registrarUsuario = function (data) {
        numUsuariosRegistrado++;
        listUsers.push(data);
    };

    var ocultarMostrar = function (ocultar, mostrar) {
        if (numUsuariosRegistrado === 1) {
            $(".lng-label-aviso-seleccioneUsuario").text($.i18n.prop('registroUsuario-unUsuario'));
        } else if (numUsuariosRegistrado > 1) {
            $(".lng-label-aviso-seleccioneUsuario").text($.i18n.prop('registroUsuario-seleccioneUsuario'));
        }

        $(ocultar).hide();
        $(mostrar).show();
    };

    var addOption = function () {
        $('#selectUsuario').append(new Option(user, user, false, false));
        if (numUsuariosRegistrado === 1) {
            $("span.selectUsuario").text(user);
        }
    };

    var setUsuario = function (user) {
        $("#recuadroUsuario").text(user);
    };

    var addClassRedFocus = function (elemento) {
        var numChildren;
        if (elemento === "#errorAvisosUsuario") {
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
            inputError("#errorAvisosUsuario", 'login-user-vacio');
            inputError("#errorAvisosDNI", 'login-dni-vacio');
            return false;
        } else if (utils.esVacio(user)) {
            inputError("#errorAvisosUsuario", 'login-user-vacio');
            return false;
        } else if (utils.esVacio(dni)) {
            inputError("#errorAvisosDNI", 'login-dni-vacio');
            return false;
        } else {
            //Comprobamos que los 6 ultimos dígitos del campo usuario son números
            var ultimos6 = user.substr(user.length - 6);
            if (!utils.esNumerico(ultimos6)) {
                inputError("#errorAvisosUsuario", 'login-dni-numerico');
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
        utils.limpiarInput("#inputAvisoUsuario");
        utils.limpiarInput("#inputAvisoDNI");
        utils.ocultarClearBtn();
        $("#btnAvisoNuevoUsuario").removeClass("btnVerde");
        $("#btnAvisoNuevoUsuario").addClass("btnGris");
    };

    var anadirUsuario = function () {
        $("#nuevoUsuario").toggle();
        $('html, body').animate({ scrollTop: $("#anadirUsuario").offset().top }, 1000);
    };

    var mostrarCabeceraApp = function () {
        $("#headerAvisos").show();
        $("#headerContent").show();
        $("#page-avisos").css("padding-top", pagePaddingTop);
    };

    var ocultarCabeceraApp = function () {
        $("#headerAvisos").hide();
        $("#headerContent").hide();
        $("#page-avisos").css("padding-top", 0);
        $("#pageAvisoCredencialesUsuario").removeClass("fijarCabecera");
    };

    var ocultarCabeceraCMS = function () {
        $('#iframeCredenciales').contents().find("div.ui-header").hide();
    };

    var esperarCargaIframe = function () {
        $('#iframeCredenciales').ready(function () {
            utils.mostrarLoading();
        }).load(function () {
            utils.quitarLoading();

            var contenido = $('#iframeCredenciales').contents();
            $('div#pageAvisoCredencialesUsuario').css('overflow', 'hidden');
            $('#sectionAvisos').css('overflow', 'hidden');
            $('#page-avisos').css('overflow', 'hidden');
            $('body').css('overflow', 'hidden');

            //Si estamos en selección de contrato, quitamos ya la cabecera
            // Si hemos metido mal la contraseña, también pagina_acceso
            if (contenido.find("#FORM_RVIA_0").length > 0 || contenido.find("#posicionGlobal").length > 0) {
                ocultarCabeceraApp();
                cargaIframe(false);
            }

            if (contenido.find("div.header a#headPowerButton").length > 0 && $("#headerAvisos").is(":visible")) {
                ocultarCabeceraApp();
                cargaIframe(false);
            }

            // Si hemos metido mal la contraseña, ocultamos la cabecera de la web y mostramos la de la app 
            if (contenido.find("#pagina_error").length > 0) {
                ocultarCabeceraCMS();
                mostrarCabeceraApp();
                cargaIframe(true);
            }

            //Si estamos en desconexión
            if (contenido.find("#textoPequenno").length > 0 || contenido.find("#header h1").text().indexOf("Desconexión") > -1) {
                checkActiveUsers()
                    .then(checkPushUsers)
                    .then(db.getLastMsg)
                    .then(reloadActivePushUser)
                    .then(chargeMessages)
                    .then(portal.mostrar);
            }

            //Si vamos a meter las credenciales, insertamos las cabeceras
            if (contenido.find("#accesoIphone").length > 0) {
                mostrarCabeceraApp();
                cargaIframe(true);
            }

            //Si se ha activado correctamente el servicio...
            if (contenido.find(".error_aplicacion").text().indexOf("El servicio de Avisos se ha") > -1) {
                //INICIO TRAMPEADO
                //Si pulsamos en finalizar y nos lleva a la desconexión, volver al portal
                contenido.find(".button").on("click", function () {
                    checkActiveUsers()
                        .then(checkPushUsers)
                        .then(db.getLastMsg)
                        .then(reloadActivePushUser)
                        .then(chargeMessages)
                        .then(portal.mostrar);
                });
                //FIN TRAMPEADO
            }
        });
    };

    var mostrarIframeAvisos = function (url, activar) {
        $("#iframeCredenciales").attr('src', url);
        if (activar) {
            ocultarMostrar("#pageAvisoPrevioActivar, p.lng-avisos-cuerpo-1, p.lng-avisos-cuerpo-2", "#pageAvisoCredencialesUsuario");
        } else {

            ocultarMostrar("#pageAvisoPrevioDesactivar", "#pageAvisoCredencialesUsuario");
        }

        esperarCargaIframe();
        pagePaddingTop = $("#page-avisos").css("padding-top");
    };

    var mostrarIframe = function (url, activar) {
        $("#iframeCredenciales").attr('src', url);
        if (activar) {
            ocultarMostrar("#pageAvisoPrevioActivar", "#pageAvisoCredencialesUsuario");
        } else {
            ocultarMostrar("#mensajePush, #footerBotones, #checkEliminar, .lng-avisos-checkbox", "#pageAvisoCredencialesUsuario");
        }

        esperarCargaIframe();
        pagePaddingTop = $("#page-avisos").css("padding-top");
    };

    var crearUrlActivar = function (accessUrl, accessDes, googleUA, activar, usuarioCifrado) {

        var plataforma = utils.getDevice();
        if (plataforma === "iOS") {
            plataforma = plataforma.toUpperCase();
        }

        var accion = activar ? "ACT" : "DST";

        var url = accessUrl + "&ISUM_ID_USUARIO=" + usuarioCifrado + "&ID_DISPOSITIVO_MOVIL=" + push.getRegID() + "&ISUM_UA=" + googleUA + "&avisos=1&ISUM_APP=1&accion=" + accion + "&S_OPERATIVO_MOVIL=" + plataforma;
        console.log("urlPush    "+url);
        return url;
    };

    var registrarApp = function (usuario, contrasena) {

        var newUser = {
            name: usuario,
        };

        jsonParam.idioma = "es";
        jsonParam.codigoEntidad = "0198";
        jsonParam.usu = usuario;

        jsonParam.nif = contrasena;
        jsonParam.idDis = localStorage.getItem("UUID"); //Chequear si está así en la app anterior

        var metodo = "EE_I_RegistroAPP";
        var datosSinCodificar = "?usuarioBE=" + jsonParam.usu + "&idExterno=" + jsonParam.nif + "&idDispositivo=" + jsonParam.idDis;
        var url = config.servidor + config.Registro + metodo + datosSinCodificar;

        utils.mostrarLoading();

        $.ajax({

            url: url,
            dataType: 'json',
            contentType: 'application/json',
            type: "GET",
            headers: config.headers,
            success: function (data, response) {
                console.log("dataSuccessAvisos: " + JSON.stringify(data));
                if (localStorage.getItem("list_users") === null) {
                    localStorage.setItem("usuarioActual", JSON.stringify(newUser));
                    registroUsuario.numUsuariosRegistrado++;
                    registroUsuario.cbRegistrarApp(data);
                } else {
                    if (localStorage.getItem("list_users").indexOf(usuario) !== -1) {
                        cbRegistrarApp(data);
                    } else {

                        localStorage.setItem("usuarioActual", JSON.stringify(newUser));
                        registroUsuario.numUsuariosRegistrado++;
                        registroUsuario.cbRegistrarApp(data);
                    }
                }
                consumirServicioRegistrarPush(clickAblePush);
            },
            error: function (response) {
                console.log("reponseError: " + JSON.stringify(response));
                registroUsuario.cbErrorRegistrarApp(response);
            }

        });
    };

    var consumirServicioRegistrarPush = function (activarDesactivar) {

        var urlAcceso = JSON.parse(localStorage.getItem("active_app")).Respuesta.Acceso.urlAcceso;
        var usuarioCifrado, googleUA;

        if (localStorage.getItem("active_user") === null || localStorage.getItem("active_user") === "null") {
            registrarApp(us, contr);

            usuarioCifrado = JSON.parse(localStorage.getItem("active_user")).campoCifrado;
            // var googleUA = JSON.parse(localStorage.getItem("active_app")).Respuesta.Estadisticas.codigoAnalytics;
            googleUA = "929292";
        } else {
            usuarioCifrado = JSON.parse(localStorage.getItem("active_user")).campoCifrado;
            var accessDes = "";
            // var googleUA = JSON.parse(localStorage.getItem("active_app")).Respuesta.Estadisticas.codigoAnalytics;
            googleUA = "929292";
        }

        var urlRegistrarPush = crearUrlActivar(urlAcceso, accessDes, googleUA, activarDesactivar, usuarioCifrado);

        console.log("urlRegistrarPushCONSUMIR: " + urlRegistrarPush);
        $("#popup-push-altaNuevoUsuario").popup().popup("close");
        $("#popup-push-bajaUsuario").popup().popup("close");
        $("#mensajePush").hide();

        $("#footerBotones").hide();
        $("#headerContent .derecha").hide();

        mostrarIframeAvisos(urlRegistrarPush, activarDesactivar);


    };

    var cambiarURLPirata = function (url) {
        url = url.replace("ISUM_Portal=84", "ISUM_Portal=104");
        return url;
    };

    var cbAppMovil = function (res, activar) {

        utils.quitarLoading();
        dataHelper.saveActiveApp(res.EE_O_AplicacionMovil);
        var usuarioSeleccionado = obtenerUsuarioSeleccionado();
        userHelper.saveActiveUser(usuarioSeleccionado);
    };

    var cbErrorAppMovil = function () {
        utils.quitarLoading();
        alerta.aviso($.i18n.prop('errorGenerico'));
    };

    //True para activar
    //False para desactivar
    var activarDesactivar = function (activar) {

        var usuarioSeleccionado;
        var url;

        if (localStorage.getItem("active_app") === "undefined") {
            url = localStorage.getItem("active_app2");
        } else {
            url = localStorage.getItem("active_app");
        }

        if (activar) {
            usuarioSeleccionado = obtenerUsuarioSeleccionado();
        } else {
            usuarioSeleccionado = obtenerUsuarioDesactivar();
        }

        // usuarioSeleccionado.pushActivo = activar;

        // userHelper.updateUser(usuarioSeleccionado.usuario, usuarioSeleccionado);

        var urlAcceso = JSON.parse(url).Respuesta.Acceso.urlAcceso;
        var usuarioCifrado = usuarioSeleccionado.campoCifrado;
        var googleUA = "929292", accessDes = "";
        var urlRegistrarPush = crearUrlActivar(urlAcceso, accessDes, googleUA, activar, usuarioCifrado);

        mostrarIframeAvisos(encodeURI(urlRegistrarPush), activar);
    };

    var obtenerUsuarioDesactivar = function () {
        var usuario = $("#divDesactivarUsuario span.selectUsuario").text();
        var lista = userHelper.getUsers();

        for (var i = 0; i < lista.length; i++) {
            if (lista[i].usuario === usuario) {
                return lista[i];
            }
        }
    }

    var existeUsuario = function (usuario) {
        var i;
        var listaUsuariosTotal = userHelper.getUsers();
        for (i = 0; i < listaUsuariosTotal.length; i++) {
            if (listaUsuariosTotal[i].usuario === usuario.usuario) {
                return true;
            }
        }
        return false;
    };

    var userRegister = function (user, dni) {
        var defer = $.Deferred();

        var newUser = {
            name: user,
            dni: dni
        };

        var url = crearUrlRegistro(user, dni);

        utils.mostrarLoading();

        $.ajax({
            url: url,
            dataType: 'json',
            contentType: 'application/json',
            type: "GET",
            headers: config.headers,
            success: function (data) {
                defer.resolve(data, newUser);
            },
            error: function (response) {
                defer.reject(response);
            }
        });

        return defer;
    };

    var checkUsuario = function (data, newUser) {
        utils.quitarLoading();
        var promise = $.Deferred();

        var cr = data.EE_O_RegistroApp.codigoRetorno;

        if (cr === "1") {
            if (localStorage.getItem("list_users") === null) {
                localStorage.setItem("usuarioActual", JSON.stringify(newUser));
                promise.resolve(data, newUser);
            } else {
                if (localStorage.getItem("list_users").indexOf(newUser.name) !== -1) {
                    promise.resolve(data, newUser);
                } else {
                    localStorage.setItem("usuarioActual", JSON.stringify(newUser));
                    promise.resolve(data, newUser);
                }
            }
        } else {
            newUser.CR = cr;
            newUser.MR = undefined;
            promise.reject(cr, newUser);
        }

        return promise;
    };

    var saveNewUser = function (data, user) {

        var promise = $.Deferred();

        var nuevoUsuario = {
            campoCifrado: data.EE_O_RegistroApp.Respuesta.codigoLogin,
            campoPersona: data.EE_O_RegistroApp.Respuesta.codigoPersona,
            CR: data.EE_O_RegistroApp.codigoRetorno,
            Entidad: data.EE_O_RegistroApp.Respuesta.codigoEntidad,
            pushActivo: false
        };

        if (!utils.esNulo(user.name)) {
            if (!utils.esNumerico(user.name)) {
                nuevoUsuario.usuario = user.name.toUpperCase();
            } else {
                nuevoUsuario.usuario = user.name;
            }
        } else {
            nuevoUsuario.usuario = user.name;
        }

        JSONData.setReg(nuevoUsuario);
        userHelper.setUser(nuevoUsuario);
        userHelper.saveActiveUser(nuevoUsuario);
        userHelper.setLoginActivityPass(true);
        numUsuariosRegistrado++;
        registroUsuario.registrarUsuario(nuevoUsuario);
        registroUsuario.addOption(nuevoUsuario.usuario);
        promise.resolve(nuevoUsuario);

        return promise;
    };

    var errorSaveUser = function (cr, usuario) {
        if (cr === "40002") {
            alerta.aviso($.i18n.prop('login-40002'));
        } else if (cr === "1500") {
            alerta.aviso(usuario.MR);
        } else {
            alerta.aviso($.i18n.prop('login-error-1') + ' ' + usuario.CR + ' ' + $.i18n.prop('login-error-2') + ' ' + usuario.MR);
        }
    };

    var crearUrlRegistro = function (user, dni) {

        jsonParam.idioma = "es";
        jsonParam.codigoEntidad = "0198";
        jsonParam.usu = user;
        jsonParam.nif = dni;
        jsonParam.idDis = localStorage.getItem("UUID"); //Chequear si está así en la app anterior

        var metodo = "EE_I_RegistroAPP";
        var datosSinCodificar = "?usuarioBE=" + jsonParam.usu + "&idExterno=" + jsonParam.nif + "&idDispositivo=" + jsonParam.idDis;
        var url = config.servidor + config.Registro + metodo + datosSinCodificar;

        return url;
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
                // irAPortal();

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
        alerta.aviso($.i18n.prop('login-error-registro'));
    };

    var nuevoUsuario = function () {

        user = $.trim($("#inputAvisoUsuario").val());
        dni = $.trim($("#inputAvisoDNI").val());

        if (inputsCorrectos()) {
            var ultimos6 = user.substr(user.length - 6);

            if (!utils.esNumerico(ultimos6)) {
                inputError("#errorAvisosUsuario", 'login-dni-numerico');
            } else {
                if (dni.length < 9) {
                    dni = utils.padCeros(dni, 9);
                }
                clickAblePush = true;
                userRegister(user, dni)
                    .fail(registroUsuario.cbErrorRegistrarApp)
                    .then(checkUsuario)
                    .fail(errorSaveUser)
                    .then(saveNewUser)
                    .then(showActive);

                limpiarInputs();
            }
        }
    };

    var altaPushNuevoUsuario = function () {
        user = $.trim($("#inputPushAltaUsuario").val());
        dni = $.trim($("#inputPushAltaDNI").val());

        if (inputsCorrectos()) {
            //Comprobamos que los 6 ultimos dígitos del campo usuario son números
            var ultimos6 = user.substr(user.length - 6);

            if (!utils.esNumerico(ultimos6)) {
                inputError("#errorAvisosUsuario", 'login-dni-numerico');
            } else {
                if (dni.length < 9) {
                    dni = utils.padCeros(dni, 9);
                }
                clickAblePush = true;
                limpiarInputs();
                userRegister(user, dni)
                    .fail(registroUsuario.cbErrorRegistrarApp)
                    .then(checkUsuario)
                    .fail(errorSaveUser)
                    .then(saveNewUser)
                    .then(hidePopUpShowActive);
            }
        }
    };

    var showActive = function (user) {
        showActivationPage();
        registroUsuario.checkActivationTemplate(user.usuario);
    };

    var hidePopUpShowActive = function (user) {
        $("#popup-push-altaNuevoUsuario").popup().popup("close");
        showActivationPage();
        registroUsuario.checkActivationTemplate(user.usuario);
    };

    var showDesactive = function () {
        ocultarMostrar("#pageMensajesPushActivados, div.derecha, #mensajePush, #footerBotones, #nuevoUsuario",
            "#pageAvisoPrevioDesactivar, p.lng-avisos-desactivar-cuerpo-1, #divDesactivarUsuario, #divDesactivarUsuario div.ui-select");
        fillDesactiveSelect();
    }

    var fillDesactiveSelect = function () {
        var actives = getActiveUsers();
        $("#pageAvisoPrevioDesactivar #divDesactivarUsuario #selectDesactivarUsuario option").remove();
        actives.forEach(function (element) {
            addDesactiveOption(element);
        });

        if (actives.length > 0) {
            $("#divDesactivarUsuario span.selectUsuario").text(actives[0].usuario);
        }
    }

    var addDesactiveOption = function (user) {
        var option = user.pushActivo ? user.usuario + " (" + $.i18n.prop('registroUsuario-AvisosActivos') + ")" : user.usuario;
        var select = $("#selectDesactivarUsuario");
        var firstOption = $('#selectDesactivarUsuario option:first');
        $('<option>').val(user.usuario).text(user.usuario).appendTo(select);
        firstOption.prop('selected', true);
    }

    var getActiveUsers = function () {
        var activeUsers = [];

        var users = userHelper.getUsers();

        users.forEach(function (element) {
            if (element.pushActivo) {
                activeUsers.push(element);
            }
        });



        return activeUsers;
    }

    var bajaPushUsuario = function () {
        user = $.trim($("#inputPushBajaUsuario").val());
        dni = $.trim($("#inputPushBajaDNI").val());

        if (inputsCorrectos()) {
            //Comprobamos que los 6 ultimos dígitos del campo usuario son números
            var ultimos6 = user.substr(user.length - 6);

            if (!utils.esNumerico(ultimos6)) {
                inputError("#errorAvisosUsuario", 'login-dni-numerico');
            } else {
                if (dni.length < 9) {
                    dni = utils.padCeros(dni, 9);
                }
                clickAblePush = false;
                limpiarInputs();
                registrarApp(user, dni);
            }
        }
    };

    var inyectar = function (obj) {
        var usuarioRegistrado = userHelper.getUsuarioRegistrado();
        var logo = dataHelper.getLogoUrl();
        console.log("obj.inyectar::::::  "+JSON.stringify(obj));
        var promesa = utils.inyectar("avisos.html", "#page-avisos", {
            usuarioRegistrado: usuarioRegistrado,
            obj: obj,
            logo: logo
        });

        return promesa;
    };

    var comprobarPushActivoUsuario = function () {

        if (!utils.esNulo(JSONData.getPush().getResultado()) && JSONData.getPush().getResultado() === 'ok') {
            var list = userHelper.getUsers();
            var len = list.length;

            for (var i = 0; i < len; i++) {
                var oldUserId = list[i].usuario;
                if (!utils.esNulo(JSONData.getPush().getListaUsuariosActivos())) {
                    if (!utils.esNulo(list[i]) && !utils.esNulo(oldUserId)) {
                        //convertimos a Array, ya que en caso de 1 solo elemento, viene como objeto
                        var listaUsuariosActivos = utils.convertirArray(EE_O_Get.getListaUsuariosActivos());
                        var listaUsuarios = listaUsuariosActivos;

                        if (!utils.esNulo(listaUsuarios)) {
                            var lenListaUsuarios = listaUsuarios.length;

                            for (var j = 0; j < lenListaUsuarios; j++) {
                                var usuarioActivo = listaUsuarios[j];

                                if (!utils.esNulo(usuarioActivo) && !utils.esNulo(usuarioActivo.id_usuario_banca) && oldUserId === usuarioActivo.id_usuario_banca) {
                                    list[i].pushActivo = true;
                                }
                            }
                        }
                    }
                }

                userHelper.updateUser(oldUserId, list[i]);
            }
        }
    };

    var showPage = function () {

        utils.quitarLoading();
        var obj = crearObjetoInyectar();
        console.log("showPage obj:   "+JSON.stringify(obj));
        inyectar(obj).then(configuracionPagina);

        function configuracionPagina() {
            if (obj.esListaPush) {
                showMessagesPage();
            } else if (obj.esActivateUsuario) {
                showActivationPage();
            }

            ocultarMostrar("#pageAvisoPrevioDesactivar, p.lng-avisos-desactivar-cuerpo-1, #divDesactivarUsuario", "");
        }
    };

    var fillActiveSelect = function () {
        var desactives = getDesactiveUsers();
        $("#pageAvisoPrevioActivar #divActivarUsuario #selectUsuario option").remove();

        desactives.forEach(function (element) {
            addActiveOption(element);
        });

        if (desactives.length > 0) {
            $("#divActivarUsuario span.selectUsuario").text(desactives[0].usuario);
        }
    };

    var getDesactiveUsers = function () {
        var desactiveUsers = [];

        var users = userHelper.getUsers();

        users.forEach(function (element) {
            if (!element.pushActivo) {
                desactiveUsers.push(element);
            }
        });

        return desactiveUsers;
    };

    var addActiveOption = function (user) {
        var option = user.pushActivo ? user.usuario + " (" + $.i18n.prop('registroUsuario-AvisosActivos') + ")" : user.usuario;
        var select = $("#selectUsuario");
        var firstOption = $('#selectUsuario option:first');
        $('<option>').val(user.usuario).text(user.usuario).appendTo(select);
        firstOption.prop('selected', true);
    };

    var showActivationPage = function () {
        fijarCabeceraMensajes();
        $("p.lng-avisos-cuerpo-2").html($.i18n.prop('avisos-activar-cuerpo-1'));
        ocultarMostrar("#pageMensajesPushActivados, div.derecha, #mensajePush, #footerBotones, #nuevoUsuario, #recuadroUsuario",
            "#pageAvisoPrevioActivar, p.lng-avisos-cuerpo-1, p.lng-avisos-cuerpo-2, #divActivarUsuario, #divActivarUsuario label.lng-label-aviso-seleccioneUsuario, #divActivarUsuario a, #divActivarUsuario select, #divActivarUsuario div, #selectUsuario, #divActivarUsuario div.ui-select, .avisos-cuerpo-credenciales-1");
        fillActiveSelect();
    };

    var showMessagesPage = function () {
        fijarCabeceraMensajes();
        ocultarMostrar("#pageAvisoPrevioActivar, p.lng-avisos-cuerpo-2.textoAvisos2, p.lng-avisos-cuerpo-1, p.lng-avisos-cuerpo-2, #divActivarUsuario, #nuevoUsuario, #divActivarUsuario label, #divActivarUsuario a, #divActivarUsuario select, #divActivarUsuario div",
            "#pageMensajesPushActivados, #headerContent, div.derecha, #mensajePush, #footerBotones");
        if (paraActivarNuevoUsuario) {
            $("p.lng-avisos-cuerpo-2.textoAvisos2").hide();
        }
        paraActivarNuevoUsuario = false;
    };

    var showRegistrationPage = function () {
        fijarCabeceraMensajes();
        $("p.lng-avisos-cuerpo-2").html($.i18n.prop('avisos-cuerpo-2'));
        ocultarMostrar("#pageMensajesPushActivados, div.derecha, #mensajePush, #footerBotones, #divActivarUsuario",
            "#pageAvisoPrevioActivar, p.lng-avisos-cuerpo-1, p.lng-avisos-cuerpo-2, #nuevoUsuario");
    };

    var cbErrorPushGet = function () {
        utils.quitarLoading();
        console.log("Error push!!");
    };

    var reloadActivePushUser = function (fecha) {

        var activeUsers = getActiveUsers();
        var result = [];
        var promise = $.Deferred();

        if (activeUsers.length > 0) {
            activeUsers.map(function (user) {
                getAllMessages(user).then(success)
            });
            return promise;
        } else {
            return promise.resolve();
        };

        function success(response) {
            result.push(response);
            if (result.length === activeUsers.length) {
                promise.resolve(result);
            }
        };

        function getMessages(url) {
            var promise = $.Deferred();
            $.ajax({
                url: encodeURI(url),
                dataType: 'json',
                contentType: 'application/json',
                headers: {
                    Accept: 'application/json; charset=utf-8'
                },
                type: "GET",

                success: function (data, response) {
                    // console.log("dataSuccess: " + JSON.stringify(data));
                    // console.log("Datos.fecha   " + JSON.stringify(data.EE_O_Get.Datos.length));
                    promise.resolve(data.EE_O_Get.Datos);
                },
                error: function (response) {
                    console.log("responseError: " + JSON.stringify(response));
                    promise.reject(response);
                }
            });

            return promise;
        };

        function getAllMessages(user) {
            var regId = localStorage.getItem("RegID") === null ? undefined : localStorage.getItem("RegID").replace(":", "%3A");

            var jsonParam = {};
            jsonParam.id_dispositivo = push.getRegID() ? push.getRegID().replace(":", "%3A") : regId;
            jsonParam.fecha = fecha && fecha.rows.length > 2 ? formarDate(fecha.rows.item(1).fecha) : "";
            jsonParam.id_usuario_banca = user.usuario;

            var metodo = "/EE_I_Get";
            var datosSinCodificar = { "EE_I_Get": jsonParam };
            var url = config.servidor + config.Mensajes + metodo + "?data=" + JSON.stringify(datosSinCodificar);

            console.log("urlPush::::" + url);

            return getMessages(url);
        };

        function formarDate(date) {
            var auxDate = date.split("/");
            var hour = auxDate[2].split(" ");
            var orderDate = [hour[0], auxDate[1], auxDate[0]];
            orderDate = orderDate.join("-");
            orderDate = orderDate + "T" + hour[1];
            return orderDate;
        };

    };

    var sortMessages = function (lista) {
        var swapped;

            do {
                swapped = false;
                for (var i=0; i < lista.length-1; i++) {
                    if (parseDate(lista[i].fecha) < parseDate(lista[i+1].fecha)) {
                        var temp = lista[i];
                        lista[i] = lista[i+1];
                        lista[i+1] = temp;
                        swapped = true;
                    }
                }
            } while (swapped);
        
         return lista
       
    };

    var parseDate = function (date) {
            var auxDate = date.split("/");
            var hour = auxDate[2].split(" ");
            var aurxHour = hour[1].split(":");
            var orderDate = [hour[0], auxDate[1], auxDate[0], aurxHour[0], aurxHour[1], aurxHour[2]];
            orderDate = orderDate.join("");
            return parseInt(orderDate, 10);
    };

    var chargeMessages = function (responses) {
        if (responses && responses.length > 0) {
            return putNewMessages(responses);
        } else {
            return $.Deferred().resolve();
        }
    };

    var putNewMessages = function (arrayResponses) {
        var promise = $.Deferred();
        var i = 0;
        var receive = 0;

        $.each(arrayResponses, function (index, elem) {
            if (elem && $.isArray(elem)) {
                elem.forEach(insertarMessage);
            } else if (elem) {
                insertarMessage(elem);
            }

            i++;
            if (i === arrayResponses.length) {
                /*if (receive > 1) {
                    var msg = receive - 1 > 1 ? "Tiene %n% mensajes nuevos" : "Tiene un mensaje nuevo";
                    alerta.avisoTitulo("Notificaciones", msg.replace("%n%", receive - 1), promise.resolve);
                } else {*/
                promise.resolve();
                //}
            }
        });

        return promise;

        function insertarMessage(obj) {
            var mensaje = {
                id: obj.id_mensaje,
                msg: obj.mensaje,
                fecha: reverseFecha(obj.fecha)
            };

            receive++;
            db.insertar(mensaje.msg, mensaje, 0, false, true);
        };

        function reverseFecha(fecha) {
            var auxDate = fecha.split("-");
            var hour = auxDate[2].split("T");
            var orderDate = [hour[0], auxDate[1], auxDate[0]];
            orderDate = orderDate.join("/");
            orderDate = orderDate + " " + hour[1];
            return orderDate;
        };
    }

    var switchUserSelector = function () {

        var usersArray = userHelper.getUsers();

        listUsers = [];
        numUsuariosRegistrado = 0;

        if (!utils.esNulo(usersArray) && usersArray.length > 0) {
            var i;

            for (i = 0; i < usersArray.length; i++) {
                var user = usersArray[i];

                if (!utils.esNulo(user) && !user.pushActivo) {

                    listUsers.push(user);
                    numUsuariosRegistrado++;
                }
            }
        }
    };

    var checkActiveUsers = function () {
        utils.mostrarLoading();

        var promise = $.Deferred();
        var jsonParam = {};

        var regId = localStorage.getItem("RegID") === null ? undefined : localStorage.getItem("RegID").replace(":", "%3A");
        jsonParam.id_dispositivo = push.getRegID() ? push.getRegID().replace(":", "%3A") : regId;



        var metodo = "/EE_I_Get";
        var datosSinCodificar = { "EE_I_Get": jsonParam };

        var url = config.servidor + config.Push + metodo + "?data=" + JSON.stringify(datosSinCodificar);

        var get = $.ajax({

            url: encodeURI(url),
            dataType: 'json',
            contentType: 'application/json',
            headers: {
                Accept: 'application/json; charset=utf-8'
            },
            type: "GET",

            success: function (data, response) {
                utils.quitarLoading();
                promise.resolve(data, response);
            },
            error: function (response) {
                promise.reject();
            }
        });

        return promise;
    };

    var checkPushUsers = function (data) {
        var activeUsers = data.EE_O_Get.ListaUsuarioDispositivo;

        desactiveEveryUser();
        if (activeUsers) {
            $.each(activeUsers, function (index, value) {
                updatePushUsers(value);
            });
        }

        return $.Deferred().resolve();
    };

    var updatePushUsers = function (user) {
        var users = userHelper.getUsers();

        users.forEach(function (elem) {
            if (elem.usuario === user || elem.usuario === user.id_usuario_banca) {
                elem.pushActivo = true;
                userHelper.updateUser(elem.usuario, elem);
                rvDataDump.activeNotification();
            }
        });
    };

    var desactiveEveryUser = function () {
        var users = userHelper.getUsers();

        users.forEach(function (elem) {
            elem.pushActivo = false;
            userHelper.updateUser(elem.usuario, elem);
        });
    };

    var eventosInput = function () {
        $('#inputAvisoUsuario, #inputAvisoDNI').on('input', function () {
            utils.comprobarInput(this);

            var lenUsuario = $("#inputAvisoUsuario").val().length;
            var lenDNI = $("#inputAvisoDNI").val().length;

            if (lenUsuario > 0 && lenDNI > 0) {
                $("#btnAvisoNuevoUsuario").removeClass("btnGris");
                $("#btnAvisoNuevoUsuario").addClass("btnVerde");
            } else if (lenUsuario === 0 || lenDNI === 0) {
                $("#btnAvisoNuevoUsuario").removeClass("btnVerde");
                $("#btnAvisoNuevoUsuario").addClass("btnGris");
            }
        });

        $('#inputAvisoUsuario, #inputAvisoDNI').on('focus', function () {
            if (this.id === "inputAvisoUsuario") {
                if ($("#errorAvisosUsuario").is(':visible')) {
                    $("#errorAvisosUsuario").hide();
                }
            } else {
                if ($("#errorAvisosDNI").is(':visible')) {
                    $("#errorAvisosDNI").hide();
                }
            }

            $(this).parent().removeClass('border-red-focus');
        });

        $(".ui-icon-delete").on("click", function () {
            $(this).siblings().val("");
            $(this).css("display", "none");
            $(this).parent().removeClass('border-red-focus');
            $("#errorAvisosUsuario").hide();
            $("#errorAvisosDNI").hide();
            $("#btnAvisoNuevoUsuario").removeClass("btnVerde");
            $("#btnAvisoNuevoUsuario").addClass("btnGris");
        });
    };

    var inicioOcultarMostrar = function () {
        var users = getDesactiveUsers();
        if (numUsuariosRegistrado === 0 && !isAvisosActivo()) {
            showRegistrationPage();
        } else if (numUsuariosRegistrado === 1 && !isAvisosActivo()) {
            setUsuario(users[0].usuario);
            showActive(users[0].usuario);
        } else if (!isAvisosActivo()) {
            showActive(users[0].usuario)
        } else {
            paraActivarNuevoUsuario = true;
            ocultarMostrar("#pageAvisoCredencialesUsuario, .lng-avisos-cuerpo-2.textoAvisos2, .lng-avisos-cuerpo-2.textoAvisos, .avisos-negrita-activar", "");
            mostrarCabeceraApp();
            showMessagesPage();
        }

        ocultarMostrar("#pageAvisoCredencialesUsuario", "");

        //Se deshabilita/habilita el checkbox de eliminar en base a que haya o no mensajes
        //Cargamos también la imagen de no avisos
        if (push.lstPush.length === 0 || !isAvisosActivo()) {
            $("#checkEliminar").attr('disabled', true);
            $("#imgNoMensajePush").attr('src', 'https://www.ruralvia.com/estilos_prtl/css/html5/img/error.png');
        } else {
            $("#checkEliminar").removeAttr('disabled');
        }
    };

    var getNumUsuariosActivos = function () {
        var usuarios = userHelper.getUsers();
        var i = 0;

        usuarios.forEach(function (value) {
            if (value.pushActivo) {
                i++;
            }
        });

        return i;
    };

    var getNumUsuariosRegistrados = function () {
        var usuarios = userHelper.getUsers();
        var i = 0;

        usuarios.forEach(function (value) {
            i++;
        });

        return i;
    };

    var backAvisos = function (atras) {
        if ($("#sectionAvisos").find("#pageMensajesPushActivados").is(':visible')) {
            checkActiveUsers()
                .then(checkPushUsers)
                .then(portal.mostrar);
        } else {
            if ($("#sectionAvisos").find("#pageAvisoPrevioActivar").is(':visible')) {
                if (getNumUsuariosActivos() === 0) {
                    checkActiveUsers()
                        .then(checkPushUsers)
                        .then(portal.mostrar);
                } else {
                    showMessagesPage();
                }

            } else if ($("#sectionAvisos").find("#pageAvisoPrevioActivar").is(':visible')) {
                checkActiveUsers()
                    .then(checkPushUsers)
                    .then(portal.mostrar);
            } else if ($("#sectionAvisos").find("#pageAvisoPrevioDesactivar").is(':visible')) {
                showMessagesPage();
                ocultarMostrar("#pageAvisoPrevioDesactivar", "");
            } else if ($("#sectionAvisos").find("#iframeCredenciales").is(':visible')) {
                checkActiveUsers()
                    .then(checkPushUsers)
                    .then(portal.mostrar);

            } else {
                checkActiveUsers()
                    .then(checkPushUsers)
                    .then(portal.mostrar);
            }
        }

        $('#sectionAvisos').css('overflow', 'auto');
        $('#page-avisos').css('overflow', 'auto');
        $('body').css('overflow', 'auto');
    };

    var inicializarPagina = function () {
        cargarCadenas();
        switchUserSelector();
        utils.ocultarClearBtn();
        mensajesSeleccionados = 0;
        inicioOcultarMostrar();

        $("#selectUsuario").bind("change", function (event, ui) {
            var valor = $("#selectUsuario").val();
            $("#divActivarUsuario span.selectUsuario").text(valor);
        });

        $("#selectDesactivarUsuario").bind("change", function (event, ui) {
            var valor = $("#selectDesactivarUsuario").val();
            $("#divDesactivarUsuario span.selectUsuario").text(valor);
        });

        $("#btnAvisoNuevoUsuario").on("click", nuevoUsuario);
        $("#btnAvisoActivar").on("click", function () {
            activarDesactivar(true);
            ocultarMostrar("", "#iframeCredenciales");
        });

        $("#btnAvisoDesactivar").on("click", function () {
            activarDesactivar(false);
            ocultarMostrar("", "#iframeCredenciales");
        });

        $("#anadirUsuario").on("click", anadirUsuario);
        $("#checkEliminar").change(seleccionarTodos);

        $(".lng-avisos-checkbox").on("click", abrirPopupEliminar);
        $("#btnPushEliminarAceptar").on("click", eliminarMensaje);
        $("#btnPushEliminarCancelar").on("click", cerrarPopup);

        iniciarEventosMensajes();

        $("#altaNuevoUsuarioButton").on("click", function () {
            clickAblePush = true;
            var numUsuariosActivos = getNumUsuariosActivos();
            numUsuariosRegistrado = getNumUsuariosRegistrados();

            if (numUsuariosRegistrado > numUsuariosActivos) {
                showActivationPage();
            } else {
                showRegistrationPage();
            }
        });

        $("#btnDesactivar").on("click", showDesactive);

        $("#lblBuscador").on("click", function () {
            $("#nuevoUsuario").addClass("anadirPadding");
        });

        $("#btnPushNuevoUsuarioAceptar").on("click", altaPushNuevoUsuario);
        $("#btnPushNuevoUsuarioCancelar").on("click", function () {
            $("#popup-push-altaNuevoUsuario").popup().popup("close");
        });

        $("#btnPushBajaUsuarioAceptar").on("click", bajaPushUsuario);
        $("#btnPushBajaUsuarioCancelar").on("click", function () {
            $("#popup-push-bajaUsuario").popup().popup("close");
        });

        $("#atras-avisos").on("click", backAvisos);

        eventosInput();

        cargaIframe(true);

        cargarXpull();
    };

    var iniciarElementos = function () {
        cargarCadenas();
    };

    var iniciarEventos = function () {

    };

    var iniciarEventosMensajes = function () {
        $("#mensajePush .msgPush").each(function (index) {
            $(this).on("click", function () {
                mostrarMensaje(index);
            });
        });

        $("#mensajePush .fechaPush").each(function (index) {
            $(this).on("click", function () {
                mostrarMensaje(index);
            });
        });

        $("#mensajePush .irPush").each(function (index) {
            $(this).on("click", function () {
                mostrarMensaje(index);
            });
        });

        $("#mensajePush :checkbox").change(function () {
            seleccionarMensaje($(this).is(":checked"), $(this).parent().parent());
        });
    };

    var cargarXpull = function () {
        $('#xpull').xpull({
            'pullThreshold': 100,
            'callback': function () {
                $('#xpull').data('plugin_xpull').options.paused = true;
                db.getLastMsg()
                    .then(reloadActivePushUser)
                    .then(chargeMessages)
                    .then(function () {
                        $('#xpull').data('plugin_xpull').options.paused = false;
                        refrescarListaMensajes();
                    });
            }
        });
    };

    var mostrarIframe = function () {
        var usuarioRegistrado = userHelper.getUsuarioRegistrado();
        var esApple = true;

        if (!config.simuleTerminal) {
            esApple = (utils.getDevice() === "iOS");
            //screen.unlockOrientation();
        }
        var logo = dataHelper.getLogoUrl();
        utils.inyectar("accesoClientes.html", "#page-accesoClientes", {
            usuarioRegistrado: usuarioRegistrado,
            esApple: esApple,
            logo: logo
        });
    };

    var fijarCabeceraMensajes = function () {
        if ($("#pageMensajesPushActivados").is(":visible")) {
            $("#headerContent").addClass("fijarCabecera");
            $("#pageMensajesPushActivados").addClass("fijarCabecera");
        } else {
            $("#pageAvisoPrevioDesactivar").addClass("fijarCabecera");
            $("#pageAvisoPrevioActivar").addClass("fijarCabecera");
            $("#pageAvisoCredencialesUsuario").addClass("fijarCabecera");
        }
    };

    //////////////////////////////////////////////
    // métodos públicos
    //////////////////////////////////////////////
    var mostrar = function () {
        checkActiveUsers()
            .then(checkPushUsers)
            .then(db.getLastMsg)
            .then(reloadActivePushUser)
            .then(chargeMessages)
            .then(showPage)
            .fail(cbErrorPushGet);
    };

    var inicializar = function () {
        $(document)
            .one('pageinit', '#page-avisos', iniciarEventos)
            .on("pageinit", "#page-avisos", iniciarElementos)
            .on("pageshow", "#page-avisos", inicializarPagina);
    };

    return {
        cerrarPopup: cerrarPopup,
        refrescarListaMensajes: refrescarListaMensajes,
        isAvisosActivo: isAvisosActivo,
        comprobarPushActivoUsuario: comprobarPushActivoUsuario,
        mostrar: mostrar,
        inicializar: inicializar,
        reloadActivePushUser: reloadActivePushUser,
        chargeMessages: chargeMessages,
        backAvisos: backAvisos,
        cargarXpull: cargarXpull,
        parseDate: parseDate
    };
} ());