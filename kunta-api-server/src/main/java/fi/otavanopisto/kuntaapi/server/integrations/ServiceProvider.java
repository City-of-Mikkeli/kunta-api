package fi.otavanopisto.kuntaapi.server.integrations;

import java.util.List;

import fi.otavanopisto.kuntaapi.server.rest.model.Service;

public interface ServiceProvider {
  
  public List<Service> listOrganizationServices(OrganizationId organizationId, ServiceClassId serviceClassId);
  
}
