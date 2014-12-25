package org.t0tec.tutorials.otmwjtcoc;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Parent;

@Embeddable
public class CategorizedItem implements Serializable {

  @Parent
  // Optional back-pointer
  private Category category;
  @ManyToOne
  @JoinColumn(name = "ITEM_ID", nullable = false, updatable = false)
  private Item item;
  @ManyToOne
  @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
  private User user;

  private String username;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "ADDED_ON", nullable = false, updatable = false)
  private Date dateAdded;

  /**
   * No-arg constructor for JavaBean tools
   */
  public CategorizedItem() {}

  public CategorizedItem(User user, Category category, Item item) {
    this.user = user;
    this.username = user.getUsername();
    this.category = category;
    this.item = item;
    this.dateAdded = GregorianCalendar.getInstance().getTime();
  }


  // ********************** Accessor Methods ********************** //

  public String getUsername() {
    return username;
  }

  public Date getDateAdded() {
    return dateAdded;
  }

  public Category getCategory() {
    return category;
  }

  public Item getItem() {
    return item;
  }

  public User getUser() {
    return user;
  }

  // ********************** Needed Methods ********************** //

  public void setCategory(Category category) {
    this.category = category;
  }

  // ********************** Common Methods ********************** //

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((category == null) ? 0 : category.hashCode());
    result = prime * result + ((dateAdded == null) ? 0 : dateAdded.hashCode());
    result = prime * result + ((item == null) ? 0 : item.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CategorizedItem other = (CategorizedItem) obj;
    if (category == null) {
      if (other.category != null)
        return false;
    } else if (!category.equals(other.category))
      return false;
    if (dateAdded == null) {
      if (other.dateAdded != null)
        return false;
    } else if (!dateAdded.equals(other.dateAdded))
      return false;
    if (item == null) {
      if (other.item != null)
        return false;
    } else if (!item.equals(other.item))
      return false;
    if (username == null) {
      if (other.username != null)
        return false;
    } else if (!username.equals(other.username))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Added by: '" + getUsername() + "', " + "On Date: '" + getDateAdded();
  }

}
