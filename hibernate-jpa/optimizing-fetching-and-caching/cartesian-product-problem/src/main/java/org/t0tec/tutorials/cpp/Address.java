package org.t0tec.tutorials.cpp;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

  private String street;
  private String zipcode;
  private String city;

  public Address() {}

  public Address(String street, String zipcode, String city) {
    this.street = street;
    this.zipcode = zipcode;
    this.city = city;
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
    return "Address [getStreet()=" + getStreet() + ", getZipcode()=" + getZipcode()
           + ", getCity()=" + getCity() + "]";
  }

}
