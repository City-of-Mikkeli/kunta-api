package fi.otavanopisto.kuntaapi.server.rest.model;




import io.swagger.annotations.*;
import java.util.Objects;


public class MenuItem   {
  
  private String id = null;
  private String parentItemId = null;
  private String type = null;
  private String pageId = null;
  private String fileId = null;
  private String externalUrl = null;

  /**
   **/
  public MenuItem id(String id) {
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
  public MenuItem parentItemId(String parentItemId) {
    this.parentItemId = parentItemId;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getParentItemId() {
    return parentItemId;
  }
  public void setParentItemId(String parentItemId) {
    this.parentItemId = parentItemId;
  }

  /**
   **/
  public MenuItem type(String type) {
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
  public MenuItem pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getPageId() {
    return pageId;
  }
  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  /**
   **/
  public MenuItem fileId(String fileId) {
    this.fileId = fileId;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getFileId() {
    return fileId;
  }
  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  /**
   **/
  public MenuItem externalUrl(String externalUrl) {
    this.externalUrl = externalUrl;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getExternalUrl() {
    return externalUrl;
  }
  public void setExternalUrl(String externalUrl) {
    this.externalUrl = externalUrl;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MenuItem menuItem = (MenuItem) o;
    return Objects.equals(id, menuItem.id) &&
        Objects.equals(parentItemId, menuItem.parentItemId) &&
        Objects.equals(type, menuItem.type) &&
        Objects.equals(pageId, menuItem.pageId) &&
        Objects.equals(fileId, menuItem.fileId) &&
        Objects.equals(externalUrl, menuItem.externalUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, parentItemId, type, pageId, fileId, externalUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MenuItem {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    parentItemId: ").append(toIndentedString(parentItemId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    pageId: ").append(toIndentedString(pageId)).append("\n");
    sb.append("    fileId: ").append(toIndentedString(fileId)).append("\n");
    sb.append("    externalUrl: ").append(toIndentedString(externalUrl)).append("\n");
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
