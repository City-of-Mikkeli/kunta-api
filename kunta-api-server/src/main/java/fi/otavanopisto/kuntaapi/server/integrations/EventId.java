package fi.otavanopisto.kuntaapi.server.integrations;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class representing event id
 * 
 * @author Antti Leppä
 */
public class EventId extends Id {
  
  /**
   * Zero-argument constructor for event id
   */
  public EventId() {
    super();
  }

  /**
   * Constructor that parses a stringified id into source and id 
   * 
   * @param id stringified id
   */
  public EventId(String id) {
    super(id);
  }
  
  /**
   * Constructor that accepts source and id
   * 
   * @param source source
   * @param id id
   */
  public EventId(String source, String id) {
    super(source, id);
  }
  
  @Override
  public IdType getType() {
    return IdType.EVENT;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof EventId) {
      EventId another = (EventId) obj;
      return StringUtils.equals(this.getSource(), another.getSource()) &&  StringUtils.equals(this.getId(), another.getId());
    }

    return false;
  }
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder(123, 135)
      .append(getSource())
      .append(getId())
      .hashCode();
  }
  
}
