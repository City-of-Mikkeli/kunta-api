package fi.otavanopisto.kuntaapi.server.persistence.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * JPA entity representing mapping for external source id to Kunta API Id
 * 
 * @author Otavan Opisto
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "type", "source", "sourceId", "kuntaApiId" }) })
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Identifier {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotNull
  @NotEmpty
  private String kuntaApiId;

  @Column(nullable = false)
  @NotNull
  @NotEmpty
  private String type;

  @Column(nullable = false)
  @NotNull
  @NotEmpty
  private String source;

  @Column(nullable = false)
  @NotNull
  @NotEmpty
  private String sourceId;
  
  public Long getId() {
    return id;
  }

  public String getKuntaApiId() {
    return kuntaApiId;
  }
  
  public void setKuntaApiId(String kuntaApiId) {
    this.kuntaApiId = kuntaApiId;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getSourceId() {
    return sourceId;
  }

  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
}
