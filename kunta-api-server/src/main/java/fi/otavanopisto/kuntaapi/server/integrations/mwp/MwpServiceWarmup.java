package fi.otavanopisto.kuntaapi.server.integrations.mwp;

import java.util.List;
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
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;
import fi.otavanopisto.mwp.client.ApiResponse;
import fi.otavanopisto.mwp.client.model.Page;

@Startup
@Singleton
public class MwpServiceWarmup {
  
  private static final int TIMER_INITIAL = 60000;
  private static final int TIMER_INTERVAL = 60000;
  private static final int RESULTS_PER_PAGE = 10;
  
  @Inject
  private Logger logger;
  
  @Inject
  private MwpApi mwpApi;
  
  @Inject
  private IdentifierController identifierController;
  
  @Resource
  private TimerService timerService;

  @PostConstruct
  public void init() {
    if (!MwpConsts.SYNCHRONIZE) {
      return;
    }
    
    page = 0;
    
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
    
    ApiResponse<List<Page>> response = mwpApi.getApi().wpV2PagesGet(null, page + 1, RESULTS_PER_PAGE, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    if (response.isOk()) {
      for (Page page : response.getResponse()) {
        String pageId = String.valueOf(page.getId());
        String kuntaApiId = page.getKuntaApiData().getServiceId();
        
        Identifier identifier = identifierController.findIdentifierByTypeSourceAndId(IdType.SERVICE, MwpConsts.IDENTIFIER_NAME, pageId);
        if (identifier == null) {
          identifierController.createIdentifier(IdType.SERVICE, kuntaApiId, MwpConsts.IDENTIFIER_NAME, pageId);
          discoverCount++;
        }
      }
      
      if (response.getResponse().isEmpty()) {
        page = 0;
      } else {
        page++;
      }
      
    } else {
      logger.severe(String.format("Failed to list pages from MWP (%d: %s)", response.getStatus(), response.getMessage()));
    }
    
    if (discoverCount > 0) {
      logger.info(String.format("Discovered %d new services", discoverCount));
    }

    startTimer(TIMER_INTERVAL);
  }
  
  private int page;
}
