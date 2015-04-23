/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class MainThread {
  public static void main(String[] args) {
    // Get the thread running the main code (it's always called 'main')
    System.out.println("Run by: " + Thread.currentThread().getName());
  }
}
