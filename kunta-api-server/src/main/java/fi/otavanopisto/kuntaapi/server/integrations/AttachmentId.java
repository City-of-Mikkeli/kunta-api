package fi.otavanopisto.kuntaapi.server.integrations;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class representing attachment id
 * 
 * @author Antti Leppä
 */
public class AttachmentId extends Id {
  
  /**
   * Zero-argument constructor for attachment id
   */
  public AttachmentId() {
    super();
  }
  
  /**
   * Constructor that parses a stringified id into source and id 
   * 
   * @param id stringified id
   */
  public AttachmentId(String id) {
    super(id);
  }
  
  /**
   * Constructor that accepts source and id
   * 
   * @param source source
   * @param id id
   */
  public AttachmentId(String source, String id) {
    super(source, id);
  }
  
  @Override
  public IdType getType() {
    return IdType.ATTACHMENT;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof AttachmentId) {
      AttachmentId another = (AttachmentId) obj;
      return StringUtils.equals(this.getSource(), another.getSource()) &&  StringUtils.equals(this.getId(), another.getId());
    }

    return false;
  }
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder(125, 137)
      .append(getSource())
      .append(getId())
      .hashCode();
  }
}
