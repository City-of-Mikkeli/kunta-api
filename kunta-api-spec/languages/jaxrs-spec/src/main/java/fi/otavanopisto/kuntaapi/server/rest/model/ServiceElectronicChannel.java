package fi.otavanopisto.kuntaapi.server.rest.model;

import fi.otavanopisto.kuntaapi.server.rest.model.LocalizedValue;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceChannelAttachment;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceChannelServiceHour;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceChannelSupport;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceChannelWebPage;
import java.util.ArrayList;
import java.util.List;



import io.swagger.annotations.*;
import java.util.Objects;


public class ServiceElectronicChannel   {
  
  private List<ServiceChannelAttachment> attachments = new ArrayList<ServiceChannelAttachment>();
  private List<LocalizedValue> urls = new ArrayList<LocalizedValue>();
  private Boolean requiresAuthentication = null;
  private Boolean requiresSignature = null;
  private Integer signatureQuantity = null;
  private List<ServiceChannelSupport> supportContacts = new ArrayList<ServiceChannelSupport>();
  private String serviceHoursAdditinalInformation = null;
  private List<ServiceChannelServiceHour> serviceHours = new ArrayList<ServiceChannelServiceHour>();
  private List<ServiceChannelWebPage> webPages = new ArrayList<ServiceChannelWebPage>();
  private List<LocalizedValue> shortDescriptions = new ArrayList<LocalizedValue>();
  private List<LocalizedValue> descriptions = new ArrayList<LocalizedValue>();
  private List<LocalizedValue> names = new ArrayList<LocalizedValue>();
  private String id = null;

  /**
   **/
  public ServiceElectronicChannel attachments(List<ServiceChannelAttachment> attachments) {
    this.attachments = attachments;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<ServiceChannelAttachment> getAttachments() {
    return attachments;
  }
  public void setAttachments(List<ServiceChannelAttachment> attachments) {
    this.attachments = attachments;
  }

  /**
   **/
  public ServiceElectronicChannel urls(List<LocalizedValue> urls) {
    this.urls = urls;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<LocalizedValue> getUrls() {
    return urls;
  }
  public void setUrls(List<LocalizedValue> urls) {
    this.urls = urls;
  }

  /**
   **/
  public ServiceElectronicChannel requiresAuthentication(Boolean requiresAuthentication) {
    this.requiresAuthentication = requiresAuthentication;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public Boolean getRequiresAuthentication() {
    return requiresAuthentication;
  }
  public void setRequiresAuthentication(Boolean requiresAuthentication) {
    this.requiresAuthentication = requiresAuthentication;
  }

  /**
   **/
  public ServiceElectronicChannel requiresSignature(Boolean requiresSignature) {
    this.requiresSignature = requiresSignature;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public Boolean getRequiresSignature() {
    return requiresSignature;
  }
  public void setRequiresSignature(Boolean requiresSignature) {
    this.requiresSignature = requiresSignature;
  }

  /**
   **/
  public ServiceElectronicChannel signatureQuantity(Integer signatureQuantity) {
    this.signatureQuantity = signatureQuantity;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public Integer getSignatureQuantity() {
    return signatureQuantity;
  }
  public void setSignatureQuantity(Integer signatureQuantity) {
    this.signatureQuantity = signatureQuantity;
  }

  /**
   **/
  public ServiceElectronicChannel supportContacts(List<ServiceChannelSupport> supportContacts) {
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

  /**
   **/
  public ServiceElectronicChannel serviceHoursAdditinalInformation(String serviceHoursAdditinalInformation) {
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
  public ServiceElectronicChannel serviceHours(List<ServiceChannelServiceHour> serviceHours) {
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
  public ServiceElectronicChannel webPages(List<ServiceChannelWebPage> webPages) {
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
  public ServiceElectronicChannel shortDescriptions(List<LocalizedValue> shortDescriptions) {
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
  public ServiceElectronicChannel descriptions(List<LocalizedValue> descriptions) {
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
  public ServiceElectronicChannel names(List<LocalizedValue> names) {
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
  public ServiceElectronicChannel id(String id) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceElectronicChannel serviceElectronicChannel = (ServiceElectronicChannel) o;
    return Objects.equals(attachments, serviceElectronicChannel.attachments) &&
        Objects.equals(urls, serviceElectronicChannel.urls) &&
        Objects.equals(requiresAuthentication, serviceElectronicChannel.requiresAuthentication) &&
        Objects.equals(requiresSignature, serviceElectronicChannel.requiresSignature) &&
        Objects.equals(signatureQuantity, serviceElectronicChannel.signatureQuantity) &&
        Objects.equals(supportContacts, serviceElectronicChannel.supportContacts) &&
        Objects.equals(serviceHoursAdditinalInformation, serviceElectronicChannel.serviceHoursAdditinalInformation) &&
        Objects.equals(serviceHours, serviceElectronicChannel.serviceHours) &&
        Objects.equals(webPages, serviceElectronicChannel.webPages) &&
        Objects.equals(shortDescriptions, serviceElectronicChannel.shortDescriptions) &&
        Objects.equals(descriptions, serviceElectronicChannel.descriptions) &&
        Objects.equals(names, serviceElectronicChannel.names) &&
        Objects.equals(id, serviceElectronicChannel.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attachments, urls, requiresAuthentication, requiresSignature, signatureQuantity, supportContacts, serviceHoursAdditinalInformation, serviceHours, webPages, shortDescriptions, descriptions, names, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceElectronicChannel {\n");
    
    sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
    sb.append("    urls: ").append(toIndentedString(urls)).append("\n");
    sb.append("    requiresAuthentication: ").append(toIndentedString(requiresAuthentication)).append("\n");
    sb.append("    requiresSignature: ").append(toIndentedString(requiresSignature)).append("\n");
    sb.append("    signatureQuantity: ").append(toIndentedString(signatureQuantity)).append("\n");
    sb.append("    supportContacts: ").append(toIndentedString(supportContacts)).append("\n");
    sb.append("    serviceHoursAdditinalInformation: ").append(toIndentedString(serviceHoursAdditinalInformation)).append("\n");
    sb.append("    serviceHours: ").append(toIndentedString(serviceHours)).append("\n");
    sb.append("    webPages: ").append(toIndentedString(webPages)).append("\n");
    sb.append("    shortDescriptions: ").append(toIndentedString(shortDescriptions)).append("\n");
    sb.append("    descriptions: ").append(toIndentedString(descriptions)).append("\n");
    sb.append("    names: ").append(toIndentedString(names)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
