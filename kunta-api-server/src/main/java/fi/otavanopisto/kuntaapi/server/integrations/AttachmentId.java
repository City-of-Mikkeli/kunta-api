package fi.otavanopisto.kuntaapi.server.integrations;

/**
 * Class represeting attachment id
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
  
}
