package fi.otavanopisto.kuntaapi.server.rest.model;

import java.time.OffsetDateTime;



import io.swagger.annotations.*;
import java.util.Objects;


public class Event   {
  
  private String id = null;
  private String originalUrl = null;
  private String name = null;
  private String description = null;
  private OffsetDateTime start = null;
  private OffsetDateTime end = null;
  private String city = null;
  private String place = null;
  private String address = null;
  private String zip = null;

  /**
   **/
  public Event id(String id) {
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
  public Event originalUrl(String originalUrl) {
    this.originalUrl = originalUrl;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getOriginalUrl() {
    return originalUrl;
  }
  public void setOriginalUrl(String originalUrl) {
    this.originalUrl = originalUrl;
  }

  /**
   **/
  public Event name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  public Event description(String description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   **/
  public Event start(OffsetDateTime start) {
    this.start = start;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public OffsetDateTime getStart() {
    return start;
  }
  public void setStart(OffsetDateTime start) {
    this.start = start;
  }

  /**
   **/
  public Event end(OffsetDateTime end) {
    this.end = end;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public OffsetDateTime getEnd() {
    return end;
  }
  public void setEnd(OffsetDateTime end) {
    this.end = end;
  }

  /**
   **/
  public Event city(String city) {
    this.city = city;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }

  /**
   **/
  public Event place(String place) {
    this.place = place;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getPlace() {
    return place;
  }
  public void setPlace(String place) {
    this.place = place;
  }

  /**
   **/
  public Event address(String address) {
    this.address = address;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   **/
  public Event zip(String zip) {
    this.zip = zip;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getZip() {
    return zip;
  }
  public void setZip(String zip) {
    this.zip = zip;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Event event = (Event) o;
    return Objects.equals(id, event.id) &&
        Objects.equals(originalUrl, event.originalUrl) &&
        Objects.equals(name, event.name) &&
        Objects.equals(description, event.description) &&
        Objects.equals(start, event.start) &&
        Objects.equals(end, event.end) &&
        Objects.equals(city, event.city) &&
        Objects.equals(place, event.place) &&
        Objects.equals(address, event.address) &&
        Objects.equals(zip, event.zip);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, originalUrl, name, description, start, end, city, place, address, zip);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Event {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    originalUrl: ").append(toIndentedString(originalUrl)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    start: ").append(toIndentedString(start)).append("\n");
    sb.append("    end: ").append(toIndentedString(end)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    place: ").append(toIndentedString(place)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    zip: ").append(toIndentedString(zip)).append("\n");
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
