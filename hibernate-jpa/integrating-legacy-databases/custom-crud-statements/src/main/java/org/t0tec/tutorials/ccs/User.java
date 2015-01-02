package org.t0tec.tutorials.ccs;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLInsert;

@Entity
@Table(name = "USER")
@SecondaryTable(name = "BILLING_ADDRESS", pkJoinColumns = {@PrimaryKeyJoinColumn(name = "USER_ID")})
// @Loader(namedQuery = "loadUser")
// @NamedNativeQueries(
// value = {
// @NamedNativeQuery(name = "loadItemsForUser",
// query = "select * from ITEM where seller_id = :seller_id", resultClass = Item.class),
// @NamedNativeQuery(
// name = "loadUser",
// query =
// "select user_id, username, firstname, lastname, password, email, rank, is_admin, default_billing_details_id as defaultBillingDetails, home_street as billingAddress.street, home_zipcode as billingAddress.zipcode, home_city as billingAddress.city from USER where user_id = :user_id",
// resultClass = User.class)})
@Loader(namedQuery = "loadUser")
@NamedNativeQuery(
    name = "loadUser",
    query = "select u.*, i.* from USER u left outer join ITEM i on u.user_id = i.seller_id where u.user_id = :user_id",
    resultClass = User.class)
@SQLInsert(
    sql = "insert into USER (USER_ID, IS_ADMIN, DEFAULT_BILLING_DETAILS_ID, EMAIL, FIRSTNAME, HOME_CITY, HOME_STREET, HOME_ZIPCODE, LASTNAME, \"PASSWORD\", RANK, USERNAME)  values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
public class User {
  @Id
  @GeneratedValue
  @Column(name = "USER_ID")
  private long id;
  @Column(name = "USERNAME", length = 16, nullable = false)
  private String username;
  @Column(name = "FIRSTNAME", length = 255, nullable = false)
  private String firstname;
  @Column(name = "LASTNAME", length = 255, nullable = false)
  private String lastname;
  @Column(name = "`PASSWORD`")
  private String password;
  @Column(name = "EMAIL")
  private String email;
  @Column(name = "RANK")
  private int ranking;
  @Column(name = "IS_ADMIN")
  private boolean admin;
  @Column(name = "HOME_STREET")
  private String homeStreet;
  @Column(name = "HOME_ZIPCODE")
  private String homeZipcode;
  @Column(name = "HOME_CITY")
  private String homeCity;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "street", column = @Column(name = "HOME_STREET",
          table = "BILLING_ADDRESS")),
      @AttributeOverride(name = "zipcode", column = @Column(name = "HOME_ZIPCODE",
          table = "BILLING_ADDRESS")),
      @AttributeOverride(name = "city", column = @Column(name = "HOME_CITY",
          table = "BILLING_ADDRESS"))})
  private Address billingAddress;

  @OneToMany(mappedBy = "user")
  @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN})
  private Set<BillingDetails> billingDetails = new HashSet<BillingDetails>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DEFAULT_BILLING_DETAILS_ID", nullable = true)
  @ForeignKey(name = "FK_DEFAULT_BILLING_DETAILS_ID")
  private BillingDetails defaultBillingDetails;

  @OneToMany(mappedBy = "seller")
  // @Loader(namedQuery = "loadItemsForUser")
  private Set<Item> itemsForSale = new HashSet<Item>();

  public User() {}

  public User(String firstname, String lastname, String username, String password, String email,
      int ranking, boolean admin) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.password = password;
    this.email = email;
    this.ranking = ranking;
    this.admin = admin;
  }

  public long getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getRanking() {
    return ranking;
  }

  public void setRanking(int ranking) {
    this.ranking = ranking;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public String getHomeStreet() {
    return homeStreet;
  }

  public void setHomeStreet(String homeStreet) {
    this.homeStreet = homeStreet;
  }

  public String getHomeZipcode() {
    return homeZipcode;
  }

  public void setHomeZipcode(String homeZipcode) {
    this.homeZipcode = homeZipcode;
  }

  public String getHomeCity() {
    return homeCity;
  }

  public void setHomeCity(String homeCity) {
    this.homeCity = homeCity;
  }

  public Address getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
    this.homeStreet = billingAddress.getStreet();
    this.homeZipcode = billingAddress.getZipcode();
    this.homeCity = billingAddress.getCity();
  }

  public Set<BillingDetails> getBillingDetails() {
    return billingDetails;
  }

  public void addBillingDetails(BillingDetails billingDetails) {
    if (billingDetails == null) {
      throw new IllegalArgumentException("Can't add a null BillingDetails.");
    }

    billingDetails.setUser(this);
    this.getBillingDetails().add(billingDetails);

    if (getBillingDetails().size() == 1) {
      setDefaultBillingDetails(billingDetails);
    }
  }

  public void setDefaultBillingDetails(BillingDetails billingDetails) {
    this.defaultBillingDetails = billingDetails;
  }

  public BillingDetails getDefaultBillingDetails() {
    return defaultBillingDetails;
  }

  public Set<Item> getItemsForSale() {
    return itemsForSale;
  }

  public void setItemsForSale(Set<Item> itemsForSale) {
    this.itemsForSale = itemsForSale;
  }

  @Override
  public String toString() {
    return "User [getId()=" + getId() + ", getFirstname()=" + getFirstname() + ", getLastname()="
        + getLastname() + ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword()
        + ", getEmail()=" + getEmail() + ", getRanking()=" + getRanking() + ", isAdmin()="
        + isAdmin() + "]";
  }

}
