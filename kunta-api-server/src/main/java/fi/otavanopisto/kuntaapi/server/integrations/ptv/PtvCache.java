package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

/**
 * Cache controller for PTV
 * 
 * @author Otavan Opisto
 */
@Stateless
public class PtvCache {
  
  @Resource (lookup = "java:jboss/infinispan/container/kunta-api")
  private EmbeddedCacheManager cacheManager;
  
  /**
   * Returns cached response for a URL
   * 
   * @param url URL of entity
   * @return cached response for a URL
   */
  public String getCached(String url) {
    Cache<String, String> cache = cacheManager.getCache("ptv");
    return cache.get(url);
  }
  
  /**
   * Stores PTV response into the cache
   * 
   * @param url URL of entity
   * @param response PTV response to be cached
   */
  public void cacheResponse(String url, String response) {
    Cache<String, String> cache = cacheManager.getCache("ptv");
    cache.put(url, response);
  }
  
}
