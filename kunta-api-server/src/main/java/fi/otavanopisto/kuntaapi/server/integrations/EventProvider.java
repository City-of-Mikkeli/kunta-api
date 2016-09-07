package fi.otavanopisto.kuntaapi.server.integrations;

import java.time.OffsetDateTime;
import java.util.List;

import fi.otavanopisto.kuntaapi.server.rest.model.Attachment;
import fi.otavanopisto.kuntaapi.server.rest.model.Event;

/**
 * Interafce that describes a single event provider
 * 
 * @author Antti Leppä
 */
public interface EventProvider {

  /**
   * List events in an organization
   * 
   * @param organizationId organization id
   * @param startBefore return only events starting before the date
   * @param startAfter return only events starting after the date
   * @param endBefore return only events ending before the date
   * @param endAfter return only events ending after the date
   * @param order order
   * @param orderDirection ordering direction
   * @param firstResult first index of results
   * @param maxResults maximum number of results
   * @return events organization events
   */
  public List<Event> listOrganizationEvents(OrganizationId organizationId, OffsetDateTime startBefore, OffsetDateTime startAfter, 
      OffsetDateTime endBefore, OffsetDateTime endAfter, EventOrder order, EventOrderDirection orderDirection, 
      Integer firstResult, Integer maxResults);
  
  /**
   * Finds a single organization event
   * 
   * @param organizationId organization id
   * @param eventId event id
   * @return
   */
  public Event findOrganizationEvent(OrganizationId organizationId, EventId eventId);

  /**
   * Lists images attached to the event
   * 
   * @param organizationId organization id
   * @param eventId event id
   * @return list of images attached to the event
   */
  public List<Attachment> listEventImages(OrganizationId organizationId, EventId eventId);
  
  /**
   * Finds an event image
   * 
   * @param organizationId organization id
   * @param eventId event id
   * @param attachmentId image id
   * @return an event image or null if not found
   */
  public Attachment findEventImage(OrganizationId organizationId, EventId eventId, AttachmentId attachmentId);
  
  /**
   * Returns data of event image
   * 
   * @param organizationId organization id
   * @param eventId event id
   * @param attachmentId image id
   * @param size max size of image. Specify null for untouched
   * @return event image data
   */
  public AttachmentData getEventImageData(OrganizationId organizationId, EventId eventId, AttachmentId attachmentId, Integer size);
  
  /**
   * Event order direction
   * 
   * @author Antti Leppä
   */
  public enum EventOrderDirection {
    
    ASCENDING,
    
    DESCENDING
  }
  
  /**
   * Event order
   * 
   * @author Antti Leppä
   */
  public enum EventOrder {
    
    START_DATE,
    
    END_DATE
  }
  
}
