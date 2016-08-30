package fi.otavanopisto.kuntaapi.server.integrations;

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
   * List all events in an organization
   * 
   * @param organizationId organization id
   * @return events organization events
   */
  public List<Event> listOrganizationEvents(OrganizationId organizationId);
  
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
   * @return event image data
   */
  public AttachmentData getEventImageData(OrganizationId organizationId, EventId eventId, AttachmentId attachmentId);
  
}
