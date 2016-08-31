package fi.otavanopisto.kuntaapi.server.settings;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.integrations.IdController;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.persistence.dao.OrganizationSettingDAO;
import fi.otavanopisto.kuntaapi.server.persistence.model.OrganizationSetting;

/**
 * Controller for organization settings. Class does not handle concurrency so caller must take care of that
 * 
 * @author Otavan Opisto
 */
@Dependent
public class OrganizationSettingController {
  
  @Inject
  private Logger logger;

  @Inject
  private OrganizationSettingDAO organizationSettingDAO;
  
  @Inject
  private IdController idController;
  
  private OrganizationSettingController() {
  }
  
  /**
   * Returns organization setting by key or null if setting is not defined
   * 
   * @param organizationId organization id
   * @param key system setting key
   * @return setting value
   */
  public String getSettingValue(OrganizationId organizationId, String key) {
    OrganizationId kuntaApiId = idController.translateOrganizationId(organizationId, KuntaApiConsts.IDENTIFIER_NAME);
    if (kuntaApiId != null) {
      return getSettingValue(kuntaApiId.getId(), key);
    } else {
      logger.severe(String.format("Failed to translate %s into KuntaApiId id", organizationId.toString()));
      return null;
    }
  }
  
  /**
   * Returns organization setting by key or null if setting is not defined
   * 
   * @param kuntaApiId Kunta API id of organization
   * @param key system setting key
   * @return setting value
   */
  public String getSettingValue(String kuntaApiId, String key) {
    OrganizationSetting organizationSetting = organizationSettingDAO.findByKeyAndOrganizationKuntaApiId(key, kuntaApiId);
    if (organizationSetting != null) {
      return organizationSetting.getValue();
    }
    
    return null;
  }
  
  /**
   * Updates system setting value
   * 
   * @param key system setting key
   * @param value new setting value
   * @param organizationKuntaApiId 
   */
  public void setSettingValue(String organizationKuntaApiId, String key, String value) {
    OrganizationSetting organizationSetting = organizationSettingDAO.findByKeyAndOrganizationKuntaApiId(key, organizationKuntaApiId);
    if (organizationSetting != null) {
      organizationSettingDAO.updateValue(organizationSetting, value);
    } else {
      organizationSettingDAO.create(key, value, organizationKuntaApiId);
    }
  }
  
}
