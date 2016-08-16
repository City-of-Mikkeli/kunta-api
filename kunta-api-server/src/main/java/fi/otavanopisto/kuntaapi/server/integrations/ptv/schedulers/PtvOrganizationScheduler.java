package fi.otavanopisto.kuntaapi.server.integrations.ptv.schedulers;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.controllers.IdentifierController;
import fi.otavanopisto.kuntaapi.server.integrations.IdType;
import fi.otavanopisto.kuntaapi.server.integrations.ptv.PtvApi;
import fi.otavanopisto.kuntaapi.server.integrations.ptv.PtvConsts;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;
import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.model.VmOpenApiGuidPage;

@Startup
@Singleton
public class PtvOrganizationScheduler {
  
  private static final int TIMER_INITIAL = 60000;
  private static final int TIMER_INTERVAL = 60000;
  
  @Inject
  private Logger logger;
  
  @Inject
  private PtvApi ptvApi;
  
  @Inject
  private IdentifierController identifierController;
  
  @Resource
  private TimerService timerService;

  @PostConstruct
  public void init() {
    if (!PtvConsts.SYNCHRONIZE_ORGANIZATIONS) {
      return;
    }
    
    page = 0;
    pageCount = 0;
    startTimer(TIMER_INITIAL);
  }
  
  private void startTimer(int duration) {
    TimerConfig timerConfig = new TimerConfig();
    timerConfig.setPersistent(false);
    timerService.createSingleActionTimer(duration, timerConfig);
  }
  
  @Timeout
  public void timeout(Timer timer) {
    int discoverCount = 0;
    boolean hasMore = false;
    
    if (pageCount > 0) {
      logger.info(String.format("Updating organizations page %d / %d", page + 1, pageCount));
    } else {
      logger.info(String.format("Updating organizations page %d", page + 1));
    }
    
    ApiResponse<VmOpenApiGuidPage> response = ptvApi.getOrganizationApi().apiOrganizationGet(null, page);
    if (response.isOk()) {
      VmOpenApiGuidPage pageData = response.getResponse();
      
      for (String guid : pageData.getGuidList()) {
        Identifier identifier = identifierController.findIdentifierByTypeSourceAndId(IdType.ORGANIZATION, PtvConsts.IDENTIFIFER_NAME, guid);
        if (identifier == null) {
          identifierController.createIdentifier(IdType.ORGANIZATION, PtvConsts.IDENTIFIFER_NAME, guid);
          discoverCount++;
        }
      }
      
      pageCount = pageData.getPageCount();
      hasMore = pageCount > page + 1;

      if (discoverCount > 0) {
        logger.info(String.format("Discovered %d new organizations", discoverCount));
      }
    } else {
      logger.severe(String.format("Failed to update organizations from PTV (%d: %s)", response.getStatus(), response.getMessage()));
    }
    
    if (hasMore) {
      page++;
    } else {
      page = 0;
    }
    
    startTimer(TIMER_INTERVAL);
  }
  
  private int page;
  private int pageCount;
}
