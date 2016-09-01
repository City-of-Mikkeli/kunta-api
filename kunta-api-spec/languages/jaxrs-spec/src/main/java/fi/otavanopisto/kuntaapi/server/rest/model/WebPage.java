package fi.otavanopisto.kuntaapi.server.rest.model;




import io.swagger.annotations.*;
import java.util.Objects;


public class WebPage   {
  
  private String type = null;
  private String value = null;
  private String url = null;
  private String language = null;

  /**
   **/
  public WebPage type(String type) {
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
  public WebPage value(String value) {
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

  /**
   **/
  public WebPage url(String url) {
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
  public WebPage language(String language) {
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
    WebPage webPage = (WebPage) o;
    return Objects.equals(type, webPage.type) &&
        Objects.equals(value, webPage.value) &&
        Objects.equals(url, webPage.url) &&
        Objects.equals(language, webPage.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, value, url, language);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WebPage {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
