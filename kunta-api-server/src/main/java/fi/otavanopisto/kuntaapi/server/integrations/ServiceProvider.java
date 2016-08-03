package fi.otavanopisto.kuntaapi.server.integrations;

import java.util.List;
import java.util.Locale;

import fi.otavanopisto.kuntaapi.server.rest.model.Service;

public interface ServiceProvider {
  
  public List<Service> listOrganizationServices(Locale locale, String organizationId);
  
}
