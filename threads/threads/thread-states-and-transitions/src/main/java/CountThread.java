/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class CountThread extends Thread {

  @Override
  public void run() {
    for (int i = 1; i <= 100; i++) {
      System.out.println("i: " + i);
      if (i % 10 == 0) {
        System.out.println("No remainder numbers when divided by 10!");
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
    }
  }
}
