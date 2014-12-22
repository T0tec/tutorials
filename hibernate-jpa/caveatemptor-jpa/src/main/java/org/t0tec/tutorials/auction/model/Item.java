package org.t0tec.tutorials.auction.model;

import java.math.BigDecimal;
import java.util.Date;

public class Item {
  private String name;
  private String description;
  private BigDecimal initialPrice;
  private BigDecimal reservePrice;
  private Date startDate;
  private Date endDate;
  private ItemState state;
  private Date approvalDatetime;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getInitialPrice() {
    return initialPrice;
  }

  public BigDecimal getReservePrice() {
    return reservePrice;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public ItemState getState() {
    return state;
  }

  public Date getApprovalDatetime() {
    return approvalDatetime;
  }

  @Override
  public boolean equals(Object o) {
    return false;
  }

  @Override
  public String toString() {
    return null;
  }
}
