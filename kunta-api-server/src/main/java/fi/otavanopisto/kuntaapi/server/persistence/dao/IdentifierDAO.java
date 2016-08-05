package fi.otavanopisto.kuntaapi.server.persistence.dao;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier_;

@Dependent
public class IdentifierDAO extends AbstractDAO<Identifier> {

  private static final long serialVersionUID = 8710294058847591299L;

  public Identifier create(String type, String kuntaApiId, String source, String sourceId) {
    Identifier identifier = new Identifier();
    
    identifier.setType(type);
    identifier.setKuntaApiId(kuntaApiId);
    identifier.setSource(source);
    identifier.setSourceId(sourceId);
    
    return persist(identifier);
  }

  public Identifier findByTypeSourceAndSourceId(String type, String source, String sourceId) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Identifier> criteria = criteriaBuilder.createQuery(Identifier.class);
    Root<Identifier> root = criteria.from(Identifier.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.and(
          criteriaBuilder.equal(root.get(Identifier_.type), type),
          criteriaBuilder.equal(root.get(Identifier_.source), source),
          criteriaBuilder.equal(root.get(Identifier_.sourceId), sourceId)          
      )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }

  public Identifier findByTypeSourceAndKuntaApiId(String type, String source, String kuntaApiId) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Identifier> criteria = criteriaBuilder.createQuery(Identifier.class);
    Root<Identifier> root = criteria.from(Identifier.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.and(
          criteriaBuilder.equal(root.get(Identifier_.type), type),
          criteriaBuilder.equal(root.get(Identifier_.source), source),
          criteriaBuilder.equal(root.get(Identifier_.kuntaApiId), kuntaApiId)          
      )
    );
    
    return getSingleResult(entityManager.createQuery(criteria));
  }

  public List<Identifier> listByTypeAndSource(String type, String source, Integer firstResult, Integer maxResults) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Identifier> criteria = criteriaBuilder.createQuery(Identifier.class);
    Root<Identifier> root = criteria.from(Identifier.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.and(
        criteriaBuilder.equal(root.get(Identifier_.type), type),
        criteriaBuilder.equal(root.get(Identifier_.source), source)     
      )
    );
    
    TypedQuery<Identifier> query = entityManager.createQuery(criteria);
    if (firstResult != null) {
      query.setFirstResult(firstResult);
    }
    
    if (maxResults != null) {
      query.setMaxResults(maxResults);
    }
    
    return query.getResultList();
  }
  
}
