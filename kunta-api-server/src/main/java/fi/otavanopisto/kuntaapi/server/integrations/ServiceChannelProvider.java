package fi.otavanopisto.kuntaapi.server.integrations;

import java.util.List;

import fi.otavanopisto.kuntaapi.server.rest.model.ServiceElectronicChannel;

/**
 * Provider for service channels
 * 
 * @author Antti Leppä
 */
public interface ServiceChannelProvider {

  /**
   * Lists service electronic channels
   * 
   * @param organizationId organization id
   * @param serviceId serviceId
   * @return List of service electronic channels
   */
  public List<ServiceElectronicChannel> listElectronicChannels(OrganizationId organizationId, ServiceId serviceId);

}
