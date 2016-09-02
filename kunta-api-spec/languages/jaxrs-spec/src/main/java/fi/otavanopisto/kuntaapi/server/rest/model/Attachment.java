package fi.otavanopisto.kuntaapi.server.rest.model;




import io.swagger.annotations.*;
import java.util.Objects;


public class Attachment   {
  
  private String id = null;
  private String contentType = null;
  private Long size = null;

  /**
   **/
  public Attachment id(String id) {
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
  public Attachment contentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getContentType() {
    return contentType;
  }
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  /**
   **/
  public Attachment size(Long size) {
    this.size = size;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public Long getSize() {
    return size;
  }
  public void setSize(Long size) {
    this.size = size;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Attachment attachment = (Attachment) o;
    return Objects.equals(id, attachment.id) &&
        Objects.equals(contentType, attachment.contentType) &&
        Objects.equals(size, attachment.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, contentType, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Attachment {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    contentType: ").append(toIndentedString(contentType)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
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
