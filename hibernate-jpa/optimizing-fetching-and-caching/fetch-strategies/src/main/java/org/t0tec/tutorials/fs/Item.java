package org.t0tec.tutorials.fs;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
public class Item {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long id;
  @Column(name = "NAME", nullable = false)
  private String name;

  // Hibernate will eagerly load the seller of this item
  // with an immediate second SELECT. (without this, it'll do a join)
  //
  //  @ManyToOne(fetch = FetchType.EAGER)
  //  @org.hibernate.annotations.Fetch(
  //      org.hibernate.annotations.FetchMode.SELECT
  //  )
  @ManyToOne
  @JoinColumn(name = "SELLER_ID", nullable = false, updatable = false)
  @ForeignKey(name = "FK_SELLER_ID")
  private User seller;

  @OneToMany(mappedBy = "item", orphanRemoval = true)
  @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE,
                    org.hibernate.annotations.CascadeType.REMOVE})
  @org.hibernate.annotations.Fetch(
      org.hibernate.annotations.FetchMode.SUBSELECT
  )
  private Set<Bid> bids = new HashSet<Bid>();

  public Item() {}

  public Item(String name) {
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

  public User getSeller() {
    return seller;
  }

  public void setSeller(User seller) {
    this.seller = seller;
  }

  public Set<Bid> getBids() {
    return bids;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + "]";
  }

}
