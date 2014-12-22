package org.t0tec.tutorials.tpcu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BillingDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "BILLING_DETAILS_ID")
  private Long id;
  @Column(name = "OWNER", nullable = false)
  private String owner;

  public BillingDetails() {}

  protected BillingDetails(String owner) {
    this.owner = owner;
  }

  public Long getId() {
    return id;
  }

  @SuppressWarnings("unused")
  private void setId(Long id) {
    this.id = id;
  }


  public String getOwnername() {
    return owner;
  }

  public void setOwnername(String ownername) {
    this.owner = ownername;
  }
}
