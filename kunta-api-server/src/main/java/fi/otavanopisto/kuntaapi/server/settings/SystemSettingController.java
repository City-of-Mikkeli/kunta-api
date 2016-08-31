package fi.otavanopisto.kuntaapi.server.settings;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.persistence.dao.SystemSettingDAO;
import fi.otavanopisto.kuntaapi.server.persistence.model.SystemSetting;

/**
 * Controller for system settings. Class does not handle concurrency so caller must take care of that
 * 
 * @author Otavan Opisto
 */
@Dependent
public class SystemSettingController {

  @Inject
  private SystemSettingDAO systemSettingDAO;
  
  private SystemSettingController() {
  }
  
  /**
   * Returns system setting by key or null if setting is not defined
   * 
   * @param key system setting key
   * @return setting value
   */
  public String getSettingValue(String key) {
    SystemSetting systemSetting = systemSettingDAO.findByKey(key);
    if (systemSetting != null) {
      return systemSetting.getValue();
    }
    
    return null;
  }
  
  /**
   * Updates system setting value
   * 
   * @param key system setting key
   * @param value new setting value
   */
  public void setSettingValue(String key, String value) {
    SystemSetting systemSetting = systemSettingDAO.findByKey(key);
    if (systemSetting != null) {
      systemSettingDAO.updateValue(systemSetting, value);
    } else {
      systemSettingDAO.create(key, value);
    }
  }
  
}
