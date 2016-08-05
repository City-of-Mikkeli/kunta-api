package fi.otavanopisto.kuntaapi.server.integrations;

import java.util.Iterator;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

@Dependent
public class IdController {
  
  @Inject
  private Instance<IdProvider> idProviders;

  public OrganizationId translateOrganizationId(OrganizationId organizationId, String target) {
    if (StringUtils.equals(organizationId.getSource(), target)) {
      return organizationId;
    }
    
    IdProvider idProvider = getIdProvider(organizationId.getSource(), target);
    if (idProvider != null) {
      return idProvider.translate(organizationId, target);
    }
    
    return null;
  }

  public ServiceId translateServiceId(ServiceId serviceId, String target) {
    if (StringUtils.equals(serviceId.getSource(), target)) {
      return serviceId;
    }
    
    IdProvider idProvider = getIdProvider(serviceId.getSource(), target);
    if (idProvider != null) {
      return idProvider.translate(serviceId, target);
    }
    
    return null;
  }

  public ServiceClassId translateServiceClassId(ServiceClassId serviceClassId, String target) {
    if (StringUtils.equals(serviceClassId.getSource(), target)) {
      return serviceClassId;
    }
    
    IdProvider idProvider = getIdProvider(serviceClassId.getSource(), target);
    if (idProvider != null) {
      return idProvider.translate(serviceClassId, target);
    }
    
    return null;
  }
  
  private IdProvider getIdProvider(String source, String target) {
    Iterator<IdProvider> iterator = idProviders.iterator();
    while (iterator.hasNext()) {
      IdProvider idProvider = iterator.next();
      if (idProvider.canTranslate(source, target)) {
        return idProvider;
      }
    }
    
    return null;
  }
  
}
