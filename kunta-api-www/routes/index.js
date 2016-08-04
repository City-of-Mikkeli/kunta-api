'use strict';

var config = require(__dirname + '/../config.json');
var feed = require("feed-read");

var KuntaApi = require(__dirname + '/../kunta-api')({
  basePath: config.api.basePath
});
var ServicesApi = new KuntaApi.ServicesApi();

module.exports = function(app){
  app.get('/', function(req, res) {
    var feedPromise = new Promise(function(resolve,reject) {
      feed(config.defaults.newsFeed, function (err, articles) {
        if (err) {
          reject(err);
        } else {
          resolve(articles);
        }
      });  
    });
    
    var serviceClassesPromise = ServicesApi.listServiceClasses(config.defaults.organizationId);
    
    Promise.all([feedPromise, serviceClassesPromise])
      .then(function (promises) { 
        var articles = promises[0];
        var apiServiceClasses = promises[1];
        
        var serviceClasses = apiServiceClasses.map(function (apiServiceClass) {
          return {
            'id': apiServiceClass.id,
            'name': apiServiceClass.name
          };
        });
        
        serviceClasses.sort(function(serviceClass1, serviceClass2) {
          return serviceClass1.name.localeCompare(serviceClass2.name);
        });
        
        var latestNews = articles.shift();
        res.render('pages/index.pug', {
          latestNews: latestNews,
          news: articles,
          serviceClasses: serviceClasses
        });
    }, function(error) {
      res.status(500).send(error);
    });
  });
};