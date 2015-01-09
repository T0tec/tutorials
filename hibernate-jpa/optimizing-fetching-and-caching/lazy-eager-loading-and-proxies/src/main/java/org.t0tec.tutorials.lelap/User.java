package org.t0tec.tutorials.lelap;

import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
// Disabling proxy generation for an entity has serious consequences. All of these
//    operations require a database hit:
//    User user = (User) session.load(User.class, new Long(123));
//    User user = em.getReference(User.class, new Long(123));
// They no longer return proxies!
@Proxy(lazy = false)
public class User {
  @Id
  @GeneratedValue
  @Column(name = "USER_ID")
  private long id;
  @Column(name = "USERNAME")
  private String username;
  @Column(name = "FIRSTNAME")
  private String firstname;
  @Column(name = "LASTNAME")
  private String lastname;
  @Column(name = "`PASSWORD`")
  private String password;
  @Column(name = "EMAIL")
  private String email;
  @Column(name = "RANK", nullable = true)
  private int ranking;
  @Column(name = "IS_ADMIN", nullable = true)
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

  public long getId() {
    return id;
  }

  private void setId(long id) {
    this.id = id;
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
    return "User{" +
           "lastname='" + lastname + '\'' +
           ", password='" + password + '\'' +
           ", ranking=" + ranking +
           ", username='" + username + '\'' +
           ", id=" + id +
           ", firstname='" + firstname + '\'' +
           ", email='" + email + '\'' +
           ", admin=" + admin +
           '}';
  }
}
