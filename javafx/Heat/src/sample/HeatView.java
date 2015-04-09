package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class HeatView extends Application implements EventHandler<MouseEvent> {

  private Label[][] labelArray;
  private Slider top;
  private Slider right;
  private Slider bottom;
  private Slider left;
  private static final int DIM = 10;
  private static final int LABEL_DIM = 40;
  private HeatModel model;

  @Override
  public void start(Stage primaryStage) {

    model = new HeatModel(this);

    labelArray = new Label[DIM][DIM];
    GridPane root = new GridPane();

    for (int i = 0; i < DIM; i++) {
      for (int j = 0; j < DIM; j++) {
        labelArray[i][j] = new Label();
        labelArray[i][j].setPrefSize(LABEL_DIM, LABEL_DIM);
        labelArray[i][j].setStyle("-fx-background-color: red");
        root.add(labelArray[i][j], j + 1, i + 1);
      }
    }

    left = new Slider(0, 100, 0);
    left.setOrientation(Orientation.VERTICAL);
    left.setPrefWidth(LABEL_DIM * 2);
    left.setOnMouseReleased(this);
    left.setMajorTickUnit(20);
    left.setShowTickMarks(true);

    right = new Slider(0, 100, 0);
    right.setOrientation(Orientation.VERTICAL);
    right.setPrefWidth(LABEL_DIM * 2);
    right.setOnMouseReleased(this);
    right.setMajorTickUnit(20);
    right.setShowTickMarks(true);

    top = new Slider(0, 100, 0);
    top.setPrefHeight(LABEL_DIM * 2);
    top.setOnMouseReleased(this);
    top.setMajorTickUnit(20);
    top.setShowTickMarks(true);

    bottom = new Slider(0, 100, 0);
    bottom.setPrefHeight(LABEL_DIM * 2);
    bottom.setOnMouseReleased(this);
    bottom.setMajorTickUnit(20);
    bottom.setShowTickMarks(true);

    root.add(left, 0, 1, 1, DIM);
    root.add(right, DIM + 1, 1, 1, DIM);
    root.add(top, 1, 0, DIM, 1);
    root.add(bottom, 1, DIM + 1, DIM, 1);

    Scene scene = new Scene(root);

    primaryStage.sizeToScene();
    primaryStage.setTitle("Heat");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  public void updateGUI() {
    double[][][] cells = model.getCells();
    for (int i = 0; i < DIM; i++) {
      for (int j = 0; j < DIM; j++) {
        labelArray[i][j].setStyle(
            "-fx-background-color: rgb(" + (int) (cells[i + 1][j + 1][0]) + ",0," +
            (int) (255 - cells[i + 1][j + 1][0]) + ");");
      }
    }
  }

  @Override
  public void handle(MouseEvent t) {
    double[][][] cells = model.getCells();
    if (t.getSource().equals(left)) {
      for (int i = 0; i < cells.length; i++) {
        cells[i][0][0] = left.getValue() * 255 / 100;
        cells[i][0][1] = left.getValue() * 255 / 100;
      }
    } else if (t.getSource().equals(right)) {
      for (int i = 0; i < cells.length; i++) {
        cells[i][DIM + 1][0] = right.getValue() * 255 / 100;
        cells[i][DIM + 1][1] = right.getValue() * 255 / 100;
      }
    } else if (t.getSource().equals(top)) {
      for (int i = 0; i < cells.length; i++) {
        cells[0][i][0] = top.getValue() * 255 / 100;
        cells[0][i][1] = top.getValue() * 255 / 100;
      }
    } else if (t.getSource().equals(bottom)) {
      for (int i = 0; i < cells.length; i++) {
        cells[DIM + 1][i][0] = bottom.getValue() * 255 / 100;
        cells[DIM + 1][i][1] = bottom.getValue() * 255 / 100;
      }
    }
  }

  /**
   * The main() method is ignored in correctly deployed JavaFX application. main() serves only as
   * fallback in case the application can not be launched through deployment artifacts, e.g., in
   * IDEs with limited FX support. NetBeans ignores main().
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
