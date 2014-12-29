package org.t0tec.tutorials.cnk;

import java.io.Serializable;

public class UserId implements Serializable {
  private String username;
  private Integer departmentNr;

  public UserId() {}

  public UserId(String username, Integer departmentNr) {
    this.username = username;
    this.departmentNr = departmentNr;
  }

  public String getUsername() {
    return username;
  }

  public Integer getDepartmentNr() {
    return departmentNr;
  }

  @Override
  public int hashCode() {
    int result;
    result = username.hashCode();
    result = 29 * result + departmentNr.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null)
      return false;
    if (!(other instanceof UserId))
      return false;
    UserId that = (UserId) other;
    return this.username.equals(that.username) && this.departmentNr.equals(that.departmentNr);
  }

  @Override
  public String toString() {
    return "UserId [getUsername()=" + getUsername() + ", getDepartmentNr()=" + getDepartmentNr()
        + "]";
  }
}
