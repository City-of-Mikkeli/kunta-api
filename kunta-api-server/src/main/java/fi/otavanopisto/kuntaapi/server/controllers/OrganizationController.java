package fi.otavanopisto.kuntaapi.server.controllers;

import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.persistence.dao.OrganizationIdentifierDAO;
import fi.otavanopisto.kuntaapi.server.persistence.model.OrganizationIdentifier;

@Dependent
public class OrganizationController {
  
  @Inject
  private OrganizationIdentifierDAO organizationIdentifierDAO;

  public OrganizationIdentifier createOrganizationIdentifier(String source, String sourceId) {
    String uuid = UUID.randomUUID().toString();
    return createOrganizationIdentifier(uuid, source, sourceId);
  }

  public OrganizationIdentifier createOrganizationIdentifier(String uuid, String source, String sourceId) {
    return organizationIdentifierDAO.create(uuid, source, sourceId);
  }

  public OrganizationIdentifier findOrganizationIdentifierBySourceAndId(String source, String sourceId) {
    return organizationIdentifierDAO.findBySourceAndSourceId(source, sourceId);
  }

  public OrganizationIdentifier findOrganizationIdentifierBySourceAndUuid(String source, String uuid) {
    return organizationIdentifierDAO.findBySourceAndUuid(source, uuid);
  }

}
