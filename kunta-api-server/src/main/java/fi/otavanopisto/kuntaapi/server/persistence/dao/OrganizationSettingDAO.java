package fi.otavanopisto.kuntaapi.server.persistence.dao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;
import fi.otavanopisto.kuntaapi.server.persistence.model.OrganizationSetting;
import fi.otavanopisto.kuntaapi.server.persistence.model.OrganizationSetting_;

/**
 * DAO class for OrganizationSetting entity
 * 
 * @author Otavan Opisto
 */
@Dependent
public class OrganizationSettingDAO extends AbstractDAO<OrganizationSetting> {

  private static final long serialVersionUID = 8710294058847591299L;

  /**
   * Creates new OrganizationSetting entity
   * 
   * @param key key of setting. Must by unique among the organization
   * @param value setting value. Not nullable.
   * @param organizationIdentifier identifier entity of organization
   * 
   * @return created OrganizationSetting entity
   */
  public OrganizationSetting create(String key, String value, Identifier organizationIdentifier) {
    OrganizationSetting organizationSetting = new OrganizationSetting();
    
    organizationSetting.setKey(key);
    organizationSetting.setValue(value);
    organizationSetting.setOrganizationIdentifier(organizationIdentifier);
    
    return persist(organizationSetting);
  }

  /**
   * Finds organization setting by key and organizationIdentifier
   * 
   * @param key setting key
   * @param organizationIdentifier identifier entity of organization
   * @return found setting or null if non found
   */
  public OrganizationSetting findByKeyAndOrganizationIdentifier(String key, Identifier organizationIdentifier) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<OrganizationSetting> criteria = criteriaBuilder.createQuery(OrganizationSetting.class);
    Root<OrganizationSetting> root = criteria.from(OrganizationSetting.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.and(
        criteriaBuilder.equal(root.get(OrganizationSetting_.key), key),
        criteriaBuilder.equal(root.get(OrganizationSetting_.organizationIdentifier), organizationIdentifier)
      )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }
  
  /**
   * Updates organization setting value
   * 
   * @param setting organization setting
   * @param value new value
   * @return updated organization setting
   */
  public OrganizationSetting updateValue(OrganizationSetting setting, String value) {
    setting.setValue(value);
    return persist(setting);
  }
  
  @Override
  public void delete(OrganizationSetting setting) {
    super.delete(setting);
  }
  
}
