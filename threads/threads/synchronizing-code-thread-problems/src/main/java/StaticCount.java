/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class StaticCount {

  private static int count = 0;

  /*  static methods can be synchronized . There is only one copy of the static data
    you're trying to protect, so you only need one lock per class to synchronize static
    methods—a lock for the whole class. There is such a lock; every class loaded in Java
    has a corresponding instance of java.lang.Class representing that class. It's that
    java.lang.Class instance whose lock is used to protect the static methods of
    the class (if they're synchronized ). There's nothing special you have to do to
    synchronize a static method */

//  public static synchronized int getCount() {
//    return count;
//  }

  public static int getCount() {
    synchronized (StaticCount.class) {
      return count;
    }
  }

  public static void classMethod() throws ClassNotFoundException {
    Class cl = Class.forName("StaticCount");
    synchronized (cl) {
      // do stuff
    }
  }
}
