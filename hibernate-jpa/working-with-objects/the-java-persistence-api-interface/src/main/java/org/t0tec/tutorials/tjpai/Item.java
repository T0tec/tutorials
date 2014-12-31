package org.t0tec.tutorials.tjpai;
import java.math.BigDecimal;
import java.util.Date;

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
  @Column(name = "NAME", nullable = false)
  private String name;
  @Column(name = "DESCRIPTION", nullable = true)
  private String description;
  @Column(name = "INITIAL_PRICE", nullable = true)
  private BigDecimal initialPrice;
  @Column(name = "END_DATE", nullable = true)
  private Date endDate;

  public Item() {}

  public Item(String name, String description, BigDecimal initialPrice, Date endDate) {
    this.name = name;
    this.description = description;
    this.initialPrice = initialPrice;
    this.endDate = endDate;
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

  public BigDecimal getInitialPrice() {
    return initialPrice;
  }

  public void setInitialPrice(BigDecimal initialPrice) {
    this.initialPrice = initialPrice;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + ", getDescription()="
        + getDescription() + ", getInitialPrice()=" + getInitialPrice() + ", getEndDate()="
        + getEndDate() + "]";
  }
  
}