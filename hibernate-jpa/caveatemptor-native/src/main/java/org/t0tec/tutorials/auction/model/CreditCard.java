package org.t0tec.tutorials.auction.model;

public class CreditCard extends BillingDetails {
	private CreditCardType type;
	private String number;
	private String expMonth;
	private String expYear;
	
	public CreditCard() {}
	
	public CreditCardType getType() {
		return type;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getExpMonth() {
		return expMonth;
	}
	
	public String getExpYear() {
		return expYear;
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
