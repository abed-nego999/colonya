/**
    Script:         portal.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          03/09/2015
    Descripción:    Módulo con las funciones y eventos que se manejan dentro del portal principal
*/
var portal = {};

portal = (function () {
    'use strict';
    //var activeUser;
    var numAvisos = 0;
    var accesoClienteUrl;
    var listDocu;
    var unaVez = 0;


    var pruebaPage = "portal.html";
    var pruebaElemento = "#page-portal";
    
    //////////////////////////////////////////////
    // métodos privados
    //////////////////////////////////////////////
    var cargarCadenas = function () {
        utils.insertarIdioma(function () {
            $("a.lng-portal-AccesoClientes").text($.i18n.prop('portal-btnAcceso'));
            $(".lng-portal-Contacto").text($.i18n.prop('portal-btnContacto'));
            $(".lng-portal-OficinasYCajeros").text($.i18n.prop('portal-btnOficinasYCajeros'));
            $("h1.lng-titulo-push-no-disponible").text($.i18n.prop('pushNoDisponibleTitulo'));
            $("p.lng-pushConexion").html($.i18n.prop('pushConexion'));
            $("p.lng-pushNoConexion").text($.i18n.prop('pushNoConexion'));
        });
    };
    
    var cerrarRestoPadres = function () {
        $(".esPadre").removeClass("flechaAbajo");
        $(".esPadre").addClass("flechaDerecha");
        $(".esHijo").hide();
    };
    
    var abrirMenuPadre = function (product, indice) {
        product.removeClass("flechaDerecha");
        product.addClass("flechaAbajo");
        
        $(".padre" + indice).show();
    };
    
    var cerrarMenuPadre = function (product, indice) {
        product.removeClass("flechaAbajo");
        product.addClass("flechaDerecha");
        
        $(".padre" + indice).hide();
    };
    
    var opcionesPanel = function () {
        var listItems = $("#menu-listview li.itemCMS");
        listItems.each(function (index, li) {
            var product = $(li);

            //Recorro la lista de elementos, cuando coincida con el indice, miro si es hijo y añado la clase
            var i;

            for (i = 0; i < listDocu.length; i++) {
                if (i === index) {
                    var elemento = listDocu[i];

                    if (elemento.esHijo) {
                        product.addClass("esHijo");
                        product.addClass("padre" + elemento.Padre);
                        break;

                    } else if (elemento.esPadre) {
                        product.addClass("esPadre");
                        product.on("click", function () {

                            //Comprobamos si esta abierto/cerrado para cambiar el icono del padre
                            if ($(".padre" + elemento.Nav_Pos).is(':visible')) {
                                cerrarMenuPadre(product, elemento.Nav_Pos);
                            } else {
                                cerrarRestoPadres();
                                abrirMenuPadre(product, elemento.Nav_Pos);
                            }
                        });
                        break;
                    }
                }
            }
            
        });
        
        $(".esHijo").hide();
    };
    
    var showPopup = function (conexion) {
        if (conexion) {
            $("#pushConexion").show();
            $("#pushNoConexion").hide();
        } else {
            $("#pushConexion").hide();
            $("#pushNoConexion").show();
        }
        $("#popup-push-unavailable").popup().popup("open");
    };
    
    var cerrarPopup = function () {
        $("#popup-push-unavailable").popup().popup("close");
    };
    
    var obtenerUrlAcceso = function () {
        var ent = userHelper.getActiveUser().Entidad;
           portal.accesoClienteUrl = dataHelper.getAccess().Url + "&ISUM_ID_USUARIO=" + userHelper.getCampoCifrado() + "&ID_PERSONA=" + userHelper.getCampoPersona() + "&ID_DISPOSITIVO_MOVIL=" + "&ISUM_UA=" + dataHelper.getGoogleAnalytics() + "&ISUM_APP=1" + "&entidad=" + ent;
        console.log("portal.accesoClienteUrl::::   " +portal.accesoClienteUrl);
    };
    
    var irAAvisos = function () {
        if (utils.comprobarConexion()) {
            avisos.mostrar();
        } else {
            showPopup(utils.comprobarConexion());
        }
    };
    
    var irABuscador = function () {
        //comprobamos si el usuario se ha autenticado
          if(localStorage.getItem("active_user") == null || localStorage.getItem("active_user") == "null") {
            var logo = dataHelper.getLogoUrl();
            alerta.aviso("Es necesario estar registrado");
          utils.inyectar("registroUsuario.html", "#page-registroUsuario", { logo : logo });
        } else {       
            buscador.mostrar();
        }
        
    };
    
    var irAContacto = function () {
        contacto.mostrar();
    };
    
    var irAAccesoClientes = function () {
        obtenerUrlAcceso();
        accesoClientes.mostrar();
    };
    
    var irACampana = function (urlCampana) {
        var urlDominio = JSON.parse(localStorage.getItem("active_app")).Respuesta.Dominio.urlDominio +"/";
        console.log("urlCampana::  "+urlCampana);
        if (urlCampana.indexOf(urlDominio) === 0) {
            
            campana.mostrar(urlCampana);
        setTimeout(function(){
            utils.quitarLoading();
            campana.ocultarCabeceraCMS();
        }, 1000);
        } else {
            window.open(urlCampana, "_system");
        }
        
    };
    
    var actualizarBadge = function () {
        // alert("actualizarBadge");
        //Cargamos los avisos
        numAvisos = 0;
        var i;
        
        for (i = 0; i < push.lstPush.length; i++) {
            if (!push.lstPush[i].leido) {
                numAvisos++;
            }
        }
        cordova.plugins.notification.badge.set(numAvisos);
        console.log("Avisos sin leer: " + numAvisos);
        
        //Cargamos el globo con el número de avisos en el icono
        if (numAvisos === 0) {
            $("#badgePush").removeClass("badgePush");
        } else {
            $("#badgePush").addClass("badgePush");
            //Limitar el numero de avisos a 99
            if (numAvisos > 99) {
                $("#badgePush").text("99+");
                
                // Adaptamos la vista para 3 digitos
                $(".badgePush").css("cssText", 'margin-right: 0em !important');
            } else {
                $("#badgePush").text(numAvisos);
                
                //Adaptamos la vista a 2-1 digito/s
                if (numAvisos > 9) {
                    $(".badgePush").css("cssText", 'margin-right: 0.4em !important');
                } else {
                    $(".badgePush").css("cssText", 'margin-right: 0.6em !important');
                }
            }
            
            
        }
        return $.Deferred().resolve();
    };
    
    var loadData = function (navegar, refrescar) {
        
        //Cargamos los banner
        var banners = dataHelper.getBanner();
        
        //Cargamos el logo
        var logo = dataHelper.getLogoUrl();
        
        //Cargamos los menús
        listDocu = dataHelper.getMenu();
        
        //Cargamos el pie
        var pie = dataHelper.getPie();
        
        var usuarioRegistrado = userHelper.getUsuarioRegistrado();
        
        //Al ser llamado varias veces, solo navegamos una vez
        if (navegar) {

            var url;

            if (app.incluirPages) {
                url = "pages/portal.html";
                app.incluirPages = false;
            } else {
                url = "portal.html";
            }
            
            utils.inyectar(url, "#page-portal", {
                logo: logo,
                pie: pie,
                productos: banners,
                usuarioRegistrado: usuarioRegistrado
            });
        }
    };

    var desplegarHijos = function () {
        if($(".esHijo").is(':visible')) {
            $(".padre").removeClass("flechaAbajo");
            $(".padre").addClass("flechaDerecha");
            $(".esHijo").hide();
        } else {
            $(".padre").removeClass("flechaDerecha");
            $(".padre").addClass("flechaAbajo");
            $(".esHijo").show();
        }
    };

    var reordenarSubmenus = function (lista) {
        var listaOrdenada = [];
        var posicion;
        for(var i = 0; i < lista.length; i++){

            var elemento = lista[i];
            posicion = elemento.Nav_Pos;

            if(elemento.Padre === "0" && elemento.Nivel === "1") {
                if(lista[i+1] !== undefined && lista[i+1].Nivel === "2") {
                    elemento.esPadre = true;    
                }
                
            } else {
                elemento.esHijo = true;

            }
            listaOrdenada.splice(posicion, 0, elemento);

        }
        return listaOrdenada;
    };

    var crearMenu = function () {
        
        var itemsMenu = dataHelper.getActiveApp().Respuesta.ListaMenu;
        var rutaCMS = dataHelper.getActiveApp().Respuesta.Dominio.urlDominio;
        
        var lista = [];
        var listaItems = [];
        
        if(!utils.esNulo(dataHelper.getActiveApp().Respuesta.ListaMenu)) {
            var numItemsMenu = dataHelper.getActiveApp().Respuesta.ListaMenu.length;
            
            var obj1 = {}, obj2 = {}, obj3 = {}, obj4 = {}, obj5 = {}, obj6 = {}, obj7 = {}, obj8 = {}, obj9 = {}, obj10 = {}, obj11 = {}, obj12 = {}, obj13 = {}, obj14 = {}, obj15 = {}, obj16 = {}, obj17 = {}, obj18 = {}, obj19 = {}, obj20 = {};
            
            listaItems = [obj1, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10, obj11, obj12, obj13, obj14, obj15, obj16, obj17, obj18, obj19, obj20];
            
            for(var i = 0; i < numItemsMenu; i++){
                
                listaItems[i].Nivel = itemsMenu[i].profundidadMenu;
                listaItems[i].Titulo = itemsMenu[i].tituloMenu ? itemsMenu[i].tituloMenu : "Título Provisional";
                listaItems[i].Nav_Pos = i+2;
                listaItems[i].logo = rutaCMS + itemsMenu[i].urlImagen;
                listaItems[i].clase = itemsMenu[i].tituloMenu ? itemsMenu[i].tituloMenu.replace(/\s+/g, '') : "ClaseProvisional";
                listaItems[i].Padre = itemsMenu[i].ordenPadre;
                listaItems[i].Orden = itemsMenu[i].ordenMenu;
                listaItems[i].Url = rutaCMS + itemsMenu[i].urlMenu;
                if( itemsMenu[i].tituloMenu === "Sin Titulo!!!" || listaItems[i].clase === "ClaseProvisional") {
                    listaItems[i].clase = listaItems[i].clase.replace("!!!", '');
                    // obj.Url = "#";
                } else {
                    listaItems[i].Url = rutaCMS + itemsMenu[i].urlMenu;
                }
                
                lista.push(listaItems[i]);


            }
            
            
        }

        lista = reordenarSubmenus(lista);
        return lista;
        
    };
    
    var cbAppMovil = function (res) {
        utils.quitarLoading();
        EE_O_PortalPubAPP.nuevo();
        EE_O_PortalPubAPP.setCache(res.EE_O_PortalPubAPP.Respuesta);
        EE_O_PortalPubAPP.setRespeti(res.EE_O_PortalPubAPP.codigoRetorno);
        
        // ¿Pongo el resto de parámetros a null??
        JSONData.setApp(EE_O_PortalPubAPP);
        
        dataHelper.saveActiveApp(JSONData.getApp());
        
        if (JSONData.getApp() !== null) {
        
            if (JSONData.getApp().getRespeti() === '1') {
                loadData(true);
                
                listDocu = crearMenu();                
                var promise = utils.inyectarRefrescar("pages/panel.html", "#menu-listview", {
                    listDocu: listDocu
                });
                
                promise.done(opcionesPanel);
                
            } else {
                if (JSONData.getApp().getRespeti().MR !== null) {
                    alerta.aviso(JSONData.getApp().getRespeti().MR);
                }
            }
        } else {
            utils.cerrarApp();
        }
    };
    
    var cbErrorAppMovil = function () {
        utils.quitarLoading();
        // alerta.aviso($.i18n.prop('errorGenerico'));
        alerta.aviso('Error al cargar los datos, compruebe su conexión');
    };
    
    var readApplicationData = function () {
        if ( localStorage.getItem("list_users") != null && localStorage.getItem("active_user") === "undefined") {
            // var a = JSON.parse(localStorage.getItem("list_users"))[0];
            // var b = JSON.stringify(a);
            localStorage.setItem("active_user", JSON.stringify(JSON.parse(localStorage.getItem("list_users"))[0]));
        }
        var ent = userHelper.getActiveUser().Entidad;
        var us = userHelper.getActiveUser().usuario;
        var idioma = "ca";
        
        var jsonParam = {};
        
        jsonParam.codigoEntidad = ent;
        jsonParam.usuarioBE = us || $("#selectUsuario").val();
        jsonParam.idDispositivo = localStorage.getItem("UUID");
        jsonParam.idioma = idioma;

        if(jsonParam.codigoEntidad === "undefined" || jsonParam.codigoEntidad === undefined) {
            jsonParam.codigoEntidad = userHelper.getGenericUser().Entidad;
        }

        if(jsonParam.usuarioBE === "undefined" || jsonParam.usuarioBE === undefined) {
            jsonParam.usuarioBE = userHelper.getGenericUser().usuario;
        }

        
        var datosSinCodificar = "?codigoEntidad="+jsonParam.codigoEntidad+"&usuarioBE="+jsonParam.usuarioBE+"&idDispositivo="+jsonParam.idDispositivo+"&idioma="+jsonParam.idioma;
        var metodo = "EE_I_PortalPubAPP";

        var url = config.servidor + config.Aplicacion + metodo + datosSinCodificar;
        console.log("urlMostrarPortal::::  "+url);
        // alert("urlPortal: " +url);
        utils.mostrarLoading();

        $.ajax({

                url: url,
                dataType: 'json',
                contentType: 'application/json',
                type: "GET",
                headers: config.headers,
                success: function(data, response) {
                    console.log("data: "+JSON.stringify(data));
                    // alert("data: "+JSON.stringify(data));
                    cbAppMovil(data);
                    downloadData();
                    comprobarNumAvisos();
                    if(localStorage.getItem("active_app") !== "undefined") {
                        localStorage.setItem("active_app2", localStorage.getItem("active_app"));
                    }
                },
                error: function(response) {
                    cbErrorAppMovil();
                }
            });        
    };
    
    var downloadData = function () {
        if (dataHelper.getActiveApp() === null) {
            readApplicationData();
        } else {
            loadData(false);
        }
    };
    
    var setDynamicButtons = function () {
        
        var alturaOficinasCajeros = $("#buscadorButton").height();
        $("#contactoButton").height(Math.floor(alturaOficinasCajeros));
        
    };
    
    var setLabelCenter = function () {
        
        var alturaLabelBuscador = $("#lblBuscador").outerHeight();
        var alturaLabelContacto = $("#lblContacto").outerHeight();
        
        var diff = alturaLabelBuscador - alturaLabelContacto;
        
        if (diff > 0) {
            var marginTopContacto = (diff / 2);
            $("#lblContacto").css("padding-top", marginTopContacto);
        } else {
            $("#lblContacto").css("padding-top", 0);
        }
    };
    
    // Función llamada al detectar un evento de swite left
    var mostrarPanelSwipe = function (event) {
        if ($.mobile.activePage.jqmData("panel") !== "open") {
            if (event.type === "swipeleft") {
                $("#panel-menu").panel("open");
                $(".itemCMS div img").first().trigger("click");
            }
        }
    };

    var tapPanel = function () {
        
        $(".headerRuralviaPortal div.ui-block-c").trigger("swipeleft");
    };
    
    var comprobarNumAvisos = function () {
        
        //Actualizamos el numero de avisos solo si está el push activo
        //avisos.comprobarPushActivoUsuario();

        var actualizarNumAvisos = avisos.isAvisosActivo();
        
        if (actualizarNumAvisos) {
            setTimeout(function(){ actualizarBadge(); }, 500);
        } else {
            $("#badgePush").removeClass("badgePush");
        }
        
    };

    var comprobarPadre = function (){
        if($(".padre").attr("ordenmenu") === $(".padre").next().attr("ordenpadre") && !$(".padre").next().attr("ordenpadre").hasClass("esHijo")){
            $(".padre").next().attr("ordenpadre").removeClass("esHijo");
            $(".padre").next().attr("ordenpadre").show();
        } else {
            $(".padre").next().attr("ordenpadre").addClass("esHijo");
            $(".padre").next().attr("ordenpadre").hide();
        }
    };
    
    var iniciarElementos = function () {
    };
    
    var iniciarSlider = function () {
        // Plugin GlideJS: https://github.com/jedrzejchalubek/Glide.js
        $('.js-slider').glide({
            autoplay: 9000,
            arrows: false,
            touchDistance: 15
        });
    };

    var irAPaginasMenu = function (event) {
        event.preventDefault();
        // var urlPaginasMenu = $(".itemCMS a").data("url");
        // console.log("irAPaginasMenu.url:  "+urlPaginasMenu);
        // paginasMenu.mostrar(urlPaginasMenu);
        

        $(".itemCMS:not('.Iralawebclásica') a.enlacePanel").each(function (index) {
            $(this).on("click", function () {
                var urlPaginasMenu = $(this).data('url');
                
                paginasMenu.mostrar(urlPaginasMenu);
            });
        });
        $(".itemCMS.Iralawebclásica a.enlacePanel").each(function (index) {
            $(this).on("click", function () {
                app.Iralawebclásica();
            });
        });
    };

    var mostrar = function () {
        
            readApplicationData();
            db.getLastMsgInit()
                .then(avisos.reloadActivePushUser)
                .then(avisos.chargeMessages)
                .then(avisos.refrescarListaMensajes)
                .then(actualizarBadge)
                .then(funcionPrueba)
                .fail(funcionError);
        
    };
    
    var iniciarPagina = function () {
        // Ponemos la pantalla del portal siempre a portrait
        if (!config.simuleTerminal) {
            utils.setOrientation("portrait");
        }

        
        
        cargarCadenas();
        
        iniciarSlider();
        setDynamicButtons();
        setLabelCenter();
        
        //mostrar();

        
        $(".headerRuralviaPortal div.ui-block-c").on('swipeleft', mostrarPanelSwipe);
        
        $(".publi").each(function (index) {
            $(this).on("click", function () {
                var urlCampana = $(this).data('url');
                
                irACampana(urlCampana);
            });
        });

        funcionPrueba();
        setTimeout(function(){
                        navigator.splashscreen.hide();
                    }, 1000);
        

    };

    
    
    var iniciarEventos = function () {
        $(document).on("vclick", "a#buscadorButton", irABuscador);
        $(document).on("vclick", "a#contactoButton", irAContacto);
        $(document).on("vclick", "a#accesoClientesButton", irAAccesoClientes);
        $(document).on("vclick", "#btnPush", irAAvisos);
        $(document).on("vclick", "#cerrarPopupPushUnavailable", cerrarPopup);
        $(document).on("vclick", ".item-menu", comprobarPadre);
        $(document).on("click", ".headerRuralviaPortal div.ui-block-c", tapPanel);
        
        
        
    };
    
    //////////////////////////////////////////////
    // métodos públicos
    //////////////////////////////////////////////   
    var inicializar = function () {
        
        $(document)
            .one('pageinit', '#page-portal', iniciarEventos)
            .on("pagebeforeshow", "#page-portal", iniciarElementos)
            .on("pageshow", "#page-portal", iniciarPagina);
    };

    var funcionPrueba = function () {
        if(localStorage.getItem("volcado") === "true" || localStorage.getItem("volcado") === true) {
            
                // avisos.backAvisos();
               $("#btnPush").click();

               
                
            
            
        }
    };

    var funcionError = function () {
        console.log("error");
        alert("error");
    }
    
    
    
    return {
        accesoClienteUrl: accesoClienteUrl,
        actualizarBadge: actualizarBadge,
        mostrar: mostrar,
        irAAvisos: irAAvisos,
        cargarCadenas: cargarCadenas,
        inicializar: inicializar,
        desplegarHijos: desplegarHijos,
        irAPaginasMenu: irAPaginasMenu
    };
}());
