/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class SynchronizedThread extends Thread {

  private StringBuffer sb;

  public SynchronizedThread(StringBuffer sb) {
    this.sb = sb;
  }

  @Override
  public void run() {
    synchronized (this) {
      for (int i = 1; i <= 100; i++) {
        System.out.println(this.sb);
      }
      char c = this.sb.charAt(0);
      this.sb.setCharAt(0, ++c);
    }
  }
}
