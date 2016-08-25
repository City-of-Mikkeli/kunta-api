/*global module:false*/

var fs = require('fs');

module.exports = function(grunt) {
  require('load-grunt-tasks')(grunt);

  grunt.initConfig({
    clean: {
      'clean-javascript': ['kunta-api-spec/languages/javascript'],
      'clean-jaxrs': ['kunta-api-spec/languages/jaxrs-spec'],
      'clean-jaxrs-generated-cruft': ['kunta-api-spec/languages/jaxrs-spec/src/main/java/fi/otavanopisto/kuntaapi/server/RestApplication.java'],
      'clean-management-composer-files': ['kunta-api-management/wp-content/plugins/kunta-api-management/vendor'],
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
      'clean-mwp-java-client': ['mwp-rest-client'],
      'clean-mwp-java-client-cruft': [
        'mwp-rest-client/docs', 
        'mwp-rest-client/gradle', 
        'mwp-rest-client/build.gradle',
        'mwp-rest-client/build.sbt',
        'mwp-rest-client/git_push.sh',
        'mwp-rest-client/gradle.properties',
        'mwp-rest-client/gradlew',
        'mwp-rest-client/gradlew.bat',
        'mwp-rest-client/LICENSE',
        'mwp-rest-client/README.md',
        'mwp-rest-client/settings.gradle',
        'mwp-rest-client/src/test',
        'mwp-rest-client/src/main/AndroidManifest.xml',
        'mwp-rest-client/src/main/java/io',
        'mwp-rest-client/src/main/java/fi/otavanopisto/mwp/auth',
        'mwp-rest-client/src/main/java/fi/otavanopisto/mwp/*.java'
      ]
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
      },
      'copy-mwp-rest-client-extras': {
        src: '**',
        dest: 'mwp-rest-client',
        cwd: 'mwp-rest-client-extras',
        expand: true
      }
    },
    curl: {
      'download-swagger-codegen':  {
        src: 'http://repo1.maven.org/maven2/io/swagger/swagger-codegen-cli/2.2.1/swagger-codegen-cli-2.2.1.jar',
        dest: 'swagger-codegen-cli.jar'
      }
    },
    if: {
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
    shell: {
      'generate-javascript-client': {
        command : 'java -jar swagger-codegen-cli.jar generate \
          -i kunta-api-spec/spec/swagger.yaml \
          -l javascript \
          --template-dir kunta-api-spec/templates/javascript \
          -o kunta-api-spec/languages/javascript/ \
          --additional-properties usePromises=true,projectName=kunta-api-client'
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
        command : 'java -jar swagger-codegen-cli.jar generate \
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
        command : 'mv ptv-rest-client/pom.xml ptv-rest-client/pom.xml.before && \
          java -jar swagger-codegen-cli.jar generate \
          -i https://api.palvelutietovaranto.suomi.fi/swagger/v1/swagger.json \
          -l java \
          --api-package fi.otavanopisto.ptv.client\
          --model-package fi.otavanopisto.ptv.client.model \
          --group-id fi.otavanopisto.ptv.rest-client \
          --artifact-id ptv-rest-client\
          --artifact-version `mvn -f ptv-rest-client/pom.xml.before -q -Dexec.executable=\'echo\' -Dexec.args=\'${project.version}\' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec` \
          --template-dir ptv-java-templates \
          --library jersey2 \
          --additional-properties dateLibrary=java8 \
          -o ptv-rest-client/'
      },
      'generate-mwp-java-client': {
        command : 'mv mwp-rest-client/pom.xml mwp-rest-client/pom.xml.before && \
          java -jar swagger-codegen-cli.jar generate \
          -i http://manage.kunta-api.dev/wp-json/apigenerate/swagger \
          -l java \
          --api-package fi.otavanopisto.mwp.client\
          --model-package fi.otavanopisto.mwp.client.model \
          --group-id fi.otavanopisto.mwp.mwp-rest-client \
          --artifact-id mwp-rest-client\
          --artifact-version `mvn -f mwp-rest-client/pom.xml.before -q -Dexec.executable=\'echo\' -Dexec.args=\'${project.version}\' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec` \
          --template-dir mwp-java-templates \
          --library jersey2 \
          --additional-properties dateLibrary=java8-localdatetime \
          -o mwp-rest-client/'
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
      },
      'release-ptv-java-client': {
        command : 'mvn -B release:clean release:prepare release:perform',
        options: {
          execOptions: {
            cwd: 'ptv-rest-client'
          }
        }
      },
      'install-mwp-java-client': {
        command : 'mvn install',
        options: {
          execOptions: {
            cwd: 'mwp-rest-client'
          }
        }
      },
      'release-mwp-java-client': {
        command : 'mvn -B release:clean release:prepare release:perform',
        options: {
          execOptions: {
            cwd: 'mwp-rest-client'
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
            cwd: 'kunta-api-management/wp-content/plugins/kunta-api-management'
          }
        }
      }
    },
    publish: {
      'publish-javascript-client': {
        src: ['kunta-api-spec/languages/javascript']
      }
    },
    gitclone: {
      'checkout-ptv-java-client': {
        options: {
          'repository': 'git@github.com:otavanopisto/ptv-rest-client.git'  
        }
      },
      'checkout-mwp-java-client': {
        options: {
          'repository': 'git@github.com:otavanopisto/mwp-rest-client.git'  
        }
      }
    }
  });
  
  grunt.registerTask('download-dependencies', 'if:require-swagger-codegen');
  grunt.registerTask('create-javascript-client', ['clean:clean-javascript', 'shell:generate-javascript-client']);
  grunt.registerTask('create-php-client', ['shell:generate-php-client', 'shell:publish-php-client']);
  grunt.registerTask('install-php-client', ['clean:clean-management-composer-files', 'shell:update-management-composer-files']);
  grunt.registerTask('create-jaxrs-spec', ['clean:clean-jaxrs', 'shell:generate-jaxrs-spec', 'clean:clean-jaxrs-generated-cruft', 'copy:copy-jaxrs-extras', 'shell:install-jaxrs-spec']);
  grunt.registerTask('publish-javascript-client', ['create-javascript-client', 'publish:publish-javascript-client']);
  grunt.registerTask('install-javascript-client-to-www', ['shell:pack-javascript-client', 'shell:install-javascript-client-www']);
  
  grunt.registerTask('generate-ptv-java-client', ['download-dependencies', 'clean:clean-ptv-java-client', 'gitclone:checkout-ptv-java-client', 'shell:generate-ptv-java-client', 'clean:clean-ptv-java-client-cruft', 'copy:copy-ptv-rest-client-extras', 'shell:install-ptv-java-client', 'shell:release-ptv-java-client', 'clean:clean-ptv-java-client']);
  grunt.registerTask('generate-mwp-java-client', ['download-dependencies', 'clean:clean-mwp-java-client', 'gitclone:checkout-mwp-java-client', 'shell:generate-mwp-java-client', 'clean:clean-mwp-java-client-cruft', 'copy:copy-mwp-rest-client-extras', 'shell:install-mwp-java-client', 'shell:release-mwp-java-client', 'clean:clean-mwp-java-client']);
  
  grunt.registerTask('default', [ 'download-dependencies', 'create-jaxrs-spec', 'create-javascript-client', 'create-php-client', 'install-javascript-client-to-www', 'install-php-client']);
};