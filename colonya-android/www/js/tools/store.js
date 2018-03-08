/**
    Script:         store.js
    Autores:        Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          08/09/2015
    Descripción:    Módulo con las funciones y eventos encargados de almacenar información en 
                    sesión y en local
*/
var store = {};

store = (function () {
	'use strict';
    
    //////////////////////////////////
	// atributos 
	////////////////////////////////// 
    var storage = null,
        keyName = null;
    
	//////////////////////////////////
	// metodos privados
	//////////////////////////////////    
	var get = function () {
        
        var cadena = storage.getItem(keyName);
             
        // En Android ~2.3 el JSON.parse se comporta mal con el null,
        // Hago esto para evitar que la app rompa en estas versiones
        if (cadena !== null) {
            cadena = JSON.parse(cadena);
        }
        
		return cadena;
	};
	
    var set = function (valor) {
        var cadena = JSON.stringify(valor);
        
        storage.setItem(keyName, cadena);
    };
    
    //////////////////////////////////
	// metodos públicos
	//////////////////////////////////  
    var local = function (clave) {
        storage = window.localStorage;
        keyName = clave;
        
        return {
            get: get,
            set: set
        };
    };
     
    var sesion = function (clave) {
        storage = window.sessionStorage;
        keyName = clave;
        
        return {
            get: get,
            set: set
        };
    };
    
    return {
        local: local,
        sesion: sesion
    };
}());