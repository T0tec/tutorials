package org.t0tec.tutorials.jatist;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@SecondaryTable(name = "BILLING_ADDRESS", pkJoinColumns = {@PrimaryKeyJoinColumn(name = "USER_ID")})
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

  @Override
  public String toString() {
    return "User [getId()=" + getId() + ", getFirstname()=" + getFirstname() + ", getLastname()="
        + getLastname() + ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword()
        + ", getEmail()=" + getEmail() + ", getRanking()=" + getRanking() + ", isAdmin()="
        + isAdmin() + ", getHomeStreet()=" + getHomeStreet() + ", getHomeZipcode()="
        + getHomeZipcode() + ", getHomeCity()=" + getHomeCity() + "]";
  }


}
