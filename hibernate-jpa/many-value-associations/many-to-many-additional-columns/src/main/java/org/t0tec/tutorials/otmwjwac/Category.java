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
@Table(name = "CATEGORY")
public class Category {
  @Id
  @GeneratedValue
  @Column(name = "CATEGORY_ID")
  private long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @OneToMany(mappedBy = "category")
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

}
