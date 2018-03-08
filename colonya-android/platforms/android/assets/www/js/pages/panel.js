/**
    Script:         panel.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          16/09/2015
    Descripción:    Módulo con las funciones y eventos que se manejan dentro del panel
*/
var panel = {};

panel = (function () {
    'use strict';
    
    //////////////////////////////////////////////
    // métodos privados
    //////////////////////////////////////////////
    var cargarCadenas = function () {
        utils.insertarIdioma(function () {
            
        });
    };
    
    var iniciarElementos = function () {
        cargarCadenas();
    };
    
    var iniciarEventos = function () {
        
    };
    
    //////////////////////////////////////////////
    // métodos públicos
    //////////////////////////////////////////////
        
    var inicializar = function () {
        $(document)
            .one('pageinit', '#panel-menu', iniciarEventos)
            .on("pageinit", "#panel-menu", iniciarElementos);
    };
    
    return {
        inicializar: inicializar
    };
}());