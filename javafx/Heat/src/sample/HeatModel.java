package sample;

import javafx.animation.AnimationTimer;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class HeatModel {

  private double[][][] cells;
  private AnimationTimer animation;

  public HeatModel(final HeatView view) {
    initCells();
    animation = new AnimationTimer() {
      @Override
      public void handle(long l) {
        changeCells();
        view.updateGUI();
      }
    };
    animation.start();
  }

  private void initCells() {

    cells = new double[12][12][2];
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[0].length; j++) {
        cells[i][j][0] = 0;
        cells[i][j][1] = 0;
      }
    }
  }

  private void changeCells() {
    for (int i = 1; i < cells.length - 1; i++) {
      for (int j = 1; j < cells[0].length - 1; j++) {
        cells[i][j][1] =
            (cells[i + 1][j][0] + cells[i - 1][j][0] + cells[i][j + 1][0] + cells[i][j - 1][0]) / 4;
      }
    }

    for (int i = 1; i < cells.length - 1; i++) {
      for (int j = 1; j < cells[0].length - 1; j++) {
        cells[i][j][0] = cells[i][j][1];

      }

    }
  }

  public double[][][] getCells() {
    return cells;
  }
}
