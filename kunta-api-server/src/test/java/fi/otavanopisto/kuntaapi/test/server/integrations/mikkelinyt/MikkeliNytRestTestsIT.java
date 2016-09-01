package fi.otavanopisto.kuntaapi.test.server.integrations.mikkelinyt;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import fi.otavanopisto.kuntaapi.server.integrations.IdType;
import fi.otavanopisto.kuntaapi.server.integrations.mikkelinyt.MikkeliNytConsts;
import fi.otavanopisto.kuntaapi.server.rest.model.Attachment;
import fi.otavanopisto.kuntaapi.test.AbstractIntegrationTest;
import fi.otavanopisto.mikkelinyt.model.Event;
import fi.otavanopisto.mikkelinyt.model.EventsResponse;

public class MikkeliNytRestTestsIT extends AbstractIntegrationTest {
  
  private static final String ORGANIZATION_SETTING_APIKEY = "test-api-key";
  private static final String ORGANIZATION_SETTING_LOCATION = "test";
  private static final int ATTACHMENT_SIZE = 38244;
  private static final String ATTACHMENT_TYPE = "image/jpeg";
  
  @Rule
  public WireMockRule wireMockRule = new WireMockRule(getWireMockPort());

  @Test
  public void testEvents() {
    String kuntaApiOrganizationId = "ka-organization-id";
    String baseUrl = getWireMockBasePath();
    String imagesBasePath = "uploads/testevents";
    String kuntaApiEventId = "ka-event-id";
    String kuntaApiAttachmentId = "ka-attachment-id";

    createSettings(kuntaApiOrganizationId, baseUrl, imagesBasePath);
    
    MikkeliNytMocker mocker = new MikkeliNytMocker();
    mockEvent(mocker, kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId, baseUrl, imagesBasePath);
    mocker.startMock();
    try {
      given() 
        .baseUri(getApiBasePath())
        .contentType("application/json")
        .get("/organizations/{ORGANIZATIONID}/events", kuntaApiOrganizationId)
        .then()
        .assertThat()
        .statusCode(200)
        .body("id.size()", is(1))
        .body("id[0]", is(kuntaApiEventId))
        .body("originalUrl[0]", is(String.format("%s/event-url-address", baseUrl)))
        .body("name[0]", is("Test Event"))
        .body("description[0]", is("My test<br/>event"))
        .body("start[0]", sameInstant(OffsetDateTime.of(2020, 5, 6, 17, 30, 0, 0, ZoneOffset.ofHours(3)).toInstant()))
        .body("end[0]", sameInstant(OffsetDateTime.of(2020, 5, 6, 19, 00, 0, 0, ZoneOffset.ofHours(3)).toInstant()))
        .body("city[0]", is(ORGANIZATION_SETTING_LOCATION))
        .body("place[0]", is("Testing Ltd"))
        .body("address[0]", is("Testroad 3"))
        .body("zip[0]", is("12345"));
    } finally {
      cleanMock(mocker, kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId);
      deleteSettings(kuntaApiOrganizationId);
    }
  }
  
  @Test
  public void testEvent() {
    String kuntaApiOrganizationId = "ka-organization-id";
    String baseUrl = getWireMockBasePath();
    String imagesBasePath = "uploads/testevents";
    String kuntaApiEventId = "ka-event-id";
    String kuntaApiAttachmentId = "ka-attachment-id";

    createSettings(kuntaApiOrganizationId, baseUrl, imagesBasePath);
    
    MikkeliNytMocker mocker = new MikkeliNytMocker();
    mockEvent(mocker, kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId, baseUrl, imagesBasePath);
    mocker.startMock();
    try {
      given() 
        .baseUri(getApiBasePath())
        .contentType("application/json")
        .get("/organizations/{ORGANIZATIONID}/events/{EVENTID}", kuntaApiOrganizationId, kuntaApiEventId)
        .then()
        .assertThat()
        .statusCode(200)
        .body("id", is(kuntaApiEventId))
        .body("originalUrl", is(String.format("%s/event-url-address", baseUrl)))
        .body("name", is("Test Event"))
        .body("description", is("My test<br/>event"))
        .body("start", sameInstant(OffsetDateTime.of(2020, 5, 6, 17, 30, 0, 0, ZoneOffset.ofHours(3)).toInstant()))
        .body("end", sameInstant(OffsetDateTime.of(2020, 5, 6, 19, 00, 0, 0, ZoneOffset.ofHours(3)).toInstant()))
        .body("city", is(ORGANIZATION_SETTING_LOCATION))
        .body("place", is("Testing Ltd"))
        .body("address", is("Testroad 3"))
        .body("zip", is("12345"));
    } finally {
      cleanMock(mocker, kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId);
      deleteSettings(kuntaApiOrganizationId);
    }
  }
  
  @Test
  public void testImage() {
    String kuntaApiOrganizationId = "ka-organization-id";
    String baseUrl = getWireMockBasePath();
    String imagesBasePath = "uploads/testevents";
    String kuntaApiEventId = "ka-event-id";
    String kuntaApiAttachmentId = "ka-attachment-id";

    createSettings(kuntaApiOrganizationId, baseUrl, imagesBasePath);

    MikkeliNytMocker mocker = new MikkeliNytMocker();
    mockEvent(mocker, kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId, baseUrl, imagesBasePath);
    mocker.startMock();
    try {
      given() 
        .baseUri(getApiBasePath())
        .contentType("application/json")
        .get("/organizations/{ORGANIZATIONID}/events/{EVENTID}/images/{IMAGEID}", kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId)
        .then()
        .assertThat()
        .statusCode(200)
        .body("id", is(kuntaApiAttachmentId))
        .body("contentType", is(ATTACHMENT_TYPE));
    } finally {
      cleanMock(mocker, kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId);
      deleteSettings(kuntaApiOrganizationId);
    }
  }

  @Test
  public void testImages() {
    String kuntaApiOrganizationId = "ka-organization-id";
    String baseUrl = getWireMockBasePath();
    String imagesBasePath = "uploads/testevents";
    String kuntaApiEventId = "ka-event-id";
    String kuntaApiAttachmentId = "ka-attachment-id";

    createSettings(kuntaApiOrganizationId, baseUrl, imagesBasePath);

    MikkeliNytMocker mocker = new MikkeliNytMocker();
    mockEvent(mocker, kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId, baseUrl, imagesBasePath);
    mocker.startMock();
    try {
      given() 
        .baseUri(getApiBasePath())
        .contentType("application/json")
        .get("/organizations/{ORGANIZATIONID}/events/{EVENTID}/images", kuntaApiOrganizationId, kuntaApiEventId)
        .then()
        .assertThat()
        .statusCode(200)
        .body("id.size()", is(1))
        .body("id[0]", is(kuntaApiAttachmentId))
        .body("size", hasItemInArray(equalTo(ATTACHMENT_SIZE)))
        .body("contentType[0]", is(ATTACHMENT_TYPE));
    } finally {
      cleanMock(mocker, kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId);
      deleteSettings(kuntaApiOrganizationId);
    }
  }
  @Test
  public void testImageData() throws Exception {
    String kuntaApiOrganizationId = "ka-organization-id";
    String baseUrl = getWireMockBasePath();
    String imagesBasePath = "uploads/testevents";
    String kuntaApiEventId = "ka-event-id";
    String kuntaApiAttachmentId = "ka-attachment-id";

    createSettings(kuntaApiOrganizationId, baseUrl, imagesBasePath);

    MikkeliNytMocker mocker = new MikkeliNytMocker();
    mockEvent(mocker, kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId, baseUrl, imagesBasePath);
    mocker.startMock();
    try {
      given() 
        .baseUri(getApiBasePath())
        .contentType("application/json")
        .get("/organizations/{ORGANIZATIONID}/events/{EVENTID}/images/{IMAGEID}/data", kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId)
        .then()
        .assertThat()
        .statusCode(200)
        .header("Content-Type", "image/jpeg");
    } finally {
      cleanMock(mocker, kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId);
      deleteSettings(kuntaApiOrganizationId);
    }
  }

  private Event mockEvent(MikkeliNytMocker mocker, String kuntaApiOrganizationId, String kuntaApiEventId, String kuntaApiAttachmentId, String baseUrl, String imagesBasePath) {
    String eventId = "mn-event-id";
    String attachmentId = "mn-attachment-id.jpg";
    String eventUrl = String.format("%s/event-url-address", baseUrl);
    String eventName = "Test Event";
    String eventDescription = "My test<br/>event";
    String eventCity = ORGANIZATION_SETTING_LOCATION;
    String eventAddres = "Testroad 3";
    String eventZip = "12345";
    String eventPlace = "Testing Ltd";
    String eventThumbPath = String.format("%s/480/%s", imagesBasePath, attachmentId);
    String eventImagePath = String.format("%s/1000/%s", imagesBasePath, attachmentId);
    String eventThumb = String.format("%s%s", baseUrl, eventThumbPath);
    String eventImage = String.format("%s%s", baseUrl, eventImagePath);
    OffsetDateTime start = OffsetDateTime.of(2020, 5, 6, 17, 30, 0, 0, ZoneOffset.ofHours(3));
    OffsetDateTime end = OffsetDateTime.of(2020, 5, 6, 19, 00, 0, 0, ZoneOffset.ofHours(3));
    createIdentifier(kuntaApiOrganizationId, MikkeliNytConsts.IDENTIFIER_NAME, "test-source-id", IdType.ORGANIZATION.name());
    createIdentifier(kuntaApiEventId, MikkeliNytConsts.IDENTIFIER_NAME, eventId, IdType.EVENT.name());
    createIdentifier(kuntaApiAttachmentId, MikkeliNytConsts.IDENTIFIER_NAME, attachmentId, IdType.ATTACHMENT.name());
    mocker.mockGetBinary("/" + eventThumbPath, "image/jpeg", "test-image-480.jpg");
    mocker.mockGetBinary("/" + eventImagePath, "image/jpeg", "test-image-1000.jpg");

    return mocker.mockEvent(kuntaApiOrganizationId, kuntaApiEventId, kuntaApiAttachmentId, eventId, eventUrl, eventName, eventDescription, eventCity, eventAddres, eventZip, eventPlace, eventImage, eventThumb, start, end);
  }

  private void cleanMock(MikkeliNytMocker mocker, String kuntaApiOrganizationId, String kuntaApiEventId, String kuntaApiAttachmentId) {
    mocker.endMock();

    deleteIndentifier(kuntaApiOrganizationId);
    deleteIndentifier(kuntaApiEventId);
    deleteIndentifier(kuntaApiAttachmentId);
  }

  private void createSettings(String kuntaApiOrganizationId, String baseUrl, String imagesBasePath) {
    insertSystemSetting(MikkeliNytConsts.SYSTEM_SETTING_APIKEY, ORGANIZATION_SETTING_APIKEY);
    insertOrganizationSetting(kuntaApiOrganizationId, MikkeliNytConsts.ORGANIZATION_SETTING_LOCATION, ORGANIZATION_SETTING_LOCATION);
    insertOrganizationSetting(kuntaApiOrganizationId, MikkeliNytConsts.ORGANIZATION_SETTING_BASEURL, baseUrl);
    insertOrganizationSetting(kuntaApiOrganizationId, MikkeliNytConsts.ORGANIZATION_SETTING_IMAGEBASEURL, String.format("%s/%s/1000/", baseUrl, imagesBasePath));
  }
  
  private void deleteSettings(String kuntaApiOrganizationId) {
    deleteSystemSetting(MikkeliNytConsts.SYSTEM_SETTING_APIKEY);
    deleteOrganizationSetting(kuntaApiOrganizationId, MikkeliNytConsts.ORGANIZATION_SETTING_LOCATION);
    deleteOrganizationSetting(kuntaApiOrganizationId, MikkeliNytConsts.ORGANIZATION_SETTING_BASEURL);
    deleteOrganizationSetting(kuntaApiOrganizationId, MikkeliNytConsts.ORGANIZATION_SETTING_IMAGEBASEURL);
  }
  
  public class MikkeliNytMocker extends AbstractMocker {
    
    public MikkeliNytMocker() {
      this.events = new ArrayList<>();
    }
    
    public Event mockEvent(String kuntaApiOrganizationId, String kuntaApiEventId, String kuntaApiAttachmentId, String id, String url, String name, String description, String city, String address, String zip, String place, String image, String thumb, OffsetDateTime start, OffsetDateTime end) {
      String location = String.format("%s, %s", formatAddress(address), formatZip(zip));
      
      Event event = new Event();
      event.setAddress(formatAddress(address));
      event.setCity(formatCity(city));
      event.setDescription(description);
      event.setEnd(formatDateTime(end));
      event.setId(id);
      event.setImage(image);
      event.setLocation(location);
      event.setName(name);
      event.setNiceDatetime(formatNiceDateRange(start, end));
      event.setPlace(formatPlace(place));
      event.setStart(formatDateTime(start));
      event.setThumb(thumb);
      event.setUrl(url);
      event.setZip(formatZip(zip));
      
      Attachment imageAttachment = new Attachment();
      imageAttachment.setContentType(ATTACHMENT_TYPE);
      imageAttachment.setSize(new Long(ATTACHMENT_SIZE));
      imageAttachment.setId(kuntaApiAttachmentId);
      
      this.events.add(event);
      
      return event;
    }
    
    private String formatAddress(String address) {
      return String.format("<span class=\"Address\">%s</span>", address);
    }
    
    private String formatZip(String zip) {
      return String.format("<span class=\"Zip\">%s</span>", zip);
    }
    
    private String formatCity(String zip) {
      return String.format("<span class=\"City\">%s</span>", zip);
    }
    
    private String formatPlace(String place) {
      return String.format("<span class=\"Place\">%s</span>", place);
    }
    
    private String formatDateTime(OffsetDateTime localDateTime) {
      DateTimeFormatter formatter = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .append(DateTimeFormatter.ISO_LOCAL_DATE)
        .appendLiteral(' ')
        .append(DateTimeFormatter.ISO_LOCAL_TIME)
        .toFormatter();
      
      return localDateTime.format(formatter);
    }
    
    private String formatNiceDateRange(OffsetDateTime from, OffsetDateTime to) {
      DateTimeFormatter startDateFormat = new DateTimeFormatterBuilder()
        .appendValue(ChronoField.DAY_OF_MONTH)
        .appendLiteral('.')
        .appendValue(ChronoField.MONTH_OF_YEAR)
        .toFormatter();
      
      DateTimeFormatter endDateFormat = new DateTimeFormatterBuilder()
          .appendValue(ChronoField.DAY_OF_MONTH)
          .appendLiteral('.')
          .appendValue(ChronoField.MONTH_OF_YEAR)
          .appendLiteral('.')
          .appendValue(ChronoField.YEAR)
          .toFormatter();
      
      DateTimeFormatter timeFormat = new DateTimeFormatterBuilder()
          .appendValue(ChronoField.HOUR_OF_DAY)
          .appendLiteral(':')
          .appendValue(ChronoField.MINUTE_OF_HOUR)
          .toFormatter();
      
      return String.format("%s - %s klo %s - %s", 
          from.format(startDateFormat), 
          to.format(endDateFormat), 
          from.format(timeFormat), 
          to.format(timeFormat));
    }
    
    @Override
    public void startMock() {
      EventsResponse eventsResponse = new EventsResponse();
      eventsResponse.setData(events);
      eventsResponse.setMessage(String.format("Löytyi %d tapahtumaa", events.size()));
      eventsResponse.setStatus(1);
      mockGetJSON(String.format("/json.php?apiKey=%s&location=%s", ORGANIZATION_SETTING_APIKEY, ORGANIZATION_SETTING_LOCATION), eventsResponse);
      super.startMock();
    }
    
    private List<Event> events;
  }
  
  
}
