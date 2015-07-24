package org.olmec.lambdas;

import org.olmec.model.MyRunnable;
import org.olmec.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class Main {

  public static void main(String[] args) {
    Callable c = () -> new MyRunnable();

    new Thread(() -> new MyRunnable().run()).start();

    Main main = new Main();
    main.methodReference();
  }

  public void problem() {
    // External iteration
    List<Student> students = getStudents();
    double highestScore = 0.0;
    for (Student s : students) {
      if (s.getGradYear() == 2011) {
        if (s.getScore() > highestScore)
          highestScore = s.getScore();
      }
    }
  }

  public void innerClass() {
    // Internal iteration with inner classes
    List<Student> students = getStudents();
//    double highestScore = students
//        .filter(new Predicate<Student>() {
//          public boolean op(Student s) {
//            return s.getGradYear() == 2011;
//          }
//        })
//        .map(new Mapper<Student,Double>() {
//          public Double extract(Student s) {
//            return s.getScore();
//          }
//        })
//        .max();
  }

  public void withLambdas() {
    // Internal iteration with lambdas
    List<Student> students = getStudents();
//    double highestScore = students.stream()
//        .filter(Student s -> s.getGradYear() == 2011)
//        .map(Student s -> s.getScore())
//        .max();
  }

  public void methodReference() {
    List<Student> students = getStudents();
    students.forEach(System.out::println);
  }

  public List<Student> getStudents() {
    List<Student> students = new ArrayList<>();
    students.add(new Student("Hans", 2010, 3.3));
    students.add(new Student("Ruth", 2011, 4.0));
    students.add(new Student("Heinz", 2012, 3.2));
    students.add(new Student("Cornelia", 2011, 4.1));
    students.add(new Student("Werner", 2010, 3.1));
    students.add(new Student("Lydia", 2011, 4.3));
    students.add(new Student("Anna", 2009, 2.9));
    students.add(new Student("Stefan", 2011, 3.7));
    students.add(new Student("Martin", 2010, 3.4));

    return students;
  }
}
