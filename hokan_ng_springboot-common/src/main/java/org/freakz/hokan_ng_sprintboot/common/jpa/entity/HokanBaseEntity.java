package org.freakz.hokan_ng_sprintboot.common.jpa.entity;


import org.freakz.hokan_ng_sprintboot.common.util.StaticStrings;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by AirioP on 21.8.2014.
 */
@MappedSuperclass
public class HokanBaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private long id;

  @Column(name = "CREATED")
  private Date created;

  @Column(name = "MODIFIED")
  private Date modified;

  @Column(name = "CREATOR_NAME")
  private String creatorName;

  public HokanBaseEntity() {
    this.created = new Date();
    this.modified = created;
    this.creatorName = StaticStrings.UNKNOWN;
  }

  public HokanBaseEntity(String creatorName) {
    this();
    this.creatorName = creatorName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getModified() {
    return modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }

  public String getCreatorName() {
    return creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  @PreUpdate
  public void preUpdate() {
    this.modified = new Date();
  }

  @PrePersist
  public void prePersist() {
    Date now = new Date();
    this.created = now;
    this.modified = now;
  }

}
