package fi.otavanopisto.kuntaapi.server.persistence.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.infinispan.persistence.spi.PersistenceException;

import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier_;

/**
 * DAO class for Identififer entity
 * 
 * @author Otavan Opisto
 */
@Dependent
public class IdentifierDAO extends AbstractDAO<Identifier> {
  
  @PostConstruct
  public void init() {
    identifierLookup = Collections.synchronizedMap(new HashMap<>());
  }

  /**
   * Creates new Identifier entity
   * 
   * @param type identifier type
   * @param kuntaApiId Kunta API id 
   * @param source source
   * @param sourceId id in source system
   * @return created identifier
   */
  public Identifier create(String type, String kuntaApiId, String source, String sourceId) {
    Identifier identifier = new Identifier();
    
    identifier.setType(type);
    identifier.setKuntaApiId(kuntaApiId);
    identifier.setSource(source);
    identifier.setSourceId(sourceId);
    
    return storeToLookup(persist(identifier));
  }

  /**
   * Finds identifier by source, type and source id
   * 
   * @param type identifier type
   * @param source source
   * @param sourceId id in source system
   * @return found identifier or null if non found
   */
  public Identifier findByTypeSourceAndSourceId(String type, String source, String sourceId) {
    Identifier lookedup = findLookupBySourceSourceId(type, source, sourceId);
    if (lookedup != null) {
      return lookedup;
    }

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

  /**
   * Finds identifier by type, source and Kunta API id
   * 
   * @param type identifier type
   * @param source source
   * @param kuntaApiId Kunta API id 
   * @return found identifier or null if non found
   */
  public Identifier findByTypeSourceAndKuntaApiId(String type, String source, String kuntaApiId) {
    Identifier lookedup = findLookupBySourceKuntaApiId(type, source, kuntaApiId);
    if (lookedup != null) {
      return lookedup;
    }
    
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

  /**
   * Lists identifiers by type and source
   * 
   * @param type identifier type
   * @param source source
   * @param firstResult first result. Null is interpret as 0
   * @param maxResults maximum number of results. Specifying null returns all identifiers
   * @return
   */
  public List<Identifier> listByTypeAndSource(String type, String source, Integer firstResult, Integer maxResults) {
    List<Identifier> lookedup = listLookupByTypeSource(type, source);
    if (lookedup != null) {
      return lookedup;
    }
    
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
  
  private Identifier findLookupBySourceKuntaApiId(String type, String source, String kuntaApiId) {
    return getSingleLookupIdentifer(createTypeSourceKuntaApiMapKey(type, source, kuntaApiId));
  }
  
  private Identifier findLookupBySourceSourceId(String type, String source, String sourceId) {
    return getSingleLookupIdentifer(createTypeSourceIdMapKey(type, source, sourceId));
  }

  private List<Identifier> listLookupByTypeSource(String type, String source) {
    return identifierLookup.get(createTypeSourceMapKey(type, source));
  }
  
  private Identifier getSingleLookupIdentifer(String key) {
    List<Identifier> identifiers = identifierLookup.get(key);
    if (identifiers == null || identifiers.isEmpty()) {
      return null;
    }
    
    if (identifiers.size() > 1) {
      throw new PersistenceException(String.format("SingleResult lookup returned %d elements", identifiers.size()));
    }
    
    return identifiers.get(0);
  }

  private String createTypeSourceKuntaApiMapKey(String type, String source, String kuntaApiId) {
    return StringUtils.join(new String[] { "TSK", type, source, kuntaApiId }, "/");
  }
  
  private String createTypeSourceIdMapKey(String type, String source, String sourceId) {
    return StringUtils.join(new String[] { "TSI", type, source, sourceId }, "/");
  }
  
  private String createTypeSourceMapKey(String type, String source) {
    return StringUtils.join(new String[] { "TS", type, source }, "/");
  }
  
  private Identifier storeToLookup(Identifier identifier) {
    identifierLookup.put(createTypeSourceKuntaApiMapKey(identifier.getType(), identifier.getSource(), identifier.getKuntaApiId()), Arrays.asList(identifier));
    identifierLookup.put(createTypeSourceIdMapKey(identifier.getType(), identifier.getSource(), identifier.getSourceId()), Arrays.asList(identifier));
  
    String typeSourceKey = createTypeSourceMapKey(identifier.getType(), identifier.getSource());
    if (identifierLookup.containsKey(typeSourceKey)) {
      identifierLookup.get(typeSourceKey).add(identifier);
    } else {
      identifierLookup.put(typeSourceKey, new ArrayList<>(Arrays.asList(identifier)));
    }
    
    return identifier;
  }
  
  private Map<String, List<Identifier>> identifierLookup;
}
