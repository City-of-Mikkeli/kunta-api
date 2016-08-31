package fi.otavanopisto.kuntaapi.server.integrations;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fi.otavanopisto.kuntaapi.server.integrations.GenericHttpClient.Response;
import fi.otavanopisto.kuntaapi.server.integrations.GenericHttpClient.ResultType;

/**
 * Response aware client cache for integrations
 * 
 * @author Antti Leppä
 */
@Dependent
public class GenericHttpCache {
  
  @Resource (lookup = "java:jboss/infinispan/container/kunta-api")
  private EmbeddedCacheManager cacheManager;
  
  @Inject
  private Logger logger;
  
  private GenericHttpCache() {
  }
  
  /**
   * Returns cached HTTP response by URI
   * 
   * @param cacheName name of the cache
   * @param uri URI
   * @param type result type
   * @return cached api reposponse or null if non found
   */
  public <T> T get(String cacheName, URI uri, ResultType<T> type) {
    return get(cacheName, uri.toString(), type);
  }

  /**
   * Returns cached HTTP response by URL
   * 
   * @param cacheName name of the cache
   * @param url URL
   * @param type result type
   * @return cached api reposponse or null if non found
   */
  public <T> T get(String cacheName, String url, ResultType<T> type) {
    Cache<String, String> cache = cacheManager.getCache(cacheName);
    if (cache.containsKey(url)) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        return objectMapper.readValue(cache.get(url), type.getTypeReference());
      } catch (IOException e) {
        cache.remove(url);
        logger.log(Level.SEVERE, "Invalid serizalized object found from the cache. Dropped object", e);
      }
    }
    
    return null;
  }
  
  /**
   * Caches a HTTP response
   * 
   * @param cacheName name of the cache
   * @param uri URI
   * @param response
   */
  public void put(String cacheName, URI uri, Response<?> response) {
    put(cacheName, uri.toString(), response);
  }
  
  /**
   * Caches a HTTP response
   * 
   * @param cacheName name of the cache
   * @param url URL
   * @param response
   */
  public void put(String cacheName, String url, Response<?> response) {
    Cache<String, String> cache = cacheManager.getCache(cacheName);
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      cache.put(url, objectMapper.writeValueAsString(response));
    } catch (JsonProcessingException e) {
      logger.log(Level.SEVERE, "Failed to serialize response into cache", e);
    }
  }
  
}
