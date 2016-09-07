package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.controllers.IdentifierController;
import fi.otavanopisto.kuntaapi.server.integrations.IdController;
import fi.otavanopisto.kuntaapi.server.integrations.IdType;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceChannelId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceChannelProvider;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceId;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceChannelAttachment;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceChannelServiceHour;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceChannelSupport;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceChannelWebPage;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceElectronicChannel;
import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.model.IVmOpenApiService;
import fi.otavanopisto.ptv.client.model.VmOpenApiAttachmentWithType;
import fi.otavanopisto.ptv.client.model.VmOpenApiElectronicChannel;
import fi.otavanopisto.ptv.client.model.VmOpenApiServiceChannels;
import fi.otavanopisto.ptv.client.model.VmOpenApiServiceHour;
import fi.otavanopisto.ptv.client.model.VmOpenApiSupport;
import fi.otavanopisto.ptv.client.model.VmOpenApiWebPage;

/**
 * Service channel provider for PTV
 * 
 * @author Antti Leppä
 * @author Heikki Kurhinen
 */
@Dependent
public class PtvServiceChannelProvider extends AbstractPtvProvider implements ServiceChannelProvider {

  @Inject
  private Logger logger;
  
  @Inject
  private PtvApi ptvApi;
  
  @Inject
  private IdController idController;

  @Inject
  private IdentifierController identifierController;
  
  private PtvServiceChannelProvider() {
  }
  
  @Override
  public List<ServiceElectronicChannel> listElectronicChannels(OrganizationId organizationId, ServiceId serviceId) {
    OrganizationId ptvOrganizationId = idController.translateOrganizationId(organizationId, PtvConsts.IDENTIFIFER_NAME);
    if (ptvOrganizationId == null) {
      logger.severe(String.format("Failed to translate organizationId %s into PTV organizationId", organizationId.toString()));
      return Collections.emptyList();
    }
    
    ServiceId ptvServiceId = idController.translateServiceId(serviceId, PtvConsts.IDENTIFIFER_NAME);
    if (ptvServiceId == null) {
      logger.severe(String.format("Failed to translate serviceId %s into PTV serviceId", serviceId.toString()));
      return Collections.emptyList();
    }

    ApiResponse<IVmOpenApiService> serviceResponse = ptvApi.getServiceApi().apiServiceByIdGet(ptvServiceId.getId());
    if (!serviceResponse.isOk()) {
      logger.severe(String.format("Service %s reported [%d] %s", ptvServiceId.getId(), serviceResponse.getStatus(), serviceResponse.getMessage()));
      return Collections.emptyList();
    } 
    
    List<VmOpenApiElectronicChannel> ptvElectronicChannel = new ArrayList<>();
    
    IVmOpenApiService ptvService = serviceResponse.getResponse();
    for (String serviceChanneld : ptvService.getServiceChannels()) {
      ApiResponse<VmOpenApiServiceChannels> channelResponse = ptvApi.getServiceChannelApi().apiServiceChannelByIdGet(serviceChanneld);
      if (!channelResponse.isOk()) {
        logger.severe(String.format("Channel response %s reported [%d] %s", serviceChanneld, channelResponse.getStatus(), channelResponse.getMessage()));
      } else {
        VmOpenApiServiceChannels channels = channelResponse.getResponse();
        VmOpenApiElectronicChannel electronicChannel = channels.getElectronicChannel();
        if (electronicChannel != null) {
          ptvElectronicChannel.add(electronicChannel);
        }
      }
    }
   
    return translateChannels(ptvElectronicChannel);
  }
  
  private List<ServiceElectronicChannel> translateChannels(List<VmOpenApiElectronicChannel> ptvChannels) {
    if (ptvChannels == null) {
      return Collections.emptyList();
    }
    
    List<ServiceElectronicChannel> result = new ArrayList<>();
    for (VmOpenApiElectronicChannel ptvChannel : ptvChannels) {
      result.add(translateChannel(ptvChannel));
    }
    
    return result;
  }  
  
  private ServiceElectronicChannel translateChannel(VmOpenApiElectronicChannel ptvChannel) {
    ServiceChannelId ptvId = new ServiceChannelId(PtvConsts.IDENTIFIFER_NAME, ptvChannel.getId());
    
    ServiceChannelId kuntaApiId = idController.translateServiceChannelId(ptvId,  KuntaApiConsts.IDENTIFIER_NAME);
    if (kuntaApiId == null) {
      Identifier identifier = identifierController.createIdentifier(IdType.SERVICE_CHANNEL, ptvId.getSource(), ptvId.getId());
      logger.info(String.format("Discovered new electric service channel %s", ptvId.toString()));
      kuntaApiId = new ServiceChannelId(KuntaApiConsts.IDENTIFIER_NAME, identifier.getKuntaApiId());
    }
    
    ServiceElectronicChannel result = new ServiceElectronicChannel();
    result.setAttachments(translateAttachments(ptvChannel.getAttachments()));
    result.setUrls(translateLanguageItems(ptvChannel.getUrls()));
    result.setRequiresAuthentication(ptvChannel.getRequiresAuthentication());
    result.setSignatureQuantity(ptvChannel.getSignatureQuantity());
    result.setSupportContacts(translateSupportContacts(ptvChannel.getSupportContacts()));
    result.setServiceHours(translateServiceHours(ptvChannel.getServiceHours()));
    result.setWebPages(translateWebPages(ptvChannel.getWebPages()));
    result.setNames(translateLocalizedItems("Name", ptvChannel.getServiceChannelNames()));
    result.setShortDescriptions(translateLocalizedItems("ShortDescription", ptvChannel.getServiceChannelNames()));
    result.setDescriptions(translateLocalizedItems("Description", ptvChannel.getServiceChannelNames()));
    result.setId(kuntaApiId.getId());
    
    return result;
  }

  private List<ServiceChannelWebPage> translateWebPages(List<VmOpenApiWebPage> ptvWebPages) {
    if (ptvWebPages == null) {
      return Collections.emptyList();
    }
    
    List<ServiceChannelWebPage> result = new ArrayList<>();
    
    for (VmOpenApiWebPage ptvWebPage : ptvWebPages) {
      ServiceChannelWebPage webPage = new ServiceChannelWebPage();
      webPage.setLanguage(ptvWebPage.getLanguage());
      webPage.setType(ptvWebPage.getType());
      webPage.setUrl(ptvWebPage.getUrl());
      webPage.setValue(ptvWebPage.getValue());
      result.add(webPage);
    }
    
    return result;
  }

  private List<ServiceChannelServiceHour> translateServiceHours(List<VmOpenApiServiceHour> ptvServiceHours) {
    if (ptvServiceHours == null) {
      return Collections.emptyList();
    }
    
    List<ServiceChannelServiceHour> result = new ArrayList<>();
    for (VmOpenApiServiceHour ptvServiceHour : ptvServiceHours) {
      translateServiceHour(result, ptvServiceHour);
    }
    
    return result;
  }

  private void translateServiceHour(List<ServiceChannelServiceHour> result, VmOpenApiServiceHour ptvServiceHour) {
    boolean[] dayBools = { 
      ptvServiceHour.getMonday(),
      ptvServiceHour.getTuesday(),
      ptvServiceHour.getWednesday(),
      ptvServiceHour.getThursday(),
      ptvServiceHour.getFriday(),
      ptvServiceHour.getSaturday(),
      ptvServiceHour.getSunday()
    };
    
    int dayIndex = 0;
    List<Integer> days = new ArrayList<>();
    boolean currentOpen = dayBools[0];
    
    while (dayIndex < dayBools.length) {
      if ((dayBools[dayIndex] != currentOpen) && (!days.isEmpty())) {
        result.add(createServiceHourObject(ptvServiceHour, days, currentOpen));
        days.clear();
        currentOpen = dayBools[dayIndex];
      } 
      
      days.add((dayIndex + 1) % 7);
      dayIndex++;
    }
    
    if (!days.isEmpty()) {
      result.add(createServiceHourObject(ptvServiceHour, days, currentOpen));
    }
  }

  private ServiceChannelServiceHour createServiceHourObject(VmOpenApiServiceHour ptvServiceHour, List<Integer> days, boolean currentOpen) {
    ServiceChannelServiceHour serviceHour = new ServiceChannelServiceHour();
    ArrayList<Integer> openDays = new ArrayList<>(days);
    
    serviceHour.setDays(openDays);
    if (currentOpen) {
      serviceHour.setOpens(ptvServiceHour.getOpens());
      serviceHour.setCloses(ptvServiceHour.getCloses());
    }
    
    serviceHour.setStatus(currentOpen ? "OPEN": "CLOSED");
    serviceHour.setType(ptvServiceHour.getServiceHourType());
    serviceHour.setValidFrom(ptvServiceHour.getValidFrom());
    serviceHour.setValidTo(ptvServiceHour.getValidTo());
    return serviceHour;
  }

  private List<ServiceChannelSupport> translateSupportContacts(List<VmOpenApiSupport> supportContacts) {
    if (supportContacts == null) {
      return Collections.emptyList();
    }
    
    List<ServiceChannelSupport> result = new ArrayList<>(supportContacts.size());
    
    for (VmOpenApiSupport supportContact : supportContacts) {
      ServiceChannelSupport support = new ServiceChannelSupport();
      support.setEmail(supportContact.getEmail());
      support.setLanguage(supportContact.getLanguage());
      support.setPhone(supportContact.getPhone());
      support.setPhoneChargeDescription(supportContact.getPhoneChargeDescription());
      support.setServiceChargeTypes(supportContact.getServiceChargeTypes());
      result.add(support);
    }

    return result;
  }

  private List<ServiceChannelAttachment> translateAttachments(List<VmOpenApiAttachmentWithType> ptvAttachments) {
    if (ptvAttachments == null) {
      return Collections.emptyList();
    }
    
    List<ServiceChannelAttachment> result = new ArrayList<>(ptvAttachments.size());
    for (VmOpenApiAttachmentWithType ptvAttachment : ptvAttachments) {
      ServiceChannelAttachment attachment = new ServiceChannelAttachment();
      attachment.setDescription(ptvAttachment.getDescription());
      attachment.setLanguage(ptvAttachment.getLanguage());
      attachment.setName(ptvAttachment.getName());
      attachment.setType(ptvAttachment.getType());
      attachment.setUrl(ptvAttachment.getUrl());
      result.add(attachment);
    }
    
    return result;
  }
  
}
