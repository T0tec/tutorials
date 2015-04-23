/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class NameRunnable implements Runnable {

  public void run() {
    System.out.println("NameRunnable is running");
    for (int x = 1; x <= 3; x++) {
      System.out.println("Run by: " + Thread.currentThread().getName() + " x, is: " + x);
    }

  }
}
