package org.t0tec.tutorials.pbr;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "ITEM")
public class Item {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long id;
  @Column(name = "NAME", nullable = false)
  private String name;
  @OneToMany(mappedBy = "item", orphanRemoval = true)
  @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
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

  public void setBids(Set<Bid> bids) {
    this.bids = bids;
  }

  public Set<Bid> getBids() {
    return bids;
  }

  public void addBid(Bid bid) {
    bid.setItem(this);
    bids.add(bid);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Item[getId()=" + getId() + ", " + "getName()=" + getName() + ", getBids()={"
        + getBids() + "}]");

    return sb.toString();
  }
}
