package lissajous;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class LissajousCanvas extends Canvas {

  private final LissajousData data;
  private static final int TALLY_POINTS = 200;
  private static final int RAND = 5;
  private final GraphicsContext gc;
  private final int factor;
  private Color colorAxis;
  private ContextMenu menu;

  public LissajousCanvas(LissajousData data, double width, double height) {
    super(width, height);
    this.data = data;
    gc = this.getGraphicsContext2D();
    factor = Math.min((int) getWidth(), (int) getHeight()) - 2 * RAND;
    colorAxis = Color.RED;
    drawAxes();
    getStyleClass().add("canvas");
    makeContextMenu();
    final LissajousCanvas canvas = this;
    addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                      @Override
                      public void handle(MouseEvent e) {
                        if (e.getButton() == MouseButton.SECONDARY) {
                          menu.show(canvas, e.getScreenX(), e.getScreenY());
                        }
                      }
                    });
  }

  private void drawAxes() {
    gc.setStroke(colorAxis);
    gc.strokeLine(0, factor / 2 + RAND, factor + 2 * RAND, factor / 2 + RAND);
    gc.strokeLine(factor / 2 + RAND, 0, factor / 2 + RAND, factor + 2 * RAND);
  }

  private void drawFigure() {
    int A = data.getVertFreq();
    int B = data.getHorizFreq();
    int fGraden = data.getPhaseDifference();
    if (A != LissajousData.HF_DEF && B != LissajousData.VF_DEF && fGraden != LissajousData.F_DEF) {
      double f = Math.PI * fGraden / 180;
      double[] x = new double[TALLY_POINTS];
      double[] y = new double[TALLY_POINTS];
      for (int i = 0; i < TALLY_POINTS; i++) {
        x[i] = RAND + factor / 2 * (1 + Math.sin(i * A * 2 * Math.PI / TALLY_POINTS));
        y[i] = RAND + factor / 2 * (1 - Math.sin(f + (i * B * 2 * Math.PI / TALLY_POINTS)));
      }
      gc.setStroke(Color.BLACK);
      gc.strokePolygon(x, y, x.length);
    }
  }

  public void draw() {
    gc.clearRect(0, 0, factor + 2 * RAND, factor + 2 * RAND);
    drawAxes();
    drawFigure();
  }

  public Color getColorAxis() {
    return this.colorAxis;
  }

  public void setColorAxis(Color colorAxis) {
    this.colorAxis = colorAxis;
    draw();
  }

  private void makeContextMenu() {
    menu = new ContextMenu();
    MenuItem item = new MenuItem("Kleur Assen");
    final LissajousCanvas canvas = this;
    item.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        new ChooseColor(canvas).show();
      }
    });
    menu.getItems().add(item);
  }
}
