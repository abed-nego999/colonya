/**
    Script:         campana.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          16/09/2015
    Descripción:    Módulo con las funciones y eventos que se manejan dentro de la página de campana
*/
var campana = {};

campana = (function () {
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
        var alturaHeader = $("#headerCampana").outerHeight();
        
        var alturaIframe = alturaVentana - alturaHeader;
        
        $("#iframeCampana").height(alturaIframe);
    };
    

    
    var ocultarCabeceraCMS = function () {
        $('#iframeCampana').contents().find("#cabecera").hide();
    };
    
    var esperarCargaIframe = function () {
        setTimeout(function(){
                        ocultarCabeceraCMS();
                    }, 500); 
        ocultarCabeceraCMS();
        $('#iframeCampana').ready(function () {
            utils.mostrarLoading();
            setTimeout(function(){
                        utils.quitarLoading();
                    }, 2000); 
        }).load(function () {
            utils.quitarLoading();
            
            if ($('#iframeCampana').contents().find("#cabecera").length > 0) {
                ocultarCabeceraCMS();
            }
        });
    };
    
    var inicializarPagina = function () {
        setiFrameHeightDynamically();
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
    var mostrar = function (urlCampana) {
        var usuarioRegistrado = userHelper.getUsuarioRegistrado();
        
        var esApple = true;

        
        if (!config.simuleTerminal) {
            esApple = (utils.getDevice() === "iOS");
            //screen.unlockOrientation();
        }
        var logo = dataHelper.getLogoUrl();
        utils.inyectar("campana.html", "#page-campana", {
            usuarioRegistrado: usuarioRegistrado,
            esApple: esApple,
            urlCampana: urlCampana,
            logo: logo
        });
        setTimeout(function(){
                        ocultarCabeceraCMS();
                    }, 500);
        setTimeout(function(){
                        ocultarCabeceraCMS();
                    }, 3000);
        setTimeout(function(){
                        ocultarCabeceraCMS();
                    }, 4000);
        setTimeout(function(){
                        ocultarCabeceraCMS();
                    }, 5000);
    };
      
    var inicializar = function () {
        $(document)
            .one('pageinit', '#page-campana', iniciarEventos)
            .on("pageinit", "#page-campana", iniciarElementos)
            .on("pageshow", "#page-campana", inicializarPagina);
        
        $(window).on("orientationchange", setiFrameHeightDynamically);
    };
    
    return {
        mostrar: mostrar,
        inicializar: inicializar,
        ocultarCabeceraCMS: ocultarCabeceraCMS
    };
}());