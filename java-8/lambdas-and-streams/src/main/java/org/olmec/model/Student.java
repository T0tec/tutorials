package org.olmec.model;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class Student {

  private String name;
  private int gradYear;
  private double score;

  public Student(String name, int gradYear, double score) {
    this.name = name;
    this.gradYear = gradYear;
    this.score = score;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getGradYear() {
    return this.gradYear;
  }

  public void setGradYear(int gradYear) {
    this.gradYear = gradYear;
  }

  public double getScore() {
    return this.score;
  }

  public void setScore(double score) {
    this.score = score;
  }

  @Override
  public String toString() {
    return "Student{" +
           "name='" + name + '\'' +
           ", gradYear=" + gradYear +
           ", score=" + score +
           '}';
  }
}
