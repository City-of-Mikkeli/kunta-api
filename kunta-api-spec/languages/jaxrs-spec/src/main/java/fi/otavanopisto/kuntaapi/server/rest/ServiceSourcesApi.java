package fi.otavanopisto.kuntaapi.server.rest;

import fi.otavanopisto.kuntaapi.server.rest.model.BadRequest;
import fi.otavanopisto.kuntaapi.server.rest.model.ServiceSource;
import fi.otavanopisto.kuntaapi.server.rest.model.Forbidden;
import fi.otavanopisto.kuntaapi.server.rest.model.InternalServerError;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;

@Path("/serviceSources")

@Api(description = "the serviceSources API")
@Consumes({ "application/json;charset=utf-8" })
@Produces({ "application/json;charset=utf-8" })
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2016-09-01T18:51:49.800+03:00")

public abstract class ServiceSourcesApi extends AbstractApi {

    @GET
    @Path("/{serviceSourceId}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "Find a service by id", notes = "Returns single service by it's unique id. ", response = ServiceSource.class, tags={ "Services", "Sources", "Services Sources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns a single service source", response = ServiceSource.class),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = ServiceSource.class),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = ServiceSource.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ServiceSource.class) })
    public abstract Response findServiceSource(@PathParam("serviceSourceId") String serviceSourceId);

    @GET
    
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @ApiOperation(value = "List service sources", notes = "Returns list of service sources. ", response = ServiceSource.class, responseContainer = "List", tags={ "Services", "Sources", "Services Sources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "An array of service sources", response = ServiceSource.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid request was sent to the server", response = ServiceSource.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Attempted to make a call with unauthorized client", response = ServiceSource.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal server error", response = ServiceSource.class, responseContainer = "List") })
    public abstract Response listServiceSources();

}

