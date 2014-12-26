package org.t0tec.tutorials.otmwjwac;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CATEGORIZED_ITEM")
public class CategorizedItem implements Serializable, Comparable<Object> {

  /**
   * Emedded composite identifier class that represents the primary key columns of the many-to-many
   * join table.
   */
  @Embeddable
  public static class Id implements Serializable {
    @Column(name = "CATEGORY_ID")
    private Long categoryId;
    @Column(name = "ITEM_ID")
    private Long itemId;

    public Id() {}

    public Id(Long categoryId, Long itemId) {
      this.categoryId = categoryId;
      this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Id) {
        Id that = (Id) o;
        return this.categoryId.equals(that.categoryId) && this.itemId.equals(that.itemId);
      } else {
        return false;
      }
    }

    @Override
    public int hashCode() {
      return categoryId.hashCode() + itemId.hashCode();
    }
  }

  @EmbeddedId
  private Id id;

  @Column(name = "ADDED_BY_USER", nullable = false, length = 16)
  private String username; // This could also be a many-to-one association to User

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "ADDED_ON")
  private final Date dateAdded = new Date();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ITEM_ID", insertable = false, updatable = false)
  @org.hibernate.annotations.ForeignKey(name = "FK_CATEGORIZED_ITEM_ITEM_ID")
  private Item item;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CATEGORY_ID", insertable = false, updatable = false)
  @org.hibernate.annotations.ForeignKey(name = "FK_CATEGORIZED_ITEM_CATEGORY_ID")
  private Category category;

  /**
   * No-arg constructor for JavaBean tools
   */
  public CategorizedItem() {}

  /**
   * Full constructor, the Category and Item instances have to have an identifier value, they have
   * to be in detached or persistent state. This constructor takes care of the bidirectional
   * relationship by adding the new instance to the collections on either side of the many-to-many
   * association (added to the collections).
   */
  public CategorizedItem(String username, Category category, Item item) {
    this.username = username;

    this.category = category;
    this.item = item;

    // Set primary key
    this.id = new Id(category.getId(), item.getId());

    // Guarantee referential integrity
    category.getCategorizedItems().add(this);
    item.getCategorizedItems().add(this);
  }

  // ********************** Accessor Methods ********************** //

  public Id getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public Date getDateAdded() {
    return dateAdded;
  }

  public Category getCategory() {
    return category;
  }

  public Item getItem() {
    return item;
  }

  // ********************** Common Methods ********************** //

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    final CategorizedItem that = (CategorizedItem) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public int compareTo(Object o) {
    // CategorizedItems are sorted by date
    if (o instanceof CategorizedItem)
      return getDateAdded().compareTo(((CategorizedItem) o).getDateAdded());
    return 0;
  }

  @Override
  public String toString() {
    return "Added by: '" + getUsername() + "', " + "On Date: '" + getDateAdded();
  }

}
