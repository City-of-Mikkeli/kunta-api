package fi.otavanopisto.kuntaapi.server.integrations;

/**
 * Id translator provider interface
 * 
 * @author Otavan Opisto
 */
public interface IdProvider {

  /**
   * Returns whether provider can translate from given source to given target
   * 
   * @param source id source type
   * @param target id target type
   * @return whether provider can translate from given source to given target
   */
  public boolean canTranslate(String source, String target);
  
  /**
   * Translate organization id
   * 
   * @param organizationId original id
   * @param target target type
   * @return translated organization id
   */
  public OrganizationId translate(OrganizationId organizationId, String target);
  
  /**
   * Translate service id
   * 
   * @param serviceId original id
   * @param target target type
   * @return translated service id
   */
  public ServiceId translate(ServiceId serviceId, String target);

  /**
   * Translate service channel id
   * 
   * @param serviceChannelId original id
   * @param target target type
   * @return translated id
   */
  public ServiceChannelId translate(ServiceChannelId serviceChannelId, String target);
  
  /**
   * Translate service class id
   * 
   * @param serviceClassId original id
   * @param target target type
   * @return translated id
   */
  public ServiceClassId translate(ServiceClassId serviceClassId, String target);
  
  /**
   * Translate event id
   * 
   * @param eventId original id
   * @param target target type
   * @return translated id
   */
  public EventId translate(EventId eventId, String target);
  
  /**
   * Translate news article id
   * 
   * @param newsArticleId original id
   * @param target target type
   * @return translated id
   */
  public NewsArticleId translate(NewsArticleId newsArticleId, String target);
  
  /**
   * Translate banner id
   * 
   * @param bannerId original id
   * @param target target type
   * @return translated id
   */
  public BannerId translate(BannerId bannerId, String target);
  
  /**
   * Translate tile id
   * 
   * @param tileId original id
   * @param target target type
   * @return translated id
   */
  public TileId translate(TileId tileId, String target);

  /**
   * Translate attachment id
   * 
   * @param attachmentId original id
   * @param target target type
   * @return translated id
   */
  public AttachmentId translate(AttachmentId attachmentId, String target);

}
