package safer;

import java.awt.*;

public abstract class Shape {

  private final int x;
  private final int y;
  private final int width;
  private final int height;
  private final Color color;

  public Shape(int x, int y, int width, int height, Color color) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = color;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public Color getColor() {
    return this.color;
  }
}
