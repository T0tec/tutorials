/**
 * Extending Threads is not advised.
 * Only if you want more specialized thread-specific behaviour
 * Also you can't extend any other (classes) behaviour.
 *
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class MyThread extends Thread {

  @Override
  public void run() {
    // Job code goes here
    System.out.println("Important job running in MyThread.");

  }

  public void run(String s) {
    // This code will be IGNORED by the Thread class unless you call it yourself.
    // And if you call the method yourself it will still not run in a
    // new thread of execution with a separate call stack.
    // It will happen in the same call stack, just like any other method call.
    System.out.println("String in run(String s) is: " + s);
  }
}
