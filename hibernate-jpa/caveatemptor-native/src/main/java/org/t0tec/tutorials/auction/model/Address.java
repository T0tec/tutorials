package org.t0tec.tutorials.auction.model;

public class Address {
	private String street;
	private String zipcode;
	private String city;
	
	/**
	 * No-arg constructor for JavaBean tools
	 */
	public Address() {}
	
	public String getStreet() {
		return street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getCity() {
		return city;
	}
	
	@Override
	public boolean equals(Object o) {
		return false;	
	}
	
	@Override
	public String toString() {
		return this.street + " " + this.zipcode + " " + this.city;
	}
}
