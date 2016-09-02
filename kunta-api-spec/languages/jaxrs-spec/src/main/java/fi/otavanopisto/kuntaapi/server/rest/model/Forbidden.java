package fi.otavanopisto.kuntaapi.server.rest.model;




import io.swagger.annotations.*;
import java.util.Objects;


public class Forbidden   {
  
  private Integer code = null;
  private String message = null;

  /**
   **/
  public Forbidden code(Integer code) {
    this.code = code;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public Integer getCode() {
    return code;
  }
  public void setCode(Integer code) {
    this.code = code;
  }

  /**
   **/
  public Forbidden message(String message) {
    this.message = message;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Forbidden forbidden = (Forbidden) o;
    return Objects.equals(code, forbidden.code) &&
        Objects.equals(message, forbidden.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Forbidden {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
