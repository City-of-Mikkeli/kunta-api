package fi.otavanopisto.kuntaapi.server.persistence.index;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.CacheMode;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

@Singleton
@Startup
@SuppressWarnings ("squid:S3306")
public class IndexStartup {
  
  private static final int TIMER_INITIAL = 1000;

  @Inject
  private Logger logger;
  
  @PersistenceContext
  private EntityManager entityManager;
  
  @Resource
  private TimerService timerService;
  
  @PostConstruct
  public void init() {
    startTimer(TIMER_INITIAL);
  }
  
  private void startTimer(int duration) {
    TimerConfig timerConfig = new TimerConfig();
    timerConfig.setPersistent(false);
    timerService.createSingleActionTimer(duration, timerConfig);
  }
  
  @Timeout
  public void timeout(Timer timer) {
    reindexHibernateSearchObjects(getIndexedEntities());
  }

  private void reindexHibernateSearchObjects(List<Class<?>> entities) {
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
    MassIndexer massIndexer = fullTextEntityManager.createIndexer(entities.toArray(new Class[0]));
    massIndexer.cacheMode(CacheMode.IGNORE);
    try {
      massIndexer.startAndWait();
    } catch (InterruptedException e) {
      logger.log(Level.SEVERE, "Indexer interrupted", e);
      Thread.currentThread().interrupt();
    }
  }
  
  private List<Class<?>> getIndexedEntities() {
    List<Class<?>> result = new ArrayList<>();
    
    Metamodel metamodel = entityManager.getEntityManagerFactory().getMetamodel();
    for (EntityType<?> entityType : metamodel.getEntities()) {
      Class<?> entityClass = entityType.getJavaType();
      if (entityClass.isAnnotationPresent(Indexed.class)) {
        result.add(entityClass);
      }
    }
    
    return result;
  }
}
