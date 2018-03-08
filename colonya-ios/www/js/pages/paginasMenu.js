/**
    Script:         garantiaSeguridad.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          16/09/2015
    Descripción:    Módulo con las funciones y eventos que se manejan dentro de la garantía de seguridad
*/
var paginasMenu = {};

paginasMenu = (function () {
    'use strict';
    
    //////////////////////////////////////////////
    // métodos privados
    //////////////////////////////////////////////
    var cargarCadenas = function () {
        utils.insertarIdioma(function () {
            
        });
    };
    
    var cargaIframe = function (hayCabecera) {
        //Cargamos dinámicamente la altura del iframe, ya que existe un problema de cross domain
        
        var alturaPage = $(document).outerHeight();
        
        var alturaCabeceraPage = hayCabecera ? $("#headerPaginasMenu").outerHeight() : 0;
        
        var alturaCabeceraContent = $(".ui-bar-b").outerHeight();
        
        var alturaIframe = alturaPage - alturaCabeceraPage - alturaCabeceraContent;
        console.log("cargaIframePaginasMenu!!!!!!!!!!!!!!!!!");
        $("#iframePaginasMenu").height(alturaIframe);
    };
    
    var eventoCollapsible = function () {
        var alturaContent = $("#iframePaginasMenu").contents().find('.ui-content').outerHeight();
        var alturaCabeceraIframe = $("#iframePaginasMenu").contents().find('.ui-bar-b').outerHeight();
        
        var alturaIframe = alturaCabeceraIframe + alturaContent;
        
        $("#iframePaginasMenu").height(alturaIframe);
    };

    
    var setIframeSource = function () {
        
        $("#iframePaginasMenu").attr('src', localStorage.getItem("irAUrl"));
    };
    
    var mostrarCabeceraApp = function () {
        
        setTimeout(function(){
            console.log("pesadilla");
            $("#headerPaginasMenu").show();
            
            var alturaCabecera = $("#headerPaginasMenu").height() + "px";
            $("#iframePaginasMenu").css("margin-top", alturaCabecera);
        }, 100); 
        
    };
    
    var ocultarCabeceraApp = function () {
        // $("#headerPaginasMenu").hide();
        $("#page-paginasMenu").css("padding-top", 0);
    };
    
    var ocultarCabeceraCMS = function () {
        $('#iframePaginasMenu').contents().find("#cabecera").hide();
    };

    var abrirNavExterno = function (event) { 
       event.preventDefault(); 
       // if($(this).attr("href").indexOf('http') == -1) {
       //  alert("if");
       //      window.open(dataHelper.getActiveApp().Respuesta.Dominio.urlDominio+($(this).attr("href"), "_system")); 
       // } else {
            window.open($(this).attr("href"), "_system");
       // }
                
       return false; 
   }; 
    
    var esperarCargaIframe = function () {
        setTimeout(function(){
                        ocultarCabeceraCMS();
                        $('#iframePaginasMenu').contents().find('.ui-link').attr('target', '_system');
                        // $("#iframePaginasMenu").contents().find('a.ui-link').on('click', abrirNavExterno);
                        // $('#iframePaginasMenu').contents().find('.ui-link').attr('target', '_system');
                    }, 500); 
        ocultarCabeceraCMS();
        mostrarCabeceraApp();

        $('#iframePaginasMenu').ready(function () {
            utils.mostrarLoading();
            mostrarCabeceraApp();
        }).load(function () {
            utils.quitarLoading();
            
            //Quitamos la cabecera del CMS y mostramos la de la APP
            if ($('#iframePaginasMenu').contents().find("#cabecera").length > 0) {
                ocultarCabeceraCMS();
                mostrarCabeceraApp();
                cargaIframe(false);
            }
            // $("#iframePaginasMenu").contents().find('a.ui-link').on('click', abrirNavExterno);
            var i = 0, numRRSS = document.getElementById('iframePaginasMenu').contentDocument.getElementsByTagName("li").length;
            for(i = 0; i<numRRSS; i++) {
                $(document.getElementById('iframePaginasMenu').contentDocument.getElementsByTagName("li")[i].getElementsByTagName("a")[0]).on("click", abrirNavExterno);
            }   
            
            //Solucón cutre porque no captura los efventos de collapse y expand de los collapsible de dentro del iframe
            $("#iframePaginasMenu").contents().find('.ui-collapsible-heading').on('click', function () {
                var plataforma = utils.getDevice().toLowerCase();
                
                if (plataforma === 'ios') {
                    // eventoCollapsible();
                }
            });
            
        });
        mostrarCabeceraApp();
        utils.quitarLoading();
    };
    
    var inicializarPagina = function () {
        cargaIframe(true);
        setIframeSource();
        ocultarCabeceraApp();
        esperarCargaIframe();
        mostrarCabeceraApp();
    };
    
    var iniciarElementos = function () {
        cargarCadenas();
        mostrarCabeceraApp();
        ocultarCabeceraApp();
    };
    
    var iniciarEventos = function () {
        mostrarCabeceraApp();
        ocultarCabeceraApp();
    };
    
    //////////////////////////////////////////////
    // métodos públicos
    //////////////////////////////////////////////
    var mostrar = function (urlPaginasMenu) {
               console.log('Mostrando...');
        var logo = dataHelper.getLogoUrl();
        utils.inyectar("paginasMenu.html", "#page-paginasMenu", {
            urlPaginasMenu: urlPaginasMenu,
            logo: logo
        });
        setTimeout(function(){
                        ocultarCabeceraCMS();
                        $("#iframePaginasMenu").contents().find('a.ui-link').on('click', abrirNavExterno);
                        // $('#iframePaginasMenu').contents().find('.ui-link').attr('target', '_blank');
                    }, 600);

        setTimeout(function(){
                        ocultarCabeceraCMS();
                        $("#iframePaginasMenu").contents().find('a.ui-link').on('click', abrirNavExterno);
                        // $('#iframePaginasMenu').contents().find('.ui-link').attr('target', '_blank');
                    }, 4000);

        setTimeout(function(){
                        ocultarCabeceraCMS();
                        $("#iframePaginasMenu").contents().find('a.ui-link').on('click', abrirNavExterno);
                        // $('#iframePaginasMenu').contents().find('.ui-link').attr('target', '_blank');
                    }, 5000);
        setTimeout(function(){
                        ocultarCabeceraCMS();
                        $("#iframePaginasMenu").contents().find('a.ui-link').on('click', abrirNavExterno);
                        // $('#iframePaginasMenu').contents().find('.ui-link').attr('target', '_blank');
                    }, 6000);
        setTimeout(function(){
                        ocultarCabeceraCMS();
                        $("#iframePaginasMenu").contents().find('a.ui-link').on('click', abrirNavExterno);
                        // $('#iframePaginasMenu').contents().find('.ui-link').attr('target', '_blank');
                    }, 7000);
        // mostrarCabeceraApp();
        
    };
        
    var inicializar = function () {
        // $(document)
        //     .one('pageinit', '#page-paginasMenu', iniciarEventos)
        //     .on("pageinit", "#page-paginasMenu", iniciarElementos)
        //     .on("pageshow", "#page-paginasMenu", inicializarPagina);
    };
    
    return {
        mostrar: mostrar,
        inicializar: inicializar
    };
}());
