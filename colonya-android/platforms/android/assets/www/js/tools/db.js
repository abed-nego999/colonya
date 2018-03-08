/**
    Script:         db.js
    Autores:        Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          03/09/2014
    Descripción:    Módulo con las funciones y eventos encargados de la base de datos
*/

var db = {};

db = (function () {
    'use strict';
    var baseDatos;
    /////////////////////////////////////////
    // métodos privados
    /////////////////////////////////////////

    var abrirBD = function () {
        baseDatos = window.sqlitePlugin.openDatabase({ name: "PushDB.db" });
    };

    var cerrarBD = function () {
        baseDatos.close();
    };

    var createDB = function () {
        abrirBD();

        baseDatos.transaction(function (tx) {
            tx.executeSql('DROP TABLE IF EXISTS Push');
            tx.executeSql('CREATE TABLE IF NOT EXISTS Push (id INTEGER PRIMARY KEY, msg TEXT, fecha DATE, leido INTEGER)');
            store.local("hayDB").set(true);
        }, function (e) {
            console.log("ERROR: " + e.message);
        });

    };

    var onSuccessRead = function (res, aviso, refrescarPortalBadge) {

        var i = 0;
        push.lstPush = [];
        for (i = 0; i < res.rows.length; i++) {
            var showPush = {
                id: res.rows.item(i).id,
                msg: res.rows.item(i).msg,
                fecha: res.rows.item(i).fecha,
                leido: res.rows.item(i).leido > 0
            };
            push.lstPush.push(showPush);
        }

        if (aviso) {
            avisos.cerrarPopup();
            avisos.refrescarListaMensajes();
        }
        if (refrescarPortalBadge) {
            portal.actualizarBadge();
        }
    };

    var onSuccessDelete = function (res) {

        var i = 0;
        push.lstPush = [];
        for (i = 0; i < res.rows.length; i++) {
            var showPush = {
                id: res.rows.item(i).id,
                msg: res.rows.item(i).msg,
                fecha: res.rows.item(i).fecha,
                leido: res.rows.item(i).leido > 0
            };
            push.lstPush.shift(showPush);
        }

        avisos.cerrarPopup();

        avisos.refrescarListaMensajes();

    };

    var selectUnread = function (callback, aviso, refrescarBadge) {
        abrirBD();

        baseDatos.transaction(function (tx) {
            tx.executeSql("select * from Push order by fecha desc", [], function (tx, res) {
                callback(res, aviso, refrescarBadge);
            }, function (error) {
                console.log("Error en select: " + error.message);
            });

        }, function (e) {
            console.log("ERROR: " + e.message);
        }, function () {
            cerrarDB();
        });

    };

    var deleteRow = function (onSuccessDelete, id) {
        abrirBD();

        baseDatos.transaction(function (tx) {
            tx.executeSql("DELETE FROM Push WHERE id=?", [id], function (tx, res) {
                selectUnread(onSuccessRead, true, false);
            }, function (e) {
                console.log("Error: " + e.message);
            });
        }, function (e) {
            console.log("ERROR: " + e.message);
        }, function () {
            cerrarDB();
        });
    };

    var previaDelete = function (id) {
        deleteRow(onSuccessDelete, id);
    };

    var previa = function () {
        selectUnread(onSuccessRead);
    };

    var hayDB = function () {
        return (store.local("hayDB").get() !== null);
    };

    var insertar = function (msg, data, leido, aviso, refrescarBadge) {

        var id = data.id ? data.id : data.id_mensaje;

        if (!id) {
            id = data.idMensaje;
        }
        console.log("data.fecha:::::::::   "+data.fecha);
        baseDatos.transaction(function (tx) {
            tx.executeSql("INSERT INTO Push (id, msg, fecha, leido) VALUES (?,?,?,?)", [id, msg, data.fecha, leido], function (tx, res) {
                selectUnread(onSuccessRead, aviso, refrescarBadge);
            }, function (e) {
                // console.log("ERROR: " + e.message);
                console.log("ERROR: " + JSON.strigify(e));
            });
        });

    };

    var actualizar = function (id, aviso) {
        baseDatos.transaction(function (tx) {
            tx.executeSql("UPDATE Push SET leido='1' WHERE id=?", [id], function (tx, res) {
                selectUnread(onSuccessRead, aviso, true);
            }, function (e) {
                console.log("ERROR: " + e.message);
            });
        });
    };

    var getLastMsgInit = function () {
        var promise = $.Deferred();

        $(document).on('deviceready', getDate);

        return promise;

        function getDate() {
            abrirBD();
            baseDatos.transaction(function (tx) {
                tx.executeSql("select fecha from Push order by fecha desc", [], function (tx, res) {
                    promise.resolve(res);
                }, function (error) {
                    promise.reject();
                });
            }, function (e) {
                promise.reject();
            }, function () {
                $(document).off('deviceready', getDate);
                cerrarDB();
            });
        }
    };

    var getLastMsg = function () {
        var promise = $.Deferred();

        getDate();

        return promise;

        function getDate() {
            abrirBD();
            baseDatos.transaction(function (tx) {
                tx.executeSql("select fecha from Push order by fecha desc", [], function (tx, res) {
                    promise.resolve(res);
                }, function (error) {
                    promise.reject();
                });
            }, function (e) {
                promise.reject();
            }, function () {
                $(document).off('deviceready', getDate);
                cerrarDB();
            });
        }
    };



    return {
        baseDatos: baseDatos,
        hayDB: hayDB,
        insertar: insertar,
        actualizar: actualizar,
        deleteRow: deleteRow,
        selectUnread: selectUnread,
        previaDelete: previaDelete,
        previa: previa,
        createDB: createDB,
        getLastMsg: getLastMsg,
        getLastMsgInit: getLastMsgInit
    };
} ());