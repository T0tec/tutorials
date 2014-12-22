package org.t0tec.tutorials.cmt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
public class Item {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long id;
  @Column(name = "NAME", nullable = true)
  private String name;
  @Column(name = "DESCRIPTION", nullable = true)
  private String description;

  // Simple: UserType
  // @org.hibernate.annotations.Type(
  // type = "org.t0tec.tutorials.cmt.persistence.MonetaryAmountSimpleUserType"
  // )
  // @Column(name = "INITIAL_PRICE")
  // Advanced: CompositeUserType
  // @org.hibernate.annotations.Type(
  // type = "org.t0tec.tutorials.cmt.persistence.MonetaryAmountCompositeUserType"
  // )
  // @org.hibernate.annotations.Columns(columns = {
  // @Column(name="INITIAL_PRICE"),
  // @Column(name="INITIAL_PRICE_CURRENCY")
  // })
  // Complex (see package-info.java)
  @org.hibernate.annotations.Type(type = "monetary_amount_usd")
  @org.hibernate.annotations.Columns(columns = {
      @Column(name = "INITIAL_PRICE", nullable = false, length = 2),
      @Column(name = "INITIAL_PRICE_CURRENCY", nullable = false, length = 3)})
  private MonetaryAmount initialPrice;

  public Item() {}

  public Item(String name, String description, MonetaryAmount initialPrice) {
    this.name = name;
    this.description = description;
    this.initialPrice = initialPrice;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MonetaryAmount getInitialPrice() {
    return initialPrice;
  }

  public void setInitialPrice(MonetaryAmount initialPrice) {
    this.initialPrice = initialPrice;
  }

  @Override
  public String toString() {
    return "Item{" + getId() + "}, " + "name: " + getName() + ",  descr: " + getDescription()
        + ", initial price: " + getInitialPrice().getValue() + " "
        + getInitialPrice().getCurrency();
  }
}
