package fi.otavanopisto.kuntaapi.server.rest.model;

import fi.otavanopisto.kuntaapi.server.rest.model.LocalizedValue;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceChannelAttachment;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceHour;
import fi.otavanopisto.kuntaapi.server.rest.model.Support;
import fi.otavanopisto.kuntaapi.server.rest.model.WebPage;
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
  private List<Support> supportContacts = new ArrayList<Support>();
  private List<ServiceHour> serviceHours = new ArrayList<ServiceHour>();
  private List<WebPage> webPages = new ArrayList<WebPage>();
  private List<LocalizedValue> name = new ArrayList<LocalizedValue>();
  private List<LocalizedValue> description = new ArrayList<LocalizedValue>();
  private String organizationId = null;
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
  public ServiceElectronicChannel supportContacts(List<Support> supportContacts) {
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

  /**
   **/
  public ServiceElectronicChannel serviceHours(List<ServiceHour> serviceHours) {
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
  public ServiceElectronicChannel webPages(List<WebPage> webPages) {
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
  public ServiceElectronicChannel name(List<LocalizedValue> name) {
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
  public ServiceElectronicChannel description(List<LocalizedValue> description) {
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
  public ServiceElectronicChannel organizationId(String organizationId) {
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
        Objects.equals(serviceHours, serviceElectronicChannel.serviceHours) &&
        Objects.equals(webPages, serviceElectronicChannel.webPages) &&
        Objects.equals(name, serviceElectronicChannel.name) &&
        Objects.equals(description, serviceElectronicChannel.description) &&
        Objects.equals(organizationId, serviceElectronicChannel.organizationId) &&
        Objects.equals(id, serviceElectronicChannel.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attachments, urls, requiresAuthentication, requiresSignature, signatureQuantity, supportContacts, serviceHours, webPages, name, description, organizationId, id);
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
    sb.append("    serviceHours: ").append(toIndentedString(serviceHours)).append("\n");
    sb.append("    webPages: ").append(toIndentedString(webPages)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    organizationId: ").append(toIndentedString(organizationId)).append("\n");
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
