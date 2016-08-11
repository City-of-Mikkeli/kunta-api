package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

@Stateless
public class PtvCache {
  
  @Resource (lookup = "java:jboss/infinispan/container/kunta-api")
  private EmbeddedCacheManager cacheManager;
  
  @PostConstruct
  public void init() {
  }

  public String getCached(String url) {
    Cache<String, String> cache = cacheManager.getCache("ptv");
    return cache.get(url);
  }
  
  public void cacheResponse(String url, String response) {
    Cache<String, String> cache = cacheManager.getCache("ptv");
    cache.put(url, response);
  }
  
}
