package fi.otavanopisto.kuntaapi.server.integrations;

public interface IdProvider {

  public boolean canTranslate(String source, String target);
  
  public OrganizationId translate(OrganizationId organizationId, String target);
  
  public ServiceId translate(ServiceId serviceId, String target);
  
  public ServiceClassId translate(ServiceClassId serviceClassId, String target);
  
}
