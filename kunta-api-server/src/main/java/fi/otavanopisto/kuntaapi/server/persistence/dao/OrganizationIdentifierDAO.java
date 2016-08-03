package fi.otavanopisto.kuntaapi.server.persistence.dao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.otavanopisto.kuntaapi.server.persistence.model.OrganizationIdentifier;
import fi.otavanopisto.kuntaapi.server.persistence.model.OrganizationIdentifier_;

@Dependent
public class OrganizationIdentifierDAO extends AbstractDAO<OrganizationIdentifier> {

  private static final long serialVersionUID = 8710294058847591299L;

  public OrganizationIdentifier create(String uuid, String source, String sourceId) {
    OrganizationIdentifier organizationIdentifier = new OrganizationIdentifier();
    organizationIdentifier.setUuid(uuid);
    organizationIdentifier.setSource(source);
    organizationIdentifier.setSourceId(sourceId);
    return persist(organizationIdentifier);
  }

  public OrganizationIdentifier findBySourceAndSourceId(String source, String sourceId) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<OrganizationIdentifier> criteria = criteriaBuilder.createQuery(OrganizationIdentifier.class);
    Root<OrganizationIdentifier> root = criteria.from(OrganizationIdentifier.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.and(
          criteriaBuilder.equal(root.get(OrganizationIdentifier_.source), source),
          criteriaBuilder.equal(root.get(OrganizationIdentifier_.sourceId), sourceId)          
      )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }

  public OrganizationIdentifier findBySourceAndUuid(String source, String uuid) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<OrganizationIdentifier> criteria = criteriaBuilder.createQuery(OrganizationIdentifier.class);
    Root<OrganizationIdentifier> root = criteria.from(OrganizationIdentifier.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.and(
          criteriaBuilder.equal(root.get(OrganizationIdentifier_.source), source),
          criteriaBuilder.equal(root.get(OrganizationIdentifier_.uuid), uuid)          
      )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }
  
}
