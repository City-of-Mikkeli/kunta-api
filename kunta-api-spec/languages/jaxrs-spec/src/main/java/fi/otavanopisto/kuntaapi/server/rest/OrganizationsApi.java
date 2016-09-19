package fi.otavanopisto.kuntaapi.server.rest;

import fi.otavanopisto.kuntaapi.server.rest.model.OrganizationSetting;
import fi.otavanopisto.kuntaapi.server.rest.model.BadRequest;
import fi.otavanopisto.kuntaapi.server.rest.model.Forbidden;
import fi.otavanopisto.kuntaapi.server.rest.model.InternalServerError;
import fi.otavanopisto.kuntaapi.server.rest.model.Service;
import fi.otavanopisto.kuntaapi.server.rest.model.NotImplemented;
import fi.otavanopisto.kuntaapi.server.rest.model.NotFound;
import fi.otavanopisto.kuntaapi.server.rest.model.NewsArticle;
import fi.otavanopisto.kuntaapi.server.rest.model.Attachment;
import fi.otavanopisto.kuntaapi.server.rest.model.Event;
import fi.otavanopisto.kuntaapi.server.rest.model.Tile;
import fi.otavanopisto.kuntaapi.server.rest.model.Banner;
import fi.otavanopisto.kuntaapi.server.rest.model.Organization;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceClass;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceElectronicChannel;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;

@Path("/organizations")

@Api(description = "the organizations API")
@Consumes({ "application/json;charset=utf-8" })
@Produces({ "application/json;charset=utf-8" })
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2016-09-19T19:50:53.030+03:00")

public abstract class OrganizationsApi extends AbstractApi {

    @POST
    @Path("/{organizationId}/settings/")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Creates organization setting", notes = "Creates organization setting ", response = OrganizationSetting.class, tags={ "Settings",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns create organization setting", response = OrganizationSetting.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = OrganizationSetting.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = OrganizationSetting.class),
        @ApiResponse(code = 500, message = "Internal server error", response = OrganizationSetting.class) })
    public abstract Response createOrganizationSetting(@PathParam("organizationId") String organizationId,OrganizationSetting setting);

    @POST
    @Path("/{organizationId}/services")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Create a service", notes = "Creates new service for the organization ", response = Service.class, responseContainer = "List", tags={ "Services",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "An array of services", response = Service.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Service.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Service.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not found", response = Service.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = Service.class, responseContainer = "List"),
        @ApiResponse(code = 501, message = "Returned when selected service does support modification of data", response = Service.class, responseContainer = "List") })
    public abstract Response createService(@PathParam("organizationId") String organizationId,Service body);

    @DELETE
    @Path("/{organizationId}/settings/{settingId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Deletes an organization setting", notes = "Deletes an organization setting ", response = void.class, tags={ "Settings",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Successful removal", response = void.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = void.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = void.class),
        @ApiResponse(code = 500, message = "Internal server error", response = void.class) })
    public abstract Response deleteOrganizationSetting(@PathParam("organizationId") String organizationId,@PathParam("settingId") String settingId);

    @DELETE
    @Path("/{organizationId}/services/{serviceId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Delete a service", notes = "Delete a single municipal service ", response = void.class, tags={ "Services",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Empty response indicating a succesfull removal", response = void.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = void.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = void.class),
        @ApiResponse(code = 404, message = "Not found", response = void.class),
        @ApiResponse(code = 500, message = "Internal server error", response = void.class) })
    public abstract Response deleteService(@PathParam("organizationId") String organizationId,@PathParam("serviceId") String serviceId);

    @GET
    @Path("/{organizationId}/banners/{bannerId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Finds organizations banner", notes = "Finds single organization banner ", response = NewsArticle.class, tags={ "Banners",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a single organization banner", response = NewsArticle.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = NewsArticle.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = NewsArticle.class),
        @ApiResponse(code = 500, message = "Internal server error", response = NewsArticle.class) })
    public abstract Response findOrganizationBanner(@PathParam("organizationId") String organizationId,@PathParam("bannerId") String bannerId);

    @GET
    @Path("/{organizationId}/banners/{bannerId}/images/{imageId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Returns a single organiztion banner image", notes = "Returns a single organiztion banner image ", response = Attachment.class, tags={ "News",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns an organization banner image", response = Attachment.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Attachment.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Attachment.class),
        @ApiResponse(code = 500, message = "Internal server error", response = Attachment.class) })
    public abstract Response findOrganizationBannerImage(@PathParam("organizationId") String organizationId,@PathParam("bannerId") String bannerId,@PathParam("imageId") String imageId);

    @GET
    @Path("/{organizationId}/events/{eventId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Returns organizations event by id", notes = "Returns organizations event by id ", response = Event.class, tags={ "Events",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a single event", response = Event.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Event.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Event.class),
        @ApiResponse(code = 500, message = "Internal server error", response = Event.class) })
    public abstract Response findOrganizationEvent(@PathParam("organizationId") String organizationId,@PathParam("eventId") String eventId);

    @GET
    @Path("/{organizationId}/events/{eventId}/images/{imageId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Returns an event image", notes = "Returns an event image  ", response = Attachment.class, tags={ "Events",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns an event image", response = Attachment.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Attachment.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Attachment.class),
        @ApiResponse(code = 500, message = "Internal server error", response = Attachment.class) })
    public abstract Response findOrganizationEventImage(@PathParam("organizationId") String organizationId,@PathParam("eventId") String eventId,@PathParam("imageId") String imageId);

    @GET
    @Path("/{organizationId}/news/{newsArticleId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Finds organizations news article", notes = "Finds organizations single news article ", response = NewsArticle.class, tags={ "News",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a single news article", response = NewsArticle.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = NewsArticle.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = NewsArticle.class),
        @ApiResponse(code = 500, message = "Internal server error", response = NewsArticle.class) })
    public abstract Response findOrganizationNewsArticle(@PathParam("organizationId") String organizationId,@PathParam("newsArticleId") String newsArticleId);

    @GET
    @Path("/{organizationId}/news/{newsArticleId}/images/{imageId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Returns an news article image", notes = "Returns an news article image ", response = Attachment.class, tags={ "News",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns an news article image", response = Attachment.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Attachment.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Attachment.class),
        @ApiResponse(code = 500, message = "Internal server error", response = Attachment.class) })
    public abstract Response findOrganizationNewsArticleImage(@PathParam("organizationId") String organizationId,@PathParam("newsArticleId") String newsArticleId,@PathParam("imageId") String imageId);

    @GET
    @Path("/{organizationId}/settings/{settingId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Finds organizations setting", notes = "Finds single organization setting ", response = OrganizationSetting.class, tags={ "Settings",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a single organization setting", response = OrganizationSetting.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = OrganizationSetting.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = OrganizationSetting.class),
        @ApiResponse(code = 500, message = "Internal server error", response = OrganizationSetting.class) })
    public abstract Response findOrganizationSetting(@PathParam("organizationId") String organizationId,@PathParam("settingId") String settingId);

    @GET
    @Path("/{organizationId}/tiles/{tileId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Finds organizations tile", notes = "Finds single organization tile ", response = Tile.class, tags={ "Tiles",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a single organization tile", response = Tile.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Tile.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Tile.class),
        @ApiResponse(code = 500, message = "Internal server error", response = Tile.class) })
    public abstract Response findOrganizationTile(@PathParam("organizationId") String organizationId,@PathParam("tileId") String tileId);

    @GET
    @Path("/{organizationId}/tiles/{tileId}/images/{imageId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Returns a single organiztion tile image", notes = "Returns a single organiztion tile image ", response = Attachment.class, tags={ "Tiles",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns an organization tile image", response = Attachment.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Attachment.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Attachment.class),
        @ApiResponse(code = 500, message = "Internal server error", response = Attachment.class) })
    public abstract Response findOrganizationTileImage(@PathParam("organizationId") String organizationId,@PathParam("tileId") String tileId,@PathParam("imageId") String imageId);

    @GET
    @Path("/{organizationId}/services/{serviceId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Find a service by id", notes = "Returns single service by it's unique id. ", response = Service.class, tags={ "Services",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a single municipal service", response = Service.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Service.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Service.class),
        @ApiResponse(code = 404, message = "Not found", response = Service.class),
        @ApiResponse(code = 500, message = "Internal server error", response = Service.class) })
    public abstract Response findService(@PathParam("organizationId") String organizationId,@PathParam("serviceId") String serviceId);

    @GET
    @Path("/{organizationId}/banners/{bannerId}/images/{imageId}/data")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/octet-stream" })
    @ApiOperation(value = "Returns an organization banner image data", notes = "Returns an organization banner image data ", response = byte[].class, tags={ "News",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns an organization banner image data", response = byte[].class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = byte[].class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = byte[].class),
        @ApiResponse(code = 500, message = "Internal server error", response = byte[].class) })
    public abstract Response getOrganizationBannerImageData(@PathParam("organizationId") String organizationId,@PathParam("bannerId") String bannerId,@PathParam("imageId") String imageId,@QueryParam("size") Integer size);

    @GET
    @Path("/{organizationId}/events/{eventId}/images/{imageId}/data")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/octet-stream" })
    @ApiOperation(value = "Returns an event image data", notes = "Returns an event image data ", response = byte[].class, tags={ "Events",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns an event image data", response = byte[].class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = byte[].class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = byte[].class),
        @ApiResponse(code = 500, message = "Internal server error", response = byte[].class) })
    public abstract Response getOrganizationEventImageData(@PathParam("organizationId") String organizationId,@PathParam("eventId") String eventId,@PathParam("imageId") String imageId,@QueryParam("size") Integer size);

    @GET
    @Path("/{organizationId}/news/{newsArticleId}/images/{imageId}/data")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/octet-stream" })
    @ApiOperation(value = "Returns a news article image data", notes = "Returns a news article image data ", response = byte[].class, tags={ "News",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a news article image data", response = byte[].class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = byte[].class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = byte[].class),
        @ApiResponse(code = 500, message = "Internal server error", response = byte[].class) })
    public abstract Response getOrganizationNewsArticleImageData(@PathParam("organizationId") String organizationId,@PathParam("newsArticleId") String newsArticleId,@PathParam("imageId") String imageId,@QueryParam("size") Integer size);

    @GET
    @Path("/{organizationId}/tiles/{tileId}/images/{imageId}/data")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/octet-stream" })
    @ApiOperation(value = "Returns an organization tile image data", notes = "Returns an organization tile image data ", response = byte[].class, tags={ "Tiles",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns an organization tile image data", response = byte[].class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = byte[].class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = byte[].class),
        @ApiResponse(code = 500, message = "Internal server error", response = byte[].class) })
    public abstract Response getOrganizationTileImageData(@PathParam("organizationId") String organizationId,@PathParam("tileId") String tileId,@PathParam("imageId") String imageId,@QueryParam("size") Integer size);

    @GET
    @Path("/{organizationId}/banners/{bannerId}/images")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Returns a list of organization banner images", notes = "Returns a list of organization banner images ", response = Attachment.class, responseContainer = "List", tags={ "Banners",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns list of organization banner images", response = Attachment.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Attachment.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Attachment.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = Attachment.class, responseContainer = "List") })
    public abstract Response listOrganizationBannerImages(@PathParam("organizationId") String organizationId,@PathParam("bannerId") String bannerId);

    @GET
    @Path("/{organizationId}/banners")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Lists organizations banners", notes = "Lists organizations banners ", response = Banner.class, responseContainer = "List", tags={ "Banners",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a list of banners", response = Banner.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Banner.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Banner.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = Banner.class, responseContainer = "List") })
    public abstract Response listOrganizationBanners(@PathParam("organizationId") String organizationId);

    @GET
    @Path("/{organizationId}/events/{eventId}/images")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Returns list of event images", notes = "Returns list of event images ", response = Attachment.class, responseContainer = "List", tags={ "Events",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a list of event images", response = Attachment.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Attachment.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Attachment.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = Attachment.class, responseContainer = "List") })
    public abstract Response listOrganizationEventImages(@PathParam("organizationId") String organizationId,@PathParam("eventId") String eventId);

    @GET
    @Path("/{organizationId}/events")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Lists organizations events", notes = "Lists organizations events ", response = Event.class, responseContainer = "List", tags={ "Events",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a list of events", response = Event.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Event.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Event.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = Event.class, responseContainer = "List") })
    public abstract Response listOrganizationEvents(@PathParam("organizationId") String organizationId,@QueryParam("startBefore") String startBefore,@QueryParam("startAfter") String startAfter,@QueryParam("endBefore") String endBefore,@QueryParam("endAfter") String endAfter,@QueryParam("firstResult") Integer firstResult,@QueryParam("maxResults") Integer maxResults,@QueryParam("orderBy") String orderBy,@QueryParam("orderDir") String orderDir);

    @GET
    @Path("/{organizationId}/news")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Lists organizations news", notes = "Lists organizations news ", response = NewsArticle.class, responseContainer = "List", tags={ "News",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a list of news", response = NewsArticle.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = NewsArticle.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = NewsArticle.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = NewsArticle.class, responseContainer = "List") })
    public abstract Response listOrganizationNews(@PathParam("organizationId") String organizationId,@QueryParam("publishedBefore") String publishedBefore,@QueryParam("publishedAfter") String publishedAfter,@QueryParam("firstResult") Integer firstResult,@QueryParam("maxResults") Integer maxResults);

    @GET
    @Path("/{organizationId}/news/{newsArticleId}/images")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Returns list of news article images", notes = "Returns list of news article images ", response = Attachment.class, responseContainer = "List", tags={ "News",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a news article images", response = Attachment.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Attachment.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Attachment.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = Attachment.class, responseContainer = "List") })
    public abstract Response listOrganizationNewsArticleImages(@PathParam("organizationId") String organizationId,@PathParam("newsArticleId") String newsArticleId);

    @GET
    @Path("/{organizationId}/settings/")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Lists organizations settings", notes = "Lists organizations settings ", response = OrganizationSetting.class, responseContainer = "List", tags={ "Settings",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a list of settings", response = OrganizationSetting.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = OrganizationSetting.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = OrganizationSetting.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = OrganizationSetting.class, responseContainer = "List") })
    public abstract Response listOrganizationSettings(@PathParam("organizationId") String organizationId,@QueryParam("key") String key);

    @GET
    @Path("/{organizationId}/tiles/{tileId}/images")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Returns a list of organization tile images", notes = "Returns a list of organization tile images ", response = Attachment.class, responseContainer = "List", tags={ "Tiles",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns list of organization tile images", response = Attachment.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Attachment.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Attachment.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = Attachment.class, responseContainer = "List") })
    public abstract Response listOrganizationTileImages(@PathParam("organizationId") String organizationId,@PathParam("tileId") String tileId);

    @GET
    @Path("/{organizationId}/tiles")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Lists organizations tiles", notes = "Lists organizations tiles ", response = Tile.class, responseContainer = "List", tags={ "Tiles",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a list of tiles", response = Tile.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Tile.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Tile.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = Tile.class, responseContainer = "List") })
    public abstract Response listOrganizationTiles(@PathParam("organizationId") String organizationId);

    @GET
    
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "List organizations", notes = "List organizations", response = Organization.class, responseContainer = "List", tags={ "Organizations",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "An array of organizations", response = Organization.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Organization.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Organization.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = Organization.class, responseContainer = "List") })
    public abstract Response listOrganizations(@QueryParam("businessName") String businessName,@QueryParam("businessCode") String businessCode);

    @GET
    @Path("/{organizationId}/serviceClasses/")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "List service classes for an organization", notes = "Returns list of organization's service classes ", response = ServiceClass.class, responseContainer = "List", tags={ "Services", "Service Categories",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a list of organization's service classes", response = ServiceClass.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = ServiceClass.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = ServiceClass.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not found", response = ServiceClass.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = ServiceClass.class, responseContainer = "List") })
    public abstract Response listServiceClasses(@PathParam("organizationId") String organizationId);

    @GET
    @Path("/{organizationId}/services/{serviceId}/electronicChannels")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "List service electornic channels", notes = "Lists service electronic channels ", response = ServiceElectronicChannel.class, tags={ "Services", "ServiceChannels",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of service electornic channels", response = ServiceElectronicChannel.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = ServiceElectronicChannel.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = ServiceElectronicChannel.class),
        @ApiResponse(code = 404, message = "Not found", response = ServiceElectronicChannel.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ServiceElectronicChannel.class) })
    public abstract Response listServiceElectornicChannels(@PathParam("organizationId") String organizationId,@PathParam("serviceId") String serviceId);

    @GET
    @Path("/{organizationId}/services")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "List services", notes = "Lists organization's services ", response = Service.class, responseContainer = "List", tags={ "Services",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "An array of services", response = Service.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Service.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Service.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Not found", response = Service.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = Service.class, responseContainer = "List") })
    public abstract Response listServices(@PathParam("organizationId") String organizationId,@QueryParam("serviceClassId") String serviceClassId);

    @PUT
    @Path("/{organizationId}/settings/{settingId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Updates an organization setting", notes = "Updates an organization setting ", response = OrganizationSetting.class, tags={ "Settings",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a single an updated organization setting", response = OrganizationSetting.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = OrganizationSetting.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = OrganizationSetting.class),
        @ApiResponse(code = 500, message = "Internal server error", response = OrganizationSetting.class) })
    public abstract Response updateOrganizationSetting(@PathParam("organizationId") String organizationId,@PathParam("settingId") String settingId);

    @PUT
    @Path("/{organizationId}/services/{serviceId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Update a service", notes = "Updates a single municipal service ", response = Service.class, tags={ "Services" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Created service", response = Service.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = Service.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = Service.class),
        @ApiResponse(code = 404, message = "Not found", response = Service.class),
        @ApiResponse(code = 500, message = "Internal server error", response = Service.class),
        @ApiResponse(code = 501, message = "Returned when endpoint does support modification of data", response = Service.class) })
    public abstract Response updateService(@PathParam("organizationId") String organizationId,@PathParam("serviceId") String serviceId);

}

