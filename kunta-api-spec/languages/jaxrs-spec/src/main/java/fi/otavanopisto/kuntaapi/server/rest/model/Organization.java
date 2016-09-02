package fi.otavanopisto.kuntaapi.server.rest.model;




import io.swagger.annotations.*;
import java.util.Objects;


public class Organization   {
  
  private String id = null;
  private String businessName = null;
  private String businessCode = null;

  /**
   * Unique identifier a organization.
   **/
  public Organization id(String id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "Unique identifier a organization.")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Primary name of organization
   **/
  public Organization businessName(String businessName) {
    this.businessName = businessName;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "Primary name of organization")
  public String getBusinessName() {
    return businessName;
  }
  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  /**
   * Business code code of organization
   **/
  public Organization businessCode(String businessCode) {
    this.businessCode = businessCode;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "Business code code of organization")
  public String getBusinessCode() {
    return businessCode;
  }
  public void setBusinessCode(String businessCode) {
    this.businessCode = businessCode;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Organization organization = (Organization) o;
    return Objects.equals(id, organization.id) &&
        Objects.equals(businessName, organization.businessName) &&
        Objects.equals(businessCode, organization.businessCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, businessName, businessCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Organization {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    businessName: ").append(toIndentedString(businessName)).append("\n");
    sb.append("    businessCode: ").append(toIndentedString(businessCode)).append("\n");
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
