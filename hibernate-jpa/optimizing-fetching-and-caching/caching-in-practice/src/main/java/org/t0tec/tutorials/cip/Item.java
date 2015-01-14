package org.t0tec.tutorials.cip;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ForeignKey;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

// If you require the Item instances themselves to be cached, you must enable
// caching of the Item class. A read-write strategy is especially appropriate. Your users
// don’t want to make decisions (placing a bid, for example) based on possibly stale
// Item data. Let’s go a step further and consider the collection of bids : A particular
// Bid in the bids collection is immutable, but the collection of bids is mutable, and
// concurrent units of work need to see any addition or removal of a collection ele-
// ment without delay
@Entity
@Table(name = "ITEM")
@org.hibernate.annotations.Cache(usage =
    org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE
)
public class Item {

  @Id
  @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long id;
  @Column(name = "NAME", nullable = false)
  private String name;

  @OneToMany(mappedBy = "item", orphanRemoval = true)
  @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE,
                    org.hibernate.annotations.CascadeType.REMOVE})
  @org.hibernate.annotations.Cache(usage =
      org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE
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

  public Set<Bid> getBids() {
    return bids;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + "]";
  }

}
