package org.t0tec.tutorials.lelap;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
public class Item {
  @Id
  @GeneratedValue
  // TODO: solves that getId executes a select statement when you don't want it
  // url: http://256.com/gray/docs/misc/hibernate_lazy_field_access_annotations.shtml
  @AccessType("property")
  @Column(name = "ITEM_ID")
  private Long id;
  @Column(name = "NAME", nullable = false)
  private String name;
  @Column(name = "DESCRIPTION")
  private String description;

  // The collection is no longer initialized if you call size(), contains() or isEmpty()
  @LazyCollection(
      org.hibernate.annotations.LazyCollectionOption.EXTRA
  )
  // The FetchType.EAGER provides the same guarantees as lazy="false"
  @OneToMany(mappedBy = "item", orphanRemoval = true) // fetch = FetchType.EAGER
  @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE,
      org.hibernate.annotations.CascadeType.REMOVE})
  private Set<Bid> bids = new HashSet<Bid>();


  @ManyToOne
  @JoinColumn(name="SELLER_ID", nullable = false, updatable = false)
  //  To enable interception, the bytecode of your classes must be instrumented after
  //  compilation, before runtime. Hibernate provides an Ant task for that purpose:
  //  <target name="instrument" depends="compile">
  //    <taskdef name="instrument"
  //    classname=
  //      "org.hibernate.tool.instrument.cglib.InstrumentTask"
  //    classpathref="project.classpath"/>
  //    <instrument verbose="true">
  //      <fileset dir="${build.dir}/my/entity/package/">
  //      <include name="*.class"/>
  //      </fileset>
  //    </instrument>
  //  </target>
  @org.hibernate.annotations.LazyToOne(
      org.hibernate.annotations.LazyToOneOption.NO_PROXY
  )
  private User seller;

  public Item() {}

  public Item(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  private void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setBids(Set<Bid> bids) {
    this.bids = bids;
  }

  public Set<Bid> getBids() {
    return bids;
  }

  public void addBid(Bid bid) {
    bid.setItem(this);
    bids.add(bid);
  }

  public User getSeller() {
    return seller;
  }

  public void setSeller(User seller) {
    this.seller = seller;
  }

  @Override
  public String toString() {
    return "Item{" +
           "bids=" + bids +
           ", id=" + id +
           ", name='" + name + '\'' +
           ", description='" + description + '\'' +
           '}';
  }
}
