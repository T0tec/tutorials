package org.t0tec.tutorials.cnk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User implements Serializable {

  @Id
  private UserId userId;
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

  public User() {}

  public User(String firstname, String lastname, String username, String password, String email,
      int ranking, boolean admin) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.password = password;
    this.email = email;
    this.ranking = ranking;
    this.admin = admin;
  }

  public UserId getUserId() {
    return userId;
  }

  public void setUserId(UserId userId) {
    this.userId = userId;
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
    return "User [getUserId()=" + getUserId() + ", getFirstname()=" + getFirstname()
        + ", getLastname()=" + getLastname() + ", getPassword()=" + getPassword() + ", getEmail()="
        + getEmail() + ", getRanking()=" + getRanking() + ", isAdmin()=" + isAdmin() + "]";
  }
}
