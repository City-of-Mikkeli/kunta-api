package fi.otavanopisto.kuntaapi.server.rest.model;

import java.time.OffsetDateTime;



import io.swagger.annotations.*;
import java.util.Objects;


public class ServiceHour   {
  
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
  public ServiceHour serviceHourType(String serviceHourType) {
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
  public ServiceHour exceptionHourType(String exceptionHourType) {
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
  public ServiceHour validFrom(OffsetDateTime validFrom) {
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
  public ServiceHour validTo(OffsetDateTime validTo) {
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
  public ServiceHour monday(Boolean monday) {
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
  public ServiceHour tuesday(Boolean tuesday) {
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
  public ServiceHour wednesday(Boolean wednesday) {
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
  public ServiceHour thursday(Boolean thursday) {
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
  public ServiceHour friday(Boolean friday) {
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
  public ServiceHour saturday(Boolean saturday) {
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
  public ServiceHour sunday(Boolean sunday) {
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
  public ServiceHour opens(String opens) {
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
  public ServiceHour closes(String closes) {
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
    ServiceHour serviceHour = (ServiceHour) o;
    return Objects.equals(serviceHourType, serviceHour.serviceHourType) &&
        Objects.equals(exceptionHourType, serviceHour.exceptionHourType) &&
        Objects.equals(validFrom, serviceHour.validFrom) &&
        Objects.equals(validTo, serviceHour.validTo) &&
        Objects.equals(monday, serviceHour.monday) &&
        Objects.equals(tuesday, serviceHour.tuesday) &&
        Objects.equals(wednesday, serviceHour.wednesday) &&
        Objects.equals(thursday, serviceHour.thursday) &&
        Objects.equals(friday, serviceHour.friday) &&
        Objects.equals(saturday, serviceHour.saturday) &&
        Objects.equals(sunday, serviceHour.sunday) &&
        Objects.equals(opens, serviceHour.opens) &&
        Objects.equals(closes, serviceHour.closes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceHourType, exceptionHourType, validFrom, validTo, monday, tuesday, wednesday, thursday, friday, saturday, sunday, opens, closes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceHour {\n");
    
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
