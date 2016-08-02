/*global module:false*/

var fs = require('fs');

module.exports = function(grunt) {
  require('load-grunt-tasks')(grunt);
  
  grunt.initConfig({
    clean: {
      'clean-javascript': ['kunta-api-spec/languages/javascript'],
      'clean-php': ['kunta-api-spec/languages/php'],
      'clean-jaxrs': ['kunta-api-spec/languages/jaxrs-spec'],
      'clean-jaxrs-generated-cruft': ['kunta-api-spec/languages/jaxrs-spec/src/main/java/fi/otavanopisto/kuntaapi/server/RestApplication.java']
    },
    copy: {
      'copy-jaxrs-extras': {
        src: '**',
        dest: 'kunta-api-spec/languages/jaxrs-spec/',
        cwd: 'kunta-api-spec/extras/jaxrs-spec/',
        expand: true
      }
    },
    curl: {
      'download-swagger-codegen':  {
        src: 'http://repo1.maven.org/maven2/io/swagger/swagger-codegen-cli/2.2.0/swagger-codegen-cli-2.2.0.jar',
        dest: 'kunta-api-spec/swagger-codegen-cli.jar'
      }
    },
    if: {
      'require-swagger-codegen': {
        options: {
          test: function(){
            try {
              fs.accessSync('kunta-api-spec/swagger-codegen-cli.jar');
              return true;
            } catch (e) {
              return false;
            }
          } 
        },
        ifFalse: [ 'curl:download-swagger-codegen' ]
      }
    },
    shell: {
      'generate-javascript-client': {
        command : 'java -jar kunta-api-spec/swagger-codegen-cli.jar generate \
          -i kunta-api-spec/spec/swagger.yaml \
          -l javascript \
          --template-dir kunta-api-spec/templates/javascript \
          -o kunta-api-spec/languages/javascript/ \
          --additional-properties usePromises=true,projectName=kunta-api-client'
      },
      'generate-php-client': {
        command : 'java -jar kunta-api-spec/swagger-codegen-cli.jar generate \
          -i kunta-api-spec/spec/swagger.yaml \
          -l php \
          -o kunta-api-spec/languages/php/'
      },
      'generate-jaxrs-spec': {
        command : 'java -jar kunta-api-spec/swagger-codegen-cli.jar generate \
          -i kunta-api-spec/spec/swagger.yaml \
          -l jaxrs-spec \
          --api-package fi.otavanopisto.kuntaapi.server.rest \
          --model-package fi.otavanopisto.kuntaapi.server.rest.model \
          --group-id fi.otavanopisto.kunta-api \
          --artifact-id kunta-api-spec \
          --template-dir kunta-api-spec/templates/jax-rs-spec \
          -o kunta-api-spec/languages/jaxrs-spec/'
      },
      'install-jaxrs-spec': {
        command : 'mvn install',
        options: {
          execOptions: {
            cwd: 'kunta-api-spec/languages/jaxrs-spec/'
          }
        }
      }
    },
    publish: {
      'publish-javascript-client': {
        src: ['kunta-api-spec/languages/javascript']
      }
    }
  });
  
  
  grunt.registerTask('download-dependencies', 'if:require-swagger-codegen');
  grunt.registerTask('create-javascript-client', ['clean:clean-javascript', 'shell:generate-javascript-client']);
  grunt.registerTask('create-php-client', ['clean:clean-php', 'shell:generate-php-client']);
  grunt.registerTask('create-jaxrs-spec', ['clean:clean-jaxrs', 'shell:generate-jaxrs-spec', 'clean:clean-jaxrs-generated-cruft', 'copy:copy-jaxrs-extras', 'shell:install-jaxrs-spec']);
  grunt.registerTask('publish-javascript-client', ['create-javascript-client', 'publish:publish-javascript-client']);
  
  grunt.registerTask('default', [ 'download-dependencies', 'create-jaxrs-spec', 'create-javascript-client', 'create-php-client' ]);
};