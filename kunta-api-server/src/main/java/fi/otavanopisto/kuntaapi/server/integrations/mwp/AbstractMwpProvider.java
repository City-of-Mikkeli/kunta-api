package fi.otavanopisto.kuntaapi.server.integrations.mwp;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.client.utils.URIBuilder;

import fi.otavanopisto.kuntaapi.server.controllers.IdentifierController;
import fi.otavanopisto.kuntaapi.server.images.ImageReader;
import fi.otavanopisto.kuntaapi.server.images.ImageScaler;
import fi.otavanopisto.kuntaapi.server.images.ImageWriter;
import fi.otavanopisto.kuntaapi.server.integrations.AttachmentData;
import fi.otavanopisto.kuntaapi.server.integrations.AttachmentId;
import fi.otavanopisto.kuntaapi.server.integrations.BinaryHttpClient;
import fi.otavanopisto.kuntaapi.server.integrations.IdController;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.BinaryHttpClient.BinaryResponse;
import fi.otavanopisto.kuntaapi.server.integrations.GenericHttpClient.Response;
import fi.otavanopisto.kuntaapi.server.persistence.model.Identifier;
import fi.otavanopisto.kuntaapi.server.rest.model.Attachment;
import fi.otavanopisto.mwp.client.ApiResponse;

/**
 * Abstract base class for mwp providers
 * 
 * @author Antti Leppä
 */
@SuppressWarnings ("squid:S3306")
public abstract class AbstractMwpProvider {
  
  @Inject
  private Logger logger;
  
  @Inject
  private MwpApi mwpApi;
  
  @Inject
  private BinaryHttpClient binaryHttpClient;

  @Inject
  private IdController idController;
  
  @Inject
  private IdentifierController identifierController;

  @Inject
  private ImageReader imageReader;

  @Inject
  private ImageWriter imageWriter;
  
  @Inject
  private ImageScaler imageScaler;
  
  protected AttachmentData scaleImage(AttachmentData imageData, Integer size) {
    BufferedImage bufferedImage = imageReader.readBufferedImage(imageData.getData());
    if (bufferedImage != null) {
      BufferedImage scaledImage = imageScaler.scaleMaxSize(bufferedImage, size);
      byte[] scaledImageData = imageWriter.writeBufferedImageAsPng(scaledImage);
      if (scaledImageData != null) {
        return new AttachmentData("image/png", scaledImageData);
      }
    }
    
    return null;
  }
  
  protected AttachmentData getImageData(String imageUrl) {
    URI uri;
    
    try {
      uri = new URIBuilder(imageUrl).build();
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, String.format("Invalid uri %s", imageUrl), e);
      return null;
    }
    
    return getImageData(uri);
  }

  protected AttachmentData getImageData(URI uri) {
    Response<BinaryResponse> response = binaryHttpClient.downloadBinary(uri);
    if (response.isOk()) {
      return new AttachmentData(response.getResponseEntity().getType(), response.getResponseEntity().getData());
    } else {
      logger.severe(String.format("Image download failed on [%d] %s", response.getStatus(), response.getMessage()));
    }
    
    return null;
  }
  
  protected AttachmentId getImageAttachmentId(Integer id) {
    AttachmentId mwpId = new AttachmentId(MwpConsts.IDENTIFIER_NAME, String.valueOf(id));
    AttachmentId kuntaApiId = idController.translateAttachmentId(mwpId, KuntaApiConsts.IDENTIFIER_NAME);
    
    if (kuntaApiId == null) {
      logger.info(String.format("Found new mwp attachment %d", id));
      Identifier newIdentifier = identifierController.createIdentifier(mwpId);
      kuntaApiId = new AttachmentId(KuntaApiConsts.IDENTIFIER_NAME, newIdentifier.getKuntaApiId());
    }
    
    return kuntaApiId;
  }
  
  protected Integer getMediaId(AttachmentId attachmentId) {
    AttachmentId mwpAttachmentId = idController.translateAttachmentId(attachmentId, MwpConsts.IDENTIFIER_NAME);
    if (mwpAttachmentId == null) {
      logger.info(String.format("Could not translate %s into mwp ", attachmentId.toString()));
      return null;
    }
    
    return NumberUtils.createInteger(mwpAttachmentId.getId());
  }

  protected fi.otavanopisto.mwp.client.model.Attachment findMedia(Integer mediaId) {
    if (mediaId == null) {
      return null;
    }
    
    ApiResponse<fi.otavanopisto.mwp.client.model.Attachment> response = mwpApi.getApi().wpV2MediaIdGet(String.valueOf(mediaId), null);
    if (!response.isOk()) {
      logger.severe(String.format("Finding media failed on [%d] %s", response.getStatus(), response.getMessage()));
    } else {
      return response.getResponse();
    }
    
    return null;
  }
  
  protected Attachment translateAttachment(fi.otavanopisto.mwp.client.model.Attachment featuredMedia) {
    Integer size = binaryHttpClient.getDownloadSize(featuredMedia.getSourceUrl());
    AttachmentId id = getImageAttachmentId(featuredMedia.getId());
    Attachment attachment = new Attachment();
    attachment.setContentType(featuredMedia.getMimeType());
    attachment.setId(id.getId());
    attachment.setSize(size != null ? size.longValue() : null);
    return attachment;
  }
  
}
