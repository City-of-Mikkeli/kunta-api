/*global module:false*/

var fs = require('fs');
var config = require('./grunt-config.json');

module.exports = function(grunt) {
  require('load-grunt-tasks')(grunt);
  
  grunt.initConfig({
    'clean': {
      'remove-wordpress': [ config.wordpress.path ]
    },
    'curl': {
      'download-swagger-codegen':  {
        src: 'http://repo1.maven.org/maven2/io/swagger/swagger-codegen-cli/2.2.1/swagger-codegen-cli-2.2.1.jar',
        dest: 'swagger-codegen-cli.jar'
      }
    },
    'shell': {
      'wordpress-languages-writable': {
        command: 'chmod a+w languages',
        options: {
          execOptions: {
            cwd: 'kunta-api-management/wp-content'
          }
        }
      },
      'wordpress-management-plugin': {
        'command': 'ln -Fs ../../../wordpress-plugins/kunta-api-management kunta-api-management/wp-content/plugins'
      }
    },
    'mustache_render': {
      'wordpressdb': {
        files : [{
          data: {
            'DATABASE': config.wordpress.database.name,
            'USER': config.wordpress.database.user,
            'PASSWORD': config.wordpress.database.password,
            'HOST': config.wordpress.database.host||'localhost'
          },
          template: 'wordpress-config/init-database.sql.mustache',
          dest: 'wordpress-config/init-database.sql'
        }]
      },
      'wordpressdb-drop': {
        files : [{
          data: {
            'DATABASE': config.wordpress.database.name
          },
          template: 'wordpress-config/drop-database.sql.mustache',
          dest: 'wordpress-config/drop-database.sql'
        }]
      }
    },
    'mysqlrunfile': {
      options: {
        connection: {
          host: config.mysql.host,
          user: config.mysql.user,
          password: config.mysql.password,
          multipleStatements: true
        }
      },
      'wordpressdb': {
        src: ['wordpress-config/init-database.sql']
      },
      'wordpressdb-drop': {
        src: ['wordpress-config/drop-database.sql']
      }
    },
    'wp-cli': {
      'download': {
        'path': config.wordpress.path,
        'command': 'core',
        'subcommand': 'download',
        'options': {'locale': 'fi'}
      },
      'config': {
        'path': config.wordpress.path,
        'command': 'core',
        'subcommand': 'config',
        'options': {
          'dbname': config.wordpress.database.name,
          'dbuser': config.wordpress.database.user,
          'dbpass': config.wordpress.database.password,
          'locale': 'fi'
        }
      },
      'install': {
        'path': config.wordpress.path,
        'command': 'core',
        'subcommand': 'install',
        'options': {
          'url': config.wordpress.site.url,
          'title': config.wordpress.site.title,
          'admin_user': config.wordpress.site.adminUser,
          'admin_password': config.wordpress.site.adminPassword,
          'admin_email': config.wordpress.site.adminEmail,
          'skip-email': true
        }
      },
      'plugins': {
        'path': config.wordpress.path,
        'command': 'plugin',
        'subcommand': 'install',
        'arguments': 'qtranslate-x rest-api error-log-monitor https://github.com/starfishmod/WPAPI-SwaggerGenerator/archive/master.zip',
        'options': {
          'activate': true
        }
      },
      'update-languages': {
        'path': config.wordpress.path,
        'command': 'core',
        'subcommand': 'language update'
      },
      'update-plugins': {
        'path': config.wordpress.path,
        'command': 'plugin',
        'subcommand': 'update',
        'options': {
          'all': true
        }
      }
    },
    'http': {
      'visit-wordpress-admin': {
        options: {
          url: 'http://' + config.wordpress.site.url + '/wp-admin'
        }
      }
    }
  });
  
  grunt.registerTask('uninstall-management-wordpress', ['mustache_render:wordpressdb-drop', 'mysqlrunfile:wordpressdb-drop', 'clean:remove-wordpress' ]);
  grunt.registerTask('install-management-wordpress', ['mustache_render:wordpressdb', 'mysqlrunfile:wordpressdb', 'wp-cli:download', 'wp-cli:config', "wp-cli:install", "shell:wordpress-languages-writable", "wp-cli:plugins", "shell:wordpress-management-plugin", "http:visit-wordpress-admin", "wp-cli:update-languages"]);
  grunt.registerTask('update-management-wordpress-plugins', ["wp-cli:update-plugins"]);
  grunt.registerTask('install-management-wordpress-plugins', ["wp-cli:plugins"]);
};