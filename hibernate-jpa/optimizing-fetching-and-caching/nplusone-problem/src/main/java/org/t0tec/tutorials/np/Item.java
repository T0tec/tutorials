package org.t0tec.tutorials.np;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;

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

  @ManyToOne
  @JoinColumn(name = "SELLER_ID", nullable = false, updatable = false)
  @ForeignKey(name = "FK_SELLER_ID")
  private User seller;

  @OneToMany(mappedBy = "item", orphanRemoval = true)
  @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE,
                    org.hibernate.annotations.CascadeType.REMOVE})
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
