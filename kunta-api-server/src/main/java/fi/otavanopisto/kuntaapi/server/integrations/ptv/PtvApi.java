package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import fi.otavanopisto.ptv.client.OrganizationApi;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.ptv.client.GeneralDescriptionApi;
import fi.otavanopisto.ptv.client.ServiceApi;
import fi.otavanopisto.ptv.client.ServiceChannelApi;

@Dependent
public class PtvApi {
  
  @Inject
  private PtvClient client;
  
  public OrganizationApi getOrganizationApi() {
    return new OrganizationApi(client);
  }

  public GeneralDescriptionApi getGeneralDescriptionApi() {
    return new GeneralDescriptionApi(client);
  }

  public ServiceApi getServiceApi() {
    return new ServiceApi(client);
  }

  public ServiceChannelApi getServiceChannelApi() {
    return new ServiceChannelApi(client);
  }
}
