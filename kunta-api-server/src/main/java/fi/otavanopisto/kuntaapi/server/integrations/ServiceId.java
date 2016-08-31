package fi.otavanopisto.kuntaapi.server.integrations;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class representing service id
 * 
 * @author Antti Leppä
 */
public class ServiceId extends Id {

  /**
   * Zero-argument constructor for service id
   */
  public ServiceId() {
    super();
  }

  /**
   * Constructor that parses a stringified id into source and id 
   * 
   * @param id stringified id
   */
  public ServiceId(String id) {
    super(id);
  }
  
  /**
   * Constructor that accepts source and id
   * 
   * @param source source
   * @param id id
   */
  public ServiceId(String source, String id) {
    super(source, id);
  }
  
  @Override
  public IdType getType() {
    return IdType.SERVICE;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ServiceId) {
      ServiceId another = (ServiceId) obj;
      return StringUtils.equals(this.getSource(), another.getSource()) &&  StringUtils.equals(this.getId(), another.getId());
    }

    return false;
  }
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder(129, 141)
      .append(getSource())
      .append(getId())
      .hashCode();
  }
  
}
