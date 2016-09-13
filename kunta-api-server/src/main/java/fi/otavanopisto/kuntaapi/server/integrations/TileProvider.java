package fi.otavanopisto.kuntaapi.server.integrations;

import java.util.List;

import fi.otavanopisto.kuntaapi.server.rest.model.Attachment;
import fi.otavanopisto.kuntaapi.server.rest.model.Tile;

/**
 * Interafce that describes a single tile provider
 * 
 * @author Antti Leppä
 */
public interface TileProvider {

  /**
   * List tiles in an organization
   * 
   * @param organizationId organization id
   * @return organization tile
   */
  public List<Tile> listOrganizationTiles(OrganizationId organizationId);
  
  /**
   * Finds a single organization tile
   * 
   * @param organizationId organization id
   * @param tileId tile id
   * @return tile or null of not found
   */
  public Tile findOrganizationTile(OrganizationId organizationId, TileId tileId);

  /**
   * Lists images attached to the tile
   * 
   * @param organizationId organization id
   * @param tileId tile id
   * @return list of images attached to the tile
   */
  public List<Attachment> listOrganizationTileImages(OrganizationId organizationId, TileId tileId);
  
  /**
   * Finds a tile image
   * 
   * @param organizationId organization id
   * @param tileId tile id
   * @param attachmentId image id
   * @return an tile image or null if not found
   */
  public Attachment findTileImage(OrganizationId organizationId, TileId tileId, AttachmentId attachmentId);
  
  /**
   * Returns data of tile image
   * 
   * @param organizationId organization id
   * @param tileId tile id
   * @param attachmentId image id
   * @param size max size of image. Specify null for untouched
   * @return tile image data
   */
  public AttachmentData getTileImageData(OrganizationId organizationId, TileId tileId, AttachmentId attachmentId, Integer size);
  
}
