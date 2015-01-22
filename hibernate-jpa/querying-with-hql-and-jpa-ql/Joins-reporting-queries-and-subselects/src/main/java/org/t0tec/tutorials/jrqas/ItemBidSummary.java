package org.t0tec.tutorials.jrqas;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemBidSummary {
  @Id
  private long itemId;
  private long bidCount;
  private Double averageBidAmount;

  public ItemBidSummary() {}

  public ItemBidSummary(long itemId, long bidCount, Double averageBidAmount) {
    this.itemId = itemId;
    this.bidCount = bidCount;
    this.averageBidAmount = averageBidAmount;
  }

  public long getItemId() {
    return itemId;
  }

  public void setItemId(long itemId) {
    this.itemId = itemId;
  }


  public long getBidCount() {
    return bidCount;
  }

  public void setBidCount(long bidCount) {
    this.bidCount = bidCount;
  }

  public Double getAverageBidAmount() {
    return averageBidAmount;
  }

  public void setAverageBidAmount(Double averageBidAmount) {
    this.averageBidAmount = averageBidAmount;
  }

  @Override
  public String toString() {
    return "ItemBidSummary{" +
           "averageBidAmount=" + averageBidAmount +
           ", itemId=" + itemId +
           ", bidCount=" + bidCount +
           '}';
  }
}
