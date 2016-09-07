package fi.otavanopisto.kuntaapi.server.rest.model;

import java.time.OffsetDateTime;



import io.swagger.annotations.*;
import java.util.Objects;


public class NewsArticle   {
  
  private String id = null;
  private String title = null;
  private String _abstract = null;
  private String contents = null;
  private OffsetDateTime published = null;

  /**
   **/
  public NewsArticle id(String id) {
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
  public NewsArticle title(String title) {
    this.title = title;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   **/
  public NewsArticle _abstract(String _abstract) {
    this._abstract = _abstract;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getAbstract() {
    return _abstract;
  }
  public void setAbstract(String _abstract) {
    this._abstract = _abstract;
  }

  /**
   **/
  public NewsArticle contents(String contents) {
    this.contents = contents;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public String getContents() {
    return contents;
  }
  public void setContents(String contents) {
    this.contents = contents;
  }

  /**
   **/
  public NewsArticle published(OffsetDateTime published) {
    this.published = published;
    return this;
  }

  
  @ApiModelProperty(example = "null", value = "")
  public OffsetDateTime getPublished() {
    return published;
  }
  public void setPublished(OffsetDateTime published) {
    this.published = published;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewsArticle newsArticle = (NewsArticle) o;
    return Objects.equals(id, newsArticle.id) &&
        Objects.equals(title, newsArticle.title) &&
        Objects.equals(_abstract, newsArticle._abstract) &&
        Objects.equals(contents, newsArticle.contents) &&
        Objects.equals(published, newsArticle.published);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, _abstract, contents, published);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewsArticle {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    _abstract: ").append(toIndentedString(_abstract)).append("\n");
    sb.append("    contents: ").append(toIndentedString(contents)).append("\n");
    sb.append("    published: ").append(toIndentedString(published)).append("\n");
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
