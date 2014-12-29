package org.t0tec.tutorials.fkicpk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
public class Item {
  @Id
  @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long id;
  @Column(name = "NAME", nullable = false)
  private String name;

  // The primary difference between a regular @ManyToOne and this mapping is the
  // number of columns involved—again, the order is important and should be the
  // same as the order of the primary key columns. However, if you declare the referencedColumnName
  // for each column, order isn’t important, and both the source
  // and target tables of the foreign key constraint can have different column names.
  @ManyToOne
  @JoinColumns({@JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME"),
      @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEPARTMENT_ID")})
  private User seller;

  public Item() {}

  public Item(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public User getSeller() {
    return seller;
  }

  public void setSeller(User seller) {
    this.seller = seller;
  }

  @Override
  public String toString() {
    return "Item [getId()=" + getId() + ", getName()=" + getName() + ", getSeller()=" + getSeller()
        + "]";
  }

}
