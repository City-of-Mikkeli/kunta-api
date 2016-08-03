package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;

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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.ResultType;

@Dependent
public class PtvClient extends fi.otavanopisto.ptv.client.ApiClient {

  private static final String BASE_PATH = "http://ptvenv.cloudapp.net:1494";
  private static final Logger logger = Logger.getLogger(PtvClient.class.getName());
  
  @Override
  public <T> ApiResponse<T> doGETRequest(String path, ResultType<T> resultType, Map<String, Object> queryParams, Map<String, Object> postParams) {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    
    URIBuilder uriBuilder;
    try {
      uriBuilder = new URIBuilder(String.format("%s%s", BASE_PATH, path));
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, "Invalid uri syntax", e);
      return new ApiResponse<T>(500, "Invalid uri syntax", null);
    }
    
    if (queryParams != null) {
      for (Entry<String, Object> entry : queryParams.entrySet()) {
        uriBuilder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
      }
    }
    
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
        return createResponse(response, resultType);
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
  
  @SuppressWarnings("unchecked")
  private <T> ApiResponse<T> createResponse(HttpResponse response, ResultType<T> resultType) throws JsonParseException, JsonMappingException, UnsupportedOperationException, IOException {
    StatusLine statusLine = response.getStatusLine();
    int statusCode = statusLine.getStatusCode();
    String message = statusLine.getReasonPhrase();
    Class<?> resultClass = null;
    
    Type type = resultType.getType();
    if (type instanceof Class) {
      resultClass = (Class<?>) type;
    } else if (type instanceof ParameterizedType) {
       resultClass = (Class<?>) ((ParameterizedType) type).getRawType();
    }
    
    switch (statusCode) {
      case 200:
        ObjectMapper objectMapper = new ObjectMapper();
        
        HttpEntity entity = response.getEntity();
        try {
          return new ApiResponse<T>(statusCode, message, (T) objectMapper.readValue(entity.getContent(), resultClass));
        } finally {
          EntityUtils.consume(entity);
        }
      case 204:
        if (resultClass.isArray()) {
          return new ApiResponse<T>(statusCode, message, (T) Array.newInstance(resultClass.getComponentType(), 0));
        } else {
          return new ApiResponse<T>(statusCode, message, null);
        }
      default:
        return new ApiResponse<T>(statusCode, message, null);
    }
  }

}
