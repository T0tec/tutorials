package org.olmec.model;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class MyRunnable implements Runnable {

  @Override
  public void run() {
    System.out.println("Running in MyRunnable!");
  }
}
