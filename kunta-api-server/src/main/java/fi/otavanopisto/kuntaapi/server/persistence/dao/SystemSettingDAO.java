package fi.otavanopisto.kuntaapi.server.persistence.dao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.otavanopisto.kuntaapi.server.persistence.model.SystemSetting;
import fi.otavanopisto.kuntaapi.server.persistence.model.SystemSetting_;

/**
 * DAO class for SystemSetting entity
 * 
 * @author Otavan Opisto
 */
@Dependent
public class SystemSettingDAO extends AbstractDAO<SystemSetting> {

  /**
   * Creates new system setting entity
   * 
   * @param key key of setting. Must by unique.
   * @param value setting value. Not nullable.
   * 
   * @return created system setting entity
   */
  public SystemSetting create(String key, String value) {
    SystemSetting systemSetting = new SystemSetting();
    
    systemSetting.setKey(key);
    systemSetting.setValue(value);
    
    return persist(systemSetting);
  }

  /**
   * Finds system setting by key
   * 
   * @param key setting key
   * @return found setting or null if non found
   */
  public SystemSetting findByKey(String key) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<SystemSetting> criteria = criteriaBuilder.createQuery(SystemSetting.class);
    Root<SystemSetting> root = criteria.from(SystemSetting.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.and(
        criteriaBuilder.equal(root.get(SystemSetting_.key), key)
      )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }
  
  /**
   * Updates system setting value
   * 
   * @param setting system setting
   * @param value new value
   * @return updated system setting
   */
  public SystemSetting updateValue(SystemSetting setting, String value) {
    setting.setValue(value);
    return persist(setting);
  }
  
}
