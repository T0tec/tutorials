package org.t0tec.tutorials.auction.model;

public class Category {
	private String name;
	
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		return false;	
	}
	
	@Override
	public String toString() {
		return name;
	}
}
