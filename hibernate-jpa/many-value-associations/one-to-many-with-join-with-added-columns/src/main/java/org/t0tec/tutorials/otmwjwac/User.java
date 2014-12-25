package org.t0tec.tutorials.otmwjwac;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {
  @Id
  @GeneratedValue
  @Column(name = "USER_ID")
  private Long id;
  @Column(name = "FIRSTNAME", length = 255, nullable = false)
  private String firstname;
  @Column(name = "LASTNAME", length = 255, nullable = false)
  private String lastname;
  @Column(name = "USERNAME", length = 16, nullable = false, unique = true)
  private String username;
  @Column(name = "`PASSWORD`", length = 12, nullable = false)
  private String password;
  @Column(name = "EMAIL", length = 255, nullable = false)
  private String email;
  @Column(name = "RANK", nullable = false)
  private int ranking;
  @Column(name = "IS_ADMIN", nullable = false)
  private boolean admin;

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

  @Override
  public String toString() {
    return "User [getId()=" + getId() + ", getFirstname()=" + getFirstname() + ", getLastname()="
        + getLastname() + ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword()
        + ", getEmail()=" + getEmail() + ", getRanking()=" + getRanking() + ", isAdmin()="
        + isAdmin() + "]";
  }
}
