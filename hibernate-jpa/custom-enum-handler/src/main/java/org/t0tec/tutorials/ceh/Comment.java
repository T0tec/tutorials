package org.t0tec.tutorials.ceh;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "COMMENT_ID")
	private Long id = null;

	@Enumerated(EnumType.STRING)
	@Column(name = "RATING", nullable = false, updatable = false)
	private Rating rating;

	@Column(name = "COMMENT_TEXT", length = 4000, nullable = true)
	private String text;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", nullable = false, updatable = false)
	private Date created = new Date();

	public Comment() {}

	public Comment(Rating rating, String text) {
		this.rating = rating;
		this.text = text;
	}

	public Long getId() {
		return id;
	}
	
	public Rating getRating() {
		return rating;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreated() {
		return created;
	}

	public String toString() {
		return "Comment{'" + getId() + "'}, " + "Rating: '" + getRating() + "',  text: " + text;
	}

}
