package fi.otavanopisto.kuntaapi.server.integrations.ptv;

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
import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.model.IVmOpenApiService;
import fi.otavanopisto.ptv.client.model.VmOpenApiFintoItem;

@Startup
@Singleton
public class PtvServiceClassWarmup {
  
  private static final int TIMER_INITIAL = 1000;
  private static final int TIMER_INTERVAL = 1000;
  private static final int MAX_RESULTS = 750;
  
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
    firstResult = 350;
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
    
    logger.info(String.format("Updating service classes from %d to %d", firstResult, firstResult +  MAX_RESULTS));
    
    List<Identifier> identifiers = identifierController.listIdentifiersByTypeAndSource(IdType.SERVICE, PtvConsts.IDENTIFIFER_NAME, firstResult, MAX_RESULTS);
    for (Identifier serviceIdentifier : identifiers) {
      ApiResponse<IVmOpenApiService> response = ptvApi.getServiceApi().apiServiceByIdGet(serviceIdentifier.getSourceId());
      if (response.isOk()) {
        for (VmOpenApiFintoItem serviceClass : response.getResponse().getServiceClasses()) {
          Identifier serviceClassIdentifier = identifierController.findIdentifierByTypeSourceAndId(IdType.SERVICE_CLASS, PtvConsts.IDENTIFIFER_NAME, serviceClass.getId());
          if (serviceClassIdentifier == null) {
            identifierController.createIdentifier(IdType.SERVICE_CLASS, PtvConsts.IDENTIFIFER_NAME, serviceClass.getId());
            discoverCount++;
          }          
        }
      } else {
        logger.severe(String.format("Failed to load service %d from PTV", serviceIdentifier.getSourceId())); 
      }
    }
    
    if (identifiers.size() < MAX_RESULTS) {
      firstResult = 0;
    } else {
      firstResult += MAX_RESULTS;
    }
    
    if (discoverCount > 0) {
      logger.severe(String.format("Discovered %d new service classes", discoverCount));
    }
    
    startTimer(TIMER_INTERVAL);
  }
  
  private int firstResult;
}
