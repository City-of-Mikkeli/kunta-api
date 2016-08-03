/*global module:false*/

var fs = require('fs');

module.exports = function(grunt) {
  require('load-grunt-tasks')(grunt);
  
  grunt.initConfig({
    clean: {
      'clean-javascript': ['kunta-api-spec/languages/javascript'],
      'clean-php': ['kunta-api-spec/languages/php'],
      'clean-jaxrs': ['kunta-api-spec/languages/jaxrs-spec'],
      'clean-jaxrs-generated-cruft': ['kunta-api-spec/languages/jaxrs-spec/src/main/java/fi/otavanopisto/kuntaapi/server/RestApplication.java'],
      'clean-ptv-java-client': ['ptv-rest-client'],
      'clean-ptv-java-client-cruft': [
        'ptv-rest-client/docs', 
        'ptv-rest-client/gradle', 
        'ptv-rest-client/build.gradle',
        'ptv-rest-client/build.sbt',
        'ptv-rest-client/git_push.sh',
        'ptv-rest-client/gradle.properties',
        'ptv-rest-client/gradlew',
        'ptv-rest-client/gradlew.bat',
        'ptv-rest-client/LICENSE',
        'ptv-rest-client/README.md',
        'ptv-rest-client/settings.gradle',
        'ptv-rest-client/src/test',
        'ptv-rest-client/src/main/AndroidManifest.xml',
        'ptv-rest-client/src/main/java/io',
        'ptv-rest-client/src/main/java/fi/otavanopisto/ptv/auth',
        'ptv-rest-client/src/main/java/fi/otavanopisto/ptv/*.java'
      ],
    },
    copy: {
      'copy-jaxrs-extras': {
        src: '**',
        dest: 'kunta-api-spec/languages/jaxrs-spec/',
        cwd: 'kunta-api-spec/extras/jaxrs-spec/',
        expand: true
      },
      'copy-ptv-rest-client-extras': {
        src: '**',
        dest: 'ptv-rest-client',
        cwd: 'ptv-rest-client-extras',
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
          --additional-properties dateLibrary=java8 \
          -o kunta-api-spec/languages/jaxrs-spec/'
      },
      'generate-ptv-java-client': {
//      -i http://ptvenv.cloudapp.net:1494/swagger/v1/swagger.json \
        command : 'java -jar kunta-api-spec/swagger-codegen-cli.jar generate \
          -i ptv-swagger.json \
          -l java \
          --api-package fi.otavanopisto.ptv.client\
          --model-package fi.otavanopisto.ptv.client.model \
          --group-id fi.otavanopisto.ptv.rest-client \
          --artifact-id rest-client\
          --template-dir ptv-java-templates \
          --library jersey2 \
          --additional-properties dateLibrary=java8 \
          -o ptv-rest-client/'
      },
      'install-jaxrs-spec': {
        command : 'mvn install',
        options: {
          execOptions: {
            cwd: 'kunta-api-spec/languages/jaxrs-spec/'
          }
        }
      },
      'install-ptv-java-client': {
        command : 'mvn install',
        options: {
          execOptions: {
            cwd: 'ptv-rest-client'
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
  grunt.registerTask('generate-ptv-java-client', ['clean:clean-ptv-java-client', 'shell:generate-ptv-java-client', 'clean:clean-ptv-java-client-cruft', 'copy:copy-ptv-rest-client-extras', 'shell:install-ptv-java-client']);
  
  grunt.registerTask('default', [ 'download-dependencies', 'create-jaxrs-spec', 'create-javascript-client', 'create-php-client' ]);
};