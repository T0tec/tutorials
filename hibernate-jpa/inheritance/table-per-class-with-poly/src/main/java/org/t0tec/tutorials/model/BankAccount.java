package org.t0tec.tutorials.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AttributeOverride(name = "owner", column =
	@Column(name = "BA_OWNER", nullable = false)
)
public class BankAccount extends BillingDetails {
	@Id @GeneratedValue
	@Column(name = "BANK_ACCOUNT_ID")
	private Long id;
	@Column(name = "NUMBER", nullable = false)
	private String number;
	@Column(name = "BANKNAME", nullable = false)
	private String bankname;
	@Column(name = "SWIFT", nullable = false)
	private String swift;
	
	public BankAccount() {
		super();
	}
	
	public BankAccount(String owner, String number, String bankname, String swift) {
		super(owner);
		this.number = number;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
		return "CreditCard{" + "id=" + id + ", number=" + number + ", bank=" + bankname + '}';
	}
}
