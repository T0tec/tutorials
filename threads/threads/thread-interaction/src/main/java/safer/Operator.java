package safer;


import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class Operator extends Thread {

  private final List<Class<? extends Shape>> shapeList = new ArrayList<Class<? extends Shape>>() {
    {
      add(Circle.class);
      add(Hexagon.class);
      add(Rectangle.class);
    }
  };
  private final Map<String, Class<?>[]> constructorArgs = new HashMap<String, Class<?>[]>() {
    {
      put(Circle.class.getName(),
          new Class<?>[]{int.class, int.class, int.class, Color.class});
      put(Hexagon.class.getName(),
          new Class<?>[]{int.class, int.class, int.class, Color.class});
      put(Rectangle.class.getName(),
          new Class<?>[]{int.class, int.class, int.class, int.class, Color.class});
    }
  };

  Machine machine; // assume this gets initialized

  public void run() {
    while (true) {
      Shape shape = getShapeFromUser();
      MachineInstructions job = calculateNewInstructionsFor(shape);
      machine.addJob(job);
    }
  }

  public Shape getShapeFromUser() {
    Random random = new Random();

    int left = random.nextInt(500);
    int top = random.nextInt(500);
    int width = random.nextInt(120 - 50 + 1) + 50;
    while ((left + width) > 500) {
      left = random.nextInt(500);
    }
    int height = random.nextInt(120 - 50 + 1) + 50;
    while ((top + Math.max(width, height)) > 500) {
      top = random.nextInt(500);
    }
    int type = random.nextInt(shapeList.size());
    Class<? extends Shape> shape = shapeList.get(type);

    Constructor constructor;
    try {
      constructor = shape.getConstructor(constructorArgs.get(shape.getName()));

      if (constructor.getParameterTypes().length == 4) {
        return (Shape) constructor.newInstance(left, top, width,
                                               new Color(random.nextInt(256), random.nextInt(256),
                                                         random.nextInt(256)));
      } else {
        return ((Shape) constructor.newInstance(
            left, top, width, height,
            new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))));
      }
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(e);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException(e);
    } catch (InstantiationException e) {
      throw new IllegalStateException(e);
    } catch (InvocationTargetException e) {
      throw new IllegalStateException(e);
    }
  }

  public MachineInstructions calculateNewInstructionsFor(Shape shape) {
    return new MachineInstructions(shape);
  }
}
