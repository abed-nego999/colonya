/**
    Script:         push.js
    Autores:        Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          04/09/2015
    Descripción:    Módulo con las funciones y eventos encargados de registrar el token identificador de cada dispositivo y gestionar las notificaciones                       callbacks de las notificaciones push.
*/
var push = {};

push = (function () {
    'use strict';
    /////////////////////////////////////////
    // variables privadas
    /////////////////////////////////////////


    var serverKey = config.serverKey;
    var senderID = config.senderID;


    var regID;
    var lstPush = [];
    var plataformas = {
        android: 'android',
        blackberry: 'blackberry10',
        ios: 'ios',//revisar enumerado
        wp8: 'wp8'//revisar enumerado
    };

    /////////////////////////////////////////
    // métodos privados
    /////////////////////////////////////////
    var successHandler = function (result) {
    };

    var errorHandler = function (error) {
    };

    window.onNotificationGCM = function (e) {

        switch (e.event) {
            case 'registered':
                if (e.regid.length > 0) {
                    setRegID(e.regid);
                    console.log("Regid " + e.regid);
                }
                break;

            case 'message':
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth() + 1; //January is 0!
                var yyyy = today.getFullYear();

                if (dd < 10) {
                    dd = '0' + dd;
                }

                if (mm < 10) {
                    mm = '0' + mm;
                }

                today = yyyy + '-' + mm + '-' + dd;
                console.log("TODAY::::::::::   "+today);
                var actualizarBadge = avisos.isAvisosActivo();

                var paginaActual = $.mobile.activePage.attr('id');

                var actualizarAvisos = (paginaActual === 'page-avisos' && avisos.isAvisosActivo());

                db.insertar(e.message, today, 0, actualizarAvisos, actualizarBadge);
                break;

            case 'error':
                alert('Error Push!');
                break;

            default:
                break;
        }
    };

    var tokenHandler = function (result) {
        setRegID(result);
    };

    window.onNotificationAPN = function (event) {

        if (event.alert) {
        }

        if (event.sound) {
        }

        if (event.badge) {

        }
    };

    var registrarPushIos = function (pushNotification) {

        pushNotification.register(tokenHandler, errorHandler, {
            "badge": "true",
            "sound": "true",
            "alert": "true",
            "ecb": "window.onNotificationAPN"
        });
    };

    var registrarPush = function (pushNotification) {

        pushNotification.on('registration', function (data) {
            setRegID(data.registrationId);
            localStorage.setItem("RegID", data.registrationId);
            console.log("Regid " + data.registrationId);
        });

        pushNotification.on('notification', function (data) {

            var actualizarBadge = avisos.isAvisosActivo();
            var paginaActual = $.mobile.activePage.attr('id');
            var msg = data.message;

            var actualizarAvisos = (paginaActual === 'page-avisos' && avisos.isAvisosActivo());

            if (avisos.isAvisosActivo()) {
                alerta.avisoTitulo('Aviso: ' + data.additionalData.fecha, msg, actualizarLeido);
            }
            
            db.insertar(msg, data.additionalData, 0, actualizarAvisos, actualizarBadge);

            function actualizarLeido() {
                var id = data.additionalData.id ? data.additionalData.id : data.additionalData.id_mensaje;
                if (!id) {
                    id = data.additionalData.idMensaje;
                }
                cordova.plugins.notification.badge.increase();
                actualizarPush(id, true);
                portal.actualizarBadge();
                avisos.cargarXpull();
            };

        });

        pushNotification.on('error', function (e, data) {
        });

    };


    /////////////////////////////////////////
    // métodos publicos
    /////////////////////////////////////////

    var setRegID = function (id) {
        push.regID = id;
    };

    var getRegID = function () {
        return push.regID;
    };

    var isPushRegistered = function () {
        console.log("isPushRegistered:  " + (push.regID !== null && (!utils.esVacio(push.regID))));
        return (push.regID !== null && (!utils.esVacio(push.regID)));
    };

    var isPushVisible = function () {
        var plataforma = utils.getDevice().toLowerCase();

        switch (plataforma) {

            case plataformas.android:
                var version = utils.getVersion();
                return version > "4.0.0";
            case plataformas.ios:
                return true;
        }

    };

    var isPushAvailable = function () {

        if (!isPushRegistered()) {
            console.log("isPushAvailable:  false");
            return false;
        }

        console.log("isPushAvailable:  true");
        return true;
    };

    var getUnreadPush = function () {
        db.previa();
    };

    var deletePush = function (id) {
        db.previaDelete(id);
    };

    var actualizarPush = function (id, aviso) {
        db.actualizar(id, aviso);
    };

    var registrar = function () {
        //registro de dispositivo en la red de notificaciones push.

        var pushNotification = PushNotification.init({

            android: {
                senderID: senderID
            },
            ios: {

                alert: "true",
                badge: "true",
                sound: "true"
            },
            windows: {}
        });

        registrarPush(pushNotification);

        /*var plataforma = utils.getDevice().toLowerCase();

        switch (plataforma) {
        
        case plataformas.android:
            registrarPush(pushNotification);
            break;

        case plataformas.blackberry:

            break;

        case plataformas.ios:
            registrarPush(pushNotification);
            break;
                
        case plataformas.wp8:

            break;
        }*/

    };

    return {
        regID: regID,
        lstPush: lstPush,
        setRegID: setRegID,
        getRegID: getRegID,
        isPushVisible: isPushVisible,
        isPushAvailable: isPushAvailable,
        deletePush: deletePush,
        actualizarPush: actualizarPush,
        getUnreadPush: getUnreadPush,
        registrar: registrar
    };
} ());