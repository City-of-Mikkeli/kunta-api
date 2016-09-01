package fi.otavanopisto.kuntaapi.server.rest.model;

import fi.otavanopisto.kuntaapi.server.rest.model.LocalizedValue;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceDescription;
import java.util.ArrayList;
import java.util.List;



import io.swagger.annotations.*;
import java.util.Objects;


public class Service   {
  
  private String id = null;
  private List<ServiceDescription> descriptions = new ArrayList<ServiceDescription>();
  private List<LocalizedValue> name = new ArrayList<LocalizedValue>();
  private List<String> sourceIds = new ArrayList<String>();
  private List<String> classIds = new ArrayList<String>();
  private List<String> electronicChannelIds = new ArrayList<String>();
  private List<String> phoneChannelIds = new ArrayList<String>();
  private List<String> printableFormChannelIds = new ArrayList<String>();
  private List<String> serviceLocationChannelIds = new ArrayList<String>();
  private List<String> webpageChannelIds = new ArrayList<String>();

  /**
   * Unique identifier representing a specific service.
   **/
  public Service id(String id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "Unique identifier representing a specific service.")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   **/
  public Service descriptions(List<ServiceDescription> descriptions) {
    this.descriptions = descriptions;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<ServiceDescription> getDescriptions() {
    return descriptions;
  }
  public void setDescriptions(List<ServiceDescription> descriptions) {
    this.descriptions = descriptions;
  }

  /**
   **/
  public Service name(List<LocalizedValue> name) {
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
   * Name of the service.
   **/
  public Service sourceIds(List<String> sourceIds) {
    this.sourceIds = sourceIds;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "Name of the service.")
  public List<String> getSourceIds() {
    return sourceIds;
  }
  public void setSourceIds(List<String> sourceIds) {
    this.sourceIds = sourceIds;
  }

  /**
   * List of service classes
   **/
  public Service classIds(List<String> classIds) {
    this.classIds = classIds;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "List of service classes")
  public List<String> getClassIds() {
    return classIds;
  }
  public void setClassIds(List<String> classIds) {
    this.classIds = classIds;
  }

  /**
   **/
  public Service electronicChannelIds(List<String> electronicChannelIds) {
    this.electronicChannelIds = electronicChannelIds;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<String> getElectronicChannelIds() {
    return electronicChannelIds;
  }
  public void setElectronicChannelIds(List<String> electronicChannelIds) {
    this.electronicChannelIds = electronicChannelIds;
  }

  /**
   **/
  public Service phoneChannelIds(List<String> phoneChannelIds) {
    this.phoneChannelIds = phoneChannelIds;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<String> getPhoneChannelIds() {
    return phoneChannelIds;
  }
  public void setPhoneChannelIds(List<String> phoneChannelIds) {
    this.phoneChannelIds = phoneChannelIds;
  }

  /**
   **/
  public Service printableFormChannelIds(List<String> printableFormChannelIds) {
    this.printableFormChannelIds = printableFormChannelIds;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<String> getPrintableFormChannelIds() {
    return printableFormChannelIds;
  }
  public void setPrintableFormChannelIds(List<String> printableFormChannelIds) {
    this.printableFormChannelIds = printableFormChannelIds;
  }

  /**
   **/
  public Service serviceLocationChannelIds(List<String> serviceLocationChannelIds) {
    this.serviceLocationChannelIds = serviceLocationChannelIds;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<String> getServiceLocationChannelIds() {
    return serviceLocationChannelIds;
  }
  public void setServiceLocationChannelIds(List<String> serviceLocationChannelIds) {
    this.serviceLocationChannelIds = serviceLocationChannelIds;
  }

  /**
   **/
  public Service webpageChannelIds(List<String> webpageChannelIds) {
    this.webpageChannelIds = webpageChannelIds;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<String> getWebpageChannelIds() {
    return webpageChannelIds;
  }
  public void setWebpageChannelIds(List<String> webpageChannelIds) {
    this.webpageChannelIds = webpageChannelIds;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Service service = (Service) o;
    return Objects.equals(id, service.id) &&
        Objects.equals(descriptions, service.descriptions) &&
        Objects.equals(name, service.name) &&
        Objects.equals(sourceIds, service.sourceIds) &&
        Objects.equals(classIds, service.classIds) &&
        Objects.equals(electronicChannelIds, service.electronicChannelIds) &&
        Objects.equals(phoneChannelIds, service.phoneChannelIds) &&
        Objects.equals(printableFormChannelIds, service.printableFormChannelIds) &&
        Objects.equals(serviceLocationChannelIds, service.serviceLocationChannelIds) &&
        Objects.equals(webpageChannelIds, service.webpageChannelIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, descriptions, name, sourceIds, classIds, electronicChannelIds, phoneChannelIds, printableFormChannelIds, serviceLocationChannelIds, webpageChannelIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Service {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    descriptions: ").append(toIndentedString(descriptions)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    sourceIds: ").append(toIndentedString(sourceIds)).append("\n");
    sb.append("    classIds: ").append(toIndentedString(classIds)).append("\n");
    sb.append("    electronicChannelIds: ").append(toIndentedString(electronicChannelIds)).append("\n");
    sb.append("    phoneChannelIds: ").append(toIndentedString(phoneChannelIds)).append("\n");
    sb.append("    printableFormChannelIds: ").append(toIndentedString(printableFormChannelIds)).append("\n");
    sb.append("    serviceLocationChannelIds: ").append(toIndentedString(serviceLocationChannelIds)).append("\n");
    sb.append("    webpageChannelIds: ").append(toIndentedString(webpageChannelIds)).append("\n");
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
