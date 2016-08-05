package fi.otavanopisto.kuntaapi.server.integrations;

public class OrganizationId extends Id {
  
  public OrganizationId() {
    super();
  }
  
  public OrganizationId(String id) {
    super(id);
  }
  
  public OrganizationId(String source, String id) {
    super(source, id);
  }
  
  @Override
  public IdType getType() {
    return IdType.ORGANIZATION;
  }
  
}
