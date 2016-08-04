'use strict';

var config = require(__dirname + '/../config.json');
var feed = require("feed-read");
var KuntaApi = require(__dirname + '/../kunta-api')({
  basePath: config.api.basePath
});
var ServicesApi = new KuntaApi.ServicesApi();

module.exports = function(app){
  app.get('/', function(req, res) {
    
    ServicesApi.listServiceClasses(config.defaults.organizationId).then(function(apiServiceClasses) {
      feed('https://open.mikkeli.fi/rss', function(err, articles) {
        if (err) throw err;
        
        // TODO: use locale
        
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
      });
    }, function(error) {
      console.error(error);
    });
  });
};