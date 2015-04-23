/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class SyncTest {

  /*  Because synchronization does hurt concurrency, you don't want to synchronize
      any more code than is necessary to protect your data. So if the scope of a method is
      more than needed, you can reduce the scope of the synchronized part to something
      less than a full method—to just a block. We call this, strangely, a synchronized block,
      and it looks like this: */
  public void doStuff() {
    System.out.println("not synchronized");
    synchronized (this) {
      System.out.println("synchronized");
    }
  }
}
