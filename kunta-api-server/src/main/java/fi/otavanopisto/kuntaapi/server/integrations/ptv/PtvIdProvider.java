package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.controllers.IdentifierController;
import fi.otavanopisto.kuntaapi.server.integrations.IdProvider;
import fi.otavanopisto.kuntaapi.server.integrations.IdType;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceId;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;

@Dependent
public class PtvIdProvider implements IdProvider {
  
  @Inject
  private IdentifierController identifierController;

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
    
    switch (organizationId.getSource()) {
      case PtvConsts.IDENTIFIFER_NAME:
        identifier = identifierController.findIdentifierById(organizationId);
        if (identifier != null) {
          return new OrganizationId(KuntaApiConsts.IDENTIFIER_NAME, identifier.getKuntaApiId());
        }
      break;
      case KuntaApiConsts.IDENTIFIER_NAME:
        identifier = identifierController.findIdentifierByTypeSourceAndKuntaApiId(IdType.ORGANIZATION, PtvConsts.IDENTIFIFER_NAME, organizationId.getId());
        if (identifier != null) {
          return new OrganizationId(PtvConsts.IDENTIFIFER_NAME, identifier.getSourceId());
        }
      break;
    }
    
    return null;
  }

  @Override
  public ServiceId translate(ServiceId serviceId, String target) {
    Identifier identifier;
    
    switch (serviceId.getSource()) {
      case PtvConsts.IDENTIFIFER_NAME:
        identifier = identifierController.findIdentifierById(serviceId);
        if (identifier != null) {
          return new ServiceId(KuntaApiConsts.IDENTIFIER_NAME, identifier.getKuntaApiId());
        }
      break;
      case KuntaApiConsts.IDENTIFIER_NAME:
        identifier = identifierController.findIdentifierByTypeSourceAndKuntaApiId(IdType.SERVICE, PtvConsts.IDENTIFIFER_NAME, serviceId.getId());
        if (identifier != null) {
          return new ServiceId(PtvConsts.IDENTIFIFER_NAME, identifier.getSourceId());
        }
      break;
    }
    
    return null;
  }

  @Override
  public ServiceClassId translate(ServiceClassId serviceClassId, String target) {
    Identifier identifier;
    
    switch (serviceClassId.getSource()) {
      case PtvConsts.IDENTIFIFER_NAME:
        identifier = identifierController.findIdentifierById(serviceClassId);
        if (identifier != null) {
          return new ServiceClassId(KuntaApiConsts.IDENTIFIER_NAME, identifier.getKuntaApiId());
        }
      break;
      case KuntaApiConsts.IDENTIFIER_NAME:
        identifier = identifierController.findIdentifierByTypeSourceAndKuntaApiId(IdType.SERVICE_CLASS, PtvConsts.IDENTIFIFER_NAME, serviceClassId.getId());
        if (identifier != null) {
          return new ServiceClassId(PtvConsts.IDENTIFIFER_NAME, identifier.getSourceId());
        }
      break;
    }
    
    return null;
  }

}
