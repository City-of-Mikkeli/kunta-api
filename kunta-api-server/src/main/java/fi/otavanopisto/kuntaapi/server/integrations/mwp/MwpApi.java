package fi.otavanopisto.kuntaapi.server.integrations.mwp;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.mwp.client.DefaultApi;

@Dependent
public class MwpApi {
  
  @Inject
  private MwpClient client;
  
  public DefaultApi getApi() {
    return new DefaultApi(client);
  }
  
}
