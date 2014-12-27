package org.t0tec.tutorials.pmtoa;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

@Entity
@DiscriminatorValue("CC")
@SecondaryTable(name = "CREDIT_CARD",
    pkJoinColumns = @PrimaryKeyJoinColumn(name = "CREDIT_CARD_ID"))
public class CreditCard extends BillingDetails {
  @Column(table = "CREDIT_CARD", name = "CC_NUMBER", nullable = false)
  private String number;
  @Column(table = "CREDIT_CARD", name = "CC_TYPE", nullable = false)
  private CreditCardType type;
  @Column(table = "CREDIT_CARD", name = "CC_EXP_MONTH", nullable = false)
  private String expMonth;
  @Column(table = "CREDIT_CARD", name = "CC_EXP_YEAR", nullable = false)
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
