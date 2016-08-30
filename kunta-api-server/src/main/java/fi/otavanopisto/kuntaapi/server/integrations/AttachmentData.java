package fi.otavanopisto.kuntaapi.server.integrations;

import java.io.InputStream;

/**
 * Attachment data
 * 
 * @author Otavan Opisto
 */
public class AttachmentData {

  private String type;
  private InputStream data;

  /**
   * Attachment constructor
   */
  public AttachmentData() {
    super();
  }

  /**
   * Attachment constructor
   * 
   * @param type attachment content type
   * @param data attachment data as input stream
   */
  public AttachmentData(String type, InputStream data) {
    super();
    this.type = type;
    this.data = data;
  }

  public InputStream getData() {
    return data;
  }

  public String getType() {
    return type;
  }
}
