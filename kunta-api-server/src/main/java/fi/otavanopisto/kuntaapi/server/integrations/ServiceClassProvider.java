package fi.otavanopisto.kuntaapi.server.integrations;

import java.util.List;

import fi.otavanopisto.kuntaapi.server.rest.model.ServiceClass;

public interface ServiceClassProvider {
  
  public List<ServiceClass> listOrganizationServiceClasses(String organizationId);
  
}
