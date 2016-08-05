package fi.otavanopisto.kuntaapi.server.integrations;

import org.apache.commons.lang3.StringUtils;

public abstract class Id {

  protected Id() {
  }
  
  public Id(String id) {
    if (StringUtils.isNotBlank(id)) {
      String[] parts = StringUtils.split(id, ':');
      if (parts.length != 2) {
        this.source = parts[0];
        this.id = parts[1];
      }
    }
  }

  public Id(String source, String id) {
    super();
    this.source = source;
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getSource() {
    return source;
  }

  public abstract IdType getType();

  public String toString() {
    return String.format("%s:%s", source, id);
  };

  private String source;
  private String id;
}
