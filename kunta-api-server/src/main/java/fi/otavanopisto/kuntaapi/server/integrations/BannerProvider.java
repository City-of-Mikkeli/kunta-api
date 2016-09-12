package fi.otavanopisto.kuntaapi.server.integrations;

import java.util.List;

import fi.otavanopisto.kuntaapi.server.rest.model.Attachment;
import fi.otavanopisto.kuntaapi.server.rest.model.Banner;

/**
 * Interafce that describes a single banner provider
 * 
 * @author Antti Leppä
 */
public interface BannerProvider {

  /**
   * List banners in an organization
   * 
   * @param organizationId organization id
   * @return organization banner
   */
  public List<Banner> listOrganizationBanners(OrganizationId organizationId);
  
  /**
   * Finds a single organization banner
   * 
   * @param organizationId organization id
   * @param bannerId banner id
   * @return banner or null of not found
   */
  public Banner findOrganizationBanner(OrganizationId organizationId, BannerId bannerId);

  /**
   * Lists images attached to the banner
   * 
   * @param organizationId organization id
   * @param bannerId banner id
   * @return list of images attached to the banner
   */
  public List<Attachment> listOrganizationBannerImages(OrganizationId organizationId, BannerId bannerId);
  
  /**
   * Finds a banner image
   * 
   * @param organizationId organization id
   * @param bannerId banner id
   * @param attachmentId image id
   * @return an banner image or null if not found
   */
  public Attachment findBannerImage(OrganizationId organizationId, BannerId bannerId, AttachmentId attachmentId);
  
  /**
   * Returns data of banner image
   * 
   * @param organizationId organization id
   * @param bannerId banner id
   * @param attachmentId image id
   * @param size max size of image. Specify null for untouched
   * @return banner image data
   */
  public AttachmentData getBannerImageData(OrganizationId organizationId, BannerId bannerId, AttachmentId attachmentId, Integer size);
  
}
