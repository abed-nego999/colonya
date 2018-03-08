/**
    Script:         alerta.js
    Autores:        Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          03/09/2014
    Descripción:    Módulo con las funciones y eventos encargados generar las alertas genéricas de
                    la aplicación
*/
var alerta = {};

alerta = (function () {
    'use strict';
    /////////////////////////////////////////
    // métodos privados
    /////////////////////////////////////////
    var RealizarCallback = function () {
        // utils.cerrarApp();
    };
    
    var ConfirmarCallback = function (index) {
        switch (index) {
        case 1:
            if (utils.getDevice() === 'android' || utils.getDevice() === 'Android') {
                cordova.plugins.settings.openSetting("wifi");
            } else {
                cordova.plugins.settings.open();
            }
            break;
        case 2:
            utils.cerrarApp();
            break;
        default:
            break;
        }
    };
    
    var realizarAlerta = function (titulo, mensaje, callback) {
        var cb = callback ? callback : RealizarCallback;

        if (config.simuleTerminal) {
            alert(mensaje);
        } else {
            navigator.notification.alert(
                mensaje,
                cb,
                titulo,
                "Aceptar"
            );
        }
    };
    
    /////////////////////////////////////////
    // métodos públicos
    /////////////////////////////////////////
 
    var aviso = function (mensaje) {
        realizarAlerta('Aviso', mensaje);
    };
    
    var avisoTitulo = function (titulo, mensaje, callback) {
        realizarAlerta(titulo, mensaje, callback);
    };
    
    var wifiSettings = function (mensaje, callback) {
        if (utils.getDevice() === 'android' || utils.getDevice() === 'Android') {
            navigator.notification.confirm(
                mensaje,
                ConfirmarCallback,
                "No hay conexión",
                ["Ir a ajustes", "Salir de la aplicación"]
            );
        } else {
            navigator.notification.confirm(
                mensaje,
                ConfirmarCallback,
                "No hay conexión",
                ["Ir a ajustes"]
            );
        }
    };
    
    var confirmar = function (mensaje, callback) {
        if (config.simuleTerminal) {
            alert(mensaje);
            callback();
        } else {
            navigator.notification.confirm(
                mensaje,
                (callback || ConfirmarCallback),
                $.i18n.prop('confirmar'),
                [$.i18n.prop('si'), $.i18n.prop('no')]
            );
        }
    };
    
    return {
        aviso: aviso,
        avisoTitulo: avisoTitulo,
        wifiSettings: wifiSettings,
        confirmar: confirmar
    };
}());