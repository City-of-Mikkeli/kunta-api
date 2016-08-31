package fi.otavanopisto.kuntaapi.server.integrations;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class representing organization id
 * 
 * @author Antti Leppä
 */
public class OrganizationId extends Id {
  
  /**
   * Zero-argument constructor for organization id
   */
  public OrganizationId() {
    super();
  }
  
  /**
   * Constructor that parses a stringified id into source and id 
   * 
   * @param id stringified id
   */
  public OrganizationId(String id) {
    super(id);
  }
  
  /**
   * Constructor that accepts source and id
   * 
   * @param source source
   * @param id id
   */
  public OrganizationId(String source, String id) {
    super(source, id);
  }
  
  @Override
  public IdType getType() {
    return IdType.ORGANIZATION;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof OrganizationId) {
      OrganizationId another = (OrganizationId) obj;
      return StringUtils.equals(this.getSource(), another.getSource()) &&  StringUtils.equals(this.getId(), another.getId());
    }

    return false;
  }
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder(127, 139)
      .append(getSource())
      .append(getId())
      .hashCode();
  }
  
}
