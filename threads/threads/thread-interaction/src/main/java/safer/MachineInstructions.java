package safer;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class MachineInstructions {
  private Shape shape;

  public MachineInstructions(Shape shape) {
    this.shape = shape;
  }

  private void doSomethingWithInstruction() {
    System.out.println("doing instructions on Shape: " + shape.getClass().getName());
  }
}
