package org.t0tec.tutorials.pmtoa;

public enum CreditCardType {
  MASTERCARD("Mastercard"), VISA("Visa"), AMEX("American Express");

  private final String debugName;

  private CreditCardType(String debugName) {
    this.debugName = debugName;
  }

  @Override
  public String toString() {
    return debugName;
  }
}
