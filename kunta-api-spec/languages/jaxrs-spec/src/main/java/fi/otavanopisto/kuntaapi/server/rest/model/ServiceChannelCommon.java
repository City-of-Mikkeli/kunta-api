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
  private List<LocalizedValue> names = new ArrayList<LocalizedValue>();
  private List<LocalizedValue> descriptions = new ArrayList<LocalizedValue>();
  private List<LocalizedValue> shortDescriptions = new ArrayList<LocalizedValue>();
  private List<ServiceChannelWebPage> webPages = new ArrayList<ServiceChannelWebPage>();
  private List<ServiceChannelServiceHour> serviceHours = new ArrayList<ServiceChannelServiceHour>();
  private String serviceHoursAdditinalInformation = null;
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
  public ServiceChannelCommon names(List<LocalizedValue> names) {
    this.names = names;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<LocalizedValue> getNames() {
    return names;
  }
  public void setNames(List<LocalizedValue> names) {
    this.names = names;
  }

  /**
   **/
  public ServiceChannelCommon descriptions(List<LocalizedValue> descriptions) {
    this.descriptions = descriptions;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<LocalizedValue> getDescriptions() {
    return descriptions;
  }
  public void setDescriptions(List<LocalizedValue> descriptions) {
    this.descriptions = descriptions;
  }

  /**
   **/
  public ServiceChannelCommon shortDescriptions(List<LocalizedValue> shortDescriptions) {
    this.shortDescriptions = shortDescriptions;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<LocalizedValue> getShortDescriptions() {
    return shortDescriptions;
  }
  public void setShortDescriptions(List<LocalizedValue> shortDescriptions) {
    this.shortDescriptions = shortDescriptions;
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
  public ServiceChannelCommon serviceHoursAdditinalInformation(String serviceHoursAdditinalInformation) {
    this.serviceHoursAdditinalInformation = serviceHoursAdditinalInformation;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getServiceHoursAdditinalInformation() {
    return serviceHoursAdditinalInformation;
  }
  public void setServiceHoursAdditinalInformation(String serviceHoursAdditinalInformation) {
    this.serviceHoursAdditinalInformation = serviceHoursAdditinalInformation;
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
        Objects.equals(names, serviceChannelCommon.names) &&
        Objects.equals(descriptions, serviceChannelCommon.descriptions) &&
        Objects.equals(shortDescriptions, serviceChannelCommon.shortDescriptions) &&
        Objects.equals(webPages, serviceChannelCommon.webPages) &&
        Objects.equals(serviceHours, serviceChannelCommon.serviceHours) &&
        Objects.equals(serviceHoursAdditinalInformation, serviceChannelCommon.serviceHoursAdditinalInformation) &&
        Objects.equals(supportContacts, serviceChannelCommon.supportContacts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, names, descriptions, shortDescriptions, webPages, serviceHours, serviceHoursAdditinalInformation, supportContacts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceChannelCommon {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    names: ").append(toIndentedString(names)).append("\n");
    sb.append("    descriptions: ").append(toIndentedString(descriptions)).append("\n");
    sb.append("    shortDescriptions: ").append(toIndentedString(shortDescriptions)).append("\n");
    sb.append("    webPages: ").append(toIndentedString(webPages)).append("\n");
    sb.append("    serviceHours: ").append(toIndentedString(serviceHours)).append("\n");
    sb.append("    serviceHoursAdditinalInformation: ").append(toIndentedString(serviceHoursAdditinalInformation)).append("\n");
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
