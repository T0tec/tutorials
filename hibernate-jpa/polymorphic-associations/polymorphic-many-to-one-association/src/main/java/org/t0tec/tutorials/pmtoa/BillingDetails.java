package org.t0tec.tutorials.pmtoa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "BILLING_DETAILS")
public abstract class BillingDetails {
  @Id
  @GeneratedValue
  @Column(name = "BILLING_DETAILS_ID")
  private Long id;
  @Column(name = "OWNER", nullable = false)
  private String owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", updatable = false)
  @ForeignKey(name = "FK_USER_ID")
  private User user;

  public BillingDetails() {}

  protected BillingDetails(String owner) {
    this.owner = owner;
  }

  public Long getId() {
    return id;
  }

  public String getOwner() {
    return owner;
  }

  public User getUser() {
    return user;
  }

  public void pay(BigDecimal bigDecimal) {
    // TODO: needs to be implemented
  }

}
