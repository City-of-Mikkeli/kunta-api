package fi.otavanopisto.kuntaapi.server.rest.model;

import fi.otavanopisto.kuntaapi.server.rest.model.LocalizedValue;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceHour;
import fi.otavanopisto.kuntaapi.server.rest.model.Support;
import fi.otavanopisto.kuntaapi.server.rest.model.WebPage;
import java.util.ArrayList;
import java.util.List;



import io.swagger.annotations.*;
import java.util.Objects;


public class ServiceChannelCommon   {
  
  private String id = null;
  private String organizationId = null;
  private List<LocalizedValue> description = new ArrayList<LocalizedValue>();
  private List<LocalizedValue> name = new ArrayList<LocalizedValue>();
  private List<WebPage> webPages = new ArrayList<WebPage>();
  private List<ServiceHour> serviceHours = new ArrayList<ServiceHour>();
  private List<Support> supportContacts = new ArrayList<Support>();

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
  public ServiceChannelCommon organizationId(String organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getOrganizationId() {
    return organizationId;
  }
  public void setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
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
  public ServiceChannelCommon webPages(List<WebPage> webPages) {
    this.webPages = webPages;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<WebPage> getWebPages() {
    return webPages;
  }
  public void setWebPages(List<WebPage> webPages) {
    this.webPages = webPages;
  }

  /**
   **/
  public ServiceChannelCommon serviceHours(List<ServiceHour> serviceHours) {
    this.serviceHours = serviceHours;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<ServiceHour> getServiceHours() {
    return serviceHours;
  }
  public void setServiceHours(List<ServiceHour> serviceHours) {
    this.serviceHours = serviceHours;
  }

  /**
   **/
  public ServiceChannelCommon supportContacts(List<Support> supportContacts) {
    this.supportContacts = supportContacts;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<Support> getSupportContacts() {
    return supportContacts;
  }
  public void setSupportContacts(List<Support> supportContacts) {
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
        Objects.equals(organizationId, serviceChannelCommon.organizationId) &&
        Objects.equals(description, serviceChannelCommon.description) &&
        Objects.equals(name, serviceChannelCommon.name) &&
        Objects.equals(webPages, serviceChannelCommon.webPages) &&
        Objects.equals(serviceHours, serviceChannelCommon.serviceHours) &&
        Objects.equals(supportContacts, serviceChannelCommon.supportContacts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, organizationId, description, name, webPages, serviceHours, supportContacts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceChannelCommon {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
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
