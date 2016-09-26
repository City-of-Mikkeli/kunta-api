package fi.otavanopisto.kuntaapi.server.rest.model;




import io.swagger.annotations.*;
import java.util.Objects;


public class MenuItem   {
  
  private String id = null;
  private String slug = null;
  private String pageId = null;
  private String fileId = null;
  private String menuId = null;

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
  public MenuItem slug(String slug) {
    this.slug = slug;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getSlug() {
    return slug;
  }
  public void setSlug(String slug) {
    this.slug = slug;
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
  public MenuItem menuId(String menuId) {
    this.menuId = menuId;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getMenuId() {
    return menuId;
  }
  public void setMenuId(String menuId) {
    this.menuId = menuId;
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
        Objects.equals(slug, menuItem.slug) &&
        Objects.equals(pageId, menuItem.pageId) &&
        Objects.equals(fileId, menuItem.fileId) &&
        Objects.equals(menuId, menuItem.menuId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, slug, pageId, fileId, menuId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MenuItem {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    slug: ").append(toIndentedString(slug)).append("\n");
    sb.append("    pageId: ").append(toIndentedString(pageId)).append("\n");
    sb.append("    fileId: ").append(toIndentedString(fileId)).append("\n");
    sb.append("    menuId: ").append(toIndentedString(menuId)).append("\n");
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
