package org.t0tec.tutorials.otmwj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
public class Item {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long Id;

  @ManyToOne
  @JoinTable(name = "ITEM_BUYER", joinColumns = {@JoinColumn(name = "ITEM_ID")},
      inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
  private User buyer;

  @Column(name = "NAME", nullable = false)
  private String name;

  public Item() {}

  public Item(String name) {
    this.name = name;
  }

  public Long getId() {
    return Id;
  }

  public User getBuyer() {
    return buyer;
  }

  public void setBuyer(User buyer) {
    this.buyer = buyer;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + "]";
  }
}
