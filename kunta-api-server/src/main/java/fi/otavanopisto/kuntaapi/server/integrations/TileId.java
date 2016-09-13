package fi.otavanopisto.kuntaapi.server.integrations;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class representing tile id
 * 
 * @author Antti Leppä
 */
public class TileId extends Id {
  
  /**
   * Zero-argument constructor for tile id
   */
  public TileId() {
    super();
  }

  /**
   * Constructor that parses a stringified id into source and id 
   * 
   * @param id stringified id
   */
  public TileId(String id) {
    super(id);
  }
  
  /**
   * Constructor that accepts source and id
   * 
   * @param source source
   * @param id id
   */
  public TileId(String source, String id) {
    super(source, id);
  }
  
  @Override
  public IdType getType() {
    return IdType.TILE;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof TileId) {
      TileId another = (TileId) obj;
      return StringUtils.equals(this.getSource(), another.getSource()) &&  StringUtils.equals(this.getId(), another.getId());
    }

    return false;
  }
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder(139, 151)
      .append(getSource())
      .append(getId())
      .hashCode();
  }
  
}
