package org.t0tec.tutorials.bs;

import org.hibernate.annotations.ForeignKey;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CREDIT_CARD")
public class CreditCard {

  @Id
  @GeneratedValue
  @Column(name = "CREDIT_CARD_ID")
  private long id;
  @Column(name = "OWNER", nullable = false)
  private String owner;
  @Column(name = "NUMBER", nullable = false)
  private String number;
  @Enumerated(EnumType.STRING)
  @Column(name = "TYPE", nullable = false)
  private CreditCardType type;
  @Column(name = "EXP_MONTH", nullable = false)
  private String expMonth;
  @Column(name = "EXP_YEAR", nullable = false)
  private String expYear;
  @Column(name = "STOLEN_ON", nullable = true)
  private Date stolenOn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", updatable = false)
  @ForeignKey(name = "FK_USER_ID")
  private User user;

  public CreditCard() {}

  public CreditCard(String owner, String number, CreditCardType type, String expMonth,
                    String expYear) {
    this.owner = owner;
    this.number = number;
    this.type = type;
    this.expMonth = expMonth;
    this.expYear = expYear;
  }

  public long getId() {
    return id;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
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

  public Date getStolenOn() {
    return stolenOn;
  }

  public void setStolenOn(Date stolenOn) {
    this.stolenOn = stolenOn;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "CreditCard{" +
           "expMonth='" + expMonth + '\'' +
           ", id=" + id +
           ", owner='" + owner + '\'' +
           ", number='" + number + '\'' +
           ", type=" + type +
           ", expYear='" + expYear + '\'' +
           ", stolenOn=" + stolenOn +
           '}';
  }
}
