package fi.otavanopisto.kuntaapi.server.rest.model;

import io.swagger.annotations.ApiModel;


/**
 * Specifies a single source for the service
 **/

import io.swagger.annotations.*;
import java.util.Objects;
@ApiModel(description = "Specifies a single source for the service")

public class ServiceSource   {
  
  private String id = null;
  private String name = null;

  /**
   * id of the service source
   **/
  public ServiceSource id(String id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "id of the service source")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   * name of the service source
   **/
  public ServiceSource name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "name of the service source")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceSource serviceSource = (ServiceSource) o;
    return Objects.equals(id, serviceSource.id) &&
        Objects.equals(name, serviceSource.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceSource {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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
