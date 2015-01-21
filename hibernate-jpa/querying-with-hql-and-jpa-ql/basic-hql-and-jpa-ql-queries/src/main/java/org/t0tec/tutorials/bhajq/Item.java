package org.t0tec.tutorials.bhajq;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries({
    @NamedQuery(
        name = "findItemsByDescription",
        query = "select i from Item i where i.description like :desc"
    )
})
@Entity
@Table(name = "ITEM")
public class Item {

  @Id
  @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long Id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "DESCRIPION", nullable = false)
  private String description;

  @Column(name = "DATE")
  private Date date;

  @Column(name = "IS_ACTIVE")
  private boolean isActive;

  @ManyToOne
  @JoinColumn(name = "SELLER_ID", nullable = false, updatable = false)
  @ForeignKey(name = "FK_SELLER_ID")
  private User seller;

  @ManyToMany(mappedBy = "items")
  private Set<Category> categories = new HashSet<Category>();

  @OneToOne
  private Bid successfulBid;

  @OneToMany(mappedBy = "item")
  private Set<Bid> bids = new HashSet<Bid>();

  public Item() {}

  public Item(String name, Set<Category> categories) {
    this.name = name;
    this.categories = categories;
    this.date = new Date();
    this.isActive = true;
  }

  public Long getId() {
    return Id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  public Set<Category> getCategories() {
    return categories;
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

  public User getSeller() {
    return seller;
  }

  public void setSeller(User seller) {
    this.seller = seller;
  }

  public Bid getSuccessfulBid() {
    return successfulBid;
  }

  public void setSuccessfulBid(Bid successfulBid) {
    this.successfulBid = successfulBid;
  }

  public Set<Bid> getBids() {
    return bids;
  }

  public void setBids(Set<Bid> bids) {
    this.bids = bids;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + "]";
  }

}
