package org.t0tec.tutorials.mtma;

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

  // In Hibernate XML you can also switch to an <idbag> with a separate primary key
  // column on the join table

  // As usual with an <idbag> mapping, the primary key is a surrogate key column,
  // CATEGORY_ITEM_ID. Duplicate links are therefore allowed; the same Item can be
  // added twice to a Category. (This doesn’t seem to be a useful feature.) With annotations,
  // you can switch to an identifier bag with the Hibernate @CollectionId:

  // @ManyToMany
  // @CollectionId(columns = @Column(name = "CATEGORY_ITEM_ID"),
  // type = @org.hibernate.annotations.Type(type = "long"), generator = "sequence")
  // @JoinTable(name = "CATEGORY_ITEM", joinColumns = {@JoinColumn(name = "CATEGORY_ID")},
  // inverseJoinColumns = {@JoinColumn(name = "ITEM_ID")})
  // private Collection<Item> items = new ArrayList<Item>();


  // You may even switch to an indexed collection (a map or list) in a many-to-many
  // association. The following example maps a list in Hibernate XML:

  // The primary key of the link table is a composite of the CATEGORY_ID and
  // DISPLAY_POSITION columns; this mapping guarantees that the position of each
  // Item in a Category is persistent.

  // @ManyToMany
  // @JoinTable(name = "CATEGORY_ITEM", joinColumns = {@JoinColumn(name = "CATEGORY_ID")},
  // inverseJoinColumns = {@JoinColumn(name = "ITEM_ID")})
  // @org.hibernate.annotations.IndexColumn(name = "DISPLAY_POSITION")
  // private List<Item> items = new ArrayList<Item>();

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
