package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.controllers.IdentifierController;
import fi.otavanopisto.kuntaapi.server.integrations.IdType;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;
import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.model.VmOpenApiGuidPage;

@Startup
@Singleton
public class PtvWarmup {
  
  @Inject
  private Logger logger;
  
  @Inject
  private PtvApi ptvApi;
  
  @Inject
  private IdentifierController identifierController;

  @PostConstruct
  public void init() {
    logger.info("Updating organization identifiers");
    int organizationIds = updateOrganizationIds();
    logger.info(String.format("Discovered %d new organization identifiers", organizationIds));
    
    logger.info("Updating service identifiers");
    int serviceIds = updateServiceIds();
    logger.info(String.format("Discovered %d new service identifiers", serviceIds));
    
  }

  private int updateOrganizationIds() {
    int count = 0;
    int page = 0;
    
    do {
      ApiResponse<VmOpenApiGuidPage> response = ptvApi.getOrganizationApi().apiOrganizationGet(null, page);
      if (response.isOk()) {
        VmOpenApiGuidPage pageData = response.getResponse();
        
        for (String guid : pageData.getGuidList()) {
          Identifier organizationIdentifier = identifierController.findIdentifierByTypeSourceAndId(IdType.ORGANIZATION, PtvConsts.SOURCE, guid);
          if (organizationIdentifier == null) {
            identifierController.createIdentifier(IdType.ORGANIZATION, PtvConsts.SOURCE, guid);
            count++;
          }
        }
        
        if (pageData.getPageCount() <= (page + 1)) {
          break;
        }
      } else {
        logger.severe(String.format("Failed to update organizations from PTV (%d: %s)", response.getStatus(), response.getMessage()));
        break;
      }
      
      page++;
    } while (true);
    
    return count;
  }

  private int updateServiceIds() {
    int count = 0;
    int page = 0;
    
    do {
      ApiResponse<VmOpenApiGuidPage> response = ptvApi.getServiceApi().apiServiceGet(null, page);
      if (response.isOk()) {
        VmOpenApiGuidPage pageData = response.getResponse();
        
        for (String guid : pageData.getGuidList()) {
          Identifier organizationIdentifier = identifierController.findIdentifierByTypeSourceAndId(IdType.SERVICE, PtvConsts.SOURCE, guid);
          if (organizationIdentifier == null) {
            identifierController.createIdentifier(IdType.SERVICE, PtvConsts.SOURCE, guid);
            count++;
          }
        }
        
        if (pageData.getPageCount() <= (page + 1)) {
          break;
        }
      } else {
        logger.severe(String.format("Failed to update organizations from PTV (%d: %s)", response.getStatus(), response.getMessage()));
        break;
      }
      
      page++;
    } while (true);
    
    return count;
  }
  
}
