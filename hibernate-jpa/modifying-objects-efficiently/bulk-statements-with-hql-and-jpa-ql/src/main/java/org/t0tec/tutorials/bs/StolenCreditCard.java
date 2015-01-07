package org.t0tec.tutorials.bs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STOLEN_CREDITCARD")
public class StolenCreditCard {

  @Id
  @GeneratedValue
  @Column(name = "STOLEN_CREDITCARD_ID")
  private long id;
  @Column(name = "NUMBER")
  private String number;
  @Column(name = "EXP_MONTH")
  private String expMonth;
  @Column(name = "EXP_YEAR")
  private String expYear;
  @Column(name = "OWNER_FIRSTNAME")
  private String ownerFirstname;
  @Column(name = "OWNER_LASTNAME")
  private String ownerLastname;
  @Column(name = "OWNER_LOGIN")
  private String ownerLogin;
  @Column(name = "OWNER_EMAIL")
  private String ownerEmailAddress;
  @Column(name = "OWNER_HOME_ADDRESS")
  private String ownerHomeAddress;
  @Enumerated(EnumType.STRING)
  @Column(name = "TYPE")
  private CreditCardType type;

  public StolenCreditCard() {}

  public StolenCreditCard(String expMonth, String expYear, String ownerEmailAddress,
                          String ownerFirstname, String ownerHomeAddress,
                          String ownerLastname, String ownerLogin, String number,
                          CreditCardType type) {
    this.expMonth = expMonth;
    this.expYear = expYear;
    this.ownerEmailAddress = ownerEmailAddress;
    this.ownerFirstname = ownerFirstname;
    this.ownerHomeAddress = ownerHomeAddress;
    this.ownerLastname = ownerLastname;
    this.ownerLogin = ownerLogin;
    this.number = number;
    this.type = type;
  }

  public Long getId() {
    return id;
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

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getOwnerEmailAddress() {
    return ownerEmailAddress;
  }

  public void setOwnerEmailAddress(String ownerEmailAddress) {
    this.ownerEmailAddress = ownerEmailAddress;
  }

  public String getOwnerFirstname() {
    return ownerFirstname;
  }

  public void setOwnerFirstname(String ownerFirstname) {
    this.ownerFirstname = ownerFirstname;
  }

  public String getOwnerHomeAddress() {
    return ownerHomeAddress;
  }

  public void setOwnerHomeAddress(String ownerHomeAddress) {
    this.ownerHomeAddress = ownerHomeAddress;
  }

  public String getOwnerLastname() {
    return ownerLastname;
  }

  public void setOwnerLastname(String ownerLastname) {
    this.ownerLastname = ownerLastname;
  }

  public String getOwnerLogin() {
    return ownerLogin;
  }

  public void setOwnerLogin(String ownerLogin) {
    this.ownerLogin = ownerLogin;
  }

  public CreditCardType getType() {
    return type;
  }

  public void setType(CreditCardType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "StolenCreditCard{" +
           "expMonth='" + expMonth + '\'' +
           ", number='" + number + '\'' +
           ", expYear='" + expYear + '\'' +
           ", ownerFirstname='" + ownerFirstname + '\'' +
           ", ownerLastname='" + ownerLastname + '\'' +
           ", ownerLogin='" + ownerLogin + '\'' +
           ", ownerEmailAddress='" + ownerEmailAddress + '\'' +
           ", ownerHomeAddress=" + ownerHomeAddress +
           ", id=" + id +
           ", type='" + type + '\'' +
           '}';
  }
}
