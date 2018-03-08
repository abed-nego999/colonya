/**
    Script:         buscador.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          16/09/2015
    Descripción:    Módulo con las funciones y eventos que se manejan dentro de la página de contacto
*/
var contacto = {};

contacto = (function () {
    'use strict';
    
    //////////////////////////////////////////////
    // métodos privados
    //////////////////////////////////////////////
    var cargarCadenas = function () {
        utils.insertarIdioma(function () {
            
        });
    };
    
    var setiFrameHeightDynamically = function () {
        var alturaVentana = $(window).outerHeight();
        var alturaHeader = $("#headerContacto").outerHeight();
        
        var alturaIframe = alturaVentana - alturaHeader;
        
        $("#iframeContacto").height(alturaIframe);
    };
    
    var setIframeSource = function () {
        var rutaCMS = dataHelper.getActiveApp().Respuesta.Dominio.urlDominio;
        // INICIO TRAMPEADO
        $("#iframeContacto").attr('src', config.rutaEstatico + config.rutaContacto);
        // FIN TRAMPEADO
    };
    
    var mostrarCabeceraApp = function () {
        $("#headerContacto").show();        
    };
    
    var ocultarCabeceraApp = function () {
        $("#headerContacto").hide();
        $("#page-contacto").css("padding-top", 0);
    };
    
    var ocultarCabeceraCMS = function () {
        $('#iframeContacto').contents().find("#cabecera").hide();
    };

    var abrirNavExterno = function (event) {
        event.preventDefault();
        // alert("pruebaURL:::"+$(this).attr("href"));
        window.open($(this).attr("href"), "_system");
        return false;
    };
    
    var esperarCargaIframe = function () {
        setTimeout(function(){
                        ocultarCabeceraCMS();
                    }, 500); 
        ocultarCabeceraCMS();
        $('#iframeContacto').ready(function () {
            utils.mostrarLoading();
        }).load(function () {
            utils.quitarLoading();
            $('#iframeContacto').contents().find('.ui-link').attr('target', '_system');
            // $(document.getElementById('iframeContacto').contentDocument.getElementsByTagName('body')[0].getElementsByTagName('div')[3].getElementsByTagName('div')[0].getElementsByTagName('div')[0].getElementsByTagName('ul')[0].getElementsByTagName('li')[2].getElementsByTagName('a')).on("click", abrirNavExterno);
            if ($('#iframeContacto').contents().find("#cabecera").length > 0) {
                ocultarCabeceraCMS();
                mostrarCabeceraApp();
            }
        });
    };
    
    var inicializarPagina = function () {
        setiFrameHeightDynamically();
        setIframeSource();
        ocultarCabeceraApp();
        esperarCargaIframe();        
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
        var logo = dataHelper.getLogoUrl();
        utils.inyectar("contacto.html", "#page-contacto", {
            usuarioRegistrado: usuarioRegistrado,
            logo: logo
        });
    };

    var inicializar = function () {
        $(document)
            .one('pageinit', '#page-contacto', iniciarEventos)
            .on("pageinit", "#page-contacto", iniciarElementos)
            .on("pageshow", "#page-contacto", inicializarPagina)
            .off("pageload", "#page-portal", utils.inyectarRefrescar);
        
    };
    
    return {
        mostrar: mostrar,
        inicializar: inicializar
    };
}());