#!/bin/sh

echo "##############################"
echo "   Generating JAX-RS specs    "
echo "##############################"
rm -fR languages/jaxrs-spec/ &&
java -jar swagger-codegen-cli.jar generate \
  -i spec/swagger.yaml \
  -l jaxrs-spec \
  --api-package fi.otavanopisto.kuntaapi.server.rest \
  --model-package fi.otavanopisto.kuntaapi.server.rest.model \
  --group-id fi.otavanopisto.kunta-api \
  --artifact-id kunta-api-spec \
  --template-dir templates/jax-rs-spec \
  -o languages/jaxrs-spec/ &&
rm -f languages/jaxrs-spec/src/main/java/fi/otavanopisto/kuntaapi/server/RestApplication.java &&
cp -R extras/jaxrs-spec/ languages/jaxrs-spec/ &&
cd languages/jaxrs-spec/ &&
mvn install