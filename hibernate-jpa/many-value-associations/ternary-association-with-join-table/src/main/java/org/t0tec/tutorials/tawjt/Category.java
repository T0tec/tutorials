package org.t0tec.tutorials.tawjt;

import java.util.HashMap;
import java.util.Map;

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

  @ManyToMany
  @org.hibernate.annotations.MapKeyManyToMany(joinColumns = @JoinColumn(name = "ITEM_ID"))
  @JoinTable(name = "CATEGORY_ITEM", joinColumns = @JoinColumn(name = "CATEGORY_ID"),
      inverseJoinColumns = @JoinColumn(name = "USER_ID"))
  private Map<Item, User> itemsAndUser = new HashMap<Item, User>();

  public Category() {}

  public Category(String name, Map<Item, User> itemsAndUser) {
    this.name = name;
    this.itemsAndUser = itemsAndUser;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<Item, User> getItemsAndUser() {
    return itemsAndUser;
  }

  @Override
  public String toString() {
    return "Category [getId()=" + getId() + ", getName()=" + getName() + ", getItemsAndUser()="
        + getItemsAndUser() + "]";
  }

}
