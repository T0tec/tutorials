package org.t0tec.tutorials.otmwjt;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SHIPMENT")
@SecondaryTable(name = "ITEM_SHIPMENT")
public class Shipment {
  @Id
  @GeneratedValue
  @Column(name = "SHIPMENT_ID")
  private Long id;
  @OneToOne
  @JoinTable(name = "ITEM_SHIPMENT", joinColumns = @JoinColumn(name = "SHIPMENT_ID"),
      inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
  private Item auction;
  @Enumerated(EnumType.STRING)
  @Column(name = "SHIPMENT_STATE", nullable = false)
  private ShipmentState state = ShipmentState.AGREED;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED", nullable = false, updatable = false)
  private Date created = new Date();

  public Shipment() {}

  public Long getId() {
    return id;
  }

  public Item getAuction() {
    return auction;
  }

  public void setAuction(Item auction) {
    this.auction = auction;
  }

  public ShipmentState getState() {
    return state;
  }

  public void setState(ShipmentState state) {
    this.state = state;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  @Override
  public String toString() {
    return "Shipment [getId()=" + getId() + ", getAuction()=" + getAuction() + ", getState()="
        + getState() + ", getCreated()=" + getCreated() + "]";
  }
}
