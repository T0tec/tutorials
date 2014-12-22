package org.t0tec.tutorials.tpcu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BANK_ACCOUNT")
public class BankAccount extends BillingDetails {
  @Column(name = "ACCOUNT", nullable = false)
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
    return "BankAccount{" + "id=" + super.getId() + ", number=" + account + ", bank=" + bankname
        + '}';
  }
}
