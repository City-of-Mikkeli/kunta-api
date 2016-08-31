package fi.otavanopisto.kuntaapi.server.images;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Image scaler 
 * 
 * @author Antti Leppä
 * @author Heikki Kurhinen
 */
@Dependent
public class ImageScaler {
 
  @Inject
  private Logger logger;

  private ImageScaler() {
  }
  
  /**
   * Down scales image into max width / height
   * 
   * @param originalImage original image
   * @param maxSize max width / height of new image
   * @return scaled image
   */
  public BufferedImage scaleMaxSize(BufferedImage originalImage, int maxSize) {
    return scaleMaxSize(originalImage, maxSize, null);
  }
  
  /**
   * Down scales image into max width / height. Accepts image observer
   * 
   * @param originalImage original image
   * @param maxSize max width / height of new image
   * @param imageObserver image observer
   * @return scaled image
   */
  public BufferedImage scaleMaxSize(BufferedImage originalImage, int maxSize, ImageObserver imageObserver) {
    int width = maxSize;
    int height = maxSize;
    
    if ((originalImage.getHeight() < maxSize) && (originalImage.getWidth() < maxSize)) {
      return originalImage;
    }

    if ((originalImage.getHeight() / maxSize) > (originalImage.getWidth() / maxSize))
      width = -1;
    else
      height = -1;

    Image scaledInstance = originalImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
    
    if (imageObserver != null) {
      scaledInstance.getWidth(imageObserver);
      scaledInstance.getHeight(imageObserver);        
    } else {
      scaledInstance.getWidth(null);
      scaledInstance.getHeight(null);
    }
    
    return toBufferedImage(scaledInstance);
  }

  private BufferedImage toBufferedImage(Image image) {
    if (image instanceof BufferedImage) {
      return (BufferedImage) image;
    } else {
      // ToolKitImages are not part of official JDK, so we need to use reflection to obtain the image
      try {
        Method getBufferedImageMethod = image.getClass().getMethod("getBufferedImage");
        return (BufferedImage) getBufferedImageMethod.invoke(image);
      } catch (Exception e) {
        logger.log(Level.SEVERE, "Failed to retrieve buffered image", e);
      }
    }
    
    return null;
  }
  
}
