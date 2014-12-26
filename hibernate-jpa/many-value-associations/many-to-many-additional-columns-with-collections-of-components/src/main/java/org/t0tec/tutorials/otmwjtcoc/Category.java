package org.t0tec.tutorials.otmwjtcoc;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

  @ElementCollection
  @JoinTable(name = "CATEGORY_ITEM", joinColumns = @JoinColumn(name = "CATEGORY_ID"))
  private Set<CategorizedItem> categorizedItems = new HashSet<CategorizedItem>();

  public Category() {}

  public Category(String name, Set<CategorizedItem> categorizedItems) {
    this.name = name;
    this.categorizedItems = categorizedItems;
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

  public Set<CategorizedItem> getCategorizedItems() {
    return categorizedItems;
  }

  @Override
  public String toString() {
    return "Category [getId()=" + getId() + ", getName()=" + getName() + ", getCategorizedItems()="
        + getCategorizedItems() + "]";
  }

}
