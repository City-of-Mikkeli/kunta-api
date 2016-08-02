(function() {
  'use strict';
  
  var KuntaApi = require('./kunta-api')({
    basePath: 'http://127.0.0.1:8080/v1' 
  });
  
  var ServicesApi = new KuntaApi.ServicesApi();
  
  ServicesApi.listServices().then(function(data) {
    console.log('API called successfully.');
    console.log(data);
  }, function(error) {
    console.error(error);
  });
  
  ServicesApi.findService('example').then(function(data) {
    console.log('API called successfully.');
    console.log(data);
  }, function(error) {
    console.error(error);
  });
 
}).call(this);