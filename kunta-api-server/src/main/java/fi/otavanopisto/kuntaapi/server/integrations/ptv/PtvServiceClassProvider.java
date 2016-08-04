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

import fi.otavanopisto.kuntaapi.server.controllers.OrganizationController;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassProvider;
import fi.otavanopisto.kuntaapi.server.persistence.model.OrganizationIdentifier;
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
  private OrganizationController organizationController;

  @Override
  public List<ServiceClass> listOrganizationServiceClasses(String organizationId) {
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
      // TODO: Incorrect ids 
      ServiceClass serviceClass = new ServiceClass();
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
