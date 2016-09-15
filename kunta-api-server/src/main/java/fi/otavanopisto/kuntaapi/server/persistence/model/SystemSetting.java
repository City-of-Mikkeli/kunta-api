package fi.otavanopisto.kuntaapi.server.persistence.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * JPA entity for storing system wide settings
 * 
 * @author Otavan Opisto
 */
@Entity
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Indexed
public class SystemSetting {

  @Id
  @GeneratedValue (strategy = GenerationType.TABLE, generator="systemsetting-uuid")
  @GenericGenerator (name="systemsetting-uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @DocumentId
  private String id;

  @Column(nullable = false, unique = true, name = "settingKey")
  @NotNull
  @NotEmpty
  @Field (analyze = Analyze.NO)
  private String key;

  @Column(nullable = false)
  @NotNull
  @NotEmpty
  @Lob
  private String value;
  
  public String getId() {
    return id;
  }
  
  public String getKey() {
    return key;
  }
  
  public void setKey(String key) {
    this.key = key;
  }
  
  public String getValue() {
    return value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
}
