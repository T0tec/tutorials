package org.t0tec.tutorials.jrqas;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "USER_ID")
  private Long id;
  @Column(name = "FIRSTNAME")
  private String firstname;
  @Column(name = "LASTNAME")
  private String lastname;
  @Column(name = "USERNAME")
  private String username;
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
  private Address homeAddress;

  @OneToMany(mappedBy = "seller")
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

  public Long getId() {
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

  public String getHomeCity() {
    return homeCity;
  }

  public void setHomeCity(String homeCity) {
    this.homeCity = homeCity;
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

  public Address getHomeAddress() {
    return homeAddress;
  }

  public void setHomeAddress(Address homeAddress) {
    this.homeAddress = homeAddress;
    this.homeStreet = homeAddress.getStreet();
    this.homeZipcode = homeAddress.getZipcode();
    this.homeCity = homeAddress.getCity();
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
