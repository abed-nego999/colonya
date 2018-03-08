/**
    Script:         EE_O_AplicacionMovil.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          25/11/2015
    Descripción:    Módulo con las funciones y atributos de la clase EE_O_AplicacionMovil
*/
var EE_O_PortalPubAPP = {};

EE_O_PortalPubAPP = (function () {
    'use strict';
    
    var codigoRetorno = {};
	var Respuesta = {} ;
    
    var nuevo = function () {
        this.codigoRetorno = {};
        this.Respuesta = {};
    };
	
	var getRespeti = function () {
		return this.codigoRetorno;
	};
    
	var setRespeti = function (valor) {
		this.codigoRetorno = valor;
	};
    
	var getCache = function () {
		return this.Respuesta;
	};
    
	var setCache = function (valor) {
		this.Respuesta = valor;
    };
    
    return {
        nuevo: nuevo,
        getRespeti: getRespeti,
        setRespeti: setRespeti,
        getCache: getCache,
        setCache: setCache,
        Respuesta: Respuesta,
        codigoRetorno: codigoRetorno
    };
}());
