package org.t0tec.tutorials.carq;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AttributeOverride(name = "owner", column = @Column(name = "BA_OWNER", nullable = false))
public class BankAccount extends BillingDetails {
  @Id
  @GeneratedValue
  @Column(name = "BANK_ACCOUNT_ID")
  private Long id;
  @Column(name = "ACOUNT", nullable = false)
  private String account;
  @Column(name = "BANKNAME", nullable = false)
  private String bankname;
  @Column(name = "SWIFT", nullable = false)
  private String swift;

  public BankAccount() {
    super();
  }

  public BankAccount(String owner, String account, String bankname, String swift) {
    super(owner);
    this.account = account;
    this.bankname = bankname;
    this.swift = swift;
  }

  public Long getId() {
    return id;
  }

  @SuppressWarnings("unused")
  private void setId(Long id) {
    this.id = id;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getBankname() {
    return bankname;
  }

  public void setBankname(String bankname) {
    this.bankname = bankname;
  }

  public String getSwift() {
    return swift;
  }

  public void setSwift(String swift) {
    this.swift = swift;
  }

  @Override
  public String toString() {
    return "BankAccount{" + "id=" + id + ", account=" + account + ", bank=" + bankname + '}';
  }
}
