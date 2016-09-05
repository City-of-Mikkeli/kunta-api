package fi.otavanopisto.kuntaapi.server.rest.model;

import java.time.OffsetDateTime;



import io.swagger.annotations.*;
import java.util.Objects;


public class ServiceChannelServiceHour   {
  
  private String serviceHourType = null;
  private String exceptionHourType = null;
  private OffsetDateTime validFrom = null;
  private OffsetDateTime validTo = null;
  private Boolean monday = null;
  private Boolean tuesday = null;
  private Boolean wednesday = null;
  private Boolean thursday = null;
  private Boolean friday = null;
  private Boolean saturday = null;
  private Boolean sunday = null;
  private String opens = null;
  private String closes = null;

  /**
   **/
  public ServiceChannelServiceHour serviceHourType(String serviceHourType) {
    this.serviceHourType = serviceHourType;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getServiceHourType() {
    return serviceHourType;
  }
  public void setServiceHourType(String serviceHourType) {
    this.serviceHourType = serviceHourType;
  }

  /**
   **/
  public ServiceChannelServiceHour exceptionHourType(String exceptionHourType) {
    this.exceptionHourType = exceptionHourType;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getExceptionHourType() {
    return exceptionHourType;
  }
  public void setExceptionHourType(String exceptionHourType) {
    this.exceptionHourType = exceptionHourType;
  }

  /**
   **/
  public ServiceChannelServiceHour validFrom(OffsetDateTime validFrom) {
    this.validFrom = validFrom;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public OffsetDateTime getValidFrom() {
    return validFrom;
  }
  public void setValidFrom(OffsetDateTime validFrom) {
    this.validFrom = validFrom;
  }

  /**
   **/
  public ServiceChannelServiceHour validTo(OffsetDateTime validTo) {
    this.validTo = validTo;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public OffsetDateTime getValidTo() {
    return validTo;
  }
  public void setValidTo(OffsetDateTime validTo) {
    this.validTo = validTo;
  }

  /**
   **/
  public ServiceChannelServiceHour monday(Boolean monday) {
    this.monday = monday;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public Boolean getMonday() {
    return monday;
  }
  public void setMonday(Boolean monday) {
    this.monday = monday;
  }

  /**
   **/
  public ServiceChannelServiceHour tuesday(Boolean tuesday) {
    this.tuesday = tuesday;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public Boolean getTuesday() {
    return tuesday;
  }
  public void setTuesday(Boolean tuesday) {
    this.tuesday = tuesday;
  }

  /**
   **/
  public ServiceChannelServiceHour wednesday(Boolean wednesday) {
    this.wednesday = wednesday;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public Boolean getWednesday() {
    return wednesday;
  }
  public void setWednesday(Boolean wednesday) {
    this.wednesday = wednesday;
  }

  /**
   **/
  public ServiceChannelServiceHour thursday(Boolean thursday) {
    this.thursday = thursday;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public Boolean getThursday() {
    return thursday;
  }
  public void setThursday(Boolean thursday) {
    this.thursday = thursday;
  }

  /**
   **/
  public ServiceChannelServiceHour friday(Boolean friday) {
    this.friday = friday;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public Boolean getFriday() {
    return friday;
  }
  public void setFriday(Boolean friday) {
    this.friday = friday;
  }

  /**
   **/
  public ServiceChannelServiceHour saturday(Boolean saturday) {
    this.saturday = saturday;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public Boolean getSaturday() {
    return saturday;
  }
  public void setSaturday(Boolean saturday) {
    this.saturday = saturday;
  }

  /**
   **/
  public ServiceChannelServiceHour sunday(Boolean sunday) {
    this.sunday = sunday;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public Boolean getSunday() {
    return sunday;
  }
  public void setSunday(Boolean sunday) {
    this.sunday = sunday;
  }

  /**
   **/
  public ServiceChannelServiceHour opens(String opens) {
    this.opens = opens;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getOpens() {
    return opens;
  }
  public void setOpens(String opens) {
    this.opens = opens;
  }

  /**
   **/
  public ServiceChannelServiceHour closes(String closes) {
    this.closes = closes;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getCloses() {
    return closes;
  }
  public void setCloses(String closes) {
    this.closes = closes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceChannelServiceHour serviceChannelServiceHour = (ServiceChannelServiceHour) o;
    return Objects.equals(serviceHourType, serviceChannelServiceHour.serviceHourType) &&
        Objects.equals(exceptionHourType, serviceChannelServiceHour.exceptionHourType) &&
        Objects.equals(validFrom, serviceChannelServiceHour.validFrom) &&
        Objects.equals(validTo, serviceChannelServiceHour.validTo) &&
        Objects.equals(monday, serviceChannelServiceHour.monday) &&
        Objects.equals(tuesday, serviceChannelServiceHour.tuesday) &&
        Objects.equals(wednesday, serviceChannelServiceHour.wednesday) &&
        Objects.equals(thursday, serviceChannelServiceHour.thursday) &&
        Objects.equals(friday, serviceChannelServiceHour.friday) &&
        Objects.equals(saturday, serviceChannelServiceHour.saturday) &&
        Objects.equals(sunday, serviceChannelServiceHour.sunday) &&
        Objects.equals(opens, serviceChannelServiceHour.opens) &&
        Objects.equals(closes, serviceChannelServiceHour.closes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceHourType, exceptionHourType, validFrom, validTo, monday, tuesday, wednesday, thursday, friday, saturday, sunday, opens, closes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceChannelServiceHour {\n");
    
    sb.append("    serviceHourType: ").append(toIndentedString(serviceHourType)).append("\n");
    sb.append("    exceptionHourType: ").append(toIndentedString(exceptionHourType)).append("\n");
    sb.append("    validFrom: ").append(toIndentedString(validFrom)).append("\n");
    sb.append("    validTo: ").append(toIndentedString(validTo)).append("\n");
    sb.append("    monday: ").append(toIndentedString(monday)).append("\n");
    sb.append("    tuesday: ").append(toIndentedString(tuesday)).append("\n");
    sb.append("    wednesday: ").append(toIndentedString(wednesday)).append("\n");
    sb.append("    thursday: ").append(toIndentedString(thursday)).append("\n");
    sb.append("    friday: ").append(toIndentedString(friday)).append("\n");
    sb.append("    saturday: ").append(toIndentedString(saturday)).append("\n");
    sb.append("    sunday: ").append(toIndentedString(sunday)).append("\n");
    sb.append("    opens: ").append(toIndentedString(opens)).append("\n");
    sb.append("    closes: ").append(toIndentedString(closes)).append("\n");
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
