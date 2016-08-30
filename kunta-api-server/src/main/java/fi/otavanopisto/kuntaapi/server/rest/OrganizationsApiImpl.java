package fi.otavanopisto.kuntaapi.server.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fi.otavanopisto.kuntaapi.server.integrations.EventProvider;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassProvider;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceProvider;
import fi.otavanopisto.kuntaapi.server.rest.model.Event;
import fi.otavanopisto.kuntaapi.server.rest.model.Service;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceClass;

/**
 * REST Service implementation
 * 
 * @author Antti Leppä
 */
@RequestScoped
@Stateful
@SuppressWarnings ("squid:S3306")
public class OrganizationsApiImpl extends OrganizationsApi {
  
  @Inject
  private Instance<ServiceProvider> serviceProviders;

  @Inject
  private Instance<ServiceClassProvider> serviceClassProvider;

  @Inject
  private Instance<EventProvider> eventProviders;
  
  @Override
  public Response createService(String organizationId, Service body) {
    return createNotImplemented("Not implemented");
  }
  
  @Override
  public Response findService(String organizationIdParam, String serviceIdParam) {
    OrganizationId organizationId  = new OrganizationId(KuntaApiConsts.IDENTIFIER_NAME, organizationIdParam);
    ServiceId serviceId = new ServiceId(KuntaApiConsts.IDENTIFIER_NAME, serviceIdParam);
    
    // TODO: Merge services
    
    for (ServiceProvider serviceProvider : getServiceProviders()) {
      Service service = serviceProvider.findOrganizationService(organizationId, serviceId);
      if (service != null) {
        return Response.ok(service)
          .build();
      }
    }
    
    return Response
        .status(Status.NOT_FOUND)
        .build();
  }

  @Override
  public Response listServices(String organizationIdParam, String serviceClassIdParam) {
    OrganizationId organizationId  = new OrganizationId(KuntaApiConsts.IDENTIFIER_NAME, organizationIdParam);
    ServiceClassId serviceClassId = new ServiceClassId(KuntaApiConsts.IDENTIFIER_NAME, serviceClassIdParam);
    
    // TODO: Merge services
    
    List<Service> services = new ArrayList<>();
    for (ServiceProvider serviceProvider : getServiceProviders()) {
      services.addAll(serviceProvider.listOrganizationServices(organizationId, serviceClassId));
    }
    
    return Response.ok(services)
      .build();
  }

  @Override
  public Response updateService(String organizationId, String serviceId) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response deleteService(String organizationId, String serviceId) {
    return createNotImplemented("Not implemented");
  }
  
  @Override
  public Response listServiceElectornicChannels(String organizationId, String serviceId) {
    return createNotImplemented("Not implemented");
  }
  
  @Override
  public Response listServiceClasses(String organizationIdParam) {
    OrganizationId organizationId  = new OrganizationId(KuntaApiConsts.IDENTIFIER_NAME, organizationIdParam);

    // TODO: Merge provider results
    
    List<ServiceClass> result = new ArrayList<>();
    for (ServiceClassProvider serviceClassProvider : getServiceClassProviders()) {
      result.addAll(serviceClassProvider.listOrganizationServiceClasses(organizationId));
    }
    
    return Response.ok(result)
      .build();
  }

  @Override
  public Response findOrganizationEvent(String organizationId, String eventId) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response findOrganizationEventImage(String organizationId, String eventId, String imageId) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response getOrganizationEventImageData(String organizationId, String eventId, String imageId, Integer size) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response listOrganizationEventImages(String organizationId, String eventId) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response listOrganizationEvents(String organizationIdParam) {
    OrganizationId organizationId = new OrganizationId(KuntaApiConsts.IDENTIFIER_NAME, organizationIdParam);
    
    List<Event> result = new ArrayList<>();
   
    // TODO: Sort events 
    for (EventProvider eventProvider : getEventProviders()) {
      result.addAll(eventProvider.listOrganizationEvents(organizationId));
    }
    
    return Response.ok(result)
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
  
  private List<ServiceClassProvider> getServiceClassProviders() {
    // TODO: Prioritize providers
    
    List<ServiceClassProvider> result = new ArrayList<>();
    
    Iterator<ServiceClassProvider> iterator = serviceClassProvider.iterator();
    while (iterator.hasNext()) {
      result.add(iterator.next());
    }
    
    return Collections.unmodifiableList(result);
  }
  
  private List<EventProvider> getEventProviders() {
    // TODO: Prioritize providers
    
    List<EventProvider> result = new ArrayList<>();
    
    Iterator<EventProvider> iterator = eventProviders.iterator();
    while (iterator.hasNext()) {
      result.add(iterator.next());
    }
    
    return Collections.unmodifiableList(result);
  }
}

