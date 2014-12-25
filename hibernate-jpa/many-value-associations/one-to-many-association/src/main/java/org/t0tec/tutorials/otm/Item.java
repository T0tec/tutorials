package org.t0tec.tutorials.otm;

import java.util.ArrayList;
import java.util.Collection;

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

  // mappedBy == inverse attribute
  // @JoinColumn(name = "ITEM_ID")
  @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE,
      org.hibernate.annotations.CascadeType.REMOVE})
  @OneToMany(mappedBy = "item", orphanRemoval = true)
  private Collection<Bid> bids = new ArrayList<Bid>();

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

  public void setBids(Collection<Bid> bids) {
    this.bids = bids;
  }

  public Collection<Bid> getBids() {
    return bids;
  }

  public void addBid(Bid bid) {
    bid.setItem(this);
    bids.add(bid);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Item{" + getId() + "}, " + "name: " + getName() + ", bids: {");

    if (!bids.isEmpty()) {
      for (Bid b : bids) {
        sb.append(b).append(", ");
      }
      sb.delete(sb.length() - 2, sb.length());
    }

    sb.append("}");

    return sb.toString();
  }
}
