/**
    Script:         buscador.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          03/09/2015
    Descripción:    Módulo con las funciones y eventos que se manejan dentro del acceso a clientes
*/
var buscador = {};

buscador = (function () {
    'use strict';
    var pagePaddingTop;
    
    //////////////////////////////////////////////
    // métodos privados
    //////////////////////////////////////////////
    var cargarCadenas = function () {
        utils.insertarIdioma(function () {
            $(".lng-buscador-titulo").text($.i18n.prop('buscador-titulo'));
        });
    };
    
    var cargaIframe = function (hayCabecera) {
        //Cargamos dinámicamente la altura del iframe, ya que existe un problema de cross domain
        
        var alturaPage = $(document).outerHeight();
        
        var alturaCabeceraPage = hayCabecera ? $("#headerBuscador").outerHeight() : 0;
        
        var alturaIframe = alturaPage - alturaCabeceraPage;
        
        $("#iframeBuscador").height(alturaIframe);
    };
    
    var setIframeSource = function () {
        var rutaCMS = dataHelper.getActiveApp().Respuesta.Dominio.urlDominio;
        // INICIO TRAMPEADO
        $("#iframeBuscador").attr('src', rutaCMS + "/rvia/generico/movil/es/oficinas/index.html");
        // FIN TRAMPEADO
    };
    
    var mostrarCabeceraApp = function () {
        $("#headerBuscador").show();
        $("#page-accesoClientes").css("padding-top", pagePaddingTop);
    };
    
    var ocultarCabeceraApp = function () {
        $("#headerBuscador").hide();
        $("#page-buscador").css("padding-top", 0);
    };
    
    var esperarCargaIframe = function () {
        $('#iframeBuscador').ready(function () {
            utils.mostrarLoading();
        }).load(function () {
            utils.quitarLoading();
            
            //Si estamos en el mapa
            if ($('#iframeBuscador').contents().find("#pieBuscador").length > 0) {
                ocultarCabeceraApp();
                cargaIframe(false);
            }
        });
    };
    
    var inicializarPagina = function () {
        cargaIframe(true);
        setIframeSource();
        esperarCargaIframe();
        pagePaddingTop = $("#page-buscador").css("padding-top");
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
        setTimeout(function(){
                   var mEntity = JSON.parse(window.localStorage.getItem("active_user")).Entidad;
                   var mEntityName = dataHelper.getPie().resultado;
                   var mShowGroupATM = false;
                   
                   RsiAtmPlugin.init({"googleMapsiOSKey":"AIzaSyDPaFRwkTfLGUgDovW6ZrldT9e77mYR7sU",
                                     "googleMapsWebKey":"AIzaSyDPaFRwkTfLGUgDovW6ZrldT9e77mYR7sU",
                                     "googleMapsAndroidKey":"AIzaSyDPaFRwkTfLGUgDovW6ZrldT9e77mYR7sU",
                                     "entity":mEntity,
                                     "atmsURL":"https://wssrsi.cajarural.com:6705/SOA_RVIA/Empresa/PS/rest/v1/SE_RVA_LocalizadorCajeros",
                                     "officesURL":"https://wssrsi.cajarural.com:6072/SOA_RVIA/Empresa/PS/rest/v2/SE_RVA_LocalizadorOficinasREST",
                                     "entityPlaceHelpTitle": mEntityName,
                                     "showGroupATMs": mShowGroupATM});
                   }, 300);
        
    };
    
    var inicializar = function () {
        $(document)
            .one('pageinit', '#page-buscador', iniciarEventos)
            .on("pageinit", "#page-buscador", iniciarElementos)
            .on("pageshow", "#page-buscador", inicializarPagina);
    };
    
    return {
        mostrar: mostrar,
        inicializar: inicializar
    };
}());
