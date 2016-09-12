package fi.otavanopisto.kuntaapi.server.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import fi.otavanopisto.kuntaapi.server.integrations.AttachmentData;
import fi.otavanopisto.kuntaapi.server.integrations.AttachmentId;
import fi.otavanopisto.kuntaapi.server.integrations.BannerId;
import fi.otavanopisto.kuntaapi.server.integrations.BannerProvider;
import fi.otavanopisto.kuntaapi.server.integrations.EventId;
import fi.otavanopisto.kuntaapi.server.integrations.EventProvider;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.NewsArticleId;
import fi.otavanopisto.kuntaapi.server.integrations.NewsProvider;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationProvider;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceChannelProvider;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceClassProvider;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceId;
import fi.otavanopisto.kuntaapi.server.integrations.ServiceProvider;
import fi.otavanopisto.kuntaapi.server.rest.model.Attachment;
import fi.otavanopisto.kuntaapi.server.rest.model.Banner;
import fi.otavanopisto.kuntaapi.server.rest.model.Event;
import fi.otavanopisto.kuntaapi.server.rest.model.NewsArticle;
import fi.otavanopisto.kuntaapi.server.rest.model.Organization;
import fi.otavanopisto.kuntaapi.server.rest.model.Service;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceClass;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceElectronicChannel;

/**
 * REST Service implementation
 * 
 * @author Antti Leppä
 */
@RequestScoped
@Stateful
@SuppressWarnings ("squid:S3306")
public class OrganizationsApiImpl extends OrganizationsApi {
  
  private static final String NOT_IMPLEMENTED = "Not implemented";

  private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

  private static final String FAILED_TO_STREAM_IMAGE_TO_CLIENT = "Failed to stream image to client";

  @Inject
  private Logger logger;
  
  @Inject
  private Instance<OrganizationProvider> organizationProviders;
  
  @Inject
  private Instance<ServiceProvider> serviceProviders;

  @Inject
  private Instance<ServiceChannelProvider> serviceChannelProviders;

  @Inject
  private Instance<ServiceClassProvider> serviceClassProviders;

  @Inject
  private Instance<EventProvider> eventProviders;

  @Inject
  private Instance<NewsProvider> newsProviders;

  @Inject
  private Instance<BannerProvider> bannerProviders;

  @Override
  public Response listOrganizations(String businessName, String businessCode) {
    List<Organization> organizations = new ArrayList<>();
    for (OrganizationProvider organizationProvider : getOrganizationProviders()) {
      organizations.addAll(organizationProvider.listOrganizations(businessName, businessCode));
    }
    
    return Response.ok(organizations)
      .build();
  }
  
  @Override
  public Response createService(String organizationId, Service body) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }
  
  @Override
  public Response findService(String organizationIdParam, String serviceIdParam) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    ServiceId serviceId = toServiceId(serviceIdParam);
    
    for (ServiceProvider serviceProvider : getServiceProviders()) {
      Service service = serviceProvider.findOrganizationService(organizationId, serviceId);
      if (service != null) {
        return Response.ok(service)
          .build();
      }
    }
    
    return Response
        .status(Status.NOT_FOUND)
        .build();
  }

  @Override
  public Response listServices(String organizationIdParam, String serviceClassIdParam) {
    if (StringUtils.isBlank(organizationIdParam)) {
      return Response.status(Status.BAD_REQUEST)
        .entity("Organization parameter is mandatory")
        .build();
    }
    
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    ServiceClassId serviceClassId = StringUtils.isBlank(serviceClassIdParam) ? null : new ServiceClassId(KuntaApiConsts.IDENTIFIER_NAME, serviceClassIdParam);
    
    List<Service> services = new ArrayList<>();
    for (ServiceProvider serviceProvider : getServiceProviders()) {
      services.addAll(serviceProvider.listOrganizationServices(organizationId, serviceClassId));
    }
    
    return Response.ok(services)
      .build();
  }

  @Override
  public Response updateService(String organizationId, String serviceId) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }

  @Override
  public Response deleteService(String organizationId, String serviceId) {
    return createNotImplemented(NOT_IMPLEMENTED);
  }
  
  @Override
  public Response listServiceElectornicChannels(String organizationIdParam, String serviceIdParam) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    ServiceId serviceId = toServiceId(serviceIdParam);
    
    List<ServiceElectronicChannel> result = new ArrayList<>();
    for (ServiceChannelProvider serviceChannelProvider : getServiceChannelProviders()) {
      result.addAll(serviceChannelProvider.listElectronicChannels(organizationId, serviceId));
    }
    
    return Response.ok(result)
      .build();
  }
  
  @Override
  public Response listServiceClasses(String organizationIdParam) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);

    List<ServiceClass> result = new ArrayList<>();
    for (ServiceClassProvider serviceClassProvider : getServiceClassProviders()) {
      result.addAll(serviceClassProvider.listOrganizationServiceClasses(organizationId));
    }
    
    return Response.ok(result)
      .build();
  }

  @Override
  public Response findOrganizationEvent(String organizationIdParam, String eventIdParam) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    EventId eventId = toEventId(eventIdParam);
    
    for (EventProvider eventProvider : getEventProviders()) {
      Event event = eventProvider.findOrganizationEvent(organizationId, eventId);
      if (event != null) {
        return Response.ok(event)
          .build();
      }
    }
    
    return Response.status(Status.NOT_FOUND)
      .build();
  }

  @Override
  public Response findOrganizationEventImage(String organizationIdParam, String eventIdParam, String imageIdParam) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    EventId eventId = toEventId(eventIdParam);
    AttachmentId attachmentId = new AttachmentId(KuntaApiConsts.IDENTIFIER_NAME, imageIdParam);
    
    for (EventProvider eventProvider : getEventProviders()) {
      Attachment attachment = eventProvider.findEventImage(organizationId, eventId, attachmentId);
      if (attachment != null) {
        return Response.ok(attachment)
          .build();
      }
    }
    
    return Response.status(Status.NOT_FOUND)
      .build();
  }

  @Override
  public Response getOrganizationEventImageData(String organizationIdParam, String eventIdParam, String imageIdParam, Integer size) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    EventId eventId = toEventId(eventIdParam);
    AttachmentId attachmentId = new AttachmentId(KuntaApiConsts.IDENTIFIER_NAME, imageIdParam);
    
    for (EventProvider eventProvider : getEventProviders()) {
      AttachmentData attachmentData = eventProvider.getEventImageData(organizationId, eventId, attachmentId, size);
      if (attachmentData != null) {
        try (InputStream stream = new ByteArrayInputStream(attachmentData.getData())) {
          return Response.ok(stream, attachmentData.getType())
              .build();
        } catch (IOException e) {
          logger.log(Level.SEVERE, FAILED_TO_STREAM_IMAGE_TO_CLIENT, e);
          return Response.status(Status.INTERNAL_SERVER_ERROR)
            .entity(INTERNAL_SERVER_ERROR)
            .build();
        }
      }
    }
    
    return Response.status(Status.NOT_FOUND)
      .build();
  }

  @Override
  public Response listOrganizationEventImages(String organizationIdParam, String eventIdParam) {
    List<Attachment> result = new ArrayList<>();
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    EventId eventId = toEventId(eventIdParam);
    
    for (EventProvider eventProvider : getEventProviders()) {
      result.addAll(eventProvider.listEventImages(organizationId, eventId));
    }
    
    return Response.ok(result)
      .build();
  }

  @Override
  public Response listOrganizationEvents(String organizationIdParam, 
      String startBefore, String startAfter,
      String endBefore, String endAfter,
      Integer firstResult, Integer maxResults,
      String orderBy, String orderDir) {
    
    EventProvider.EventOrder order = EventProvider.EventOrder.START_DATE;
    EventProvider.EventOrderDirection orderDirection = EventProvider.EventOrderDirection.DESCENDING;
    
    if (StringUtils.isNotBlank(orderBy)) {
      order = EnumUtils.getEnum(EventProvider.EventOrder.class, orderBy);
      if (order == null) {
        return Response.status(Status.BAD_REQUEST)
          .entity(String.format("Invalid event order %s", orderBy))
          .build();
      }
    }
    
    if (StringUtils.isNotBlank(orderDir)) {
      orderDirection = EnumUtils.getEnum(EventProvider.EventOrderDirection.class, orderDir);
      if (orderDirection == null) {
        return Response.status(Status.BAD_REQUEST)
          .entity(String.format("Invalid event order direction %s", orderDir))
          .build();
      }
    }
    
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    
    List<Event> result = new ArrayList<>();
   
    for (EventProvider eventProvider : getEventProviders()) {
      result.addAll(eventProvider.listOrganizationEvents(organizationId, getDateTime(startBefore), getDateTime(startAfter), getDateTime(endBefore), getDateTime(endAfter), order, orderDirection, firstResult, maxResults));
    }
    
    return Response.ok(result)
      .build();
  }
  
  /* News */

  @Override
  public Response listOrganizationNews(String organizationIdParam, String publishedBefore, String publishedAfter,
      Integer firstResult, Integer maxResults) {
    
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    
    List<NewsArticle> result = new ArrayList<>();
   
    for (NewsProvider newsProvider : getNewsProviders()) {
      result.addAll(newsProvider.listOrganizationNews(organizationId, getDateTime(publishedBefore), getDateTime(publishedAfter), firstResult, maxResults));
    }
    
    return Response.ok(result)
      .build();
    
  }

  @Override
  public Response findOrganizationNewsArticle(String organizationIdParam, String newsArticleIdParam) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    NewsArticleId newsArticleId = toNewsArticleId(newsArticleIdParam);
    
    for (NewsProvider newsProvider : getNewsProviders()) {
      NewsArticle newsArticle = newsProvider.findOrganizationNewsArticle(organizationId, newsArticleId);
      if (newsArticle != null) {
        return Response.ok(newsArticle)
          .build();
      }
    }
    
    return Response.status(Status.NOT_FOUND)
      .build();
  }

  @Override
  public Response findOrganizationNewsArticleImage(String organizationIdParam, String newsArticleIdParam, String imageIdParam) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    NewsArticleId newsArticleId = toNewsArticleId(newsArticleIdParam);
    AttachmentId attachmentId = toAttachmentId(imageIdParam);
    
    for (NewsProvider newsProvider : getNewsProviders()) {
      Attachment attachment = newsProvider.findNewsArticleImage(organizationId, newsArticleId, attachmentId);
      if (attachment != null) {
        return Response.ok(attachment)
          .build();
      }
    }
    
    return Response.status(Status.NOT_FOUND)
      .build();
  }

  @Override
  public Response getOrganizationNewsArticleImageData(String organizationIdParam, String newsArticleIdParam, String imageIdParam, Integer size) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    NewsArticleId newsArticleId = toNewsArticleId(newsArticleIdParam);
    AttachmentId attachmentId = toAttachmentId(imageIdParam);
    
    for (NewsProvider newsProvider : getNewsProviders()) {
      AttachmentData attachmentData = newsProvider.getNewsArticleImageData(organizationId, newsArticleId, attachmentId, size);
      if (attachmentData != null) {
        try (InputStream stream = new ByteArrayInputStream(attachmentData.getData())) {
          return Response.ok(stream, attachmentData.getType())
              .build();
        } catch (IOException e) {
          logger.log(Level.SEVERE, FAILED_TO_STREAM_IMAGE_TO_CLIENT, e);
          return Response.status(Status.INTERNAL_SERVER_ERROR)
            .entity(INTERNAL_SERVER_ERROR)
            .build();
        }
      }
    }
    
    return Response.status(Status.NOT_FOUND)
      .build();
  }

  @Override
  public Response listOrganizationNewsArticleImages(String organizationIdParam, String newsArticleIdParam) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    NewsArticleId newsArticleId = toNewsArticleId(newsArticleIdParam);
    
    List<Attachment> result = new ArrayList<>();
   
    for (NewsProvider newsProvider : getNewsProviders()) {
      result.addAll(newsProvider.listNewsArticleImages(organizationId, newsArticleId));
    }
    
    return Response.ok(result)
      .build();
  }

  /* Banners */
  
  @Override
  public Response listOrganizationBanners(String organizationIdParam) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    
    List<Banner> result = new ArrayList<>();
   
    for (BannerProvider bannerProvider : getBannerProviders()) {
      result.addAll(bannerProvider.listOrganizationBanners(organizationId));
    }
    
    return Response.ok(result)
      .build();
  }

  @Override
  public Response findOrganizationBanner(String organizationIdParam, String bannerIdParam) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    BannerId bannerId = toBannerId(bannerIdParam);
    
    for (BannerProvider bannerProvider : getBannerProviders()) {
      Banner banner = bannerProvider.findOrganizationBanner(organizationId, bannerId);
      if (banner != null) {
        return Response.ok(banner)
          .build();
      }
    }
    
    return Response.status(Status.NOT_FOUND)
      .build();
  }

  @Override
  public Response listOrganizationBannerImages(String organizationIdParam, String bannerIdParam) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    BannerId bannerId = toBannerId(bannerIdParam);
    
    List<Attachment> result = new ArrayList<>();
   
    for (BannerProvider bannerProvider : getBannerProviders()) {
      result.addAll(bannerProvider.listOrganizationBannerImages(organizationId, bannerId));
    }
    
    return Response.ok(result)
      .build();
  }

  @Override
  public Response findOrganizationBannerImage(String organizationIdParam, String bannerIdParam, String imageIdParam) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    BannerId bannerId = toBannerId(bannerIdParam);
    AttachmentId attachmentId = toAttachmentId(imageIdParam);
    
    for (BannerProvider bannerProvider : getBannerProviders()) {
      Attachment attachment = bannerProvider.findBannerImage(organizationId, bannerId, attachmentId);
      if (attachment != null) {
        return Response.ok(attachment)
          .build();
      }
    }
    
    return Response.status(Status.NOT_FOUND)
      .build();
  }

  @Override
  public Response getOrganizationBannerImageData(String organizationIdParam, String bannerIdParam, String imageIdParam, Integer size) {
    OrganizationId organizationId = toOrganizationId(organizationIdParam);
    BannerId bannerId = toBannerId(bannerIdParam);
    AttachmentId attachmentId = toAttachmentId(imageIdParam);
    
    for (BannerProvider bannerProvider : getBannerProviders()) {
      AttachmentData attachmentData = bannerProvider.getBannerImageData(organizationId, bannerId, attachmentId, size);
      if (attachmentData != null) {
        try (InputStream stream = new ByteArrayInputStream(attachmentData.getData())) {
          return Response.ok(stream, attachmentData.getType())
              .build();
        } catch (IOException e) {
          logger.log(Level.SEVERE, FAILED_TO_STREAM_IMAGE_TO_CLIENT, e);
          return Response.status(Status.INTERNAL_SERVER_ERROR)
            .entity(INTERNAL_SERVER_ERROR)
            .build();
        }
      }
    }
    
    return Response.status(Status.NOT_FOUND)
      .build();
  }
  
  private BannerId toBannerId(String id) {
    if (StringUtils.isNotBlank(id)) {
      return new BannerId(KuntaApiConsts.IDENTIFIER_NAME, id);
    }
    
    return null;
  }
  
  private NewsArticleId toNewsArticleId(String id) {
    if (StringUtils.isNotBlank(id)) {
      return new NewsArticleId(KuntaApiConsts.IDENTIFIER_NAME, id);
    }
    
    return null;
  }

  private OrganizationId toOrganizationId(String id) {
    if (StringUtils.isNotBlank(id)) {
      return new OrganizationId(KuntaApiConsts.IDENTIFIER_NAME, id);
    }
    
    return null;
  }

  private ServiceId toServiceId(String id) {
    if (StringUtils.isNotBlank(id)) {
      return new ServiceId(KuntaApiConsts.IDENTIFIER_NAME, id);
    }
    
    return null;
  }

  private EventId toEventId(String id) {
    if (StringUtils.isNotBlank(id)) {
      return new EventId(KuntaApiConsts.IDENTIFIER_NAME, id);
    }
    
    return null;
  }

  private AttachmentId toAttachmentId(String id) {
    if (StringUtils.isNotBlank(id)) {
      return new AttachmentId(KuntaApiConsts.IDENTIFIER_NAME, id);
    }
    
    return null;
  }
  
  private OffsetDateTime getDateTime(String timeString) {
    if (StringUtils.isNotBlank(timeString)) {
      return OffsetDateTime.parse(timeString);
    }
    
    return null;
  }
  
  private List<OrganizationProvider> getOrganizationProviders() {
    List<OrganizationProvider> result = new ArrayList<>();
    
    Iterator<OrganizationProvider> iterator = organizationProviders.iterator();
    while (iterator.hasNext()) {
      result.add(iterator.next());
    }
    
    return Collections.unmodifiableList(result);
  }
  
  private List<ServiceProvider> getServiceProviders() {
    List<ServiceProvider> result = new ArrayList<>();
    
    Iterator<ServiceProvider> iterator = serviceProviders.iterator();
    while (iterator.hasNext()) {
      result.add(iterator.next());
    }
    
    return Collections.unmodifiableList(result);
  }
  
  private List<ServiceChannelProvider> getServiceChannelProviders() {
    List<ServiceChannelProvider> result = new ArrayList<>();
    
    Iterator<ServiceChannelProvider> iterator = serviceChannelProviders.iterator();
    while (iterator.hasNext()) {
      result.add(iterator.next());
    }
    
    return Collections.unmodifiableList(result);
  }
  
  private List<ServiceClassProvider> getServiceClassProviders() {
    List<ServiceClassProvider> result = new ArrayList<>();
    
    Iterator<ServiceClassProvider> iterator = serviceClassProviders.iterator();
    while (iterator.hasNext()) {
      result.add(iterator.next());
    }
    
    return Collections.unmodifiableList(result);
  }
  
  private List<EventProvider> getEventProviders() {
    List<EventProvider> result = new ArrayList<>();
    
    Iterator<EventProvider> iterator = eventProviders.iterator();
    while (iterator.hasNext()) {
      result.add(iterator.next());
    }
    
    return Collections.unmodifiableList(result);
  }
  
  private List<NewsProvider> getNewsProviders() {
    List<NewsProvider> result = new ArrayList<>();
    
    Iterator<NewsProvider> iterator = newsProviders.iterator();
    while (iterator.hasNext()) {
      result.add(iterator.next());
    }
    
    return Collections.unmodifiableList(result);
  }
  
  private List<BannerProvider> getBannerProviders() {
    List<BannerProvider> result = new ArrayList<>();
    
    Iterator<BannerProvider> iterator = bannerProviders.iterator();
    while (iterator.hasNext()) {
      result.add(iterator.next());
    }
    
    return Collections.unmodifiableList(result);
  }
}

