package org.t0tec.tutorials.tiu;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@org.hibernate.annotations.Entity(selectBeforeUpdate = true)
@Table(name = "ITEM")
public class Item {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long id;
  @Column(name = "NAME", nullable = false)
  private String name;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED", nullable = false, updatable = false)
  private Date created = new Date();
  @Version
  @Column(name = "OBJ_VERSION")
  private int version = 0;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "LAST_MODIFIED", nullable = false)
  private Date lastModified = new Date();

  @PrePersist
  protected void onCreated() {
    lastModified = created = new Date();
  }

  // TODO: lastModified not updating
  @PreUpdate
  protected void onLastModified() {
    lastModified = new Date();
  }

  public Item() {
    this.version = 0;
  }

  public Item(String name) {
    this.version = 0;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreated() {
    return created;
  }

  public int getVersion() {
    return version;
  }

  public Date getLastModified() {
    return lastModified;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + ", getCreated()="
        + getCreated() + ", getVersion()=" + getVersion() + ", getLastModified()="
        + getLastModified() + "]";
  }

}
