package fi.otavanopisto.kuntaapi.server.rest.model;




import io.swagger.annotations.*;
import java.util.Objects;


public class OrganizationSetting   {
  
  private String id = null;
  private String key = null;
  private String value = null;

  /**
   **/
  public OrganizationSetting id(String id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   **/
  public OrganizationSetting key(String key) {
    this.key = key;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
  }

  /**
   **/
  public OrganizationSetting value(String value) {
    this.value = value;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationSetting organizationSetting = (OrganizationSetting) o;
    return Objects.equals(id, organizationSetting.id) &&
        Objects.equals(key, organizationSetting.key) &&
        Objects.equals(value, organizationSetting.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationSetting {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
