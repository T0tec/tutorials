package org.t0tec.tutorials.auction.model;

import java.math.BigDecimal;
import java.util.Date;

public class Bid {
	private BigDecimal amount;
	private Date created;

	/**
	 * No-arg constructor for JavaBean tools
	 */
	public Bid() {}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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
