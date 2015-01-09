package org.t0tec.tutorials.lelap;

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
  @ManyToOne
  @JoinColumn(name = "BIDDER_ID", nullable = false)
  @ForeignKey(name="FK_BIDDER_ID")
  private User bidder;

  public Bid() {}

  public Bid(BigDecimal amount) {
    this.amount = amount;
    this.created = new Date();
  }

  public Long getId() {
    return id;
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

  public User getBidder() {
    return bidder;
  }

  public void setBidder(User bidder) {
    this.bidder = bidder;
  }

  @Override
  public String toString() {
    return "Bid{" + getId() + "}, " + "amount: " + getAmount() + ", created: " + getCreated();
  }
}
