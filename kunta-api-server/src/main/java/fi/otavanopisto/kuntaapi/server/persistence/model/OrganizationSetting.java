package fi.otavanopisto.kuntaapi.server.persistence.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * JPA entity for storing system wide settings
 * 
 * @author Otavan Opisto
 */
@Entity
@Cacheable(true)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "organizationIdentifier_id", "settingKey" }) })
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class OrganizationSetting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "settingKey")
  @NotNull
  @NotEmpty
  private String key;

  @Column(nullable = false)
  @NotNull
  @NotEmpty
  @Lob
  private String value;
  
  @ManyToOne (optional = false)
  private Identifier organizationIdentifier;
  
  public Long getId() {
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
  
  public Identifier getOrganizationIdentifier() {
    return organizationIdentifier;
  }
  
  public void setOrganizationIdentifier(Identifier organizationIdentifier) {
    this.organizationIdentifier = organizationIdentifier;
  }
}