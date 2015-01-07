package org.t0tec.tutorials.bs;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "ITEM")
public class Item {

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

  public Item(String name) {
    this.name = name;
  }

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
