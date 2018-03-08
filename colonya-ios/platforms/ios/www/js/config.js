var config = {
    version: '1.0.0',
    // servidor: 'http://soa03.risa:7001/SOA_AppMovil_v2/Empresa/', // Desarrollo
    // servidor: 'http://soa02.risa/SOA_AppMovil_v2/Empresa/', // Test

    // servidor: 'http://soa02.risa/SOA_AppMovil_v2/Empresa/',
    // http://soa02.risa/SOA_AppMovil_v2/Empresa/PS/rest/v1/
    // http://soa02.risa/SOA_AppMovil_v2/Empresa/PS/rest/v1/SE_RMV_PortalPubAPP
    // servidor: 'http://www.ruralserviciosinformaticos.com/empresa/SE_RMV_PortalPubAPP',
    // Aplicacion: 'PS/SE_RMV_AplicacionMovil_REST',
    // Aplicacion: 'PS/rest/v1/SE_RMV_PortalPubAPP_REST',
    Aplicacion: 'PS/rest/v1/SE_RMV_PortalPubAPP/',
    // Registro: 'SE_RMV_RegistroAPP_REST',
    Registro: 'PS/rest/v2/SE_RMV_RegistroAPP/', //TEST
    Push: 'PS/SE_RMV_MensajeriaPush',
    Mensajes: 'PS/SE_RMV_MensajesPush',

    rutaContacto: "bl/colonya/movil/ca/contacto/index.html",


    rutaEstatico2: "http://www02.ruralvia.com",

    os: 'ios',
    online: true,
    lang: 'ca-ES',


    // PRO!!!!
    servidor: 'http://services.ruralvia.com/SOA_AppMovil_v2/Empresa/', // Produccion
    rutaEstatico: "http://www.colonya.com/cms/estatico/", // Produccion
    rutaSession : "https://www.colonya.com/isum/TestSession.jsp",

    headers: {
                    CODSecUser: "",
                    CODSecTrans: "",
                    CODSecIp: "10.1.246.12",
                    CODSecEnt: "2056",
                    CODTerminal: "",
                    CODApl: "BDP",
                    CODCanal: "18"
    },



    serverKey : 'AIzaSyDPaFRwkTfLGUgDovW6ZrldT9e77mYR7sU',
    senderID : '192046065163',

    logConsole: false, // debe estar a false cuando generemos para un terminal movil real
    simuleTerminal: false // debe estar a false cuando generemos para un terminal movil real
};
