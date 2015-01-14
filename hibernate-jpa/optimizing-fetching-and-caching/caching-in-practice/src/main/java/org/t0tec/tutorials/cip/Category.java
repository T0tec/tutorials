package org.t0tec.tutorials.cip;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

// You use read-write instead of nonstrict-read-write because Category is a
// highly concurrent class, shared between many concurrent transactions. (It’s clear
// that a read committed isolation level is good enough.) A nonstrict-read-write
// would rely only on cache expiration (timeout), but you prefer changes to categories
// to be visible immediately.
@Entity
@Table(name = "CATEGORY")
@org.hibernate.annotations.Cache(usage =
    org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE
)
public class Category {
  @Id
  @GeneratedValue
  @Column(name = "CATEGORY_ID")
  private long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  public Category() {}

  public Category(String name, Set<Item> items) {
    this.name = name;
    this.items = items;
  }

  @ManyToMany
  @JoinTable(name = "CATEGORY_ITEM", joinColumns = {@JoinColumn(name = "CATEGORY_ID")},
      inverseJoinColumns = {@JoinColumn(name = "ITEM_ID")})
  @org.hibernate.annotations.Cache(usage =
      org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE
  )
  private Set<Item> items = new HashSet<Item>();

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Item> getItems() {
    return items;
  }

  @Override
  public String toString() {
    return "Category [getId()=" + getId() + ", getName()=" + getName() + ", getItems()="
        + getItems() + "]";
  }

}
