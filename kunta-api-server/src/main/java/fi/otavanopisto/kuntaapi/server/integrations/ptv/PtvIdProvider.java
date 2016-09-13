package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.controllers.IdentifierController;
import fi.otavanopisto.kuntaapi.server.integrations.AttachmentId;
import fi.otavanopisto.kuntaapi.server.integrations.BannerId;
import fi.otavanopisto.kuntaapi.server.integrations.EventId;
import fi.otavanopisto.kuntaapi.server.integrations.IdProvider;
import fi.otavanopisto.kuntaapi.server.integrations.IdType;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.NewsArticleId;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceChannelId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceId;
import fi.otavanopisto.kuntaapi.server.integrations.TileId;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;

/**
 * Id provider for palvelu tieto varanto
 * 
 * @author Otavan Opisto
 */
@Dependent
public class PtvIdProvider implements IdProvider {
  
  @Inject
  private IdentifierController identifierController;

  private PtvIdProvider() {
  }
  
  @Override
  public boolean canTranslate(String source, String target) {
    if (PtvConsts.IDENTIFIFER_NAME.equals(source) && KuntaApiConsts.IDENTIFIER_NAME.equals(target)) {
      return true;
    }
    
    if (PtvConsts.IDENTIFIFER_NAME.equals(target) && KuntaApiConsts.IDENTIFIER_NAME.equals(source)) {
      return true;
    }
    
    return false;
  }

  @Override
  public OrganizationId translate(OrganizationId organizationId, String target) {
    Identifier identifier;
    
    if (PtvConsts.IDENTIFIFER_NAME.equals(organizationId.getSource())) {
      identifier = identifierController.findIdentifierById(organizationId);
      if (identifier != null) {
        return new OrganizationId(KuntaApiConsts.IDENTIFIER_NAME, identifier.getKuntaApiId());
      }
    } else if (KuntaApiConsts.IDENTIFIER_NAME.equals(organizationId.getSource())) {
      identifier = identifierController.findIdentifierByTypeSourceAndKuntaApiId(IdType.ORGANIZATION, PtvConsts.IDENTIFIFER_NAME, organizationId.getId());
      if (identifier != null) {
        return new OrganizationId(PtvConsts.IDENTIFIFER_NAME, identifier.getSourceId());
      }
    }
    
    return null;
  }

  @Override
  public ServiceId translate(ServiceId serviceId, String target) {
    Identifier identifier;
    
    if (PtvConsts.IDENTIFIFER_NAME.equals(serviceId.getSource())) {
      identifier = identifierController.findIdentifierById(serviceId);
      if (identifier != null) {
        return new ServiceId(KuntaApiConsts.IDENTIFIER_NAME, identifier.getKuntaApiId());
      } 
    } else if (KuntaApiConsts.IDENTIFIER_NAME.equals(serviceId.getSource())) {
      identifier = identifierController.findIdentifierByTypeSourceAndKuntaApiId(IdType.SERVICE, PtvConsts.IDENTIFIFER_NAME, serviceId.getId());
      if (identifier != null) {
        return new ServiceId(PtvConsts.IDENTIFIFER_NAME, identifier.getSourceId());
      }
    }
    
    return null;
  }

  @Override
  public ServiceChannelId translate(ServiceChannelId serviceChannelId, String target) {
    Identifier identifier;
    
    if (PtvConsts.IDENTIFIFER_NAME.equals(serviceChannelId.getSource())) {
      identifier = identifierController.findIdentifierById(serviceChannelId);
      if (identifier != null) {
        return new ServiceChannelId(KuntaApiConsts.IDENTIFIER_NAME, identifier.getKuntaApiId());
      }
    } else if (KuntaApiConsts.IDENTIFIER_NAME.equals(serviceChannelId.getSource())) {
      identifier = identifierController.findIdentifierByTypeSourceAndKuntaApiId(IdType.SERVICE_CHANNEL, PtvConsts.IDENTIFIFER_NAME, serviceChannelId.getId());
      if (identifier != null) {
        return new ServiceChannelId(PtvConsts.IDENTIFIFER_NAME, identifier.getSourceId());
      }
    }
    
    return null;
  }

  @Override
  public ServiceClassId translate(ServiceClassId serviceClassId, String target) {
    Identifier identifier;
    
    if (PtvConsts.IDENTIFIFER_NAME.equals(serviceClassId.getSource())) {
      identifier = identifierController.findIdentifierById(serviceClassId);
      if (identifier != null) {
        return new ServiceClassId(KuntaApiConsts.IDENTIFIER_NAME, identifier.getKuntaApiId());
      }
    } else if (KuntaApiConsts.IDENTIFIER_NAME.equals(serviceClassId.getSource())) {
      identifier = identifierController.findIdentifierByTypeSourceAndKuntaApiId(IdType.SERVICE_CLASS, PtvConsts.IDENTIFIFER_NAME, serviceClassId.getId());
      if (identifier != null) {
        return new ServiceClassId(PtvConsts.IDENTIFIFER_NAME, identifier.getSourceId());
      }
    }
    
    return null;
  }

  @Override
  public EventId translate(EventId eventId, String target) {
    return null;
  }

  @Override
  public AttachmentId translate(AttachmentId attachmentId, String target) {
    return null;
  }

  @Override
  public NewsArticleId translate(NewsArticleId newsArticleId, String target) {
    return null;
  }

  @Override
  public BannerId translate(BannerId bannerId, String target) {
    return null;
  }
  
  @Override
  public TileId translate(TileId tileId, String target) {
    return null;
  }

}
