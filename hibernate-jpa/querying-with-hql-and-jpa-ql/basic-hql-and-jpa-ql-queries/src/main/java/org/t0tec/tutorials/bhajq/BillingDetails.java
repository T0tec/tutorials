package org.t0tec.tutorials.bhajq;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BillingDetails {
  @Column(name = "OWNER", nullable = false)
  private String owner;

  public BillingDetails() {}

  protected BillingDetails(String owner) {
    this.owner = owner;
  }

  public String getOwnername() {
    return owner;
  }

  public void setOwnername(String ownername) {
    this.owner = ownername;
  }
}
