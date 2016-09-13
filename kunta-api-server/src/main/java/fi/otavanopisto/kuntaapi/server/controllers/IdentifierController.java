package fi.otavanopisto.kuntaapi.server.controllers;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.integrations.Id;
import fi.otavanopisto.kuntaapi.server.integrations.IdType;
import fi.otavanopisto.kuntaapi.server.persistence.dao.IdentifierDAO;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;

/**
 * Identifier controller
 * 
 * @author Antti Leppä
 */
@Dependent
public class IdentifierController {
  
  @Inject
  private IdentifierDAO identifierDAO;
  
  private IdentifierController() {
  }

  /**
   * Creates new identifier.
   * 
   * @param id identifier
   * @return created identifier
   */
  public Identifier createIdentifier(Id id) {
    String kuntaApiId = UUID.randomUUID().toString();
    return createIdentifier(id.getType().toString(), kuntaApiId, id.getSource(), id.getId());
  }
  
  /**
   * Creates new identifier and assigns a Kunta API id for it
   * 
   * @param id identifier
   * @param kuntaApiId Kunta API id to be assigned
   * @return created identifier
   */
  public Identifier createIdentifier(Id id, String kuntaApiId) {
    return createIdentifier(id.getType().toString(), kuntaApiId, id.getSource(), id.getId());
  }

  private Identifier createIdentifier(String type, String kuntaApiId, String source, String sourceId) {
    return identifierDAO.create(type, kuntaApiId, source, sourceId);
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
