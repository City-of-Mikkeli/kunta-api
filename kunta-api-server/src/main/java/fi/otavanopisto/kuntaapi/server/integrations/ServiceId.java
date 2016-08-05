package fi.otavanopisto.kuntaapi.server.integrations;

public class ServiceId extends Id {
  
  public ServiceId() {
    super();
  }
  
  public ServiceId(String id) {
    super(id);
  }
  
  public ServiceId(String source, String id) {
    super(source, id);
  }
  
  @Override
  public IdType getType() {
    return IdType.SERVICE;
  }
  
}
