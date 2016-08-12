package fi.otavanopisto.mwp.client;

public class ApiResponse<T> {

  public ApiResponse(int status, String message, T response) {
    this.status = status; 
    this.response = response;
    this.message = message;
  }

  public T getResponse() {
    return response;
  }

  public int getStatus() {
    return status;
  }
  
  public String getMessage() {
    return message;
  }

  public boolean isOk() {
    return status >= 200 && status <= 299;
  }

  private int status;
  private String message;
  private T response;
}