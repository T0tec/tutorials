package org.t0tec.tutorials.fkicpk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEPARTMENT")
public class Department {
  @Id
  @Column(name = "DEPARTMENT_ID")
  @GeneratedValue
  private Integer id;
  @Column(name = "NAME", length = 255, nullable = false)
  private String name;

  public Department() {}

  public Department(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Departement [getName()=" + getName() + ", getId()=" + getId() + "]";
  }
}
