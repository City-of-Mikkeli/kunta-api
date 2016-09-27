/*global module:false*/

var fs = require('fs');
var config = require('./grunt-config.json');

module.exports = function(grunt) {
  require('load-grunt-tasks')(grunt);
  
  grunt.initConfig({
    'clean': {
      'clean-javascript': ['kunta-api-spec/languages/javascript'],
      'clean-jaxrs-generated-cruft': ['kunta-api-spec/languages/jaxrs-spec/src/main/java/fi/otavanopisto/kuntaapi/server/RestApplication.java'],
      'clean-management-composer-files': ['wordpress-plugins/kunta-api-management/vendor'],
      'remove-wordpress': [ config.wordpress.path ]
    },
    'copy': {
      'copy-jaxrs-extras': {
        src: '**',
        dest: 'kunta-api-spec/languages/jaxrs-spec/',
        cwd: 'kunta-api-spec/extras/jaxrs-spec/',
        expand: true
      }
    },
    'curl': {
      'download-swagger-codegen':  {
        src: 'http://repo1.maven.org/maven2/io/swagger/swagger-codegen-cli/2.2.1/swagger-codegen-cli-2.2.1.jar',
        dest: 'swagger-codegen-cli.jar'
      }
    },
    'if': {
      'require-swagger-codegen': {
        options: {
          test: function(){
            try {
              fs.accessSync('swagger-codegen-cli.jar');
              return true;
            } catch (e) {
              return false;
            }
          } 
        },
        ifFalse: [ 'curl:download-swagger-codegen' ]
      }
    },
    'shell': {
      'generate-javascript-client': {
        command : 'java -jar swagger-codegen-cli.jar generate \
          -i kunta-api-spec/spec/swagger.yaml \
          -l javascript \
          --template-dir kunta-api-spec/templates/javascript \
          -o kunta-api-spec/languages/javascript/ \
          --additional-properties usePromises=true,projectName=kunta-api-client,projectVersion=0.0.8'
      },
      'generate-php-client': {
        command : 'java -jar swagger-codegen-cli.jar generate \
          -i kunta-api-spec/spec/swagger.yaml \
          -l php \
          --template-dir kunta-api-spec/templates/php \
          -o kunta-api-spec/languages/php/ \
          --additional-properties packagePath=kunta-api-php-client,composerVendorName=otavanopisto,composerProjectName=kunta-api-php-client,artifactVersion=0.0.1-alpha1,variableNamingConvention=camelCase,invokerPackage=KuntaAPI,apiPackage=KuntaAPI\\\\Api,modelPackage=KuntaAPI\\\\Model'
      },
      'publish-php-client': {
        command : 'sh git_push.sh',
        options: {
          execOptions: {
            cwd: 'kunta-api-spec/languages/php/kunta-api-php-client/'
          }
        }
      },
      'generate-jaxrs-spec': {
        command : 'mv kunta-api-spec/languages/jaxrs-spec/pom.xml kunta-api-spec/languages/jaxrs-spec/pom.xml.before && \
          java -jar swagger-codegen-cli.jar generate \
          -i kunta-api-spec/spec/swagger.yaml \
          -l jaxrs-spec \
          --api-package fi.otavanopisto.kuntaapi.server.rest \
          --model-package fi.otavanopisto.kuntaapi.server.rest.model \
          --group-id fi.otavanopisto.kunta-api \
          --artifact-id kunta-api-spec \
          --artifact-version `mvn -f kunta-api-spec/languages/jaxrs-spec/pom.xml.before -q -Dexec.executable=\'echo\' -Dexec.args=\'${project.version}\' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec` \
          --template-dir kunta-api-spec/templates/jax-rs-spec \
          --additional-properties dateLibrary=java8 \
          -o kunta-api-spec/languages/jaxrs-spec/'
      },
      'install-jaxrs-spec': {
        command : 'mvn install',
        options: {
          execOptions: {
            cwd: 'kunta-api-spec/languages/jaxrs-spec/'
          }
        }
      },
      'release-jaxrs-spec': {
        command : 'git add src pom.xml && git commit -m "Generated source" && git push && mvn -B release:clean release:prepare release:perform',
        options: {
          execOptions: {
            cwd: 'kunta-api-spec/languages/jaxrs-spec/'
          }
        }
      },
      'pack-javascript-client': {
        command: 'npm pack',
        options: {
          execOptions: {
            cwd: 'kunta-api-spec/languages/javascript'
          }
        }
      },
      'install-javascript-client-www': {
        command: 'npm install ../kunta-api-spec/languages/javascript/kunta-api-client-0.0.1.tgz',
        options: {
          execOptions: {
            cwd: 'kunta-api-www'
          }
        }
      },
      'update-management-composer-files': {
        command: 'composer clear-cache && composer update',
        options: {
          execOptions: {
            cwd: 'wordpress-plugins/kunta-api-management'
          }
        }
      },
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
    'publish': {
      'publish-javascript-client': {
        src: ['kunta-api-spec/languages/javascript']
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
  
  grunt.registerTask('download-dependencies', 'if:require-swagger-codegen');
  grunt.registerTask('create-javascript-client', ['clean:clean-javascript', 'shell:generate-javascript-client']);
  grunt.registerTask('create-php-client', ['shell:generate-php-client', 'shell:publish-php-client']);
  grunt.registerTask('install-php-client', ['clean:clean-management-composer-files', 'shell:update-management-composer-files']);
  grunt.registerTask('create-jaxrs-spec', ['shell:generate-jaxrs-spec', 'clean:clean-jaxrs-generated-cruft', 'copy:copy-jaxrs-extras', 'shell:install-jaxrs-spec', 'shell:release-jaxrs-spec']);
  grunt.registerTask('publish-javascript-client', ['create-javascript-client', 'publish:publish-javascript-client']);
  grunt.registerTask('install-javascript-client-to-www', ['shell:pack-javascript-client', 'shell:install-javascript-client-www']);
  
  grunt.registerTask('uninstall-management-wordpress', ['mustache_render:wordpressdb-drop', 'mysqlrunfile:wordpressdb-drop', 'clean:remove-wordpress' ]);
  grunt.registerTask('install-management-wordpress', ['mustache_render:wordpressdb', 'mysqlrunfile:wordpressdb', 'wp-cli:download', 'wp-cli:config', "wp-cli:install", "shell:wordpress-languages-writable", "wp-cli:plugins", "shell:wordpress-management-plugin", "http:visit-wordpress-admin", "wp-cli:update-languages"]);
  grunt.registerTask('update-management-wordpress-plugins', ["wp-cli:update-plugins"]);
  grunt.registerTask('install-management-wordpress-plugins', ["wp-cli:plugins"]);

  grunt.registerTask('default', [ 'download-dependencies', 'create-jaxrs-spec', 'create-javascript-client', 'create-php-client', 'install-php-client']);
};