package org.t0tec.tutorials.lelap;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
public class Item {
  @Id
  @GeneratedValue
  // TODO: solves that getId executes a select statement when you don't want it
  // url: http://256.com/gray/docs/misc/hibernate_lazy_field_access_annotations.shtml
  @AccessType("property")
  @Column(name = "ITEM_ID")
  private Long id;
  @Column(name = "NAME", nullable = false)
  private String name;
  @Column(name = "DESCRIPTION")
  private String description;

  // The collection is no longer initialized if you call size(), contains() or isEmpty()
  @LazyCollection(
      org.hibernate.annotations.LazyCollectionOption.EXTRA
  )
  // The FetchType.EAGER provides the same guarantees as lazy="false"
  @OneToMany(mappedBy = "item", orphanRemoval = true, fetch = FetchType.EAGER)
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

  private void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
    return "Item{" +
           "bids=" + bids +
           ", id=" + id +
           ", name='" + name + '\'' +
           ", description='" + description + '\'' +
           '}';
  }
}
