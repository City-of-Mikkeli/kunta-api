package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.integrations.IdController;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceProvider;
import fi.otavanopisto.kuntaapi.server.rest.model.LocalizedValue;
import fi.otavanopisto.kuntaapi.server.rest.model.Service;
import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.model.IVmOpenApiService;
import fi.otavanopisto.ptv.client.model.VmOpenApiFintoItem;
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
  private IdController idController;

  @Override
  public List<Service> listOrganizationServices(OrganizationId organizationId, ServiceClassId serviceClassId) {
    ServiceClassId ptvServiceClassId = null;
    if (serviceClassId != null) {
      ptvServiceClassId = idController.translateServiceClassId(serviceClassId, PtvConsts.IDENTIFIFER_NAME);
      if (ptvServiceClassId == null) {
        logger.severe(String.format("Failed to translate serviceClassId %s into PTV serviceClassId", serviceClassId.toString()));
        return Collections.emptyList();
      }
    }
    
    OrganizationId ptvOrganizationId = idController.translateOrganizationId(organizationId, PtvConsts.IDENTIFIFER_NAME);
    
    ApiResponse<VmOpenApiOrganization> organizationResponse = ptvApi.getOrganizationApi()
        .apiOrganizationByIdGet(ptvOrganizationId.getId());
    
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
    TreeSet<String> ptvServiceIds = new TreeSet<>();
    for (VmOpenApiOrganizationService organizationService : organizationServices) {
      ptvServiceIds.add(organizationService.getServiceId());
    }
    
    for (String ptvServiceId : ptvServiceIds) {
      ApiResponse<IVmOpenApiService> serviceResponse = ptvApi.getServiceApi().apiServiceByIdGet(ptvServiceId);
      if (serviceResponse.isOk()) {
        IVmOpenApiService ptvService = serviceResponse.getResponse();
        if ((ptvServiceClassId == null) || hasServiceClass(ptvService, ptvServiceClassId)) {
          ptvServices.add(ptvService);
        }
      } else {
        logger.severe(String.format("Failed to find service %s", ptvServiceId));
      }
    }
    
    return transform(ptvServices);
  }
  
  private boolean hasServiceClass(IVmOpenApiService ptvService, ServiceClassId ptvServiceClassId) {
    List<VmOpenApiFintoItem> serviceClasseses = ptvService.getServiceClasses();
    if (serviceClasseses == null) {
      return false;
    }
    
    for (VmOpenApiFintoItem serviceClasses : serviceClasseses) {
      if (ptvServiceClassId.getId().equals(serviceClasses.getId())) {
        return true;
      }
    }
    
    return false;
  }

  private List<Service> transform(List<IVmOpenApiService> ptvServices) {
    List<Service> result = new ArrayList<>(ptvServices.size());
    
    // Name types: Name, AlternateName
    // Description types: ShortDescription, ChargeDescription, ServiceUserInstruction, Description
    
    for (IVmOpenApiService ptvService : ptvServices) {
      ServiceId ptvId = new ServiceId(PtvConsts.IDENTIFIFER_NAME, ptvService.getId());
      ServiceId kuntaApiId = idController.translateServiceId(ptvId, KuntaApiConsts.IDENTIFIER_NAME);
      if (kuntaApiId == null) {
        logger.severe(String.format("Could not translate %s into Kunta API id", ptvId.getId()));
        continue;  
      }
      
      Service service = new Service();
      service.setId(kuntaApiId.getId());
      service.setName(getLocalizedValues("Name", ptvService.getServiceNames()));
      service.setDescription(getLocalizedValues("ShortDescription", ptvService.getServiceDescriptions()));
      List<String> classIds = new ArrayList<>(ptvService.getServiceClasses().size());
      
      for (VmOpenApiFintoItem serviceClass : ptvService.getServiceClasses()) {
        ServiceClassId classPtvId = new ServiceClassId(PtvConsts.IDENTIFIFER_NAME, serviceClass.getId());
        ServiceClassId classKuntaApiId = idController.translateServiceClassId(classPtvId, KuntaApiConsts.IDENTIFIER_NAME);
        if (classKuntaApiId != null) {
          classIds.add(classKuntaApiId.getId());
        } else {
          logger.severe(String.format("Could not translate %s into Kunta API id", classPtvId.getId()));
        }
      }
      
      service.setClassIds(classIds);
      
      result.add(service);
    }
    
    return result;
  }
  
  private List<LocalizedValue> getLocalizedValues(String type, List<VmOpenApiLocalizedListItem> items) {
    if (items != null && !items.isEmpty()) {
      List<LocalizedValue> result = new ArrayList<>();
      List<VmOpenApiLocalizedListItem> typeItems = new ArrayList<>();
      
      for (VmOpenApiLocalizedListItem item : items) {
        if (type.equals(item.getType())) {
          typeItems.add(item);
        }
      }
      
      if (!typeItems.isEmpty()) {
        for (VmOpenApiLocalizedListItem item : typeItems) {
          LocalizedValue localizedValue = new LocalizedValue();
          localizedValue.setLanguage(item.getLanguage());
          localizedValue.setValue(item.getValue());
          result.add(localizedValue);
        }
      
        return result;
      }
    }
    
    return null;
  }
  
}
