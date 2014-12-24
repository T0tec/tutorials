package org.t0tec.tutorials.otofka;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class Address {
  @Id
  @GeneratedValue
  @Column(name = "ADDRESS_ID")
  private long id;
  @OneToOne(mappedBy = "shippingAddress")
  private User user;
  @Column(name = "STREET", length = 255, nullable = false)
  private String street;
  @Column(name = "ZIPCODE", length = 255, nullable = false)
  private String zipcode;
  @Column(name = "CITY", length = 255, nullable = false)
  private String city;

  public Address() {}

  public Address(String street, String zipcode, String city) {
    this.street = street;
    this.zipcode = zipcode;
    this.city = city;
  }

  public long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public String toString() {
    return "Address [getId()=" + getId() + ", getStreet()=" + getStreet() + ", getZipcode()="
        + getZipcode() + ", getCity()=" + getCity() + "]";
  }

}
