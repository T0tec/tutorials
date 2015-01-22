package org.t0tec.tutorials.jrqas;

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

@Entity
@Table(name = "CATEGORY")
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
