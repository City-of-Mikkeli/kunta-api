package fi.otavanopisto.kuntaapi.server.integrations;

import java.util.Iterator;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

/**
 * Controller for ids
 * 
 * @author Antti Leppä
 */
@Dependent
public class IdController {
  
  @Inject
  private Instance<IdProvider> idProviders;
  
  private IdController() {
  }
  
  /**
   * Translates organization id into into target id
   * 
   * @param organizationId id to be translated
   * @param target target
   * @return translated id or null if translation has failed
   */
  public OrganizationId translateOrganizationId(OrganizationId organizationId, String target) {
    if (StringUtils.equals(organizationId.getSource(), target)) {
      return organizationId;
    }
    
    IdProvider idProvider = getIdProvider(organizationId.getSource(), target);
    if (idProvider != null) {
      return idProvider.translate(organizationId, target);
    }
    
    return null;
  }

  /**
   * Translates service id into into target id
   * 
   * @param serviceId id to be translated
   * @param target target
   * @return translated id or null if translation has failed
   */
  public ServiceId translateServiceId(ServiceId serviceId, String target) {
    if (StringUtils.equals(serviceId.getSource(), target)) {
      return serviceId;
    }
    
    IdProvider idProvider = getIdProvider(serviceId.getSource(), target);
    if (idProvider != null) {
      return idProvider.translate(serviceId, target);
    }
    
    return null;
  }
  
  /**
   * Translates service class id into into target id
   * 
   * @param serviceClassId id to be translated
   * @param target target
   * @return translated id or null if translation has failed
   */
  public ServiceClassId translateServiceClassId(ServiceClassId serviceClassId, String target) {
    if (StringUtils.equals(serviceClassId.getSource(), target)) {
      return serviceClassId;
    }
    
    IdProvider idProvider = getIdProvider(serviceClassId.getSource(), target);
    if (idProvider != null) {
      return idProvider.translate(serviceClassId, target);
    }
    
    return null;
  }
  
  /**
   * Translates event class id into into target id
   * 
   * @param eventId id to be translated
   * @param target target
   * @return translated id or null if translation has failed
   */
  public EventId translateEventId(EventId eventId, String target) {
    if (StringUtils.equals(eventId.getSource(), target)) {
      return eventId;
    }
    
    IdProvider idProvider = getIdProvider(eventId.getSource(), target);
    if (idProvider != null) {
      return idProvider.translate(eventId, target);
    }
    
    return null;
  }
  
  /**
   * Translates event class id into into target id
   * 
   * @param attachmentId id to be translated
   * @param target target
   * @return translated id or null if translation has failed
   */
  public AttachmentId translateAttachmentId(AttachmentId attachmentId, String target) {
    if (StringUtils.equals(attachmentId.getSource(), target)) {
      return attachmentId;
    }
    
    IdProvider idProvider = getIdProvider(attachmentId.getSource(), target);
    if (idProvider != null) {
      return idProvider.translate(attachmentId, target);
    }
    
    return null;
  }

  /**
   * Translates both ids into Kunta Api ids and check whether they match
   * 
   * @param id1 id1
   * @param id2 id2
   * @return whether ids match
   */
  public boolean idsEqual(AttachmentId id1, AttachmentId id2) {
    AttachmentId kuntaApiId1 = translateAttachmentId(id1, KuntaApiConsts.IDENTIFIER_NAME);
    AttachmentId kuntaApiId2 = translateAttachmentId(id2, KuntaApiConsts.IDENTIFIER_NAME);
    
    if (kuntaApiId1 == null || kuntaApiId2 == null) {
      return false;
    }
    
    return kuntaApiId1.equals(kuntaApiId2);
  }

  /**
   * Translates both ids into Kunta Api ids and check whether they match
   * 
   * @param id1 id1
   * @param id2 id2
   * @return whether ids match
   */
  public boolean idsEqual(EventId id1, EventId id2) {
    EventId kuntaApiId1 = translateEventId(id1, KuntaApiConsts.IDENTIFIER_NAME);
    EventId kuntaApiId2 = translateEventId(id2, KuntaApiConsts.IDENTIFIER_NAME);
    
    if (kuntaApiId1 == null || kuntaApiId2 == null) {
      return false;
    }
    
    return kuntaApiId1.equals(kuntaApiId2);
  }

  /**
   * Translates both ids into Kunta Api ids and check whether they match
   * 
   * @param id1 id1
   * @param id2 id2
   * @return whether ids match
   */
  public boolean idsEqual(OrganizationId id1, OrganizationId id2) {
    OrganizationId kuntaApiId1 = translateOrganizationId(id1, KuntaApiConsts.IDENTIFIER_NAME);
    OrganizationId kuntaApiId2 = translateOrganizationId(id2, KuntaApiConsts.IDENTIFIER_NAME);
    
    if (kuntaApiId1 == null || kuntaApiId2 == null) {
      return false;
    }
    
    return kuntaApiId1.equals(kuntaApiId2);
  }

  /**
   * Translates both ids into Kunta Api ids and check whether they match
   * 
   * @param id1 id1
   * @param id2 id2
   * @return whether ids match
   */
  public boolean idsEqual(ServiceClassId id1, ServiceClassId id2) {
    ServiceClassId kuntaApiId1 = translateServiceClassId(id1, KuntaApiConsts.IDENTIFIER_NAME);
    ServiceClassId kuntaApiId2 = translateServiceClassId(id2, KuntaApiConsts.IDENTIFIER_NAME);
    
    if (kuntaApiId1 == null || kuntaApiId2 == null) {
      return false;
    }
    
    return kuntaApiId1.equals(kuntaApiId2);
  }

  /**
   * Translates both ids into Kunta Api ids and check whether they match
   * 
   * @param id1 id1
   * @param id2 id2
   * @return whether ids match
   */
  public boolean idsEqual(ServiceId id1, ServiceId id2) {
    ServiceId kuntaApiId1 = translateServiceId(id1, KuntaApiConsts.IDENTIFIER_NAME);
    ServiceId kuntaApiId2 = translateServiceId(id2, KuntaApiConsts.IDENTIFIER_NAME);
    
    if (kuntaApiId1 == null || kuntaApiId2 == null) {
      return false;
    }
    
    return kuntaApiId1.equals(kuntaApiId2);
  }
  
  private IdProvider getIdProvider(String source, String target) {
    Iterator<IdProvider> iterator = idProviders.iterator();
    while (iterator.hasNext()) {
      IdProvider idProvider = iterator.next();
      if (idProvider.canTranslate(source, target)) {
        return idProvider;
      }
    }
    
    return null;
  }
  
}
