package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.controllers.IdentifierController;
import fi.otavanopisto.kuntaapi.server.integrations.IdController;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceChannelId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceProvider;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;
import fi.otavanopisto.kuntaapi.server.rest.model.Service;
import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.model.IVmOpenApiService;
import fi.otavanopisto.ptv.client.model.VmOpenApiFintoItem;
import fi.otavanopisto.ptv.client.model.VmOpenApiOrganization;
import fi.otavanopisto.ptv.client.model.VmOpenApiOrganizationService;

/**
 * PTV Service provider
 * 
 * @author Antti Leppä
 */
@Dependent
public class PtvServiceProvider extends AbstractPtvProvider implements ServiceProvider {

  @Inject
  private Logger logger;
  
  @Inject
  private PtvApi ptvApi;
  
  @Inject
  private IdController idController;

  @Inject
  private IdentifierController identifierController;

  private PtvServiceProvider() {
  }

  @Override
  public Service findOrganizationService(OrganizationId organizationId, ServiceId serviceId) {
    ServiceId ptvServiceId = idController.translateServiceId(serviceId, PtvConsts.IDENTIFIFER_NAME);
    if (ptvServiceId == null) {
      logger.severe(String.format("Failed to translate serviceId %s into PTV serviceId", serviceId.toString()));
      return null;
    }
    
    ApiResponse<IVmOpenApiService> response = ptvApi.getServiceApi().apiServiceByIdGet(ptvServiceId.getId());
    if (!response.isOk()) {
      logger.severe(String.format("Failed to find service for id %s", ptvServiceId));
      return null;
    }
    
    IVmOpenApiService service = response.getResponse();
    // TODO: Check that service belongs to correct organization
    
    return transform(service);
  }

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
    
    for (IVmOpenApiService ptvService : ptvServices) {
      Service service = transform(ptvService);
      if (service != null) {
        result.add(service);
      }
    }
    
    return result;
  }
  
  private Service transform(IVmOpenApiService ptvService) {
    ServiceId ptvId = new ServiceId(PtvConsts.IDENTIFIFER_NAME, ptvService.getId());
    ServiceId kuntaApiId = idController.translateServiceId(ptvId, KuntaApiConsts.IDENTIFIER_NAME);
    if (kuntaApiId == null) {
      logger.severe(String.format("Could not translate %s into Kunta API id", ptvId.getId()));
      return null;
    }
    
    Service service = new Service();
    service.setId(kuntaApiId.getId());
    service.setNames(translateLocalizedItems("Name", ptvService.getServiceNames()));
    service.setAlternativeNames(translateLocalizedItems("AlternativeName", ptvService.getServiceNames()));
    service.setDescriptions(translateLocalizedItems("Description", ptvService.getServiceDescriptions()));
    service.setShortDescriptions(translateLocalizedItems("ShortDescription", ptvService.getServiceDescriptions()));
    service.setServiceUserInstructions(translateLocalizedItems("ServiceUserInstruction", ptvService.getServiceDescriptions()));
    parseChannelIds(ptvService, service);
    
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
    
    return service;
  }

  private void parseChannelIds(IVmOpenApiService ptvService, Service service) {
    List<String> electronicChannelIds = new ArrayList<>();
    List<String> phoneChannelIds = new ArrayList<>();
    List<String> printableFormChannelIds = new ArrayList<>();
    List<String> webpageChannelIds = new ArrayList<>();
    List<String> serviceLocationChannelIds = new ArrayList<>();
    /**
    
    FIXME: Temporarity disabled because PTV does not respect design REST principles. Currently trying 
    to convince PTV team to fix their API but if they refuse we need to create some dirty hack
    to fix this problem

    for (String serviceChanneld : ptvService.getServiceChannels()) {
      ApiResponse<VmOpenApiServiceChannels> channelResponse = ptvApi.getServiceChannelApi().apiServiceChannelByIdGet(serviceChanneld);
      if (!channelResponse.isOk()) {
        logger.severe(String.format("Channel response %s reported [%d] %s", serviceChanneld, channelResponse.getStatus(), channelResponse.getMessage()));
      } else {
        String serviceChannelKuntaApiId = getKuntaApiChannelId(serviceChanneld);
        
        VmOpenApiServiceChannels channels = channelResponse.getResponse();
        if (channels.getElectronicChannel() != null) {
          electronicChannelIds.add(serviceChannelKuntaApiId);
        }
        
        if (channels.getPhoneChannel() != null) {
          phoneChannelIds.add(serviceChannelKuntaApiId);
        }
        
        if (channels.getPrintableFormChannel() != null) {
          printableFormChannelIds.add(serviceChannelKuntaApiId);
        }
        
        if (channels.getWebPageChannel() != null) {
          webpageChannelIds.add(serviceChannelKuntaApiId);
        }
        
        if (channels.getLocationChannel() != null) {
          serviceLocationChannelIds.add(serviceChannelKuntaApiId);
        }
        
      }
    }
    **/
    service.setElectronicChannelIds(electronicChannelIds);
    service.setPhoneChannelIds(phoneChannelIds);
    service.setPrintableFormChannelIds(printableFormChannelIds);
    service.setWebpageChannelIds(webpageChannelIds);
    service.setServiceLocationChannelIds(serviceLocationChannelIds);
  }

  @SuppressWarnings("unused")
  private String getKuntaApiChannelId(String ptvChannelId) {
    ServiceChannelId ptvId = new ServiceChannelId(PtvConsts.IDENTIFIFER_NAME, ptvChannelId);
    
    ServiceChannelId kuntaApiId = idController.translateServiceChannelId(ptvId,  KuntaApiConsts.IDENTIFIER_NAME);
    if (kuntaApiId == null) {
      Identifier identifier = identifierController.createIdentifier(ptvId);
      logger.info(String.format("Discovered new electric service channel %s", ptvId.toString()));
      kuntaApiId = new ServiceChannelId(KuntaApiConsts.IDENTIFIER_NAME, identifier.getKuntaApiId());
    }
    
    return kuntaApiId.getId();
  }

}
