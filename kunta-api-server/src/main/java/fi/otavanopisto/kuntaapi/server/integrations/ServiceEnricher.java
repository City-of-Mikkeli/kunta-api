package fi.otavanopisto.kuntaapi.server.integrations;

import java.util.List;

import fi.otavanopisto.kuntaapi.server.rest.model.Service;

public interface ServiceEnricher {

  public List<Service> enrich(List<Service> services);
  
}
