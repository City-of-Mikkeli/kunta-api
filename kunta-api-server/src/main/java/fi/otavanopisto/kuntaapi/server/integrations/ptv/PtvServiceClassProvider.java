package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.integrations.IdController;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassProvider;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceClass;
import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.model.IVmOpenApiService;
import fi.otavanopisto.ptv.client.model.VmOpenApiFintoItem;
import fi.otavanopisto.ptv.client.model.VmOpenApiOrganization;
import fi.otavanopisto.ptv.client.model.VmOpenApiOrganizationService;

@Dependent
public class PtvServiceClassProvider implements ServiceClassProvider {

  @Inject
  private Logger logger;
  
  @Inject
  private PtvApi ptvApi;
  
  @Inject
  private IdController idController;

  @Override
  public List<ServiceClass> listOrganizationServiceClasses(OrganizationId organizationId) {
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
        ptvServices.add(serviceResponse.getResponse());
      } else {
        logger.severe(String.format("Failed to find service %s", ptvServiceId));
      }
    }
    
    List<VmOpenApiFintoItem> ptvServiceClasses = new ArrayList<>();
    Set<String> ptvServiceClassIds = new HashSet<>();
    for (IVmOpenApiService ptvService : ptvServices) {
      for (VmOpenApiFintoItem ptvServiceClass : ptvService.getServiceClasses()) {
        if (!ptvServiceClassIds.contains(ptvServiceClass.getId())) {
          ptvServiceClassIds.add(ptvServiceClass.getId());
          ptvServiceClasses.add(ptvServiceClass);
        }
      }
    }
    
    List<ServiceClass> result = new ArrayList<>(ptvServiceClasses.size());
    for (VmOpenApiFintoItem ptvServiceClass : ptvServiceClasses) {
      ServiceClassId ptvId = new ServiceClassId(PtvConsts.IDENTIFIFER_NAME, ptvServiceClass.getId());
      ServiceClassId kuntaApiId = idController.translateServiceClassId(ptvId, KuntaApiConsts.IDENTIFIER_NAME);
      if (kuntaApiId == null) {
        logger.severe(String.format("Could not translate %s into Kunta API id", ptvServiceClass.getId()));
        continue;  
      }
      
      ServiceClass serviceClass = new ServiceClass();
      serviceClass.setId(kuntaApiId.getId());
      serviceClass.setCode(ptvServiceClass.getCode());
      serviceClass.setName(ptvServiceClass.getName());
      serviceClass.setOntologyType(ptvServiceClass.getOntologyType());
      serviceClass.setParentId(ptvServiceClass.getParentId());
      serviceClass.setParentUri(ptvServiceClass.getParentUri());
      serviceClass.setUri(ptvServiceClass.getUri());
      result.add(serviceClass);
    }
    
    return result;
  }
  
}
