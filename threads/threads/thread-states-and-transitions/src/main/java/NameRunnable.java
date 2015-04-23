/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class NameRunnable implements Runnable {

  public void run() {
    for (int x = 1; x <= 4; x++) {
      System.out.println("Run by: " + Thread.currentThread().getName());

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
    }

  }
}
