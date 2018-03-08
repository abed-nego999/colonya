/**
    Script:         userHelper.js
    Autores:        Alberto Gómez Martín
    Empresa:        Altran
    Fecha:          06/11/2015
    Descripción:    Módulo con las funciones y eventos encargados del manejo de usuarios
*/
var userHelper = {};

userHelper = (function () {
    'use strict';
    
    /////////////////////////////////////////
    // métodos públicos
    /////////////////////////////////////////
    // Función que comprueba si hay un usuario registrado
    var getUsuarioRegistrado = function () {
        
        var registrado = store.local("list_users").get();
        
        if ( registrado === null) {
            return false;
        }
        
        return registrado.length > 0;
    };
    
    // Probar en la app, ver como funciona, y crear el código
    var updateUser = function (oldUser, newUser) {
        if (!utils.esNulo(oldUser) && !utils.esNulo(newUser)) {
            
            var listUsers = store.local("list_users").get();
            var len = listUsers.length;
            
            if (len > 0) {
                var i;
                var finalUserList = [];
                
                for (i = 0; i < len; i++) {
                    var user = listUsers[i];
                    
                    if (oldUser === user.usuario) {
                        user = newUser;
                    }
                    finalUserList.push(user);
                }
                store.local("list_users").set(finalUserList);
            }
            
            return true;
            
        } else {
            return false;
        }
    };
    
    var setUser = function (value) {
        
        var listaUsuarios = store.local("list_users").get();
        var list_users;
        
        //Si hay usuarios..
        if (listaUsuarios !== null && listaUsuarios.length > 0) {
            list_users = listaUsuarios;
            var saveUser = true;
            var i = 0;
            for (i = 0; i < listaUsuarios.length; i++) {
                if (_.isEqual(value, listaUsuarios[i])) {
                    saveUser = false;
                }
            }
            
            if (saveUser) {
                list_users.push(value);
            }
            store.local("list_users").set(list_users);
            
        } else {
            list_users = [];
            list_users.push(value);
            store.local("list_users").set(list_users);
        }
        
    };
    
    var getUsers = function () {
        var users = [];
        
        var usersAux = store.local("list_users").get();
        
        if (!utils.esNulo(usersAux) && usersAux.length > 0) {
            var len = usersAux.length;
            var i;
            
            //Cambiar esto por un users = usersAux??
            for (i = 0; i < len; i++) {
                users.push(usersAux[i]);
            }
        }
        
        return users;
            
    };
    
    var deleteUser = function (user) {
        var list_users = store.local("list_users").get();
        var list = [];
        
        var len = list_users.length;
        
        if (!utils.esNulo(list_users)) {
            var i;
            
            for (i = 0; i < len; i++) {
                if (user.usuario !== list_users[i].usuario) {
                    list.push(list_users[i]);
                }
            }
        }
        
        store.local("list_users").set(list);
    };
    
    var removeActiveUser = function () {
        store.local("active_user").set(null);
    };
    
    var saveActiveUser = function (value) {
        store.local("active_user").set(value);
    };
    
    var getGenericUser = function () {
        
        var generico = {};
        generico.Entidad = 9998;
        generico.campoCifrado = 9998;
        generico.usuario = 99999999;
        generico.pushActivo = false;
        
        return generico;

    };
    
    var getActiveUser = function () {
        var usuarioActivo = store.local("active_user").get();
        // console.log("xxxxxxxxxxx::::::"+usuarioActivo);

        
        if (usuarioActivo === null || localStorage.getItem("active_user") == "undefined" || localStorage.getItem("active_user") == undefined) {
            usuarioActivo = getGenericUser();
        }
        
        return usuarioActivo;
    };
    
    var getCampoCifrado = function () {
        var reg = getActiveUser();
        console.log("campoCifrado:::::::"+reg.campoCifrado);
        // if (!utils.esNulo(reg.campoCifrado)) {
            return reg.campoCifrado;
        // } else {
            // return reg.getcCifrado();
        // }
    };

    var getCampoPersona = function () {
        var reg = getActiveUser();
        
        if (!utils.esNulo(reg.campoPersona)) {
            return reg.campoPersona;
        } else {
            return reg.campoPersona = "";
        }
    };
    
    var setLoginActivityPass = function (value) {
        store.local("loginActivityPass").set(value);
    };
    
    // Si no se encuentra el valor 'login', no se ha seteado, por tanto, hay que ir a login
    var isLoginActivityPass = function () {
        var login = store.local("loginActivityPass").get();
        
        if (login === null || localStorage.getItem("active_user") == null || localStorage.getItem("active_app") == null) {
            return false;
        } else {
            return true;
        }
    };
    
    var setRegister = function (value) {
        store.local("register").set(value);
    };
    
    var reloadActivePushUsers = function () {
        avisos.mostrar();
    };
    
    return {
        getUsuarioRegistrado: getUsuarioRegistrado,
        updateUser: updateUser,
        setUser: setUser,
        getUsers: getUsers,
        deleteUser: deleteUser,
        removeActiveUser: removeActiveUser,
        saveActiveUser: saveActiveUser,
        getGenericUser: getGenericUser,
        getActiveUser: getActiveUser,
        getCampoCifrado: getCampoCifrado,
        getCampoPersona: getCampoPersona,
        setLoginActivityPass: setLoginActivityPass,
        isLoginActivityPass: isLoginActivityPass,
        setRegister: setRegister,
        reloadActivePushUsers: reloadActivePushUsers
    };
}());