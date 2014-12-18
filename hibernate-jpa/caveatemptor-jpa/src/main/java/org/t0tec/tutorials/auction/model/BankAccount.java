package org.t0tec.tutorials.auction.model;

public class BankAccount extends BillingDetails {
	private String number;
	private String bankname;
	private String swift;
	
	public String getNumber() {
		return number;
	}
	
	public String getBankname() {
		return bankname;
	}
	
	public String getSwift() {
		return swift;
	}
}
