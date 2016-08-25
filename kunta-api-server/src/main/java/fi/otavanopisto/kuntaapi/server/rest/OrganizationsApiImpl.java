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

import org.apache.commons.lang3.StringUtils;

import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationProvider;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassProvider;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceEnricher;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceProvider;
import fi.otavanopisto.kuntaapi.server.rest.model.Organization;
import fi.otavanopisto.kuntaapi.server.rest.model.Service;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceClass;

@RequestScoped
@Stateful
public class OrganizationsApiImpl extends OrganizationsApi {

  @Inject
  private Instance<OrganizationProvider> organizationProviders;

  @Inject
  private Instance<ServiceProvider> serviceProviders;

  @Inject
  private Instance<ServiceEnricher> serviceEnrichers;

  @Inject
  private Instance<ServiceClassProvider> serviceClassProvider;
  
  @Override
  public Response listOrganizations(String businessName, String businessCode) {
    List<Organization> organizations = new ArrayList<>();
    for (OrganizationProvider organizationProvider : getOrganizationProviders()) {
      organizations.addAll(organizationProvider.listOrganizations(businessName, businessCode));
    }
    
    return Response.ok(organizations)
      .build();
  }
  
  @Override
  public Response createService(String organizationId, Service body) {
    return createNotImplemented("Not implemented");
  }

  @Override
  public Response findService(String organizationIdParam, String serviceIdParam, Boolean onlySource) {
    OrganizationId organizationId  = new OrganizationId(KuntaApiConsts.IDENTIFIER_NAME, organizationIdParam);
    ServiceId serviceId = new ServiceId(KuntaApiConsts.IDENTIFIER_NAME, serviceIdParam);
    Service service = null;
    
    for (ServiceProvider serviceProvider : getServiceProviders()) {
      service = serviceProvider.findOrganizationService(organizationId, serviceId);
      if (service != null) {
        continue;
      }
    }

    if (!Boolean.TRUE.equals(onlySource)) {
      for (ServiceEnricher serviceEnricher : getServiceEnrichers()) {
        List<Service> enrichedService = serviceEnricher.enrich(Collections.singletonList(service));
        if (enrichedService != null && !enrichedService.isEmpty()) {
          service = enrichedService.get(0);
        }
      }
    }
    
    if (service != null) {
      return Response.ok(service)
        .build();
    } else {
      return Response
          .status(Status.NOT_FOUND)
          .build();
    }
    
  }

  @Override
  public Response listServices(String organizationIdParam, String serviceClassIdParam) {
    OrganizationId organizationId  = new OrganizationId(KuntaApiConsts.IDENTIFIER_NAME, organizationIdParam);
    ServiceClassId serviceClassId = StringUtils.isBlank(serviceClassIdParam) ? null : new ServiceClassId(KuntaApiConsts.IDENTIFIER_NAME, serviceClassIdParam);
    
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
  
  private List<OrganizationProvider> getOrganizationProviders() {
    // TODO: Prioritize providers
    
    List<OrganizationProvider> result = new ArrayList<>();
    
    Iterator<OrganizationProvider> iterator = organizationProviders.iterator();
    while (iterator.hasNext()) {
      result.add(iterator.next());
    }
    
    return Collections.unmodifiableList(result);
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

  private List<ServiceEnricher> getServiceEnrichers() {
    // TODO: Prioritize
    
    List<ServiceEnricher> result = new ArrayList<>();
    
    Iterator<ServiceEnricher> iterator = serviceEnrichers.iterator();
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
}

