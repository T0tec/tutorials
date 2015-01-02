package org.t0tec.tutorials.nk;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ITEMS")
public class Item {
  @Id
  @Column(name = "ITEM_ID", columnDefinition = "char(32)")
  @GeneratedValue(generator = "hibernate-uuid.hex")
  @GenericGenerator(name = "hibernate-uuid.hex", strategy = "uuid.hex")
  private String id;
  @Column(name = "INIT_PRICE", nullable = false, precision = 10, scale = 2)
  private BigDecimal initialPrice;
  @Column(name = "ITM_DESCRIPTION", length = 4000)
  private String description;

  public Item() {}

  public Item(BigDecimal initialPrice, String description) {
    super();
    this.initialPrice = initialPrice;
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BigDecimal getInitialPrice() {
    return initialPrice;
  }

  public void setInitialPrice(BigDecimal initialPrice) {
    this.initialPrice = initialPrice;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getInitialPrice()=" + getInitialPrice()
        + ", getDescription()=" + getDescription() + "]";
  }

}
