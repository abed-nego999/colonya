var exec = require('cordova/exec');

exports.volcado = function(success, error) {
    exec(success, error, "Data-Dump", "volcado", []);
};

exports.activeNotification = function(success, error) {
    exec(success, error, "Data-Dump", "activeNotification", []);
};
