(function() {
  'use strict';
  
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