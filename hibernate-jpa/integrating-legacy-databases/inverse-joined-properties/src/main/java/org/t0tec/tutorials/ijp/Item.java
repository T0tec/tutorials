package org.t0tec.tutorials.ijp;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "ITEM")
public class Item {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long id;
  @Column(name = "NAME", nullable = false)
  private String name;

  // <join table="DAILY_BILLING" optional="true" inverse="true">
  // <key column="ITEM_ID"/>
  // <property name="billingTotal"
  // type="big_decimal"
  // column="TOTAL"/>
  // </join>

  // <property name="billingTotal"
  // type="big_decimal"
  // formula="( select db.TOTAL from DAILY_BILLING db
  // where db.ITEM_ID = ITEM_ID )"/>

  // @JoinTable(name = "DAILY_BILLING", inverseJoinColumns = {@JoinColumn(name = "BILLING_ID")})
  @Formula(value = "(select db.TOTAL from DAILY_BILLING db where db.ITEM_ID = ITEM_ID)")
  private BigDecimal billingTotal;

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

  public BigDecimal getBillingTotal() {
    return billingTotal;
  }

  public void setBillingTotal(BigDecimal billingTotal) {
    this.billingTotal = billingTotal;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + ", getBillingTotal()="
        + getBillingTotal() + "]";
  }

}
