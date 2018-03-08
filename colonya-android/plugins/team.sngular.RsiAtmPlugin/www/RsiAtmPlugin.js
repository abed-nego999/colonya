var RsiAtmPlugin = 
{ init: function(config) { 
	cordova.exec(function(winParam) {}, 
		function(error) {}, 
		"RsiAtmPlugin", 
		"init", 
		[config]); 
	}
}

