package fi.otavanopisto.kuntaapi.server.images;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.imageio.ImageIO;
import javax.inject.Inject;

/**
 * Image reader
 * 
 * @author Antti Leppä
 * @author Heikki Kurhinen
 */
@Dependent
public class ImageReader {
 
  @Inject
  private Logger logger;

  private ImageReader() {
  }
  
  /**
   * Reads image from input stream into BufferedImage
   * 
   * @param data image data
   * @return BufferedImage or null if image could not be read
   */
  public BufferedImage readBufferedImage(InputStream data) {
    try {
      return ImageIO.read(data);
    } catch (IOException e) {
      logger.log(Level.WARNING, "Could not read image", e);
    }
    
    return null;
  }
  
}
