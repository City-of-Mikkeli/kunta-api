package fi.otavanopisto.mwp.client;

import java.util.Map;

public abstract class ApiClient {
  
  public abstract <T> ApiResponse<T> doGETRequest(String path, ResultType<T> resultType, Map<String, Object> queryParams, Map<String, Object> postParams);
  
  public abstract <T> ApiResponse<T> doPOSTRequest(String path, ResultType<T> resultType, Map<String, Object> queryParams, Map<String, Object> postParams);
  
  public abstract <T> ApiResponse<T> doPUTRequest(String path, ResultType<T> resultType, Map<String, Object> queryParams, Map<String, Object> postParams);
  
  public abstract <T> ApiResponse<T> doDELETERequest(String path, ResultType<T> resultType, Map<String, Object> queryParams, Map<String, Object> postParams);
  
}