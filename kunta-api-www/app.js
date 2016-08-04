(function() {
  'use strict';
  
  /*var KuntaApi = require('./kunta-api')({
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
  });*/
 
  var express = require('express');
  var path = require('path');
  
  var app = express();
  var http = require('http').Server(app);
  
  app.set('view engine', 'pug');
  app.set('views', path.join(__dirname, 'views'));
  app.use(express.static(path.join(__dirname, 'public')));
 
  require('./routes')(app);
 
  http.listen(3000, function(){
    console.log('listening on *:3000');
  });
 
}).call(this);