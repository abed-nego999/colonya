/**
    Script:         ShowPush.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          28/10/2015
    Descripción:    Módulo con las funciones y atributos de la clase ShowPush
*/
var ShowPush = {};

ShowPush = (function () {
    'use strict';
    var	id;
	var	msg;
	var	fecha;
	var	fechaStr;
	var	leido;
	var	checked;

	var getFechaStr = function () {
		return this.fechaStr;
	};

    var setFechaStr = function (valor) {
		this.fechaStr = valor;
	};

    var getId = function () {
		return this.id;
	};

    var setId = function (valor) {
		this.id = valor;
	};
    
    var getMsg = function () {
		return this.msg;
	};

    var setMsg = function (valor) {
		this.msg = valor;
	};
    
    var getFecha = function () {
		return this.fecha;
	};

    var setFecha = function (valor) {
		this.fecha = valor;
	};
    
    var isLeido = function () {
		return this.leido;
	};

    var setLeido = function (valor) {
		this.leido = valor;
	};
    
    var isChecked = function () {
		return this.checked;
	};

    var setChecked = function (valor) {
		this.checked = valor;
	};
    
    return {
        id: id,
        msg: msg,
        fecha: fecha,
        fechaStr: fechaStr,
        leido: leido,
        isChecked: isChecked,
        getFechaStr: getFechaStr,
        setFechaStr: setFechaStr,
        getId: getId,
        setId: setId,
        getMsg: getMsg,
        setMsg: setMsg,
        getFecha: getFecha,
        setFecha: setFecha,
        isLeido: isLeido,
        setLeido: setLeido,
        checked: checked,
        setChecked: setChecked
    };
}());
