package org.t0tec.tutorials.mtma;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

  // This makes it bidirectional NOTE: for lists and maps this doesn't work
  @ManyToMany(mappedBy = "items")
  private Set<Category> categories = new HashSet<Category>();

  // for a bag
  // @ManyToMany(mappedBy = "items")
  // private Collection<Category> categories = new ArrayList<Category>();

  public Item() {}

  public Item(String name, Set<Category> categories) {
    this.name = name;
    this.categories = categories;
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

  public Set<Category> getCategories() {
    return categories;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + "]";
  }

}
