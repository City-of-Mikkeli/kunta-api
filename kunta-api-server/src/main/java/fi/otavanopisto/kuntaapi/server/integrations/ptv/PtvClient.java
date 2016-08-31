package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.http.client.utils.URIBuilder;

import fi.otavanopisto.kuntaapi.server.integrations.GenericHttpCache;
import fi.otavanopisto.kuntaapi.server.integrations.GenericHttpClient;
import fi.otavanopisto.kuntaapi.server.integrations.GenericHttpClient.Response;
import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.ResultType;

/**
 * API Client for palvelutietoväylä
 * 
 * @author Antti Leppä
 */
@Dependent
public class PtvClient extends fi.otavanopisto.ptv.client.ApiClient {

  private static final String INVALID_URI_SYNTAX = "Invalid uri syntax";

  private static final String BASE_PATH = "http://ptvenv.cloudapp.net:1494";

  @Inject
  private Logger logger;

  @Inject
  private GenericHttpClient httpClient;

  @Inject
  private GenericHttpCache httpCache;
  
  private PtvClient() {
  }
  
  @Override
  public <T> ApiResponse<T> doGETRequest(String path, ResultType<T> resultType, Map<String, Object> queryParams, Map<String, Object> postParams) {
    URIBuilder uriBuilder;
    try {
      uriBuilder = new URIBuilder(String.format("%s%s", BASE_PATH, path));
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, INVALID_URI_SYNTAX, e);
      return new ApiResponse<>(500, INVALID_URI_SYNTAX, null);
    }
    
    if (queryParams != null) {
      for (Entry<String, Object> entry : queryParams.entrySet()) {
        uriBuilder.addParameter(entry.getKey(), parameterToString(entry.getValue()));
      }
    }
    
    URI uri;
    try {
      uri = uriBuilder.build();
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, INVALID_URI_SYNTAX, e);
      return new ApiResponse<>(500, INVALID_URI_SYNTAX, null);
    }
    
    Response<T> response = httpCache.get(PtvConsts.CACHE_NAME, uri, new GenericHttpClient.ResultType<T>() {});
    if (response == null) {
      response = httpClient.doGETRequest(uri, new GenericHttpClient.ResultType<T>() {});
      httpCache.put(PtvConsts.CACHE_NAME, uri, response);
    }
    
    return new ApiResponse<>(response.getStatus(), response.getMessage(), response.getResponseEntity());
  }
  
  @Override
  public <T> ApiResponse<T> doPOSTRequest(String path, ResultType<T> resultType, Map<String, Object> queryParams,
      Map<String, Object> postParams) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> ApiResponse<T> doPUTRequest(String path, ResultType<T> resultType, Map<String, Object> queryParams,
      Map<String, Object> postParams) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> ApiResponse<T> doDELETERequest(String path, ResultType<T> resultType, Map<String, Object> queryParams,
      Map<String, Object> postParams) {
    // TODO Auto-generated method stub
    return null;
  }
  
  private String parameterToString(Object value) {
    return String.valueOf(value);
  }
  
}
