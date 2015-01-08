package org.t0tec.tutorials.ihe;

import org.t0tec.tutorials.ihe.persistence.Auditable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ITEM")
public class Item implements Auditable {

  @Id
  @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long id;
  @Version
  @Column(name = "OBJ_VERSION")
  private int version = 0;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "LAST_MODIFIED", nullable = false)
  private Date lastModified = new Date();
  @Column(name = "NAME", nullable = false)
  private String name;
  @Column(name = "IS_ACTIVE", nullable = false)
  private boolean isActive;

  public Item() {}

  public Item(String name, boolean isActive) {
    this.name = name;
    this.isActive = isActive;
  }

  @Override
  public Long getId() {
    return id;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public int getVersion() {
    return version;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + ", isActive()="
           + isActive() + ", getVersion()=" + getVersion() + ", getLastModified()="
           + getLastModified() + "]";
  }
}
