package org.t0tec.tutorials.tawjt;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKey;
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

  @MapKey(name = "id")
  @OneToMany
  private Map<Long, Bid> bidsByIdentifier = new HashMap<Long, Bid>();

  public Item() {}

  public Item(String name, Map<Long, Bid> bidsByIdentifier) {
    this.name = name;
    this.bidsByIdentifier = bidsByIdentifier;
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

  public Map<Long, Bid> getBids() {
    return bidsByIdentifier;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + ", getBids()=" + getBids()
        + "]";
  }

}
