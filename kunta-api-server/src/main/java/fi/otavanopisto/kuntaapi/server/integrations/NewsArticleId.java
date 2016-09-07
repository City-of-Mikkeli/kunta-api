package fi.otavanopisto.kuntaapi.server.integrations;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class representing event id
 * 
 * @author Antti Leppä
 */
public class NewsArticleId extends Id {
  
  /**
   * Zero-argument constructor for article id
   */
  public NewsArticleId() {
    super();
  }

  /**
   * Constructor that parses a stringified id into source and id 
   * 
   * @param id stringified id
   */
  public NewsArticleId(String id) {
    super(id);
  }
  
  /**
   * Constructor that accepts source and id
   * 
   * @param source source
   * @param id id
   */
  public NewsArticleId(String source, String id) {
    super(source, id);
  }
  
  @Override
  public IdType getType() {
    return IdType.NEWS_ARTICLE;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof NewsArticleId) {
      NewsArticleId another = (NewsArticleId) obj;
      return StringUtils.equals(this.getSource(), another.getSource()) &&  StringUtils.equals(this.getId(), another.getId());
    }

    return false;
  }
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder(135, 147)
      .append(getSource())
      .append(getId())
      .hashCode();
  }
  
}
