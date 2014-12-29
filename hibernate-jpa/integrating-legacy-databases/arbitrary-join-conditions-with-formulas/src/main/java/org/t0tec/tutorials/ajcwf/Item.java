package org.t0tec.tutorials.ajcwf;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Item {
  private Long id;

  private String name;
  private BigDecimal initialPrice;
  private BigDecimal reservePrice;

  private Set<Bid> bids = new HashSet<Bid>();

  private Bid successfulBid;

  public Item() {}

  public Item(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  @SuppressWarnings("unused")
  private void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getInitialPrice() {
    return initialPrice;
  }

  public void setInitialPrice(BigDecimal initialPrice) {
    this.initialPrice = initialPrice;
  }

  public BigDecimal getReservePrice() {
    return reservePrice;
  }

  public void setReservePrice(BigDecimal reservePrice) {
    this.reservePrice = reservePrice;
  }

  public Set<Bid> getBids() {
    return bids;
  }

  public void setBids(Set<Bid> bids) {
    this.bids = bids;
  }

  public Bid getSuccessfulBid() {
    return successfulBid;
  }

  public void setSuccessfulBid(Bid successfulBid) {
    if (successfulBid != null) {
      for (Bid bid : bids) {
        bid.setSuccessful(false);
      }
      successfulBid.setSuccessful(true);
      this.successfulBid = successfulBid;
    }
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + ", getInitialPrice()="
        + getInitialPrice() + ", getReservePrice()=" + getReservePrice() + ", getBids().size()="
        + getBids().size() + "]";
  }

}
