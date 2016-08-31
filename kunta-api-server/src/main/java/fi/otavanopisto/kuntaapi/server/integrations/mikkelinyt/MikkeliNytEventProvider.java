package fi.otavanopisto.kuntaapi.server.integrations.mikkelinyt;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import fi.otavanopisto.kuntaapi.server.controllers.IdentifierController;
import fi.otavanopisto.kuntaapi.server.integrations.AttachmentData;
import fi.otavanopisto.kuntaapi.server.integrations.AttachmentId;
import fi.otavanopisto.kuntaapi.server.integrations.EventId;
import fi.otavanopisto.kuntaapi.server.integrations.EventProvider;
import fi.otavanopisto.kuntaapi.server.integrations.GenericHttpClient;
import fi.otavanopisto.kuntaapi.server.integrations.GenericHttpClient.Response;
import fi.otavanopisto.kuntaapi.server.integrations.IdController;
import fi.otavanopisto.kuntaapi.server.integrations.IdType;
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
  private SystemSettingController systemSettingController;
  
  @Inject
  private OrganizationSettingController organizationSettingController;
  
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
  public List<Event> listOrganizationEvents(OrganizationId organizationId) {
    if (StringUtils.isBlank(apiKey)) {
      logger.severe(API_KEY_NOT_CONFIGURED);
      return Collections.emptyList();
    }

    Response<EventsResponse> response = listEvents(organizationId);
    if (response.isOk()) {
      EventsResponse events = response.getResponseEntity();
      return transform(events.getData());
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
  public AttachmentData getEventImageData(OrganizationId organizationId, EventId eventId, AttachmentId attachmentId) {
    if (StringUtils.isBlank(apiKey)) {
      logger.severe(API_KEY_NOT_CONFIGURED);
      return null;
    }

    EventId kuntaApiEventId = idController.translateEventId(eventId,  KuntaApiConsts.IDENTIFIER_NAME);
    fi.otavanopisto.mikkelinyt.model.Event event = findEvent(organizationId, kuntaApiEventId);
    if ((event != null) && StringUtils.isNotBlank(event.getImage())) {
      AttachmentId imageId = getImageAttachmentId(event.getImage());
      if (idController.idsEqual(attachmentId, imageId)) {
        return getImageData(organizationId, imageId);
      }
    }

    return null;
  }
  
  private Attachment loadEventImageAttachment(OrganizationId organizationId, AttachmentId imageId) {
    AttachmentData attachmentData = getImageData(organizationId, imageId);
    try {
      long size = getImageSize(attachmentData);
      Attachment attachment = new Attachment();
      attachment.setContentType(attachmentData.getType());
      attachment.setId(imageId.getId());
      attachment.setSize(size);
      return attachment;
    } finally {
      try {
        attachmentData.getData().close();
      } catch (IOException e) {
        logger.log(Level.SEVERE, "Failed to close image data stream", e);
      }
    }
  }
  
  private long getImageSize(AttachmentData attachmentData) {
    try {
      return IOUtils.toByteArray(attachmentData.getData()).length;
    } catch (IOException e) {
      logger.log(Level.WARNING, "Could not determine image size", e);
    }
    
    return 0l;
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
    
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("apiKey", apiKey);
    
    if (StringUtils.isNotBlank(location)) {
       queryParams.put("location", location);
    } else {
      logger.warning("location not specified. Returning unfiltered event list");
    }
    
    return httpClient.doGETRequest(baseUrl, "/json.php", new GenericHttpClient.ResultType<fi.otavanopisto.mikkelinyt.model.EventsResponse>() {}, queryParams);
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
    try {
      CloseableHttpClient imageClient = HttpClients.createDefault();
      try {
        HttpGet httpGet = new HttpGet(imageUrl);
        
        CloseableHttpResponse response = imageClient.execute(httpGet);
        try {
          StatusLine statusLine = response.getStatusLine();
          int statusCode = statusLine.getStatusCode();
          String message = statusLine.getReasonPhrase();
          InputStream data = IOUtils.toBufferedInputStream(response.getEntity().getContent());
          String type = response.getEntity().getContentType().getValue();
          return new Response<>(statusCode, message, new AttachmentData(type, data));
        } finally {
          response.close();
        }
      } finally {
        imageClient.close();
      }
    } catch (IOException e) {
      logger.log(Level.WARNING, String.format("Failed to fetch event imagae from %s", imageUrl), e);
      return new Response<>(500, "Interval Server Error", null);
    }
  }
  
  private AttachmentId getImageAttachmentId(String url) {
    String imageId = StringUtils.substringAfterLast(url, "/");
    AttachmentId mikkeliNytId = new AttachmentId(MikkeliNytConsts.IDENTIFIER_NAME, imageId);
    AttachmentId kuntaApiId = idController.translateAttachmentId(mikkeliNytId, KuntaApiConsts.IDENTIFIER_NAME);
    
    if (kuntaApiId == null) {
      logger.info(String.format("Found new MikkeliNyt attachment %s", imageId));
      Identifier newIdentifier = identifierController.createIdentifier(IdType.ATTACHMENT, MikkeliNytConsts.IDENTIFIER_NAME, imageId);
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
      Identifier newIdentifier = identifierController.createIdentifier(IdType.EVENT, MikkeliNytConsts.IDENTIFIER_NAME, nytEvent.getId());
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
  
  private static String stripHtml(String html) {
    return StringUtils.trim(StringUtils.normalizeSpace(StringEscapeUtils.unescapeHtml4(html.replaceAll("\\<.*?>"," "))));
  }
  
  private static OffsetDateTime parseOffsetDateTime(String text) {
    LocalDateTime localDateTime = parseLocalDateTime(text);
    return localDateTime.atZone(ZoneId.systemDefault()).toOffsetDateTime();
  }
  
  private static LocalDateTime parseLocalDateTime(String text) {
    
    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .append(DateTimeFormatter.ISO_LOCAL_DATE)
      .appendLiteral(' ')
      .append(DateTimeFormatter.ISO_LOCAL_TIME)
      .toFormatter();
    
    return LocalDateTime.parse(text, formatter);
  }

}
