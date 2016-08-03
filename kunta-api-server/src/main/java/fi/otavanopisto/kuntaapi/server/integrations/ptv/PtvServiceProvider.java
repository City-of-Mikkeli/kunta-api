package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.controllers.OrganizationController;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceProvider;
import fi.otavanopisto.kuntaapi.server.persistence.model.OrganizationIdentifier;
import fi.otavanopisto.kuntaapi.server.rest.model.Service;
import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.model.IVmOpenApiService;
import fi.otavanopisto.ptv.client.model.VmOpenApiLocalizedListItem;
import fi.otavanopisto.ptv.client.model.VmOpenApiOrganization;
import fi.otavanopisto.ptv.client.model.VmOpenApiOrganizationService;

@Dependent
public class PtvServiceProvider implements ServiceProvider {

  @Inject
  private Logger logger;
  
  @Inject
  private PtvApi ptvApi;
  
  @Inject
  private OrganizationController organizationController;

  @Override
  public List<Service> listOrganizationServices(Locale locale, String organizationId) {
    OrganizationIdentifier organizationIdentifier = organizationController.findOrganizationIdentifierBySourceAndUuid(PtvConsts.SOURCE, organizationId);
    if (organizationIdentifier == null) {
      logger.warning(String.format("Could not find ptv organization for organizationId %s", organizationId));
      return Collections.emptyList();
    }
    
    ApiResponse<VmOpenApiOrganization> organizationResponse = ptvApi.getOrganizationApi()
        .apiOrganizationByIdGet(organizationIdentifier.getSourceId());
    
    if (!organizationResponse.isOk()) {
      logger.severe(String.format("finding organization failed for organizationId %s", organizationId));
      return Collections.emptyList();
    }
    
    List<VmOpenApiOrganizationService> organizationServices = organizationResponse.getResponse()
        .getServices();

    if (!organizationResponse.isOk()) {
      logger.severe(String.format("Failed to list organization services for organizationId %s", organizationId));
      return Collections.emptyList();
    }

    List<IVmOpenApiService> ptvServices = new ArrayList<>();
    
    for (VmOpenApiOrganizationService organizationService : organizationServices) {
      ApiResponse<IVmOpenApiService> serviceResponse = ptvApi.getServiceApi().apiServiceByIdGet(organizationService.getServiceId());
      if (serviceResponse.isOk()) {
        ptvServices.add(serviceResponse.getResponse());
      } else {
        logger.severe(String.format("Failed to find service %s", organizationService.getServiceId()));
      }
    }
    
    return transform(locale, ptvServices);
  }

  private List<Service> transform(Locale locale, List<IVmOpenApiService> ptvServices) {
    List<Service> result = new ArrayList<>(ptvServices.size());
    
    // Name types: Name, AlternateName
    // Description types: ShortDescription, ChargeDescription, ServiceUserInstruction, Description
    
    for (IVmOpenApiService ptvService : ptvServices) {
      Service service = new Service();
      service.setName(getLocalizedItemValue(locale, "Name", ptvService.getServiceNames()));
      service.setDescription(getLocalizedItemValue(locale, "ShortDescription", ptvService.getServiceDescriptions()));
      result.add(service);
    }
    
    return result;
  }
  
  private String getLocalizedItemValue(Locale locale, String type, List<VmOpenApiLocalizedListItem> items) {
    if (items != null && !items.isEmpty()) {
      List<VmOpenApiLocalizedListItem> typeItems = new ArrayList<>();
      
      for (VmOpenApiLocalizedListItem item : items) {
        if (type.equals(item.getType())) {
          typeItems.add(item);
        }
      }
      
      if (!typeItems.isEmpty()) {
        for (VmOpenApiLocalizedListItem item : typeItems) {
          if (locale.getLanguage().equals(item.getLanguage())) {
            return item.getValue();
          }
        }
      
        return typeItems.get(0).getValue();
      }
    }
    
    return null;
  }
  
}
