/*global module:false*/

var fs = require('fs');
 q
module.exports = function(grunt) {
  require('load-grunt-tasks')(grunt);
  
  var scssFiles = fs.readdirSync('scss').filter(function (file) {
    return file.endsWith('.scss');
  });
  
  var scssMap = {};
  
  scssFiles.forEach(function (scssFile) {
    scssMap['public/css/' + scssFile.substring(0, scssFile.length - 5) + '.css'] = 'scss/' + scssFile;
  });
  
  console.log(scssMap);
  
  grunt.initConfig({
    clean: {
      'clean-styles': ['public/css/']
    },
    sass: {
      'build-styles': {
        options: {
          sourceMap: true,
          outputStyle: 'compressed'
        },
        dist: {
          files: scssMap
        }
      }
    }
  });
  
  grunt.registerTask('default', ['clean:clean-styles', 'sass:build-styles' ]);
};