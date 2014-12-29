package org.t0tec.tutorials.nk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "USER")
@org.hibernate.annotations.Entity(optimisticLock = org.hibernate.annotations.OptimisticLockType.ALL)
public class User {
  @Id
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

  @Version
  @Column(name = "OBJ_VERSION")
  private int version = 0;

  public User() {
    this.version = 0;
  }

  public User(String firstname, String lastname, String username, String password, String email,
      int ranking, boolean admin) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.password = password;
    this.email = email;
    this.ranking = ranking;
    this.admin = admin;
    this.version = 0;
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

  public int getVersion() {
    return version;
  }

  @Override
  public String toString() {
    return "User [getFirstname()=" + getFirstname() + ", getLastname()=" + getLastname()
        + ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword() + ", getEmail()="
        + getEmail() + ", getRanking()=" + getRanking() + ", isAdmin()=" + isAdmin() + "]";
  }

}
