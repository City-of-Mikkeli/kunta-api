package fi.otavanopisto.kuntaapi.server.integrations;

public class ServiceClassId extends Id {

  public ServiceClassId() {
    super();
  }
  
  public ServiceClassId(String id) {
    super(id);
  }
  
  public ServiceClassId(String source, String id) {
    super(source, id);
  }
  
  @Override
  public IdType getType() {
    return IdType.SERVICE_CLASS;
  }
  
}
