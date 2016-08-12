package fi.otavanopisto.kuntaapi.server.controllers;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.integrations.Id;
import fi.otavanopisto.kuntaapi.server.integrations.IdType;
import fi.otavanopisto.kuntaapi.server.persistence.dao.IdentifierDAO;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;

@Dependent
public class IdentifierController {
  
  @Inject
  private IdentifierDAO identifierDAO;

  public Identifier createIdentifier(String type, String source, String sourceId) {
    String kuntaApiId = UUID.randomUUID().toString();
    return createIdentifier(type, kuntaApiId, source, sourceId);
  }

  public Identifier createIdentifier(IdType type, String source, String sourceId) {
    return createIdentifier(type.toString(), source, sourceId);
  }
  
  public Identifier createIdentifier(IdType type, String kuntaApiId, String source, String sourceId) {
    return createIdentifier(type.toString(), kuntaApiId, source, sourceId);
  }
  
  public Identifier createIdentifier(String type, String kuntaApiId, String source, String sourceId) {
    return identifierDAO.create(type, kuntaApiId, source, sourceId);
  }

  public Identifier createIdentifier(Id id) {
    return createIdentifier(id.getType().toString(), id.getSource(), id.getId());
  }

  public Identifier findIdentifierByTypeSourceAndId(String type, String source, String sourceId) {
    return identifierDAO.findByTypeSourceAndSourceId(type, source, sourceId);
  }

  public Identifier findIdentifierByTypeSourceAndId(IdType type, String source, String sourceId) {
    return findIdentifierByTypeSourceAndId(type.toString(), source, sourceId);
  }
  
  public Identifier findIdentifierById(Id id) {
    return findIdentifierByTypeSourceAndId(id.getType().toString(), id.getSource(), id.getId());
  }

  public Identifier findIdentifierByTypeSourceAndKuntaApiId(String type, String source, String kuntaApiId) {
    return identifierDAO.findByTypeSourceAndKuntaApiId(type, source, kuntaApiId);
  }

  public Identifier findIdentifierByTypeSourceAndKuntaApiId(IdType type, String source, String kuntaApiId) {
    return findIdentifierByTypeSourceAndKuntaApiId(type.toString(), source, kuntaApiId);
  }

  public List<Identifier> listIdentifiersByTypeAndSource(IdType type, String source, Integer firstResult, Integer maxResults) {
    return identifierDAO.listByTypeAndSource(type.toString(), source, firstResult, maxResults);
  }


}
