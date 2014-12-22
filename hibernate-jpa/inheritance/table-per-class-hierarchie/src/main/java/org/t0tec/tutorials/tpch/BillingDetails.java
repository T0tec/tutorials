package org.t0tec.tutorials.tpch;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "BILLING_DETAILS_TYPE", discriminatorType = DiscriminatorType.STRING)
// @org.hibernate.annotations.DiscriminatorFormula(
// "case when CC_NUMBER is not null then 'CC' else 'BA' end"
// )
public abstract class BillingDetails {
  @Id
  @GeneratedValue
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

  @Transient
  public String getDiscriminatorValue() {
    DiscriminatorValue val = this.getClass().getAnnotation(DiscriminatorValue.class);
    return val == null ? null : val.value();
  }

}
