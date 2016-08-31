package fi.otavanopisto.kuntaapi.server.integrations;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class representing service class id
 * 
 * @author Antti Leppä
 */
public class ServiceClassId extends Id {

  /**
   * Zero-argument constructor for service class id
   */
  public ServiceClassId() {
    super();
  }
  
  /**
   * Constructor that parses a stringified id into source and id 
   * 
   * @param id stringified id
   */
  public ServiceClassId(String id) {
    super(id);
  }
  
  /**
   * Constructor that accepts source and id
   * 
   * @param source source
   * @param id id
   */
  public ServiceClassId(String source, String id) {
    super(source, id);
  }
  
  @Override
  public IdType getType() {
    return IdType.SERVICE_CLASS;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ServiceClassId) {
      ServiceClassId another = (ServiceClassId) obj;
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
