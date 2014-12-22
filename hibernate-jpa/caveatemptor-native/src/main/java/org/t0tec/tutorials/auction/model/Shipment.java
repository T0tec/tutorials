package org.t0tec.tutorials.auction.model;

import java.util.Date;

public class Shipment {
  private int inspectionPeriodDays;
  private ShipmentState state;
  private Date created;

  public Shipment() {}

  public int getInspectionPeriodDays() {
    return inspectionPeriodDays;
  }

  public ShipmentState getState() {
    return state;
  }

  public Date getCreated() {
    return created;
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
