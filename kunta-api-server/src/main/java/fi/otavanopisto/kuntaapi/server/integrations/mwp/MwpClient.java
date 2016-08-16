package fi.otavanopisto.kuntaapi.server.integrations.mwp;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fi.otavanopisto.mwp.client.ApiResponse;
import fi.otavanopisto.mwp.client.ResultType;

@Dependent
public class MwpClient extends fi.otavanopisto.mwp.client.ApiClient {

  private static final String BASE_PATH = "http://manage.kunta-api.dev/wp-json";

  @Inject
  private Logger logger;

  @Inject
  private MwpCache cache;
  
  @Override
  public <T> ApiResponse<T> doGETRequest(String path, ResultType<T> resultType, Map<String, Object> queryParams, Map<String, Object> postParams) {
    URIBuilder uriBuilder;
    try {
      uriBuilder = new URIBuilder(String.format("%s%s", BASE_PATH, path));
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, "Invalid uri syntax", e);
      return new ApiResponse<T>(500, "Invalid uri syntax", null);
    }
    
    if (queryParams != null) {
      for (Entry<String, Object> entry : queryParams.entrySet()) {
        uriBuilder.addParameter(entry.getKey(), parameterToString(entry.getValue()));
      }
    }
    
    String url = uriBuilder.toString();
    String cachedResponse = cache.getCached(url);
    if (cachedResponse != null) {
      try {
        return createResponse(cachedResponse, resultType);
      } catch (JsonParseException | JsonMappingException  e) {
        logger.log(Level.SEVERE, "Response parsing failed", e);
        return new ApiResponse<T>(500, "Response parsing failed", null);
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Request timed out", e);
        return new ApiResponse<T>(408, "Request timed out", null);
      }
    } else {
      CloseableHttpClient httpclient = HttpClients.createDefault();
      
      HttpGet httpGet;
      try {
        httpGet = new HttpGet(uriBuilder.build());
      } catch (URISyntaxException e) {
        logger.log(Level.SEVERE, "Invalid uri syntax", e);
        return new ApiResponse<T>(500, "Invalid uri syntax", null);
      }
      
      try {
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
          return createResponse(url, response, resultType);
        } finally {
          response.close();
        }
      } catch (JsonParseException | JsonMappingException  e) {
        logger.log(Level.SEVERE, "Response parsing failed", e);
        return new ApiResponse<T>(500, "Response parsing failed", null);
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Request timed out", e);
        return new ApiResponse<T>(408, "Request timed out", null);
      }
    }
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
  
  @SuppressWarnings("unchecked")
  private <T> ApiResponse<T> createResponse(String url, HttpResponse httpResponse, ResultType<T> resultType) throws JsonParseException, JsonMappingException, UnsupportedOperationException, IOException {
    StatusLine statusLine = httpResponse.getStatusLine();
    int statusCode = statusLine.getStatusCode();
    String message = statusLine.getReasonPhrase();
    Class<?> resultClass = getResultClass(resultType);
    TypeReference<T> typeReference = new TypeReference<T>() { 
      @Override
      public Type getType() {
        return resultType.getType();
      }
    };
    
    switch (statusCode) {
      case 200:
        ObjectMapper objectMapper = new ObjectMapper();
        
        HttpEntity entity = httpResponse.getEntity();
        try {
          String httpResponseContent = IOUtils.toString(entity.getContent());
          T reponseObject = objectMapper.readValue(httpResponseContent, typeReference);
          if (reponseObject instanceof List) {
            if (((List<?>) reponseObject).isEmpty()) {
              reponseObject = (T) Collections.emptyList();
            }
          }
          
          ApiResponse<T> response = new ApiResponse<T>(statusCode, message, reponseObject);
          cache.cacheResponse(url, httpResponseContent);
          return response;
        } finally {
          EntityUtils.consume(entity);
        }
      case 204:
        if (resultClass.isAssignableFrom(List.class)) {
          return new ApiResponse<T>(statusCode, message, (T) Collections.emptyList());
        } else if (resultClass.isAssignableFrom(Set.class)) {
          return new ApiResponse<T>(statusCode, message, (T) Collections.emptySet());
        }

        return new ApiResponse<T>(statusCode, message, null);
      default:
        return new ApiResponse<T>(statusCode, message, null);
    }
  }
  
  @SuppressWarnings("unchecked")
  private <T> ApiResponse<T> createResponse(String cachedResponse, ResultType<T> resultType) throws JsonParseException, JsonMappingException, IOException {
    Class<?> resultClass = getResultClass(resultType);
    ObjectMapper objectMapper = new ObjectMapper();
    return new ApiResponse<T>(200, "OK", (T) objectMapper.readValue(cachedResponse, resultClass));
  }

  private <T> Class<?> getResultClass(ResultType<T> resultType) {
    Class<?> resultClass = null;
    
    Type type = resultType.getType();
    if (type instanceof Class) {
      resultClass = (Class<?>) type;
    } else if (type instanceof ParameterizedType) {
       resultClass = (Class<?>) ((ParameterizedType) type).getRawType();
    }
    return resultClass;
  }

}
