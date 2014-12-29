package org.t0tec.tutorials.ajcwf;

import java.math.BigDecimal;

public class Bid {
  private long id;
  private BigDecimal amount;
  private Boolean successful;
  private Item item;

  public Bid() {}

  public long getId() {
    return id;
  }

  @SuppressWarnings("unused")
  private void setId(long id) {
    this.id = id;
  }

  public Bid(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Boolean getSuccessful() {
    return successful;
  }

  public void setSuccessful(Boolean successful) {
    this.successful = successful;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  @Override
  public String toString() {
    return "Bid [getId()=" + getId() + ", getAmount()=" + getAmount() + ", getSuccessful()="
        + getSuccessful() + ", getItem()=" + getItem() + "]";
  }

}
