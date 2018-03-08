/**
    Script:         dataHelper.js
    Autores:        Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          06/11/2015
    Descripción:    Módulo con las funciones y eventos encargados del manejo de datos
*/
var dataHelper = {};

dataHelper = (function () {
    'use strict';
    
    /////////////////////////////////////////
    // métodos públicos
    /////////////////////////////////////////
    
    var getBanner = function () {
        
        var listPub = [];
        var pub = {};
        var urlDominio = JSON.parse(localStorage.getItem("active_app")).Respuesta.Dominio.urlDominio +"/";
        
        if (!utils.esNulo(getActiveApp()) && !utils.esNulo(getActiveApp().Respuesta) && !utils.esNulo(getActiveApp().Respuesta.ListaPublicidad)) {
            
            var rutaCMS = dataHelper.getActiveApp().Respuesta.Dominio.urlDominio;
            var i = 0;
        
        for (i = 0; i < getActiveApp().Respuesta.ListaPublicidad.length; i++) {
            if (getActiveApp().Respuesta.ListaPublicidad[i] !== null) {
            }
        }

            var pub1 = {};
                
                    pub1.Cont = rutaCMS + getActiveApp().Respuesta.ListaPublicidad[0].urlImagen;
                    if ((rutaCMS + getActiveApp().Respuesta.ListaPublicidad[0].urlEnlaceImagen).indexOf(urlDominio) === 0) {
                        pub1.url = rutaCMS + getActiveApp().Respuesta.ListaPublicidad[0].urlEnlaceImagen;
                    } else {
                        pub1.url =   getActiveApp().Respuesta.ListaPublicidad[0].urlEnlaceImagen;
                    }
                    
            var pub2 = {};
                        
                    pub2.Cont = rutaCMS + getActiveApp().Respuesta.ListaPublicidad[1].urlImagen;
                    


                    if ((rutaCMS + getActiveApp().Respuesta.ListaPublicidad[1].urlEnlaceImagen).indexOf(urlDominio) === 0) {
                        pub2.url = rutaCMS + getActiveApp().Respuesta.ListaPublicidad[1].urlEnlaceImagen;
                    } else {
                        pub2.url =  getActiveApp().Respuesta.ListaPublicidad[1].urlEnlaceImagen;
                    }

            listPub.push(pub1);
            listPub.push(pub2);

            var pub3 = {};

            if(getActiveApp().Respuesta.ListaPublicidad.length>2) {
                pub3.Cont = rutaCMS + getActiveApp().Respuesta.ListaPublicidad[2].urlImagen;
                

                if ((rutaCMS + getActiveApp().Respuesta.ListaPublicidad[2].urlEnlaceImagen).indexOf(urlDominio) === 0) {
                        pub3.url = rutaCMS + getActiveApp().Respuesta.ListaPublicidad[2].urlEnlaceImagen;
                    } else {
                        pub3.url =  getActiveApp().Respuesta.ListaPublicidad[2].urlEnlaceImagen;
                    }
                listPub.push(pub3);

            }        
            
        } 
        
        var i = 0;
        
        for (i = 0; i < listPub.length; i++) {
            if (listPub[i].Cont !== null) {
                var publiCont = listPub[i].Cont;
                if (!utils.startsWith(publiCont, "http://") && !utils.startsWith(publiCont, "https://")) {
                    listPub[i].Cont = publiCont;
                }
            }
        }
        
        return listPub;
        
    };
    
    var saveActiveApp = function (activeApp) {
        if (activeApp !== null && activeApp !== undefined) {
            store.local("active_app").set(activeApp);
        }
    };
    
    var removeActiveApp = function () {
        store.local("active_app").set(null);
    };
    
    var getActiveApp = function () {
        var appActiva = store.local("active_app").get();
        
        return appActiva;
        
    };
    
    var getAccess = function () {
        var access;
        
        if (!utils.esNulo(getActiveApp()) && !utils.esNulo(getActiveApp().Respuesta) && !utils.esNulo(getActiveApp().Respuesta.Acceso)) {
            access = getActiveApp().Respuesta.Acceso;
            access.Url = getActiveApp().Respuesta.Acceso.urlAcceso;
            
        }
    
        if (utils.esNulo(access)) {
            access = {};
            access.Desc = getActiveApp().Respuesta.Acceso.textoAcceso;
            //access.Url = "/docs/0083/RSI/www.cajarural.com/rurales/iphone/mv4/demos/docs/lists/acceso.html";
            //Lo cambiamos porque falla
            access.Url = getActiveApp().Respuesta.Acceso.urlAcceso;
        }
        
        // INICIO TRAMPEADO
        // Si es producción.. si es test..
        // if (access.Url.indexOf("www.ruralvia.com") > -1) {
        //     access.Url = access.Url.replace("ISUM_Portal=84", "ISUM_Portal=104");
        // } else {
        //     access.Url = access.Url.replace("ISUM_Portal=84", "ISUM_Portal=90");
        // }
        // FIN TRAMPEADO
    
        return access;
    };
    
    var getGoogleAnalytics = function () {
        var result = null;
        
        var analytics = null;
        
        if (!utils.esNulo(getActiveApp()) && !utils.esNulo(getActiveApp().Respuesta) && !utils.esNulo(getActiveApp().Respuesta.Estadisticas)) {
            analytics = getActiveApp().Respuesta.Estadisticas.codigoAnalytics;
        }
        
        if (!utils.esNulo(analytics)) {
            result = analytics;
        } else {
            result = "UA-17071707";
        }
        
        return result;
    };
    
    var getLogoUrl = function () {
        var rutaCMS = getActiveApp().Respuesta.Dominio.urlDominio;
        var logo = null;
        
        if (!utils.esNulo(getActiveApp()) && !utils.esNulo(getActiveApp().Respuesta) && !utils.esNulo(getActiveApp().Respuesta.Logo) && !utils.esNulo(getActiveApp().Respuesta.Logo.urlLogo)) {
            logo = getActiveApp().Respuesta.Logo.urlLogo;
        }
        
        if (logo === null) {
			logo = "/rvia/_componentes/img/logos/logo_generico_movil.gif";
		}

		if (!utils.startsWith(logo, "http://") && !utils.startsWith(logo, "https://")) {
			logo = rutaCMS + logo;
		}

		return logo;
        
    };
    
    var getPie = function () {
        var rutaCMS = getActiveApp().Respuesta.Dominio.urlDominio;
        var pie = {};
        
        if (!utils.esNulo(getActiveApp()) && !utils.esNulo(getActiveApp().Respuesta) && !utils.esNulo(getActiveApp().Respuesta.Pie)) {
            // alert("pasa");
            pie.resultado = getActiveApp().Respuesta.Pie.resultado;
            pie.indicador = getActiveApp().Respuesta.Pie.indicador;
        }
        
        //Obtenemos el P_Indic
        //0: URL
        //1: Nombre entidad
        //3: Genérico
        //9: Error        
                
        if (pie.indicador === '0' && !utils.startsWith(pie.resultado, "http://") && !utils.startsWith(pie.resultado, "https://")) {
            // alert("no pasa");
            pie.resultado = rutaCMS + pie.resultado;
        }
        
        return pie;
        
    };
    
    var getMenu = function () {
        
        var lDocu = [];
        
        if (!utils.esNulo(getActiveApp()) && !utils.esNulo(getActiveApp().Cache) && !utils.esNulo(getActiveApp().Cache.Menu)) {
            lDocu = getActiveApp().Cache.Menu.LDocu;
        }
        
        //Si viene vacio, generamos un menú genérico
        if (lDocu.length === 0) {
            var imag = "/docs/0083/RSI/www.cajarural.com/rurales/iphone/mv4/demos/docs/lists/images/2.png";
            var Path = "/docs/0083/RSI/www.cajarural.com/rurales/iphone/mv4/demos/docs/lists/info_interes.html";
            var Tit = "TELEFONOS DE INTERES";
            
            var obj = {};
            
            obj.Imag = imag;
            obj.Path = Path;
            obj.Tit = Tit;
            
            lDocu.push(obj);
        }
        
        return lDocu;
        
    };
    
    return {
        getBanner: getBanner,
        saveActiveApp: saveActiveApp,
        removeActiveApp: removeActiveApp,
        getActiveApp: getActiveApp,
        getAccess: getAccess,
        getGoogleAnalytics: getGoogleAnalytics,
        getLogoUrl: getLogoUrl,
        getPie: getPie,
        getMenu: getMenu
    };
}());