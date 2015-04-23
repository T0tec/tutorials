/***
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class MyThread extends Thread {

  @Override
  public void run() {
    System.out.println("Important job running in MyThread.");

  }

  public void run(String s) {
    System.out.println("String in run(String s) is: " + s);
  }
}
