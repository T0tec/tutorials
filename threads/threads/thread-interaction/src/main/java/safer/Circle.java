package safer;


import java.awt.*;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class Circle extends Shape {

  public Circle(int x, int y, int diameter, Color color) {
    super(x, y, diameter, diameter, color);
  }
}
