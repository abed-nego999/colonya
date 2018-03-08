var guardadoUsuarios = {};

guardadoUsuarios = (function(){
    var listadoMensajes = [];
    var listadoUsuarios = {};

    var iniciarGuardado = function(existe){
        db.selectUnread(recogerLista);
        if(existe){
            window.resolveLocalFileSystemURL(cordova.file.externalRootDirectory + "colonya/", function(entry){
                createFile(entry, "gu.co", true);
                createFile(entry, "gm.co", true);
            }, onErrorGetDir);
        } else{
            window.resolveLocalFileSystemURL(cordova.file.externalRootDirectory, function(entry){
                crearCarpeta(entry);
            }, onErrorGetDir);
        }
    };

    var crearCarpeta = function(entry){
        entry.getDirectory("colonya/", {create: true, exclusive: false}, function(otroEntry){
            createFile(otroEntry, "gu.co", true);
            createFile(otroEntry, "gm.co", true);
        }, onErrorGetDir);
    };

    var error = function(){
        console.log("Error");
    };

    function manageQueryResult(rows) {
        var items = [];

        for (var x = 0, length = rows.length; x < length; x++) {
            items.push(rows.item(x));
        }

        return items;
    }

    var recogerLista = function(res){
        listadoMensajes = manageQueryResult(res.rows);
    };
    
    function createFile(dirEntry, fileName, isAppend) {
        listadoUsuarios = JSON.parse(localStorage.getItem("list_users"));
        db.selectUnread( 
            function(res) {
              recogerLista(res);

              if(fileName === "gu.co"){
                var textoEncriptadoUsuarios = encriptarTexto(JSON.stringify(listadoUsuarios));
                
                dirEntry.getFile(fileName, {create: true, exclusive: false}, function(fileEntry) {
                    writeFile(fileEntry, textoEncriptadoUsuarios, isAppend);
                }, onErrorCreateFile);
    
              } else if(fileName === "gm.co"){
                var textoEncriptadoMensajes = encriptarTexto(JSON.stringify(listadoMensajes));
                dirEntry.getFile(fileName, {create: true, exclusive: false}, function(fileEntry) {
                    writeFile(fileEntry, textoEncriptadoMensajes, isAppend);
                }, onErrorCreateFile);
              }
            }
        );

    };

    var onErrorCreateFile = function(){
        console.log("onErrorCreateFile");
    };

    var onErrorLoadFs = function(){
        console.log("onErrorLoadFs");
    };

    var onErrorReadFile = function(){
        console.log("onErrorReadFile");
    };

    var onErrorGetDir = function(){
        console.log("onErrorGetDir");
    };

    var writeFile = function(fileEntry, dataObj) {
        fileEntry.createWriter(function (fileWriter) {
            fileWriter.onwriteend = function() {
                readFile(fileEntry);
            };
    
            fileWriter.onerror = function (e) {
                console.log("Failed file write: " + e.toString());
            };

            dataObj = new Blob([dataObj], {type: 'text/plain'});
    
            fileWriter.write(dataObj);
            
        });
    };

    var readFile = function(fileEntry) {
        fileEntry.file(function (file) {
            var reader = new FileReader();
    
            reader.onloadend = function() {
            };
    
            reader.readAsText(file);
    
        }, onErrorReadFile);
    };

    var encriptarTexto = function(texto){
        // Encrypt 
        var ciphertext = CryptoJS.AES.encrypt(texto, 'com.rsi');

        return ciphertext;
    };

    var desencriptarTexto = function(cifrado){
        // Decrypt 
        var bytes  = CryptoJS.AES.decrypt(cifrado.toString(), 'com.rsi');
        var plaintext = bytes.toString(CryptoJS.enc.Utf8);
        
    };

    return{
        iniciarGuardado: iniciarGuardado
    };

})();