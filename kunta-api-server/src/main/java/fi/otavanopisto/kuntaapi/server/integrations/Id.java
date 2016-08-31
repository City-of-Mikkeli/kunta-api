package fi.otavanopisto.kuntaapi.server.integrations;

import org.apache.commons.lang3.StringUtils;

/**
 * Abstract class representing an id in the system
 * 
 * @author Antti Leppä
 */
public abstract class Id {

  private String source;
  private String id;

  protected Id() {
  }
  
  /**
   * Constructor that parses stringified id
   * 
   * @param id stringified id
   */
  public Id(String id) {
    if (StringUtils.isNotBlank(id)) {
      String[] parts = StringUtils.split(id, ':');
      if (parts.length != 2) {
        this.source = parts[0];
        this.id = parts[1];
      }
    }
  }

  /**
   * Constructor that accepts source and id parameters
   * 
   * @param source source
   * @param id id
   */
  public Id(String source, String id) {
    super();
    this.source = source;
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getSource() {
    return source;
  }

  public abstract IdType getType();

  /**
   * Stringifies id
   */
  @Override
  public String toString() {
    return String.format("%s:%s", source, id);
  }

}
