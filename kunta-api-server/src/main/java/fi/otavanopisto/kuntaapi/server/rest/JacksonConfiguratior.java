package fi.otavanopisto.kuntaapi.server.rest;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@Provider
public class JacksonConfiguratior implements ContextResolver<ObjectMapper> {

  @Override
  public ObjectMapper getContext(Class<?> type) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JSR310Module());
    return objectMapper;
}
  
}
