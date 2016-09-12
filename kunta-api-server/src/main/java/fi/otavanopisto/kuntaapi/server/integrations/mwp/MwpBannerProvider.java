package fi.otavanopisto.kuntaapi.server.integrations.mwp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.otavanopisto.kuntaapi.server.controllers.IdentifierController;
import fi.otavanopisto.kuntaapi.server.integrations.AttachmentData;
import fi.otavanopisto.kuntaapi.server.integrations.AttachmentId;
import fi.otavanopisto.kuntaapi.server.integrations.BannerId;
import fi.otavanopisto.kuntaapi.server.integrations.BannerProvider;
import fi.otavanopisto.kuntaapi.server.integrations.IdController;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;
import fi.otavanopisto.kuntaapi.server.rest.model.Attachment;
import fi.otavanopisto.kuntaapi.server.rest.model.Banner;
import fi.otavanopisto.mwp.client.ApiResponse;
import fi.otavanopisto.mwp.client.model.Attachment.MediaTypeEnum;

/**
 * Banner provider for management wordpress
 * 
 * @author Antti Leppä
 */
@Dependent
public class MwpBannerProvider extends AbstractMwpProvider implements BannerProvider {
  
  @Inject
  private Logger logger;
  
  @Inject
  private MwpApi mwpApi;

  @Inject
  private IdController idController;
  
  @Inject
  private IdentifierController identifierController;

  private MwpBannerProvider() {
  }

  @Override
  public List<Banner> listOrganizationBanners(OrganizationId organizationId) {
    String context = null;
    Integer page = null;
    Integer perPage = null;
    String search = null;
    LocalDateTime after = null;
    LocalDateTime before = null;
    List<String> exclude = null;
    List<String> include = null;
    Integer offset = null;
    String order = null; 
    String orderby = null;
    String slug = null;
    String status = null;
    String filter = null;

    ApiResponse<List<fi.otavanopisto.mwp.client.model.Banner>> response = mwpApi.getApi().wpV2BannerGet(context, page, perPage, search, after,
        before, exclude, include, offset, order, orderby, slug, status, filter);

    if (!response.isOk()) {
      logger.severe(String.format("Banner listing failed on [%d] %s", response.getStatus(), response.getMessage()));
    } else {
      return translateBanners(response.getResponse());
    }
    
    return Collections.emptyList();
  }

  @Override
  public Banner findOrganizationBanner(OrganizationId organizationId, BannerId bannerId) {
    fi.otavanopisto.mwp.client.model.Banner mwpBanner = findBannerByBannerId(bannerId);
    if (mwpBanner != null) {
      return translateBanner(mwpBanner);
    }
  
    return null;
  }

  @Override
  public List<Attachment> listOrganizationBannerImages(OrganizationId organizationId, BannerId bannerId) {
    fi.otavanopisto.mwp.client.model.Banner mwpBanner = findBannerByBannerId(bannerId);
    if (mwpBanner != null) {
      Integer featuredMediaId = mwpBanner.getFeaturedMedia();
      if (featuredMediaId != null) {
        fi.otavanopisto.mwp.client.model.Attachment featuredMedia = findMedia(featuredMediaId);
        if ((featuredMedia != null) && (featuredMedia.getMediaType() == MediaTypeEnum.IMAGE)) {
          return Collections.singletonList(translateAttachment(featuredMedia));
        }
      }
    }
  
    return Collections.emptyList();
  }

  @Override
  public Attachment findBannerImage(OrganizationId organizationId, BannerId bannerId, AttachmentId attachmentId) {
    fi.otavanopisto.mwp.client.model.Banner banner = findBannerByBannerId(bannerId);
    if (banner != null) {
      Integer featuredMediaId = banner.getFeaturedMedia();
      if (featuredMediaId != null) {
        AttachmentId mwpAttachmentId = getImageAttachmentId(featuredMediaId);
        if (!idController.idsEqual(attachmentId, mwpAttachmentId)) {
          return null;
        }
        
        fi.otavanopisto.mwp.client.model.Attachment attachment = findMedia(featuredMediaId);
        if (attachment != null) {
          return translateAttachment(attachment);
        }
      }
    }
  
    return null;
  }

  @Override
  public AttachmentData getBannerImageData(OrganizationId organizationId, BannerId bannerId, AttachmentId attachmentId,
      Integer size) {
    
    Integer mediaId = getMediaId(attachmentId);
    if (mediaId == null) {
      return null;
    }
    
    fi.otavanopisto.mwp.client.model.Attachment featuredMedia = findMedia(mediaId);
    if (featuredMedia.getMediaType() == MediaTypeEnum.IMAGE) {
      AttachmentData imageData = getImageData(featuredMedia.getSourceUrl());
      
      if (size != null) {
        return scaleImage(imageData, size);
      } else {
        return imageData;
      }
      
    }
    
    return null;
  }

  private fi.otavanopisto.mwp.client.model.Banner findBannerByBannerId(BannerId bannerId) {
    BannerId kuntaApiId = idController.translateBannerId(bannerId, MwpConsts.IDENTIFIER_NAME);
    if (kuntaApiId == null) {
      logger.severe(String.format("Failed to convert %s into MWP id", bannerId.toString()));
      return null;
    }
    
    ApiResponse<fi.otavanopisto.mwp.client.model.Banner> response = mwpApi.getApi().wpV2BannerIdGet(kuntaApiId.getId(), null);
    if (!response.isOk()) {
      logger.severe(String.format("Finding banner failed on [%d] %s", response.getStatus(), response.getMessage()));
    } else {
      return response.getResponse();
    }
    
    return null;
  }

  private List<Banner> translateBanners(List<fi.otavanopisto.mwp.client.model.Banner> mwpBanners) {
    List<Banner> result = new ArrayList<>();
    
    for (fi.otavanopisto.mwp.client.model.Banner mwpBanner : mwpBanners) {
      result.add(translateBanner(mwpBanner));
    }
    
    return result;
  }

  private Banner translateBanner(fi.otavanopisto.mwp.client.model.Banner mwpBanner) {
    Banner banner = new Banner();
    
    BannerId mwpId = new BannerId(MwpConsts.IDENTIFIER_NAME, String.valueOf(mwpBanner.getId()));
    BannerId kuntaApiId = idController.translateBannerId(mwpId, KuntaApiConsts.IDENTIFIER_NAME);
    if (kuntaApiId == null) {
      logger.info(String.format("Found new news article %d", mwpBanner.getId()));
      Identifier newIdentifier = identifierController.createIdentifier(mwpId);
      kuntaApiId = new BannerId(KuntaApiConsts.IDENTIFIER_NAME, newIdentifier.getKuntaApiId());
    }
    
    banner.setContents(mwpBanner.getContent().getRendered());
    banner.setId(kuntaApiId.getId());
    banner.setLink(mwpBanner.getBannerLink());
    banner.setTitle(mwpBanner.getTitle().getRendered());
    
    return banner;
  }

}
