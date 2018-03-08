/**
    Script:         utils.js
    Autores:        Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          02/09/2015
    Descripción:    Módulo con las funciones genericas utiles para toda la app
*/
var utils = {};

utils = (function () {
    'use strict';
    
    //////////////////////////////////
	// variables
	////////////////////////////////// 
    var alfanumericoEstricto = /^(?=[\w\W]*?[a-z])(?=[\w\W]*?\d)[a-z\d]+$/i;
    var alfanumerico = /^[a-zA-Z0-9_]*$/;
    var numerico = /^[0-9]*$/;
    var nombreFichero = 'recursos';
    var rutaIdiomas = '../lang/';
    var primera = true;

    // var pruebaPage = "portal.html";
    // var pruebaElemento = "#page-portal";
    
    
    //////////////////////////////////
	// metodos privados
	//////////////////////////////////  
    var ejecutarHandlebars = function (event) {
        if (config.logConsole) {
            console.log("ejecutarHandlebars");
        }
        // alert("ejecutarHandlebars");
        var data = event.data;
        // console.log("dataHandebars: "+JSON.stringify(data));
        var source = $(data.elemento).html();
        // console.log("sourceHandlebars: "+source);
        var template = Handlebars.compile(source);
        var html = template(data.objeto);
        $(data.elemento).html(html);
    };
    
	//////////////////////////////////
	// metodos públicos
	//////////////////////////////////
    var insertarIdioma = function (funcion, ruta) {
        var rutaFinal = ruta || rutaIdiomas;
        
        $.i18n.properties({
            name: nombreFichero,
            path: rutaFinal,
            mode: 'map',
            language: config.lang,
            callback: funcion
        });
    };
    
    var abrirEnlace = function (url, modo) {
        window.open(url, modo);
    };
    
    var cerrarApp = function () {
        navigator.app.exitApp();
    };
    
    var irAPagina = function (pagina) {
        $(":mobile-pagecontainer").pagecontainer("change", pagina);
    };
    
    var setOrientation = function (orientacion) {
        // screen.lockOrientation(orientacion);
    };
    
    // Funcion con la que iremos a la página anterior o en su defecto, cerraremos la aplicacion
    var irAPaginaAnterior = function () {
        // alert("irAPaginaAnterior");
        var paginaActual = $.mobile.activePage.attr('id');
            
        switch (paginaActual) {
        
            case 'page-portal':
            case 'page-login':
            case 'loading':
                cerrarApp();
                break;

            case 'page-registroUsuario':
            case 'page-avisos':
                
                    avisos.backAvisos();    
                
                
                break;
                    
            case 'page-buscador':
            case 'page-contacto':
                portal.mostrar();
                break;
            case 'page-accesoClientes':
                
                if($("#headerAccesoClientes").is(":visible")) {
                    portal.mostrar();
                } else {
                    parent.history.back();
                }
                

                
                break;
            case 'page-garantiaSeguridad':
            case 'page-infoInteres':
            case 'page-otrosServicios':
                parent.history.back();
                break;
            default:
                
                portal.mostrar();
        }
    };


    
    var esVacio = function (dato) {
        return (!dato || dato.length === 0);
    };
    
    var esNulo = function (dato) {
        return (dato === null || dato === undefined);
    };
    
    var esNumerico = function (dato) {
        return (!dato.match(numerico)) ? false : true;
    };
    
    var deshabilitarElementos = function () {
        $('[type="button"], [data-role="listview"] a, a').addClass('ui-disabled');
        $('input').prop('disabled', true);
    };
    
    var habilitarElementos = function () {
        $('[type="button"], [data-role="listview"] a, a').removeClass('ui-disabled');
        $('input').prop('disabled', false);
    };
    
    var mostrarLoading = function () {
        $.mobile.loading('show', {
            text: "cargando",
            textVisible: false,
            theme: "a",
            textonly: false,
            html: ""
        });
        /*$.mobile.loading("show", {
            text: '',
            textVisible: true,
            theme: '',
            textonly: false,
            html:   '<div class="spinner">' +
                        '<div class="bounce1"></div>' +
                        '<div class="bounce2"></div>' +
                        '<div class="bounce3"></div>' +
                        '<div class="bounce4"></div>' +
                    '</div>'
        });*/
        
        deshabilitarElementos();
    };
    
    var quitarLoading = function () {
        habilitarElementos();
        $.mobile.loading('hide');
    };
    
    var ejecutarHandlebarsRefrescar = function (source, elemento, objeto) {
        if (config.logConsole) {
            console.log("ejecutarHandlebarsRefrescar");
        }
        var template = Handlebars.compile(source);
        var html = template(objeto);
        $(elemento).html(html);
    };
    
    var inyectar = function (page, elemento, objeto) {
        var promise = $.Deferred();

        if (config.logConsole) {
            console.log("inyectar");
        }
        console.log("page: "+page);
        console.log("elemento: "+elemento);
        console.log("objeto: "+JSON.stringify(objeto));
        var data = {
            elemento: elemento,
            objeto: objeto
        };
        
        irAPagina(page);
        
        $(document)
            .off('pagecreate', elemento)
            .on('pagecreate', elemento, data, promiseInflate);

        return promise;

        function promiseInflate(event){
            ejecutarHandlebars(event);
            promise.resolve();
        }
    };
    
    var inyectarRefrescar = function (page, elemento, objeto, tratamientoPosterior) {
        if (config.logConsole) {
            console.log("inyectarRefrescar");
        }
        return $.get(page, function (data) {
            if (elemento === "#menu-listview") {
                if (primera) {
                    $('body').append(data);
                    $("body>[data-role='panel']").panel();
                    $("#menu-listview").listview();
                    primera = false;
                }
            }
            data = $(data).find(elemento).html();
            ejecutarHandlebarsRefrescar(data, elemento, objeto);
            $(elemento).trigger('create');
            quitarLoading();
            
            if (tratamientoPosterior) {
                tratamientoPosterior.success(tratamientoPosterior.objetoInyectar);
            }
        }).fail(function (ra, b, c) {
            console.log('Ha ocurrido un error' + ra + ' ' + b + ' ' + c);
        });
    };
    
    var comprobarConexion = function () {
        if (config.simuleTerminal) {
            return true; // si estamos utilizando el navegador del PC en lugar de un terminal real devolvemos true para evitar que de fallo al acceder al objeto navigator
        }
        var networkState = navigator.connection.type;

        if (networkState === 'none' || networkState === 'unknown') {
            return false;
        } else {
            return true;
        }
    };
    
    var jsonAArray = function (json) {
        return $.map(json, function (valor, clave) {
            return [{ clave: clave, valor: valor }];
        });
    };
    
    // NOTE: hemos visto que al volver a una vista, tardaba bastante al tener
    // muchos datos en el html. Esto nos pasaba en un nexus4 (imaginemos peores móviles, 
    // con que opte por intentar aligerar este proceso y ganar en usabilidad. Para ello utilice 
    // el parametro jqm 'data-dom-cache="true"' en las page que he visto necesario.
    // El problema de usar esto es que según lo tenemos montado, perdemos las etiquetas
    // de handlebar si queremos inyectar datos de nuevo, así que he creado un reseteo para
    // cuando queramos quitar el 'cacheo' de la page. Con esta función lo solucionamos
    var quitarPageCacheado = function (id) {
        if (config.logConsole) {
            console.log("quitarPageCacheado");
        }
        $(id + '[data-role="page"]').remove();
    };
    
    var rellenar = function (cadena, relleno) {
        cadena = cadena.toString();
        
        return relleno.substring(0, relleno.length - cadena.length) + cadena;
    };
    
    var padCeros = function (number, length) {
   
        var str = '' + number;
        while (str.length < length) {
            str = '0' + str;
        }
   
        return str;

    };
    
    var limpiarInput = function (id) {
        $(id).val("");
    };
    
    var convertirArray = function (elemento) {
        return (elemento === undefined || elemento instanceof Array) ? elemento : [elemento];
    };
    
    var refrescarSlide = function (elemento) {
        $(elemento).glide().data('api_glide').resize();
    };
    
    var obtenerFechaActual = function () {
        var hoy = new Date();
        return hoy;
    };
    
    var getDevice = function () {
        return device.platform;
        //return (navigator.userAgent.match(/iPad/i))  === "iPad" ? "iPad" : (navigator.userAgent.match(/iPhone/i))  === "iPhone" ? "iPhone" : (navigator.userAgent.match(/Android/i)) === "Android" ? "Android" : (navigator.userAgent.match(/BlackBerry/i)) === "BlackBerry" ? "BlackBerry" : "null";
    };
    
    var getVersion = function () {
        return device.version;
    };
    
    var ocultarClearBtn = function () {
        $(".ui-icon-delete").each(function () {
            $(this).css("display", "none");
        });
    };
    
    var comprobarInput = function (elemento) {
        var len = $(elemento).val().length;
        
        if (len > 0) {
            $(elemento).siblings().css("display", "block");
        } else {
            $(elemento).siblings().css("display", "none");
        }
    };
    
    var startsWith = function (string, prefix) {
        if(string == undefined ||  prefix == undefined){
            return 0;
        } else {
            return string.slice(0, prefix.length) === prefix;    
        }
        
    };
    
    var formatearFechaAviso = function (fechaSinFormatear) {
            
        var fechaSplit = fechaSinFormatear.split("-");
        
        return fechaSplit[2] + "-" + fechaSplit[1] + "-" + fechaSplit[0];
        
    };
    
    return {
        insertarIdioma: insertarIdioma,
        esVacio: esVacio,
        esNulo: esNulo,
        esNumerico: esNumerico,
        inyectar: inyectar,
        inyectarRefrescar: inyectarRefrescar,
        comprobarConexion: comprobarConexion,
        irAPagina: irAPagina,
        cerrarApp: cerrarApp,
        irAPaginaAnterior: irAPaginaAnterior,
		abrirEnlace: abrirEnlace,
        jsonAArray: jsonAArray,
        quitarPageCacheado: quitarPageCacheado,
        rellenar: rellenar,
        padCeros: padCeros,
        limpiarInput: limpiarInput,
        convertirArray: convertirArray,
        refrescarSlide: refrescarSlide,
        obtenerFechaActual: obtenerFechaActual,
        setOrientation: setOrientation,
        getDevice: getDevice,
        getVersion: getVersion,
        ejecutarHandlebars: ejecutarHandlebars,
        ejecutarHandlebarsRefrescar: ejecutarHandlebarsRefrescar,
        deshabilitarElementos: deshabilitarElementos,
        habilitarElementos: habilitarElementos,
        mostrarLoading: mostrarLoading,
        quitarLoading: quitarLoading,
        ocultarClearBtn: ocultarClearBtn,
        comprobarInput: comprobarInput,
        startsWith: startsWith,
        formatearFechaAviso: formatearFechaAviso
    };
}());