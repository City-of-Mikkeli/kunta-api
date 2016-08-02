package fi.otavanopisto.kuntaapi.server.rest;

import fi.otavanopisto.kuntaapi.server.rest.model.BadRequest;
import fi.otavanopisto.kuntaapi.server.rest.model.Forbidden;
import fi.otavanopisto.kuntaapi.server.rest.model.InternalServerError;
import fi.otavanopisto.kuntaapi.server.rest.model.NotImplemented;
import fi.otavanopisto.kuntaapi.server.rest.model.NotFound;
import javax.ws.rs.core.Response;

public abstract class AbstractApi {

  protected Response createBadRequest(String message) {
    BadRequest entity = new BadRequest();
    entity.setCode(Response.Status.BAD_REQUEST.getStatusCode());
    entity.setMessage(message);
    return Response
      .status(Response.Status.BAD_REQUEST)
      .entity(entity)
      .build();
  }
  
  protected Response createNotFound(String message) {
    NotFound entity = new NotFound();
    entity.setCode(Response.Status.NOT_FOUND.getStatusCode());
    entity.setMessage(message);
    return Response
      .status(Response.Status.NOT_FOUND)
      .entity(entity)
      .build();
  }
  
  protected Response createNotImplemented(String message) {
    NotImplemented entity = new NotImplemented();
    entity.setCode(Response.Status.NOT_IMPLEMENTED.getStatusCode());
    entity.setMessage(message);
    return Response
      .status(Response.Status.NOT_IMPLEMENTED)
      .entity(entity)
      .build();
  }
  
  protected Response createInternalServerError(String message) {
    InternalServerError entity = new InternalServerError();
    entity.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    entity.setMessage(message);
    return Response
      .status(Response.Status.INTERNAL_SERVER_ERROR)
      .entity(entity)
      .build();
  }
  
  protected Response createForbidden(String message) {
    Forbidden entity = new Forbidden();
    entity.setCode(Response.Status.FORBIDDEN.getStatusCode());
    entity.setMessage(message);
    return Response
      .status(Response.Status.FORBIDDEN)
      .entity(entity)
      .build();
  }

}

