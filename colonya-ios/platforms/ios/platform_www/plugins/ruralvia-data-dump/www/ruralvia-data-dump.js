cordova.define("ruralvia-data-dump.ruralvia-data-dump", function(require, exports, module) {
var exec = require('cordova/exec');

exports.volcado = function(success, error) {
    exec(success, error, "Data-Dump", "volcado", []);
};

});
