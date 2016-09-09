package fi.otavanopisto.kuntaapi.server.integrations;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class representing banner id
 * 
 * @author Antti Leppä
 */
public class BannerId extends Id {
  
  /**
   * Zero-argument constructor for banner id
   */
  public BannerId() {
    super();
  }

  /**
   * Constructor that parses a stringified id into source and id 
   * 
   * @param id stringified id
   */
  public BannerId(String id) {
    super(id);
  }
  
  /**
   * Constructor that accepts source and id
   * 
   * @param source source
   * @param id id
   */
  public BannerId(String source, String id) {
    super(source, id);
  }
  
  @Override
  public IdType getType() {
    return IdType.BANNER;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof BannerId) {
      BannerId another = (BannerId) obj;
      return StringUtils.equals(this.getSource(), another.getSource()) &&  StringUtils.equals(this.getId(), another.getId());
    }

    return false;
  }
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder(137, 149)
      .append(getSource())
      .append(getId())
      .hashCode();
  }
  
}
