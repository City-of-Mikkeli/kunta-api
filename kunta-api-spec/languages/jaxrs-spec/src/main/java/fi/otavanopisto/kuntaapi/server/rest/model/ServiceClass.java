package fi.otavanopisto.kuntaapi.server.rest.model;




import io.swagger.annotations.*;
import java.util.Objects;


public class ServiceClass   {
  
  private String id = null;
  private String name = null;
  private String code = null;
  private String ontologyType = null;
  private String uri = null;
  private String parentId = null;
  private String parentUri = null;

  /**
   **/
  public ServiceClass id(String id) {
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
  public ServiceClass name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  public ServiceClass code(String code) {
    this.code = code;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }

  /**
   **/
  public ServiceClass ontologyType(String ontologyType) {
    this.ontologyType = ontologyType;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getOntologyType() {
    return ontologyType;
  }
  public void setOntologyType(String ontologyType) {
    this.ontologyType = ontologyType;
  }

  /**
   **/
  public ServiceClass uri(String uri) {
    this.uri = uri;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getUri() {
    return uri;
  }
  public void setUri(String uri) {
    this.uri = uri;
  }

  /**
   **/
  public ServiceClass parentId(String parentId) {
    this.parentId = parentId;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getParentId() {
    return parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  /**
   **/
  public ServiceClass parentUri(String parentUri) {
    this.parentUri = parentUri;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getParentUri() {
    return parentUri;
  }
  public void setParentUri(String parentUri) {
    this.parentUri = parentUri;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceClass serviceClass = (ServiceClass) o;
    return Objects.equals(id, serviceClass.id) &&
        Objects.equals(name, serviceClass.name) &&
        Objects.equals(code, serviceClass.code) &&
        Objects.equals(ontologyType, serviceClass.ontologyType) &&
        Objects.equals(uri, serviceClass.uri) &&
        Objects.equals(parentId, serviceClass.parentId) &&
        Objects.equals(parentUri, serviceClass.parentUri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, code, ontologyType, uri, parentId, parentUri);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceClass {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    ontologyType: ").append(toIndentedString(ontologyType)).append("\n");
    sb.append("    uri: ").append(toIndentedString(uri)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
    sb.append("    parentUri: ").append(toIndentedString(parentUri)).append("\n");
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
