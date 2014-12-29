package org.t0tec.tutorials.fkicpk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "THIRD_USER")
@IdClass(UserId.class)
public class OptionalUser {
  @Id
  private String username;
  @Id
  private Integer departmentNr;

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

  @ManyToOne
  @JoinColumn(name = "DEPARTMENT_ID", insertable = false, updatable = false)
  private Department departement;

  public OptionalUser() {}

  public OptionalUser(String firstname, String lastname, String password, String email,
      int ranking, boolean admin) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.password = password;
    this.email = email;
    this.ranking = ranking;
    this.admin = admin;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getDepartmentNr() {
    return departmentNr;
  }

  public void setDepartmentNr(Integer departmentNr) {
    this.departmentNr = departmentNr;
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

  public Department getDepartement() {
    return departement;
  }

  public void setDepartement(Department departement) {
    this.departement = departement;
  }

  @Override
  public String toString() {
    return "OptionalUser [getUsername()=" + getUsername() + ", getDepartmentNr()="
        + getDepartmentNr() + ", getFirstname()=" + getFirstname() + ", getLastname()="
        + getLastname() + ", getPassword()=" + getPassword() + ", getEmail()=" + getEmail()
        + ", getRanking()=" + getRanking() + ", isAdmin()=" + isAdmin() + ", getDepartement()="
        + getDepartement() + "]";
  }
}
