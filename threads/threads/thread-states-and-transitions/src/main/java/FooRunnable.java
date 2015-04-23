/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class FooRunnable implements Runnable {

  public void run() {
    System.out.println("Important job running in " + FooRunnable.class.getName());
    System.out.println("Priority is: " + Thread.currentThread().getPriority());
  }
}
