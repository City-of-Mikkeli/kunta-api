package fi.otavanopisto.kuntaapi.server.integrations;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Response aware HTTP client for integrations
 * 
 * @author Otavan Opisto
 */
@Dependent
public class GenericHttpClient {

  private static final String REQUEST_TIMED_OUT = "Request timed out";
  private static final String RESPONSE_PARSING_FAILED = "Response parsing failed";
  private static final String INVALID_URI_SYNTAX = "Invalid uri syntax";
  
  @Inject
  private Logger logger;

  private GenericHttpClient() {
  }
  
  /**
   * Executes a get request into a specified URI
   * 
   * @param basePath base path for all requests
   * @param path path of request
   * @param resultType type of request
   * @param queryParams query params
   * @return the response
   */
  public <T> Response<T> doGETRequest(String basePath, String path, ResultType<T> resultType, Map<String, Object> queryParams) {
    URIBuilder uriBuilder;
    try {
      uriBuilder = new URIBuilder(String.format("%s%s", basePath, path));
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, INVALID_URI_SYNTAX, e);
      return new Response<>(500, INVALID_URI_SYNTAX, null);
    }
    
    if (queryParams != null) {
      for (Entry<String, Object> entry : queryParams.entrySet()) {
        uriBuilder.addParameter(entry.getKey(), parameterToString(entry.getValue()));
      }
    }
    
    try {
      return doGETRequest(uriBuilder.build(), resultType);
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, INVALID_URI_SYNTAX, e);
      return new Response<>(500, INVALID_URI_SYNTAX, null);
    }
  }

  /**
   * Executes a get request into a specified URI
   * 
   * @param uri request uri
   * @param resultType type of request
   * @param queryParams query params
   * @return the response
   */
  public <T> Response<T> doGETRequest(URI uri, ResultType<T> resultType) {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      return executeRequest(resultType, uri, httpClient);
    } finally {
      closeClient(httpClient);
    }
  }

  private <T> Response<T> executeRequest(ResultType<T> resultType, URI uri,
      CloseableHttpClient httpClient) {
    HttpGet httpGet = new HttpGet(uri);
   
    try {
      CloseableHttpResponse response = httpClient.execute(httpGet);
      try {
        return createResponse(response, resultType);
      } finally {
        response.close();
      }
    } catch (JsonParseException | JsonMappingException  e) {
      logger.log(Level.SEVERE, RESPONSE_PARSING_FAILED, e);
      return new Response<>(500, RESPONSE_PARSING_FAILED, null);
    } catch (IOException e) {
      logger.log(Level.SEVERE, REQUEST_TIMED_OUT, e);
      return new Response<>(408, REQUEST_TIMED_OUT, null);
    }
  }
  
  private void closeClient(CloseableHttpClient httpClient) {
    try {
      httpClient.close();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Failed to close http client", e);
    }
  }

  private String parameterToString(Object value) {
    return String.valueOf(value);
  }
  
  private <T> Response<T> createResponse(HttpResponse httpResponse, ResultType<T> resultType) throws IOException {
    StatusLine statusLine = httpResponse.getStatusLine();
    int statusCode = statusLine.getStatusCode();
    String message = statusLine.getReasonPhrase();
    
    switch (statusCode) {
      case 200:
        return handleOkResponse(httpResponse, statusCode, message, resultType.getTypeReference());
      case 204:
        return handleNoContentResponse(statusCode, message, resultType.getTypeReference());
      default:
      return handleErrorResponse(statusCode, message);
    }
  }

  @SuppressWarnings("unchecked")
  private <T> Response<T> handleOkResponse(HttpResponse httpResponse, int statusCode, String message, TypeReference<T> typeReference) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    
    HttpEntity entity = httpResponse.getEntity();
    try {
      String httpResponseContent = IOUtils.toString(entity.getContent());
      return new Response<>(statusCode, message, (T) objectMapper.readValue(httpResponseContent, typeReference));
    } finally {
      EntityUtils.consume(entity);
    }
  }

  @SuppressWarnings("unchecked")
  private <T> Response<T> handleNoContentResponse(int statusCode, String message, TypeReference<T> typeReference) {
    Type type = typeReference.getType();
    if (type instanceof Class) {
      Class<T> clazz = (Class<T>) type;
      if (clazz.isArray()) {
        return new Response<>(statusCode, message, (T) Array.newInstance(clazz.getComponentType(), 0));
      }
    }
    
    return new Response<>(statusCode, message, null);
  }

  private <T> Response<T> handleErrorResponse(int statusCode, String message) {
    return new Response<>(statusCode, message, null);
  }
  
  /**
   * Interface representing returned entity type
   * 
   * @author Otavan Opisto
   *
   * @param <T> type of returned entity
   */
  @SuppressWarnings ("squid:S2326")
  public static class ResultType<T> {
    
    /**
     * Returns generic type of result type
     * 
     * @return generic type of result type
     */
    public Type getType() {
      Type superClass = getClass().getGenericSuperclass();
      if (superClass instanceof ParameterizedType) {
        return ((ParameterizedType) superClass).getActualTypeArguments()[0];
      }
        
      return null;
    }
    
    public TypeReference<T> getTypeReference() {
      Type type = getType();
      return new TypeReference<T>() { 
        @Override
        public Type getType() {
          return type;
        }
      };
    }
  }
  
  /**
   * Class representing request response
   * 
   * @author Otavan Opisto
   *
   * @param <T> response type
   */
  public static class Response<T> {

    private int status;
    private String message;
    private T responseEntity;
    
    /**
     * Zero-argument constructor for Response
     */
    public Response() {
      // Zero-argument constructor is empty
    }

    /**
     * Constructor for class
     * 
     * @param status HTTP status code
     * @param message HTTP status message
     * @param responseEntity unmarshalled response entity
     */
    public Response(int status, String message, T responseEntity) {
      this.status = status; 
      this.responseEntity = responseEntity;
      this.message = message;
    }

    public T getResponseEntity() {
      return responseEntity;
    }
    
    public void setResponseEntity(T responseEntity) {
      this.responseEntity = responseEntity;
    }
    
    public int getStatus() {
      return status;
    }
    
    public void setStatus(int status) {
      this.status = status;
    }
    
    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
    
    @JsonIgnore    
    public boolean isOk() {
      return status >= 200 && status <= 299;
    }
  }
  
}
