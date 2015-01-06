package org.t0tec.tutorials.pbr;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "CATEGORY")
public class Category {
  @Id
  @GeneratedValue
  @Column(name = "CATEGORY_ID")
  private long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "PARENT_CATEGORY_ID", nullable = true)
  private Category parentCategory;

  @OneToMany(mappedBy = "parentCategory")
  @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
  private Set<Category> childCategories = new HashSet<Category>();

  public Category() {}

  public Category(String name) {
    this.name = name;
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

  public Category getParentCategory() {
    return parentCategory;
  }

  public void setParentCategory(Category parentCategory) {
    this.parentCategory = parentCategory;
  }

  public Set<Category> getChildCategories() {
    return childCategories;
  }

  public void setChildCategories(Set<Category> childCategories) {
    this.childCategories = childCategories;
  }

  public void addChildCategory(Category childCategory) {
    if (childCategory == null) {
      throw new IllegalArgumentException("Null child category!");
    }

    if (childCategory.getParentCategory() != null) {
      childCategory.getParentCategory().getChildCategories().remove(childCategory);
    }
    childCategory.setParentCategory(parentCategory);
    childCategories.add(childCategory);
  }

  public void removeChildCategory(Category childCategory) {
    if (childCategory == null) {
      throw new IllegalArgumentException("Null child category!");
    }
    childCategory.setParentCategory(null);
    childCategories.remove(childCategory);
  }

  @Override
  public String toString() {
    return "Category [getId()=" + getId() + ", getName()=" + getName() + ", getParentCategory()="
        + getParentCategory() + "]";
  }

}
