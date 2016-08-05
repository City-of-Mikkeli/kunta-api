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
    if (PtvConsts.SOURCE.equals(source) && KuntaApiConsts.SOURCE.equals(target)) {
      return true;
    }
    
    if (PtvConsts.SOURCE.equals(target) && KuntaApiConsts.SOURCE.equals(source)) {
      return true;
    }
    
    return false;
  }

  @Override
  public OrganizationId translate(OrganizationId organizationId, String target) {
    Identifier identifier;
    
    switch (organizationId.getSource()) {
      case PtvConsts.SOURCE:
        identifier = identifierController.findIdentifierById(organizationId);
        if (identifier != null) {
          return new OrganizationId(KuntaApiConsts.SOURCE, identifier.getKuntaApiId());
        }
      break;
      case KuntaApiConsts.SOURCE:
        identifier = identifierController.findIdentifierByTypeSourceAndKuntaApiId(IdType.ORGANIZATION, PtvConsts.SOURCE, organizationId.getId());
        if (identifier != null) {
          return new OrganizationId(PtvConsts.SOURCE, identifier.getSourceId());
        }
      break;
    }
    
    return null;
  }

  @Override
  public ServiceId translate(ServiceId serviceId, String target) {
    return null;
  }

  @Override
  public ServiceClassId translate(ServiceClassId serviceClassId, String target) {
    return null;
  }

}
