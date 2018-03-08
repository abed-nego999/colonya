/**
    Script:         EE_O_RegistroApp.js
    Autor:          Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          27/10/2015
    Descripción:    Módulo con las funciones y atributos de la clase EE_O_RegistroApp
*/
var EE_O_RegistroApp = {};

EE_O_RegistroApp = (function () {
    'use strict';
    var	CR;
	var	MR;
	var	Entidad;
	var	CCifrado;
	var	usuario;
	var	nombre;

	var	entidad;
	var	campoCifrado;

	var	pushActivo;
	var	isumUa;
	var	accessUrl;
    
    var nuevo = function () {
        this.CR = undefined;
        this.MR = undefined;
        this.Entidad = undefined;
        this.CCifrado = undefined;
        this.usuario = undefined;
        this.nombre = undefined;
        this.entidad = undefined;
        this.campoCifrado = undefined;
        this.pushActivo = undefined;
        this.isumUa = undefined;
        this.accessUrl = undefined;
    };

	var getCampoCifrado = function () {
		return this.campoCifrado;
	};

    var setCampoCifrado = function (valor) {
		this.campoCifrado = valor;
	};

    var getnombre = function () {
		return this.nombre;
	};

    var setnombre = function (valor) {
		this.usuario = valor;
	};
    
    var setUsuario = function (valor) {
        
		if (valor !== null && valor !== undefined) {
            if (!utils.esNumerico(valor)) {
                this.usuario = valor.toUpperCase();
            } else {
                this.usuario = valor;
            }            
		} else {
			this.usuario = valor;
		}
	};

    var getUsuario = function () {
		var result = null;

		if (utils.esVacio(this.usuario) || this.usuario === null) {
			setUsuario(this.nombre);
		}

		if (this.usuario !== null || this.usuario !== undefined) {
            if (!utils.esNumerico(this.usuario)) {
                result = this.usuario.toUpperCase();
            } else {
                result = this.usuario;
            }
		}

		return result;
	};

    var setcCifrado = function (valor) {
		this.CCifrado = valor;
	};
    
    var getcR = function () {
		return this.CR;
	};

    var setcR = function (valor) {
		this.CR = valor;
	};

    var getmR = function () {
		return this.MR;
	};

    var setmR = function (valor) {
		this.MR = valor;
	};
    
    var setEntidad = function (valor) {
		this.Entidad = valor;
	};

    var getEntidad = function () {
		if (utils.esVacio(this.Entidad)) {
			setEntidad(this.entidad);
		}
		return this.Entidad;
	};

    var getcCifrado = function () {
		if (utils.esVacio(this.CCifrado)) {
			setcCifrado(this.campoCifrado);
		}
		return this.CCifrado;
	};

    var isPushActivo = function () {
		return this.pushActivo;
	};

    var setPushActivo = function (valor) {
		this.pushActivo = valor;
	};

    var getIsumUa = function () {
		return this.isumUa;
	};

    var setIsumUa = function (valor) {
		this.isumUa = valor;
	};

    var getAccessUrl = function () {
		return this.accessUrl;
	};

    var setAccessUrl = function (valor) {
		this.accessUrl = valor;
	};
    
    return {
        nuevo: nuevo,
        getCampoCifrado: getCampoCifrado,
        setCampoCifrado: setCampoCifrado,
        getnombre: getnombre,
        setnombre: setnombre,
        setUsuario: setUsuario,
        getUsuario: getUsuario,
        setcCifrado: setcCifrado,
        getcR: getcR,
        setcR: setcR,
        getmR: getmR,
        setmR: setmR,
        setEntidad: setEntidad,
        getEntidad: getEntidad,
        getcCifrado: getcCifrado,
        isPushActivo: isPushActivo,
        setPushActivo: setPushActivo,
        getIsumUa: getIsumUa,
        setIsumUa: setIsumUa,
        getAccessUrl: getAccessUrl,
        setAccessUrl: setAccessUrl,
        CR: CR,
        MR: MR,
        Entidad: Entidad,
        CCifrado: CCifrado,
        usuario: usuario,
        nombre: nombre,
        entidad: entidad,
        campoCifrado: campoCifrado,
        pushActivo: pushActivo,
        isumUA: isumUa,
        accesUrl: accessUrl
    };
}());
