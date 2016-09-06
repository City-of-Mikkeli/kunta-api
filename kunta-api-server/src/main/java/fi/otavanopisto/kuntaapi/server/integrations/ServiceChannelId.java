package fi.otavanopisto.kuntaapi.server.integrations;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class representing service channel id
 * 
 * @author Antti Leppä
 */
public class ServiceChannelId extends Id {

  /**
   * Zero-argument constructor for service channel id
   */
  public ServiceChannelId() {
    super();
  }

  /**
   * Constructor that parses a stringified id into source and id 
   * 
   * @param id stringified id
   */
  public ServiceChannelId(String id) {
    super(id);
  }
  
  /**
   * Constructor that accepts source and id
   * 
   * @param source source
   * @param id id
   */
  public ServiceChannelId(String source, String id) {
    super(source, id);
  }
  
  @Override
  public IdType getType() {
    return IdType.SERVICE_CHANNEL;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ServiceChannelId) {
      ServiceChannelId another = (ServiceChannelId) obj;
      return StringUtils.equals(this.getSource(), another.getSource()) &&  StringUtils.equals(this.getId(), another.getId());
    }

    return false;
  }
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder(131, 143)
      .append(getSource())
      .append(getId())
      .hashCode();
  }
  
}
