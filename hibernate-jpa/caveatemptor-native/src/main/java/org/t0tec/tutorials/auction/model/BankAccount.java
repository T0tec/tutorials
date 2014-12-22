package org.t0tec.tutorials.auction.model;

public class BankAccount extends BillingDetails {
  private String number;
  private String bankname;
  private String swift;

  /**
   * No-arg constructor for JavaBean tools
   */
  public BankAccount() {}

  public String getNumber() {
    return number;
  }

  public String getBankname() {
    return bankname;
  }

  public String getSwift() {
    return swift;
  }

  @Override
  public boolean equals(Object o) {
    return false;
  }

  @Override
  public String toString() {
    return null;
  }
}
