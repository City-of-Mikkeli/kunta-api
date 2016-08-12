package fi.otavanopisto.kuntaapi.server.integrations.mwp;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

@Stateless
public class MwpCache {
  
  @Resource (lookup = "java:jboss/infinispan/container/kunta-api")
  private EmbeddedCacheManager cacheManager;
  
  @PostConstruct
  public void init() {
  }

  public String getCached(String url) {
    Cache<String, String> cache = cacheManager.getCache("mvp");
    return cache.get(url);
  }
  
  public void cacheResponse(String url, String response) {
    Cache<String, String> cache = cacheManager.getCache("mvp");
    cache.put(url, response);
  }
  
}
