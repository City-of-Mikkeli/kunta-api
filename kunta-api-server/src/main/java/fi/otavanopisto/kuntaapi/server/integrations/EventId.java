package fi.otavanopisto.kuntaapi.server.integrations;

/**
 * Class represeting event id
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
  
}
