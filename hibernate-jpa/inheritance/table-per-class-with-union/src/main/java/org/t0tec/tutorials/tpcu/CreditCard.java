package org.t0tec.tutorials.tpcu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CREDIT_CARD")
public class CreditCard extends BillingDetails {
  @Column(name = "NUMBER", nullable = false)
  private String number;
  @Column(name = "TYPE", nullable = false)
  private CreditCardType type;
  @Column(name = "EXP_MONTH", nullable = false)
  private String expMonth;
  @Column(name = "EXP_YEAR", nullable = false)
  private String expYear;

  public CreditCard() {
    super();
  }

  public CreditCard(String owner, String number, CreditCardType type, String expMonth,
      String expYear) {
    super(owner);
    this.number = number;
    this.type = type;
    this.expMonth = expMonth;
    this.expYear = expYear;
  }

  public CreditCardType getType() {
    return type;
  }

  public void setType(CreditCardType type) {
    this.type = type;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getExpMonth() {
    return expMonth;
  }

  public void setExpMonth(String expMonth) {
    this.expMonth = expMonth;
  }

  public String getExpYear() {
    return expYear;
  }

  public void setExpYear(String expYear) {
    this.expYear = expYear;
  }

  @Override
  public String toString() {
    return "CreditCard{" + "id=" + super.getId() + ", number=" + number + ", type="
        + type.toString() + '}';
  }
}
