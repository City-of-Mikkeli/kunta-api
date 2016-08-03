package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.controllers.OrganizationController;
import fi.otavanopisto.kuntaapi.server.persistence.model.OrganizationIdentifier;
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
  private OrganizationController organizationController;

  @PostConstruct
  public void init() {
    int page = 0;
    
    do {
      ApiResponse<VmOpenApiGuidPage> response = ptvApi.getOrganizationApi().apiOrganizationGet(null, page);
      if (response.isOk()) {
        VmOpenApiGuidPage pageData = response.getResponse();
        
        for (String guid : pageData.getGuidList()) {
          OrganizationIdentifier organizationIdentifier = organizationController.findOrganizationIdentifierBySourceAndId(PtvConsts.SOURCE, guid);
          if (organizationIdentifier == null) {
            organizationController.createOrganizationIdentifier(PtvConsts.SOURCE, guid); 
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
  }
  
}
