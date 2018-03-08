/**
    Script:         accesoClientes.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          03/09/2015
    Descripción:    Módulo con las funciones y eventos que se manejan dentro del acceso a clientes
*/
var accesoClientes = {};

accesoClientes = (function () {
    'use strict';
    var pagePaddingTop, eventInstance = false;

    //////////////////////////////////////////////
    // métodos privados
    //////////////////////////////////////////////
    var cargarCadenas = function () {
        utils.insertarIdioma(function () {
            $(".lng-accesoClientes-titulo").text($.i18n.prop('accesoClientes-titulo'));
        });
    };

    var cargaIframe = function (hayCabecera) {
        //Cargamos dinámicamente la altura del iframe, ya que existe un problema de cross domain

        var alturaPage = $(document).outerHeight();

        var alturaCabeceraPage = hayCabecera ? $("#headerAccesoClientes").outerHeight() : 0;

        var alturaCabeceraContent = hayCabecera ? $("#headerContent").outerHeight() : 0;

        var alturaIframe = alturaPage - alturaCabeceraPage - alturaCabeceraContent;

        $("#iframeAccesoClientes").height(alturaIframe);

        // Para evitar espacio en gris al final de la página
        $("#page-accesoClientes").css("min-height", alturaIframe);
    };

    var setIframeSource = function () {
        if (!utils.esNulo(portal.accesoClienteUrl)) {
            $("#iframeAccesoClientes").attr('src', portal.accesoClienteUrl);
        }
    };

    var mostrarCabeceraApp = function () {
        $("#headerAccesoClientes").show();
        $("#headerContent").show();
        $("#page-accesoClientes").css("padding-top", pagePaddingTop);
    };

    var ocultarCabeceraApp = function () {
        $("#headerAccesoClientes").hide();
        $("#headerContent").hide();
        $("#page-accesoClientes").css("padding-top", 0);
    };

    var esperarCargaIframe = function () {
        $('#iframeAccesoClientes').ready(function () {
            utils.mostrarLoading();
        }).load(function () {
            utils.quitarLoading();
            if ($("#headerAccesoClientes").is(':visible') && $("#headerContent").is(":visible")) {
                if (!eventInstance) {
                    $(document).on("resume active", comprobarSesion);
                    eventInstance = true;
                }
                ocultarCabeceraApp();
                cargaIframe(false);
            }

            //Si estamos en selección de contrato, quitamos ya la cabecera
            if ($('#iframeAccesoClientes').contents().find("#FORM_RVIA_0").length > 0 || $('#iframeAccesoClientes').contents().find(".registro_marca").length > 0 || $('#iframeAccesoClientes').contents().find("#posicionGlobal").length > 0) {
                if (!eventInstance) {
                    $(document).on("resume active", comprobarSesion);
                    eventInstance = true;
                }
                ocultarCabeceraApp();
                cargaIframe(false);
            }

            //Si estamos en desconexión
            if ($('#iframeAccesoClientes').contents().find("#textoPequenno").length > 0) {
                setTimeout(function () {
                    portal.mostrar();
                }, 2000);

                if (eventInstance) {
                    $(document).off("resume active", comprobarSesion);
                    eventInstance = false;
                }
            }

            if ($('#iframeAccesoClientes').contents().find("#volverPortalIframe").length > 0) {
                console.log("volverPortalIframe");
                $('#iframeAccesoClientes').contents().find("#volverPortalIframe").bind('volverPortal',function(e) {
                    e.preventDefault();
                    console.log("eventoEspecial");
                        portal.mostrar();
                });
            }


            //Si vamos a meter las credenciales, insertamos las cabeceras
            if ($('#iframeAccesoClientes').contents().find("#accesoIphone").length > 0) {
                if (eventInstance) {
                    $(document).off("resume active", comprobarSesion);
                    eventInstance = false;
                }
                mostrarCabeceraApp();
                cargaIframe(true);
            }

            if (!$("#headerAccesoClientes").is(':visible') && !$("#headerContent").is(":visible")) {
                $("#iframeAccesoClientes").removeClass("fijarCabecera");
            }

            $('#iframeAccesoClientes').contents().find('div.ui-header.ui-bar-e').hide();
            $('section.contenedorInterior.ui-content').css('overflow', 'hidden');
        });
    };

    var checkClearCache = function() {
        console.log("Comprobando si hay que borrar cache");
        var lastRefreshCache = window.localStorage.getItem('lastRefreshCache') || window.localStorage.setItem('lastRefreshCache', 'refrescarCache');

        $.ajax({
            url: config.cacheService
        }).done(function (data) {
            console.log("llamada realizada para comprobar version");
            lastRefreshCache = window.localStorage.getItem('lastRefreshCache');
            if (data !== lastRefreshCache) {
                var success = function(status) {
                    console.log('Cache borrada correctamente!: ' + status);
                };

                var error = function(status) {
                    console.log('Cache no fue borrada correctamente!: ' + status);
                };

                window.cache.clear(success, error);
                window.localStorage.setItem('lastRefreshCache', data);
            }
            else {
                console.log('No es necesario refrescar la cache');
            }
        });
    };

    var inicializarPagina = function () {
        checkClearCache();


        cargaIframe(true);
        setIframeSource();
        esperarCargaIframe();
        pagePaddingTop = $("#page-accesoClientes").css("padding-top");
        fixKeyboard();
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
        var esApple = true;
        dataHelper.noLlamarPortalPubb(true);
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

    var comprobarSesion = function () {
        $.ajax({
            url: config.testSession + 'TestSession.jsp'
        })
            .done(function (data) {
                if (data.indexOf("KO") != -1) {
                    portal.mostrar();
                }
            });
    };

    var inicializar = function () {
        $(document)
            .one('pageinit', '#page-accesoClientes', iniciarEventos)
            .on("pageinit", "#page-accesoClientes", iniciarElementos)
            .on("pageshow", "#page-accesoClientes", inicializarPagina);
    };

    var fixKeyboard = function () {
        $(document).ready(function () {
            var curWindow = $(window);
            var initialHeight = curWindow.height();

            curWindow.resize(function () {
                if (curWindow.height() == initialHeight) {
                    $('#iframeAccesoClientes').contents().find('#rellenoInputAndroid').hide();
                    $('#iframeAccesoClientes').contents().find('input:focus').blur();
                } else {
                    $('#iframeAccesoClientes').contents().find('#rellenoInputAndroid').show();
                };
            });
        });
    }

    return {
        mostrar: mostrar,
        inicializar: inicializar
    };
} ());
