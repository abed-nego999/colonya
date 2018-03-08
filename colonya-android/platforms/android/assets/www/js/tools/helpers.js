/**
    Script:         helpers.js
    Autores:        Alberto Gómez
    Empresa:        Altran
    Fecha:          07/12/2015
    Descripción:    Módulo con las funciones que registran nuevos helpers en la libreria handelbars
*/


Handlebars.registerHelper('rsi-dateFormatPush', function (fecha) {
    'use strict';
    var today = new Date();
    var date = fecha.split("/");
    var dia = date[0];
    var mes = date[1];
    var ano = date[2].split(" ")[0];

    date = new Date("" + ano + "-" + mes + "-" + dia + "");

    if (date.getYear() === today.getYear() && date.getMonth() === today.getMonth() && date.getDate() === today.getDate()) {
        return new Handlebars.SafeString("Hoy");
    } else {
        return new Handlebars.SafeString(fecha.split(" ")[0]);
    }
});

Handlebars.registerHelper('esURL', function (indicador, opciones) {
    'use strict';

    return (indicador === '0') ? opciones.fn(this) : opciones.inverse(this);
});