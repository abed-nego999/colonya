/**
    Script:         JSONData.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          28/10/2015
    Descripción:    Módulo con las funciones y atributos de la clase EE_O_RegistroApp
*/
var JSONData = {};

JSONData = (function () {
    'use strict';
    var	app;
	var	reg;
	var	push;
	var	messages;

	var getApp = function () {
		return this.app;
	};

    var setApp = function (valor) {
		this.app = valor;
	};

    var getReg = function () {
		return this.reg;
	};

    var setReg = function (valor) {
		this.reg = valor;
	};
    
    var getPush = function () {
		return this.push;
	};

    var setPush = function (valor) {
		this.push = valor;
	};
    
    var getMessages = function () {
		return this.messages;
	};

    var setMessages = function (valor) {
		this.messages = valor;
	};
    
    return {
        getApp: getApp,
        setApp: setApp,
        getReg: getReg,
        setReg: setReg,
        getPush: getPush,
        setPush: setPush,
        getMessages: getMessages,
        setMessages: setMessages,
        app: app,
        reg: reg,
        push: push,
        messages: messages
    };
}());
