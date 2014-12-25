package org.t0tec.tutorials.otmwp;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BID")
public class Bid {
  @Id
  @GeneratedValue
  @Column(name = "BID_ID")
  private Long id;

  // JPA
  // the default name for the foreign key column is item plus id (if not specified),
  // separated with an underscore
  // the optional="false" attribute on the @ManyToOne would
  // also result in a NOT NULL constraint on the generated column.
  // @ManyToOne(targetEntity = org.t0tec.tutorials.mpc.Item.class)
  // @JoinColumn(name = "ITEM_ID", nullable = false, updatable = false, insertable = false)

  // Hibernate
  // This mapping is noninverse because no mappedBy attribute is present. Because JPA
  // doesn’t support persistent indexed lists (only ordered with an @OrderBy at load
  // time), you need to add a Hibernate extension annotation for index support.
  @ManyToOne
  @JoinColumn(name = "ITEM_ID", updatable = false, insertable = false)
  // , nullable = false
  private Item item;
  @Column(name = "AMOUNT")
  private BigDecimal amount;
  @Column(name = "CREATED")
  private Date created;

  public Bid() {}

  public Bid(BigDecimal amount, Date created) {
    this.amount = amount;
    this.created = created;
  }

  public Long getId() {
    return id;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  @Override
  public String toString() {
    return "Bid{" + getId() + "}, " + "amount: " + getAmount() + ", created: " + getCreated();
  }
}
