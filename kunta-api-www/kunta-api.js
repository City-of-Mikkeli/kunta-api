(function() {
  'use strict';
  
  var KuntaApiClient = require('kunta-api-client');

  module.exports = function (opts) {
    var clientInstance = KuntaApiClient.ApiClient.instance;
    
    if (opts.basePath) { 
      clientInstance.basePath = opts.basePath;
    }
    
    return KuntaApiClient;
  }

}).call(this);