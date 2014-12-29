package org.t0tec.tutorials.ijp;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DAILY_BILLING")
public class DailyBilling {
  @Id
  @GeneratedValue
  @Column(name = "BILLING_ID")
  private long id;
  @Column(name = "STATUS")
  private int status;
  @Column(name = "TOTAL")
  private BigDecimal total;

  @OneToOne
  @JoinColumn(name = "ITEM_ID")
  private Item item;

  public DailyBilling() {}

  public DailyBilling(int status, BigDecimal total) {
    this.status = status;
    this.total = total;
  }

  public long getId() {
    return id;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

}
