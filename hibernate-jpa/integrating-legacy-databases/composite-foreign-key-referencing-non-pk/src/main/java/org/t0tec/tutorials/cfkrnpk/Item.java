package org.t0tec.tutorials.cfkrnpk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
  @JoinColumns(value = {@JoinColumn(name = "SELLER_FIRSTNAME", referencedColumnName = "FIRSTNAME"),
      @JoinColumn(name = "SELLER_LASTNAME", referencedColumnName = "LASTNAME"),
      @JoinColumn(name = "SELLER_BIRTHDAY", referencedColumnName = "BIRTHDAY")})
  private User seller;

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

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + ", getSeller()=" + getSeller()
        + "]";
  }

}
