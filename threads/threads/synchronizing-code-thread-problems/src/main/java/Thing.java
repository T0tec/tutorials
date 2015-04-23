/*
  Access to static fields should be done using static synchronized methods.
  Access to non-static fields should be done using non-static synchronized
  methods. For example:
*/

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class Thing {

  private static int staticField;
  private int nonStaticField;

  public static synchronized int getStaticField() {
    return staticField;
  }

  public static synchronized void setStaticField(
      int staticField) {
    Thing.staticField = staticField;
  }

  public synchronized int getNonStaticField() {
    return nonStaticField;
  }

  public synchronized void setNonStaticField(
      int nonStaticField) {
    this.nonStaticField = nonStaticField;
  }
}
