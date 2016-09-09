package fi.otavanopisto.kuntaapi.server.integrations.mikkelinyt;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.controllers.IdentifierController;
import fi.otavanopisto.kuntaapi.server.integrations.AttachmentId;
import fi.otavanopisto.kuntaapi.server.integrations.EventId;
import fi.otavanopisto.kuntaapi.server.integrations.IdProvider;
import fi.otavanopisto.kuntaapi.server.integrations.IdType;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.NewsArticleId;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceChannelId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceId;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;

/**
 * Id provider for Mikkeli Nyt
 * 
 * @author Antti Leppä
 */
@Dependent
public class MikkeliNytIdProvider implements IdProvider {
  
  @Inject
  private IdentifierController identifierController;

  private MikkeliNytIdProvider() {
  }
  
  @Override
  public boolean canTranslate(String source, String target) {
    if (MikkeliNytConsts.IDENTIFIER_NAME.equals(source) && KuntaApiConsts.IDENTIFIER_NAME.equals(target)) {
      return true;
    }
    
    if (MikkeliNytConsts.IDENTIFIER_NAME.equals(target) && KuntaApiConsts.IDENTIFIER_NAME.equals(source)) {
      return true;
    }
    
    return false;
  }

  @Override
  public OrganizationId translate(OrganizationId organizationId, String target) {
    return null;
  }

  @Override
  public ServiceId translate(ServiceId serviceId, String target) {
    return null;
  }
  
  @Override
  public ServiceChannelId translate(ServiceChannelId serviceChannelId, String target) {
    return null;
  }

  @Override
  public ServiceClassId translate(ServiceClassId serviceClassId, String target) {
    return null;
  }

  @Override
  public EventId translate(EventId eventId, String target) {
    Identifier identifier;
    
    if (MikkeliNytConsts.IDENTIFIER_NAME.equals(eventId.getSource())) {
      identifier = identifierController.findIdentifierById(eventId);
      if (identifier != null) {
        return new EventId(KuntaApiConsts.IDENTIFIER_NAME, identifier.getKuntaApiId());
      }
    } else if (KuntaApiConsts.IDENTIFIER_NAME.equals(eventId.getSource())) {
      identifier = identifierController.findIdentifierByTypeSourceAndKuntaApiId(IdType.EVENT, MikkeliNytConsts.IDENTIFIER_NAME, eventId.getId());
      if (identifier != null) {
        return new EventId(MikkeliNytConsts.IDENTIFIER_NAME, identifier.getSourceId());
      }
    }
    
    return null;
  }

  @Override
  public AttachmentId translate(AttachmentId attachmentId, String target) {
    Identifier identifier;
    
    if (MikkeliNytConsts.IDENTIFIER_NAME.equals(attachmentId.getSource())) {
      identifier = identifierController.findIdentifierById(attachmentId);
      if (identifier != null) {
        return new AttachmentId(KuntaApiConsts.IDENTIFIER_NAME, identifier.getKuntaApiId());
      }
    } else if (KuntaApiConsts.IDENTIFIER_NAME.equals(attachmentId.getSource())) {
      identifier = identifierController.findIdentifierByTypeSourceAndKuntaApiId(IdType.ATTACHMENT, MikkeliNytConsts.IDENTIFIER_NAME, attachmentId.getId());
      if (identifier != null) {
        return new AttachmentId(MikkeliNytConsts.IDENTIFIER_NAME, identifier.getSourceId());
      }
    }
    
    return null;
  }

  @Override
  public NewsArticleId translate(NewsArticleId newsArticleId, String target) {
    return null;
  }

}
