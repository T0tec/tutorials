package org.t0tec.tutorials.auction.model;

public class User {
  private String firstname;
  private String lastname;
  private String username;
  private String password;
  private String email;
  private int ranking;
  private boolean admin;

  public User() {}

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public int getRanking() {
    return ranking;
  }

  public boolean isAdmin() {
    return admin;
  }

  @Override
  public boolean equals(Object o) {
    return false;
  }

  @Override
  public String toString() {
    return null;
  }
}
