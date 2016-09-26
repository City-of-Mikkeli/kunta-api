package fi.otavanopisto.kuntaapi.server.rest.model;

import fi.otavanopisto.kuntaapi.server.rest.model.LocalizedValue;
import java.util.ArrayList;
import java.util.List;



import io.swagger.annotations.*;
import java.util.Objects;


public class Page   {
  
  private String id = null;
  private String slug = null;
  private List<LocalizedValue> titles = new ArrayList<LocalizedValue>();
  private List<LocalizedValue> contents = new ArrayList<LocalizedValue>();
  private String parentId = null;

  /**
   **/
  public Page id(String id) {
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
  public Page slug(String slug) {
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
  public Page titles(List<LocalizedValue> titles) {
    this.titles = titles;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<LocalizedValue> getTitles() {
    return titles;
  }
  public void setTitles(List<LocalizedValue> titles) {
    this.titles = titles;
  }

  /**
   **/
  public Page contents(List<LocalizedValue> contents) {
    this.contents = contents;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public List<LocalizedValue> getContents() {
    return contents;
  }
  public void setContents(List<LocalizedValue> contents) {
    this.contents = contents;
  }

  /**
   **/
  public Page parentId(String parentId) {
    this.parentId = parentId;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getParentId() {
    return parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Page page = (Page) o;
    return Objects.equals(id, page.id) &&
        Objects.equals(slug, page.slug) &&
        Objects.equals(titles, page.titles) &&
        Objects.equals(contents, page.contents) &&
        Objects.equals(parentId, page.parentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, slug, titles, contents, parentId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Page {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    slug: ").append(toIndentedString(slug)).append("\n");
    sb.append("    titles: ").append(toIndentedString(titles)).append("\n");
    sb.append("    contents: ").append(toIndentedString(contents)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
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
