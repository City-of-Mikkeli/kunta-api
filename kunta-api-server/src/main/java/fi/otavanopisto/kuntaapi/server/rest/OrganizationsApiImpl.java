package fi.otavanopisto.kuntaapi.server.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import fi.otavanopisto.kuntaapi.server.integrations.ServiceProvider;
import fi.otavanopisto.kuntaapi.server.rest.model.Service;

@Path("/services")
public class OrganizationsApiImpl extends OrganizationsApi {
  
  @Inject
  private Instance<ServiceProvider> serviceProviders;
  
  @Context
  private HttpServletRequest request;
  
  @Override
  public Response createService(String organizationId, Service body) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response deleteService(String organizationId, String serviceId) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response deleteServiceData(String organizationId, String serviceId, String dataId) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response findService(String organizationId, String serviceId) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response findServiceData(String organizationId, String serviceId, String dataId) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response listServiceDatas(String organizationId, String serviceId, String sourceId) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response listServices(String organizationId) {
    // TODO: Merge services
    
    Locale locale = request.getLocale();
    List<Service> services = new ArrayList<>();
    for (ServiceProvider serviceProvider : getServiceProviders()) {
      services.addAll(serviceProvider.listOrganizationServices(locale, organizationId));
    }
    
    return Response.ok(services)
      .build();
  }
  
  private List<ServiceProvider> getServiceProviders() {
    // TODO: Prioritize providers
    
    List<ServiceProvider> result = new ArrayList<>();
    
    Iterator<ServiceProvider> iterator = serviceProviders.iterator();
    while (iterator.hasNext()) {
      result.add(iterator.next());
    }
    
    return Collections.unmodifiableList(result);
  }

  @Override
  public Response updateService(String organizationId, String serviceId) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response updateServiceData(String organizationId, String serviceId, String dataId) {
    return createNotImplemented("Not implemented");
  }
  
}

