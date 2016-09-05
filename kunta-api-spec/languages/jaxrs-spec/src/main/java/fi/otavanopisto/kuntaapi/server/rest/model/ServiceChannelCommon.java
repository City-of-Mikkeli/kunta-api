package fi.otavanopisto.kuntaapi.server.rest.model;

import fi.otavanopisto.kuntaapi.server.rest.model.LocalizedValue;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceChannelServiceHour;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceChannelSupport;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceChannelWebPage;
import java.util.ArrayList;
import java.util.List;



import io.swagger.annotations.*;
import java.util.Objects;


public class ServiceChannelCommon   {
  
  private String id = null;
  private List<LocalizedValue> description = new ArrayList<LocalizedValue>();
  private List<LocalizedValue> name = new ArrayList<LocalizedValue>();
  private List<ServiceChannelWebPage> webPages = new ArrayList<ServiceChannelWebPage>();
  private List<ServiceChannelServiceHour> serviceHours = new ArrayList<ServiceChannelServiceHour>();
  private List<ServiceChannelSupport> supportContacts = new ArrayList<ServiceChannelSupport>();

  /**
   **/
  public ServiceChannelCommon id(String id) {
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
  public ServiceChannelCommon description(List<LocalizedValue> description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<LocalizedValue> getDescription() {
    return description;
  }
  public void setDescription(List<LocalizedValue> description) {
    this.description = description;
  }

  /**
   **/
  public ServiceChannelCommon name(List<LocalizedValue> name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<LocalizedValue> getName() {
    return name;
  }
  public void setName(List<LocalizedValue> name) {
    this.name = name;
  }

  /**
   **/
  public ServiceChannelCommon webPages(List<ServiceChannelWebPage> webPages) {
    this.webPages = webPages;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<ServiceChannelWebPage> getWebPages() {
    return webPages;
  }
  public void setWebPages(List<ServiceChannelWebPage> webPages) {
    this.webPages = webPages;
  }

  /**
   **/
  public ServiceChannelCommon serviceHours(List<ServiceChannelServiceHour> serviceHours) {
    this.serviceHours = serviceHours;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<ServiceChannelServiceHour> getServiceHours() {
    return serviceHours;
  }
  public void setServiceHours(List<ServiceChannelServiceHour> serviceHours) {
    this.serviceHours = serviceHours;
  }

  /**
   **/
  public ServiceChannelCommon supportContacts(List<ServiceChannelSupport> supportContacts) {
    this.supportContacts = supportContacts;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<ServiceChannelSupport> getSupportContacts() {
    return supportContacts;
  }
  public void setSupportContacts(List<ServiceChannelSupport> supportContacts) {
    this.supportContacts = supportContacts;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceChannelCommon serviceChannelCommon = (ServiceChannelCommon) o;
    return Objects.equals(id, serviceChannelCommon.id) &&
        Objects.equals(description, serviceChannelCommon.description) &&
        Objects.equals(name, serviceChannelCommon.name) &&
        Objects.equals(webPages, serviceChannelCommon.webPages) &&
        Objects.equals(serviceHours, serviceChannelCommon.serviceHours) &&
        Objects.equals(supportContacts, serviceChannelCommon.supportContacts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, name, webPages, serviceHours, supportContacts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceChannelCommon {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    webPages: ").append(toIndentedString(webPages)).append("\n");
    sb.append("    serviceHours: ").append(toIndentedString(serviceHours)).append("\n");
    sb.append("    supportContacts: ").append(toIndentedString(supportContacts)).append("\n");
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
