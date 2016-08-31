package fi.otavanopisto.kuntaapi.server.persistence.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Abstract base class for all DAO classes
 * 
 * @author Antti Leppä
 *
 * @param <T> entity type
 */
public abstract class AbstractDAO<T> {

  @PersistenceContext
  private EntityManager entityManager;
  
  /**
   * Returns entity by id
   * 
   * @param id entity id
   * @return entity or null if non found
   */
  @SuppressWarnings("unchecked")
  public T findById(Long id) {
    return (T) getEntityManager().find(getGenericTypeClass(), id);
  }

  /**
   * Lists all entities from database
   * 
   * @return all entities from database
   */
  @SuppressWarnings("unchecked")
  public List<T> listAll() {
    Class<?> genericTypeClass = getGenericTypeClass();
    Query query = getEntityManager().createQuery("select o from " + genericTypeClass.getName() + " o");
    return query.getResultList();
  }

  /**
   * Lists all entities from database limited by firstResult and maxResults parameters
   * 
   * @param firstResult first result
   * @param maxResults max results
   * @return all entities from database limited by firstResult and maxResults parameters
   */
  @SuppressWarnings("unchecked")
  public List<T> listAll(int firstResult, int maxResults) {
    Class<?> genericTypeClass = getGenericTypeClass();
    Query query = getEntityManager().createQuery("select o from " + genericTypeClass.getName() + " o");
    query.setFirstResult(firstResult);
    query.setMaxResults(maxResults);
    return query.getResultList();
  }

  /**
   * Returns count of all entities
   * 
   * @return entity count
   */
  public Long count() {
    Class<?> genericTypeClass = getGenericTypeClass();
    Query query = getEntityManager().createQuery("select count(o) from " + genericTypeClass.getName() + " o");
    return (Long) query.getSingleResult();
  }

  /**
   * Deletes entity
   * 
   * @param e entity
   */
  public void delete(T e) {
    getEntityManager().remove(e);
    flush();
  }

  /**
   * Flushes persistence context state
   */
  public void flush() {
    getEntityManager().flush();
  }

  /**
   * Persists an entity
   * 
   * @param object entity to be persisted
   * @return persisted entity
   */
  protected T persist(T object) {
    getEntityManager().persist(object);
    return object;
  }

  protected T getSingleResult(Query query) {
    @SuppressWarnings("unchecked")
    List<T> list = query.getResultList();

    if (list.isEmpty())
      return null;

    if (list.size() == 1)
      return list.get(0);

    throw new NonUniqueResultException("SingleResult query returned " + list.size() + " elements");
  }

  private Class<?> getFirstTypeArgument(ParameterizedType parameterizedType) {
    return (Class<?>) parameterizedType.getActualTypeArguments()[0];
  }

  protected Class<?> getGenericTypeClass() {
    Type genericSuperclass = getClass().getGenericSuperclass();

    if (genericSuperclass instanceof ParameterizedType) {
      return getFirstTypeArgument((ParameterizedType) genericSuperclass);
    } else {
      if ((genericSuperclass instanceof Class<?>) && (AbstractDAO.class.isAssignableFrom((Class<?>) genericSuperclass))) {
        return getFirstTypeArgument((ParameterizedType) ((Class<?>) genericSuperclass).getGenericSuperclass());
      }
    }

    return null;
  }

  protected EntityManager getEntityManager() {
    return entityManager;
  }
}
