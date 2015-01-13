package org.t0tec.tutorials.fs;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "BID")
public class Bid {
  @Id
  @GeneratedValue
  @Column(name = "BID_ID")
  private Long id;
  @ManyToOne
  @JoinColumn(name = "ITEM_ID", nullable = false)
  @ForeignKey(name = "FK_ITEM_ID")
  private Item item;
  @Column(name = "AMOUNT")
  private BigDecimal amount;
  @Column(name = "CREATED")
  private Date created;

  public Bid() {}

  public Bid(BigDecimal amount, Date created) {
    this.amount = amount;
    this.created = created;
  }

  public Long getId() {
    return id;
  }

  private void setId(long id) {
    this.id = id;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  @Override
  public String toString() {
    return "Bid{" + getId() + "}, " + "amount: " + getAmount() + ", created: " + getCreated();
  }
}
