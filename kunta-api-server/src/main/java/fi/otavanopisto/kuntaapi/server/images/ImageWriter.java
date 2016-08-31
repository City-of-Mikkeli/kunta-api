package fi.otavanopisto.kuntaapi.server.images;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.imageio.ImageIO;
import javax.inject.Inject;

/**
 * Image writer
 * 
 * @author Antti Leppä
 * @author Heikki Kurhinen
 */
@Dependent
public class ImageWriter {
 
  @Inject
  private Logger logger;

  private ImageWriter() {
  }
  
  /**
   * Writes buffered image as PNG byte array
   * 
   * @param image image
   * @return image data as byte array or null when writing has failed
   */
  @SuppressWarnings ("squid:S1168")
  public byte[] writeBufferedImageAsPng(BufferedImage image) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
    try {
      if (ImageIO.write(image, "png", outputStream)) {
        outputStream.flush();
        outputStream.close();
        return outputStream.toByteArray();
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Failed to write buffered image as png", e);
    }
    
    return null;
  }
  
}
