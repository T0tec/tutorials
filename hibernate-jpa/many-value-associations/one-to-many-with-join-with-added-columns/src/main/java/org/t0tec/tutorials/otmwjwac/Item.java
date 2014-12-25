package org.t0tec.tutorials.otmwjwac;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
public class Item {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long Id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @OneToMany(mappedBy = "category")
  private Set<CategorizedItem> categorizedItems = new HashSet<CategorizedItem>();

  public Item() {}

  public Item(String name, Set<CategorizedItem> categorizedItems) {
    this.name = name;
    this.categorizedItems = categorizedItems;
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

  public Set<CategorizedItem> getCategorizedItems() {
    return categorizedItems;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + "]";
  }

}
