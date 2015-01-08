package org.t0tec.tutorials.ddf;

import org.hibernate.annotations.ForeignKey;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "ITEM")
@org.hibernate.annotations.FilterDef(
    name="limitItemsByUserRank",
    parameters = {
        @org.hibernate.annotations.ParamDef(
            name = "currentUserRank", type = "int"
        )
    }
)
@org.hibernate.annotations.Filter(
    name = "limitItemsByUserRank",
    condition = ":currentUserRank >= " +
                "(select u.RANK from USER u" +
                " where u.USER_ID = SELLER_ID)"
)
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

  @ManyToOne
  @JoinColumn(name = "SELLER_ID", nullable = false, updatable = false)
  @ForeignKey(name = "FK_SELLER_ID")
  private User seller;

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

  public User getSeller() {
    return seller;
  }

  public void setSeller(User seller) {
    this.seller = seller;
  }

  @Override
  public String toString() {
    return "Item{" +
           "id=" + id +
           ", version=" + version +
           ", lastModified=" + lastModified +
           ", name='" + name + '\'' +
           ", seller=" + seller +
           '}';
  }
}
