package fi.otavanopisto.kuntaapi.server.rest.model;




import io.swagger.annotations.*;
import java.util.Objects;


public class ServiceChannelAttachment   {
  
  private String type = null;
  private String name = null;
  private String description = null;
  private String url = null;
  private String language = null;

  /**
   **/
  public ServiceChannelAttachment type(String type) {
    this.type = type;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  /**
   **/
  public ServiceChannelAttachment name(String name) {
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
  public ServiceChannelAttachment description(String description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   **/
  public ServiceChannelAttachment url(String url) {
    this.url = url;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   **/
  public ServiceChannelAttachment language(String language) {
    this.language = language;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceChannelAttachment serviceChannelAttachment = (ServiceChannelAttachment) o;
    return Objects.equals(type, serviceChannelAttachment.type) &&
        Objects.equals(name, serviceChannelAttachment.name) &&
        Objects.equals(description, serviceChannelAttachment.description) &&
        Objects.equals(url, serviceChannelAttachment.url) &&
        Objects.equals(language, serviceChannelAttachment.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, name, description, url, language);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceChannelAttachment {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
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
