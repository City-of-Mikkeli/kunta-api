package fi.otavanopisto.kuntaapi.server.rest.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;



import io.swagger.annotations.*;
import java.util.Objects;


public class ServiceChannelServiceHour   {
  
  private String type = null;
  private String displayable = null;
  private OffsetDateTime validFrom = null;
  private OffsetDateTime validTo = null;
  private String opens = null;
  private String closes = null;
  private List<Integer> days = new ArrayList<Integer>();
  private String status = null;

  /**
   **/
  public ServiceChannelServiceHour type(String type) {
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
  public ServiceChannelServiceHour displayable(String displayable) {
    this.displayable = displayable;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getDisplayable() {
    return displayable;
  }
  public void setDisplayable(String displayable) {
    this.displayable = displayable;
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

  /**
   **/
  public ServiceChannelServiceHour days(List<Integer> days) {
    this.days = days;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<Integer> getDays() {
    return days;
  }
  public void setDays(List<Integer> days) {
    this.days = days;
  }

  /**
   **/
  public ServiceChannelServiceHour status(String status) {
    this.status = status;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
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
    return Objects.equals(type, serviceChannelServiceHour.type) &&
        Objects.equals(displayable, serviceChannelServiceHour.displayable) &&
        Objects.equals(validFrom, serviceChannelServiceHour.validFrom) &&
        Objects.equals(validTo, serviceChannelServiceHour.validTo) &&
        Objects.equals(opens, serviceChannelServiceHour.opens) &&
        Objects.equals(closes, serviceChannelServiceHour.closes) &&
        Objects.equals(days, serviceChannelServiceHour.days) &&
        Objects.equals(status, serviceChannelServiceHour.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, displayable, validFrom, validTo, opens, closes, days, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceChannelServiceHour {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    displayable: ").append(toIndentedString(displayable)).append("\n");
    sb.append("    validFrom: ").append(toIndentedString(validFrom)).append("\n");
    sb.append("    validTo: ").append(toIndentedString(validTo)).append("\n");
    sb.append("    opens: ").append(toIndentedString(opens)).append("\n");
    sb.append("    closes: ").append(toIndentedString(closes)).append("\n");
    sb.append("    days: ").append(toIndentedString(days)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
