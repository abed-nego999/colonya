
/**
    Script:         app.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          01/09/2015
    Descripción:    Módulo con las funciones y eventos encargados de realizar las primeras configuraciones
                    generales de la aplicacion
*/
var app = {};

app = (function () {
    'use strict';
    var productos = [];
    var incluirPages = true;
    var online = false;
    var offline = true;

    //////////////////////////////////////////////
    // métodos ViewNext
    //////////////////////////////////////////////
    var b64toBlob = function (b64Data, contentType, sliceSize) {
        console.log('Convirtiendo a blob');
        var input = b64Data.replace(/\s/g, ''),
            byteCharacters = atob(input),
            byteArrays = [],
            offset, slice, byteNumbers, i, byteArray, blob;

        contentType = contentType || '';
        sliceSize = sliceSize || 512;

        for (offset = 0; offset < byteCharacters.length; offset += sliceSize) {
            slice = byteCharacters.slice(offset, offset + sliceSize);

            byteNumbers = new Array(slice.length);
            for (i = 0; i < slice.length; i++) {
                byteNumbers[i] = slice.charCodeAt(i);
            }

            byteArray = new Uint8Array(byteNumbers);

            byteArrays.push(byteArray);
        }

        //Convert to blob.
        try {
            blob = new Blob(byteArrays, { type: contentType });
        }
        catch (e) {
            // TypeError old chrome, FF and Android browser
            window.BlobBuilder = window.BlobBuilder ||
                window.WebKitBlobBuilder ||
                window.MozBlobBuilder ||
                window.MSBlobBuilder;
            if (e.name == 'TypeError' && window.BlobBuilder) {
                var bb = new BlobBuilder();
                for (offset = 0; offset < byteArrays.length; offset += 1) {
                    bb.append(byteArrays[offset].buffer);
                }
                blob = bb.getBlob(contentType);
            }
            else if (e.name == "InvalidStateError") {
                blob = new Blob(byteArrays, {
                    type: contentType
                });
            }
            else {
                return null;
            }
        }

        return blob;
    };

    //////////////////////////////////////////////
    // fin métodos ViewNext
    //////////////////////////////////////////////


    //////////////////////////////////////////////
    // métodos privados
    //////////////////////////////////////////////
    var cargarCadenas = function () {
        utils.insertarIdioma(function () {

        });
    };

    var irAWebClasica = function (event) {
        event.preventDefault();
        window.open($(this).data("url"), '_system');
        return false;
    };

    // Por ahora lo dejamos así
    // Más adelante se descargarán los productos a utilizar
    var obtenerProductos = function () {
        var index = 1;

        for (index; index < 4; index++) {
            productos.push({
                imagen: "imagen" + index
            });
        }
    };

    var inicializarPagina = function () {
        // Consultamos si tenemos que mostrar la pagina de login inicial

        setTimeout(function(){
            rvDataDump.volcado(function (data) {
                console.log(JSON.stringify(data));
                if (data.resultado === "ok") {
                    // alert("volcado");
                    localStorage.setItem("vieneDeVolcado", true);
                    localStorage.setItem("volcado", true);
                    // alert("vieneDeVolcado"+localStorage.getItem("vieneDeVolcado"));
                    cordova.plugins.notification.badge.set(0);
                    //console.log("OK" + JSON.stringify(data.resultado));
                    if (data.active_user !== "") {
                        //console.log("active user" + JSON.stringify(data.active_user));
                        localStorage.setItem("active_user", JSON.stringify(data.active_user));
                    }
                    if (data.list_users !== "") {
                        console.log("listusers" + JSON.stringify(data.list_users));
                        var array = [];
                        for (var i = 0; i < data.list_users.length; i++) {
                            console.log(data.list_users);
                            array.push(data.list_users[i]);
                        }
                        localStorage.setItem("list_users",JSON.stringify(array));
                    }

                    if (data.list_users !== "" && data.active_user !== "") {
                        //console.log(JSON.stringify("utlimo if" + data));
                        localStorage.setItem("loginActivityPass", "true");
                    }
                }


                redirect();
            });

        }, 500);
        function redirect() {
            if (navigator.userAgent.match(/(iPhone|iPod|iPad|Android|BlackBerry)/)) {
                if (localStorage.getItem("loginActivityPass") != "true") {

       //var logo = dataHelper.getLogoUrl();
                    //alert("redirect false");
                    if (app.incluirPages) {
                        app.incluirPages = false;

       utils.inyectar("pages/login.html", "#page-login", { });
                    } else {
                        utils.inyectar("login.html", "#page-login", {});
                    }
                } else {
                    // alert("redirect true portal.mostrar");
                    portal.mostrar();
                }
            } else {
                // Obtenemos los productos a mostrar su info y lo inyectamos en el slider
                obtenerProductos();
                var usuarioRegistrado = userHelper.getUsuarioRegistrado();
                if (app.incluirPages) {
                    app.incluirPages = false;
                    utils.inyectar("pages/portal.html", "#page-portal", {
                        productos: productos,
                        usuarioRegistrado: usuarioRegistrado
                    });
                } else {
                    utils.inyectar("portal.html", "#page-portal", {
                        productos: productos,
                        usuarioRegistrado: usuarioRegistrado
                    });
                }
            }
        }
    };

    var enConstruccion = function () {
        alert("error al cargar los datos");
    };

    var alertHref = function () {
        var myClass = "." + this.className.split(' ').join('.');
        var myHref = $(myClass + " div div a").prop("href");
        localStorage.setItem("irAUrl", myHref);
        //return myHref;
    };

    // var irAPaginasMenu = function () {
    //     // event.preventDefault();
    //     // var urlPaginasMenu = $(".itemCMS a").data("url");
    //     // console.log("irAPaginasMenu.url:  "+urlPaginasMenu);
    //     // paginasMenu.mostrar(urlPaginasMenu);


    //     $(".itemCMS a.enlacePanel").each(function (index) {
    //         $(this).click(function () {
    //             var urlPaginasMenu = $(this).data('url');

    //             paginasMenu.mostrar(urlPaginasMenu);
    //         });
    //     });
    // };


    var InicializarMovil = function () {
        $.mobile.defaultPageTransition = 'none';
        $.mobile.loader.prototype.options.textVisible = false;
        $.mobile.loader.prototype.options.textonly = false;
        $.mobile.allowCrossDomainPages = true;
        $.support.cors = true;
        $.mobile.touchOverflowEnabled = true;


        $(document)
            //.one('pagecreate', '[data-role="page"][data-panel="true"]', inicializarPanel)
            .on('click', '.js-registro-usuario', registroUsuario.mostrar)
            //.on('click', '#accesoClientesButton', registroUsuario.mostrar)
            // .on('vclick', '.Garantíadeseguridad', garantiaSeguridad.mostrar)
            // .on('vclick', '.SinTitulo', enConstruccion)
            // .on('click', '.Avisolegal', avisoLegal.mostrar)
            // .on('click', '.Conócenos', conocenos.mostrar)
            // .on('click', '.ruralviaenredessociales', redesSociales.mostrar)
            // .on('click', '.Recordarclave', recordarClave.mostrar)
            .on('click', '.Iralawebclásica a', irAWebClasica)
            .on('click', '.icono-atras:not("#atras-registroUsuario"):not("#atras-avisos")', utils.irAPaginaAnterior)
            // .on('click', '.icono-atras:not("#atras-avisos")', utils.irAPaginaAnterior)
            .on('click', '#panel-menu li.menu-item:not(".js-registro-usuario")', alertHref)
            //.on('click', '#panel-menu li.menu-item:not(".js-registro-usuario"):not(".padre")', portal.irAPaginasMenu)
            .on('click', '#panel-menu li.menu-item.padre', portal.desplegarHijos)
            .on('pageshow', '#loading', inicializarPagina);


        $(document).on('focus', 'input', function () {
            $(this).parent().addClass('ui-focus');
        });

        $(document).on('focusout', 'input', function () {
            $(this).parent().removeClass('ui-focus');
        });
    };

    var onDeviceReady = function () {
        //Si estamos en el navegador, no ponemos a portrait la pantalla ni registramos las notificaciones push
        //console.log("UUID:  " + device.uuid);
        //localStorage.setItem("UUID", device.serial);

        ////plugin versionApp
        var versionApp = AppVersion.version;
        var dispID = device.manufacturer + "_" + device.model + "_" + device.platform + "_" + device.version +"_"+versionApp;
        ////plugin versionApp
        // var dispID = device.manufacturer + "_" + device.model + "_" + device.platform + "_" + device.version +"_3.0.0";
        localStorage.setItem("UUID",dispID);
        if (!config.simuleTerminal) {
            //Detectamos si estamos sin conexión
            if (utils.comprobarConexion()) {

                utils.setOrientation("portrait");

                push.registrar();


                //En android oculta el teclado los inputs
                if (device.platform === "iOS") {

                    //StatusBar.hide();
                    StatusBar.overlaysWebView(false);
                    $.mobile.hashListeningEnabled = true;
                    $.mobile.pushStateEnabled = false;
                }

                localStorage.setItem("SO", device.platform);

                if (push.isPushVisible()) {
                    if (!db.hayDB()) {
                        db.createDB();
                    }
                    push.getUnreadPush();
                }
            }
        }

        document.addEventListener("backbutton", utils.irAPaginaAnterior, false);


    };

    var onOffline = function () {
        // Workaround
        // Event is fired twice
        if (offline) {
            alerta.wifiSettings("La conexión a Internet está desactivada, ¿quieres activarla?");
            utils.mostrarLoading();
            offline = false;
            online = false;
        }
    };

    var onOnline = function () {
        // Workaround
        // Event is fired twice
        if (!online) {
            inicializarPagina();
            utils.quitarLoading();
            online = true;
            offline = true;

            // db.getLastMsg()
            //     .then(avisos.reloadActivePushUser)
            //     .then(avisos.chargeMessages)
            //     .then(portal.actualizarBadge);
        }
    };

    var pruebaURL = function (event) {
        event.preventDefault();
        // alert("pruebaURL");
        window.open($(this).attr("href"), "_system");
        return false;
    };

    var inicializarDevice = function () {
        if (navigator.userAgent.match(/(iPhone|iPod|iPad|Android|BlackBerry)/)) {
            document.addEventListener("deviceready", onDeviceReady, false);
        } else {
            onDeviceReady();
        }
        document.addEventListener("offline", onOffline, true);
        document.addEventListener("online", onOnline, true);


        // document.addEventListener("message", "*");

        window.addEventListener('message',function(event) {
                        console.log(event.data);
           //window.open(event.data,'_blank','toolbarposition=top,location=no,enableViewportScale=yes,closebuttoncaption=Oks') // call function here
           //portal.mostrar();
        },false);

        //////////////////////////////////////////////
        // métodos ViewNext
        //////////////////////////////////////////////

        var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
        var eventer = window[eventMethod];
        var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";
        var domain = "file://";
        // Listen to message from child window
        eventer(messageEvent, function (e) {
            var key = e.message ? "message" : "data";
            var data = e[key];
            console.log('mensaje recibido')
            if(data==='volverPortal'){

                console.log("portal.mostrar desconexion");
                portal.mostrar();
            } else {

                  var base64Matcher = new RegExp("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{4})$");
                  if(base64Matcher.test(data)){
                   var fileToSave = b64toBlob(data, 'application/pdf');

                   window.resolveLocalFileSystemURL(cordova.file.externalDataDirectory, function (dir) {
                       console.log("got main dir" + cordova.file.externalDataDirectory);
                       dir.getFile("movimiento.pdf", { create: true }, function (file) {
                           file.createWriter(function (fileWriter) {
                               fileWriter.write(fileToSave);

                               cordova.plugins.fileOpener2.open(
                                   cordova.file.externalDataDirectory + 'movimiento.pdf', // You can also use a Cordova-style file uri: cdvfile://localhost/persistent/Download/starwars.pdf
                                   'application/pdf',
                                   {
                                       error: function (e) {
                                           console.log('Error status: ' + e.status + ' - Error message: ' + e.message);
                                       },
                                       success: function () {
                                           console.log('Archivo abierto correctamente');
                                       }
                                   }
                               );

                               //window.open("file://" + file);
                           }, function () {
                               console.log('Algo fallo');
                           });
                       });
                   });
                 }else{
                   console.log(data);
                   if (data.includes("http"))
                      window.open(data, '_blank');
                   else
                      window.open(config.rutaEstatico+data, '_blank');
                 }
               }



        }, false);
        //////////////////////////////////////////////
        // fin métodos ViewNext
        //////////////////////////////////////////////

    };

    var pdfios = function (url) {
        window.open(url,'_blank','toolbarposition=top,location=no,enableViewportScale,closebuttoncaption=Ok')
    };

    //////////////////////////////////////////////
    // métodos públicos
    //////////////////////////////////////////////
    var Inicializar = function () {
        $(document).on('mobileinit', InicializarMovil);

        $(document).on("click", "a[target='_blank']", pruebaURL);
        // $("#iframeAccesoClientes").on("abrirURLiOS", function (url) {

        // });
        $(document).ready(inicializarDevice);
        $("li").click(function () {
            var myClass = "." + this.className.split(' ').join('.');
            var myHref = $(myClass + " div div a").prop("href");
            alert(myHref);
        });
    };

    return {
        incluirPages: incluirPages,
        Inicializar: Inicializar,
        alertHref: alertHref,
        pdfios: pdfios,
        inicializarPagina: inicializarPagina,
        irAWebClasica: irAWebClasica
    };
} ());
