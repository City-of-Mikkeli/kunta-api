'use strict';

var feed = require("feed-read");

module.exports = function(app){
  app.get('/', function(req, res){
    feed('https://open.mikkeli.fi/rss', function(err, articles) {
      if (err) throw err;
        var latestNews = articles.shift();
        console.log(latestNews);
        res.render('pages/index.pug', {
          latestNews: latestNews,
          news: articles
        });
    });
  });
};