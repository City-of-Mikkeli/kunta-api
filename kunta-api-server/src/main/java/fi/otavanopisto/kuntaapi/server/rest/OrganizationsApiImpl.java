package fi.otavanopisto.kuntaapi.server.rest;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import fi.otavanopisto.kuntaapi.server.rest.model.Service;

@Path("/services")
public class ServicesApiImpl extends ServicesApi{

  @Override
  public Response createService(Service body) {
    return null;
  }

  @Override
  public Response deleteService(String serviceId) {
    return null;
  }

  @Override
  public Response deleteServiceData(String serviceId, String dataId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Response findService(String serviceId) {
    return null;
  }

  @Override
  public Response findServiceData(String serviceId, String dataId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Response listServiceDatas(String serviceId, String sourceId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Response listServices(String serviceTypeId) {
    return null;
  }

  @Override
  public Response updateService(String serviceId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Response updateServiceData(String serviceId, String dataId) {
    // TODO Auto-generated method stub
    return null;
  }

  
  
}

