package org.t0tec.tutorials.auction.model;

import java.util.Date;

public class Comment {
	private Rating rating;
	private String text;
	private Date created;
	
	public Rating getRating() {
		return rating;
	}
	
	public String getText() {
		return text;
	}
	
	public Date getCreated() {
		return created;
	}
	
}
