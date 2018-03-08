/**
    Script:         EE_O_Get.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          25/11/2015
    Descripción:    Módulo con las funciones y atributos de la clase EE_O_AplicacionMovil
*/
var EE_O_Get = {};

EE_O_Get = (function () {
    'use strict';
    
    var resultado;
	var ListaUsuarioDispositivo = {};
	
    var nuevo = function () {
        this.resultado = undefined;
        this.ListaUsuarioDispositivo = {};
    };
    
	var getResultado = function () {
		return this.resultado;
	};
    
	var setResultado = function (valor) {
		this.resultado = valor;
	};
    
	var getListaUsuariosActivos = function () {
		return this.ListaUsuarioDispositivo;
	};
    
	var setListaUsuarioDispositivo = function (valor) {
		this.ListaUsuarioDispositivo = valor;
    };
    
    return {
        nuevo: nuevo,
        getResultado: getResultado,
        setResultado: setResultado,
        getListaUsuariosActivos: getListaUsuariosActivos,
        setListaUsuarioDispositivo: setListaUsuarioDispositivo,
        resultado: resultado,
        ListaUsuarioDispositivo: ListaUsuarioDispositivo
    };
}());
