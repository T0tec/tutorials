/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class SynchronizedThreadsTest {

  public static void main(String[] args) {
    StringBuffer sb = new StringBuffer("A");

    SynchronizedThread one = new SynchronizedThread(sb);
    SynchronizedThread two = new SynchronizedThread(sb);
    SynchronizedThread three = new SynchronizedThread(sb);

    one.start();
    two.start();
    three.start();
  }
}
