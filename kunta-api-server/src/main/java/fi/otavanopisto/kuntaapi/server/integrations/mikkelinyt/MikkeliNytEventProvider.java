package fi.otavanopisto.kuntaapi.server.integrations.mikkelinyt;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;

import fi.otavanopisto.kuntaapi.server.controllers.IdentifierController;
import fi.otavanopisto.kuntaapi.server.images.ImageReader;
import fi.otavanopisto.kuntaapi.server.images.ImageScaler;
import fi.otavanopisto.kuntaapi.server.images.ImageWriter;
import fi.otavanopisto.kuntaapi.server.integrations.AttachmentData;
import fi.otavanopisto.kuntaapi.server.integrations.AttachmentId;
import fi.otavanopisto.kuntaapi.server.integrations.BinaryHttpClient;
import fi.otavanopisto.kuntaapi.server.integrations.BinaryHttpClient.BinaryResponse;
import fi.otavanopisto.kuntaapi.server.integrations.EventId;
import fi.otavanopisto.kuntaapi.server.integrations.EventProvider;
import fi.otavanopisto.kuntaapi.server.integrations.GenericHttpCache;
import fi.otavanopisto.kuntaapi.server.integrations.GenericHttpClient;
import fi.otavanopisto.kuntaapi.server.integrations.GenericHttpClient.Response;
import fi.otavanopisto.kuntaapi.server.integrations.IdController;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;
import fi.otavanopisto.kuntaapi.server.rest.model.Attachment;
import fi.otavanopisto.kuntaapi.server.rest.model.Event;
import fi.otavanopisto.kuntaapi.server.settings.OrganizationSettingController;
import fi.otavanopisto.kuntaapi.server.settings.SystemSettingController;
import fi.otavanopisto.mikkelinyt.model.EventsResponse;

/**
 * Event provider for Mikkeli Nyt
 * 
 * @author Antti Leppä
 */
@Dependent
public class MikkeliNytEventProvider implements EventProvider {
  
  private static final String API_KEY_NOT_CONFIGURED = "Api key not configured";

  @Inject
  private Logger logger;
  
  @Inject
  private IdController idController;
  
  @Inject
  private IdentifierController identifierController;
  
  @Inject
  private GenericHttpClient httpClient;
  
  @Inject
  private BinaryHttpClient binaryHttpClient;
  
  @Inject
  private GenericHttpCache httpCache;
  
  @Inject
  private SystemSettingController systemSettingController;
  
  @Inject
  private OrganizationSettingController organizationSettingController;

  @Inject
  private ImageReader imageReader;

  @Inject
  private ImageWriter imageWriter;
  
  @Inject
  private ImageScaler imageScaler;
  
  private String apiKey;
  
  private MikkeliNytEventProvider() {
  }
  
  /**
   * Bean initialization
   */
  @PostConstruct
  public void init() {
    apiKey = systemSettingController.getSettingValue(MikkeliNytConsts.SYSTEM_SETTING_APIKEY);
  }

  @Override
  public List<Event> listOrganizationEvents(OrganizationId organizationId, OffsetDateTime startBefore,
      OffsetDateTime startAfter, OffsetDateTime endBefore, OffsetDateTime endAfter, EventOrder order,
      EventOrderDirection orderDirection, Integer firstResult, Integer maxResults) {
    
   if (StringUtils.isBlank(apiKey)) {
      logger.severe(API_KEY_NOT_CONFIGURED);
      return Collections.emptyList();
    }

    Response<EventsResponse> response = listEvents(organizationId);
    if (response.isOk()) {
      EventsResponse events = response.getResponseEntity();
      
      List<fi.otavanopisto.mikkelinyt.model.Event> mikkeliNytEvents = getFilteredEventsByDates(startBefore, startAfter, endBefore, endAfter, events.getData());
      mikkeliNytEvents = limitEventCount(firstResult, maxResults, mikkeliNytEvents);
      Collections.sort(mikkeliNytEvents, new EventComparator(order, orderDirection));
      
      return transform(mikkeliNytEvents);
    } else {
      logger.severe(String.format("Request list organization %s failed on [%d] %s", organizationId.toString(), response.getStatus(), response.getMessage()));
    }
    
    return Collections.emptyList();
  }

  @Override
  public Event findOrganizationEvent(OrganizationId organizationId, EventId eventId) {
    if (StringUtils.isBlank(apiKey)) {
      logger.severe(API_KEY_NOT_CONFIGURED);
      return null;
    }
    
    EventId kuntaApiEventId = idController.translateEventId(eventId,  KuntaApiConsts.IDENTIFIER_NAME);
    return transform(findEvent(organizationId, kuntaApiEventId));
  }

  @Override
  public List<Attachment> listEventImages(OrganizationId organizationId, EventId eventId) {
    if (StringUtils.isBlank(apiKey)) {
      logger.severe(API_KEY_NOT_CONFIGURED);
      return Collections.emptyList();
    }
    
    EventId kuntaApiEventId = idController.translateEventId(eventId,  KuntaApiConsts.IDENTIFIER_NAME);
    fi.otavanopisto.mikkelinyt.model.Event event = findEvent(organizationId, kuntaApiEventId);
    if ((event != null) && StringUtils.isNotBlank(event.getImage())) {
      Attachment imageAttachment = loadEventImageAttachment(organizationId, getImageAttachmentId(event.getImage()));
      if (imageAttachment != null) {
        return Collections.singletonList(imageAttachment);
      }
    }

    return Collections.emptyList();
  }
  
  @Override
  public Attachment findEventImage(OrganizationId organizationId, EventId eventId, AttachmentId attachmentId) {
    if (StringUtils.isBlank(apiKey)) {
      logger.severe(API_KEY_NOT_CONFIGURED);
      return null;
    }
    
    EventId kuntaApiEventId = idController.translateEventId(eventId, KuntaApiConsts.IDENTIFIER_NAME);
    fi.otavanopisto.mikkelinyt.model.Event event = findEvent(organizationId, kuntaApiEventId);
    if ((event != null) && StringUtils.isNotBlank(event.getImage())) {
      AttachmentId imageId = getImageAttachmentId(event.getImage());
      if (idController.idsEqual(attachmentId, imageId)) {
        return loadEventImageAttachment(organizationId, imageId);
      }
    }

    return null;
  }

  @Override
  public AttachmentData getEventImageData(OrganizationId organizationId, EventId eventId, AttachmentId attachmentId, Integer size) {
    if (StringUtils.isBlank(apiKey)) {
      logger.severe(API_KEY_NOT_CONFIGURED);
      return null;
    }

    EventId kuntaApiEventId = idController.translateEventId(eventId,  KuntaApiConsts.IDENTIFIER_NAME);
    fi.otavanopisto.mikkelinyt.model.Event event = findEvent(organizationId, kuntaApiEventId);
    if ((event != null) && StringUtils.isNotBlank(event.getImage())) {
      AttachmentId imageId = getImageAttachmentId(event.getImage());
      if (idController.idsEqual(attachmentId, imageId)) {
        AttachmentData imageData = getImageData(organizationId, imageId);
        if (size != null) {
          return scaleEventImage(imageData, size);
        } else {
          return imageData;
        }
      }
    }

    return null;
  }

  private List<fi.otavanopisto.mikkelinyt.model.Event> limitEventCount(Integer firstResult, Integer maxResults, List<fi.otavanopisto.mikkelinyt.model.Event> mikkeliNytEvents) {
    if (firstResult != null || maxResults != null) {
      int first = firstResult == null ? 0 : firstResult.intValue();
      int last = maxResults == null ? mikkeliNytEvents.size() : maxResults.intValue() + first;

      if (first >= mikkeliNytEvents.size()) {
        return Collections.emptyList();
      }
      
      return mikkeliNytEvents.subList(first, last);
    }
    
    return mikkeliNytEvents;
  }

  private List<fi.otavanopisto.mikkelinyt.model.Event> getFilteredEventsByDates(OffsetDateTime startBefore, OffsetDateTime startAfter, OffsetDateTime endBefore,
      OffsetDateTime endAfter, List<fi.otavanopisto.mikkelinyt.model.Event> events) {
    
    List<fi.otavanopisto.mikkelinyt.model.Event> filteredResult = new ArrayList<>(events);
    
    for (int i = filteredResult.size() - 1; i >= 0; i--) {
      fi.otavanopisto.mikkelinyt.model.Event event = filteredResult.get(i);
      if (!isWithinTimeRanges(event, startBefore, startAfter, endBefore, endAfter)) {
        filteredResult.remove(i);
      } 
    }
    
    return filteredResult;
  }
  
  private boolean isWithinTimeRanges(fi.otavanopisto.mikkelinyt.model.Event event, OffsetDateTime startBefore,
      OffsetDateTime startAfter, OffsetDateTime endBefore, OffsetDateTime endAfter) {
    
    OffsetDateTime eventStart = parseOffsetDateTime(event.getStart());
    OffsetDateTime eventEnd = parseOffsetDateTime(event.getEnd());
    
    if (eventStart == null || eventEnd == null) {
      return false;
    }
    
    if (!isWithinTimeRange(startBefore, startAfter, eventStart)) {
      return false;
    }
    
    if (!isWithinTimeRange(endBefore, endAfter, eventEnd)) {
      return false;
    }
    
    return true;
  }

  private boolean isWithinTimeRange(OffsetDateTime before, OffsetDateTime after, OffsetDateTime time) {
    if (before != null && time.isAfter(before)) {
      return false;
    }


    if (after != null && time.isBefore(after)) {
      return false;
    }
    
    return true;
  }
  
  private AttachmentData scaleEventImage(AttachmentData imageData, Integer size) {
    BufferedImage bufferedImage = imageReader.readBufferedImage(imageData.getData());
    if (bufferedImage != null) {
      BufferedImage scaledImage = imageScaler.scaleMaxSize(bufferedImage, size);
      byte[] scaledImageData = imageWriter.writeBufferedImageAsPng(scaledImage);
      if (scaledImageData != null) {
        return new AttachmentData("image/png", scaledImageData);
      }
    }
    
    return null;
  }
  
  private Attachment loadEventImageAttachment(OrganizationId organizationId, AttachmentId imageId) {
    AttachmentData attachmentData = getImageData(organizationId, imageId);
    
    long size = getImageSize(attachmentData);
    Attachment attachment = new Attachment();
    attachment.setContentType(attachmentData.getType());
    attachment.setId(imageId.getId());
    attachment.setSize(size);
    return attachment;
  }
  
  private long getImageSize(AttachmentData attachmentData) {
    return attachmentData.getData().length;
  }
  
  private fi.otavanopisto.mikkelinyt.model.Event findEvent(OrganizationId kuntaApiOrganizationId, EventId kuntaApiEventId) {
    Response<EventsResponse> listResponse = listEvents(kuntaApiOrganizationId);
    if (!listResponse.isOk()) {
      logger.severe(String.format("Request to list events on %s failed on [%d] %s", kuntaApiOrganizationId.toString(), listResponse.getStatus(), listResponse.getMessage()));
      return null;
    }
    
    for (fi.otavanopisto.mikkelinyt.model.Event event : listResponse.getResponseEntity().getData()) {
      EventId mikkeliNytEventId = new EventId(MikkeliNytConsts.IDENTIFIER_NAME, event.getId());
      if (idController.idsEqual(mikkeliNytEventId, kuntaApiEventId)) {
        return event;
      }
    }
    
    return null;
  }

  private Response<EventsResponse> listEvents(OrganizationId organizationId) {
    String location = organizationSettingController.getSettingValue(organizationId, MikkeliNytConsts.ORGANIZATION_SETTING_LOCATION);
    String baseUrl = organizationSettingController.getSettingValue(organizationId, MikkeliNytConsts.ORGANIZATION_SETTING_BASEURL);
    
    URI uri;
    try {
      URIBuilder uriBuilder = new URIBuilder(String.format("%s%s", baseUrl, "/json.php?showall=1"));

      uriBuilder.addParameter("apiKey", apiKey);
      if (StringUtils.isNotBlank(location)) {
        uriBuilder.addParameter("location", location);
      } else {
        logger.warning("location not specified. Returning unfiltered event list");
      }
     
      uri = uriBuilder.build();
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, "Invalid uri", e);
      return new Response<>(500, "Internal Server Error", null);
    }
    
    
    Response<EventsResponse> cachedResponse = httpCache.get(MikkeliNytConsts.CACHE_NAME, uri, new GenericHttpClient.ResultType<Response<fi.otavanopisto.mikkelinyt.model.EventsResponse>>() {});
    if (cachedResponse != null) {
      return cachedResponse;
    }
    
    Response<EventsResponse> response = httpClient.doGETRequest(uri, new GenericHttpClient.ResultType<fi.otavanopisto.mikkelinyt.model.EventsResponse>() {});
    httpCache.put(MikkeliNytConsts.CACHE_NAME, uri, response);
    return response;
  }

  private AttachmentData getImageData(OrganizationId organizationId, AttachmentId imageId) {
    AttachmentId mikkeliNytId = idController.translateAttachmentId(imageId, MikkeliNytConsts.IDENTIFIER_NAME);
    if (mikkeliNytId == null) {
      logger.severe(String.format("Failed to translate %s into MikkeliNyt id", imageId.toString()));
      return null;
    }
    
    String imageBaseUrl = organizationSettingController.getSettingValue(organizationId, MikkeliNytConsts.ORGANIZATION_SETTING_IMAGEBASEURL);
    if (StringUtils.isNotBlank(imageBaseUrl)) {
      String imageUrl = String.format("%s%s", imageBaseUrl, mikkeliNytId.getId());
      Response<AttachmentData> imageDataResponse = getImageData(imageUrl);
      if (imageDataResponse.isOk()) {
        return imageDataResponse.getResponseEntity();
      } else {
        logger.severe(String.format("Request to find image (%s) data failed on [%d] %s", imageId.toString(), imageDataResponse.getStatus(), imageDataResponse.getMessage()));
      }
    }
    
    logger.severe(String.format("Image imageBaseUrl has not been configured properly for organization %s", organizationId));
    
    return null;
  }
  
  private Response<AttachmentData> getImageData(String imageUrl) {
    URI uri;
    
    try {
      uri = new URIBuilder(imageUrl).build();
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, String.format("Invalid uri %s", imageUrl), e);
      return new Response<>(500, "Internal Server Error", null);
    }
    
    Response<AttachmentData> cachedResponse = httpCache.get(MikkeliNytConsts.CACHE_NAME, uri, new GenericHttpClient.ResultType<Response<AttachmentData>>() {});
    if (cachedResponse != null) {
      return cachedResponse;
    }
    
    Response<BinaryResponse> response = binaryHttpClient.downloadBinary(uri);
    AttachmentData data = null;
    if (response.getResponseEntity() != null) {
      data = new AttachmentData(response.getResponseEntity().getType(), response.getResponseEntity().getData());
    }
    
    Response<AttachmentData> attachmentResponse = new Response<>(response.getStatus(), response.getMessage(), data);
    
    httpCache.put(MikkeliNytConsts.CACHE_NAME, uri, attachmentResponse); 
    
    return attachmentResponse;
  }
  
  private AttachmentId getImageAttachmentId(String url) {
    String imageId = StringUtils.substringAfterLast(url, "/");
    AttachmentId mikkeliNytId = new AttachmentId(MikkeliNytConsts.IDENTIFIER_NAME, imageId);
    AttachmentId kuntaApiId = idController.translateAttachmentId(mikkeliNytId, KuntaApiConsts.IDENTIFIER_NAME);
    
    if (kuntaApiId == null) {
      logger.info(String.format("Found new MikkeliNyt attachment %s", imageId));
      Identifier newIdentifier = identifierController.createIdentifier(mikkeliNytId);
      kuntaApiId = new AttachmentId(KuntaApiConsts.IDENTIFIER_NAME, newIdentifier.getKuntaApiId());
    }
    
    return kuntaApiId;
  }

  private List<Event> transform(List<fi.otavanopisto.mikkelinyt.model.Event> nytEvents) {
    List<Event> result = new ArrayList<>(nytEvents.size());
    
    for (fi.otavanopisto.mikkelinyt.model.Event nytEvent : nytEvents) {
      result.add(transform(nytEvent));
    }
    
    return result;
  }
  
  private Event transform(fi.otavanopisto.mikkelinyt.model.Event nytEvent) {
    if (nytEvent == null) {
      return null;
    }
    
    EventId mikkeliNytId = new EventId(MikkeliNytConsts.IDENTIFIER_NAME, nytEvent.getId());
    EventId kuntaApiId = idController.translateEventId(mikkeliNytId, KuntaApiConsts.IDENTIFIER_NAME);
    if (kuntaApiId == null) {
      logger.info(String.format("Found new MikkeliNyt event %s", nytEvent.getId()));
      Identifier newIdentifier = identifierController.createIdentifier(mikkeliNytId);
      kuntaApiId = new EventId(KuntaApiConsts.IDENTIFIER_NAME, newIdentifier.getKuntaApiId());
    }
    
    Event result = new Event();
    
    result.setAddress(stripHtml(nytEvent.getAddress()));
    result.setCity(stripHtml(nytEvent.getCity()));
    result.setDescription(nytEvent.getDescription());
    result.setEnd(parseOffsetDateTime(nytEvent.getEnd()));
    result.setId(kuntaApiId.getId());
    result.setName(stripHtml(nytEvent.getName()));
    result.setOriginalUrl(nytEvent.getUrl());
    result.setPlace(stripHtml(nytEvent.getPlace()));
    result.setZip(stripHtml(nytEvent.getZip()));
    result.setStart(parseOffsetDateTime(nytEvent.getStart()));
    
    return result;
  }
  
  private String stripHtml(String html) {
    return StringUtils.trim(StringUtils.normalizeSpace(StringEscapeUtils.unescapeHtml4(html.replaceAll("\\<.*?>"," "))));
  }
  
  private OffsetDateTime parseOffsetDateTime(String text) {
    LocalDateTime localDateTime = parseLocalDateTime(text);
    return localDateTime.atZone(ZoneId.of(MikkeliNytConsts.SERVER_TIMEZONE_ID)).toOffsetDateTime();
  }
  
  private LocalDateTime parseLocalDateTime(String text) {
    
    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .append(DateTimeFormatter.ISO_LOCAL_DATE)
      .appendLiteral(' ')
      .append(DateTimeFormatter.ISO_LOCAL_TIME)
      .toFormatter();
    
    return LocalDateTime.parse(text, formatter);
  }

  private class EventComparator implements Comparator<fi.otavanopisto.mikkelinyt.model.Event> {
    
    private EventOrder order;
    private EventOrderDirection direction;
    
    public EventComparator(EventOrder order, EventOrderDirection direction) {
      this.order = order;
      this.direction = direction;
    }
    
    @Override
    public int compare(fi.otavanopisto.mikkelinyt.model.Event event1, fi.otavanopisto.mikkelinyt.model.Event event2) {
      int result;
      
      switch (order) {
        case END_DATE:
          result = compareEndDates(event1, event2);
        break;
        case START_DATE:
          result = compareStartDates(event1, event2);
        break;
        default:
          result = 0;
        break;
      }
      
      if (direction == EventOrderDirection.ASCENDING) {
        return -result;
      } 
      
      return result;
    }

    private int compareStartDates(fi.otavanopisto.mikkelinyt.model.Event event1,
        fi.otavanopisto.mikkelinyt.model.Event event2) {
      return compareDates(parseLocalDateTime(event1.getStart()), parseLocalDateTime(event2.getStart()));
    }

    private int compareEndDates(fi.otavanopisto.mikkelinyt.model.Event event1,
        fi.otavanopisto.mikkelinyt.model.Event event2) {
      return compareDates(parseLocalDateTime(event1.getEnd()), parseLocalDateTime(event2.getEnd()));
    }
    
    private int compareDates(LocalDateTime dateTime1, LocalDateTime dateTime2) {
      if (dateTime1 == null && dateTime2 == null) {
        return 0;
      }
      
      if (dateTime1 == null) {
        return 1;
      } else if (dateTime2 == null) {
        return -1;
      }
              
      return dateTime1.compareTo(dateTime2);
    }
    
  }
}
