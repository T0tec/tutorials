package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Board;
import model.Status;

public class Minesweeper extends Application implements EventHandler<MouseEvent> {

  private MyButton[][] mineField;
  private Board board;
  public static final int ROWS = 11;
  public static final int COLUMNS = 11;

  @Override
  public void start(final Stage primaryStage) {
    mineField = new MyButton[ROWS][COLUMNS];
    board = new Board(ROWS, COLUMNS, 10, this);
    GridPane root = new GridPane();
    for (int i = 0; i < mineField.length; i++) {
      for (int j = 0; j < mineField.length; j++) {
        mineField[i][j] = new MyButton(i, j);
        mineField[i][j].setOnMousePressed(this);
        Image image = new Image(getClass().getResourceAsStream("/resources/images/Minesz.gif"));
        mineField[i][j].setGraphic(new ImageView(image));
        root.add(mineField[i][j], j, i);
      }

    }

    root.setAlignment(Pos.CENTER);
    Scene scene = new Scene(root);
    scene.getStylesheets().add("/view/minesweeperCSS.css");

    primaryStage.setTitle("Minesweeper");
    primaryStage.setResizable(false);
    primaryStage.sizeToScene();
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  @Override
  public void handle(MouseEvent t) {
    MyButton button = (MyButton) t.getSource();
    int row = button.getRow();
    int column = button.getColumn();
    if (t.getButton().equals(MouseButton.PRIMARY) && !board.isTileMarked(row, column)) {
      board.handleLeftClick(row, column);
    }
    if (t.getButton().equals(MouseButton.SECONDARY)) {
      board.handleRightClick(row, column);
    }

  }

  public void updateButtons() {
    for (int i = 0; i < mineField.length; i++) {
      for (int j = 0; j < mineField[0].length; j++) {
        Status state = board.getTileStatus(i, j);
        if (state == Status.CLICKED) {
          Image image =
              new Image(getClass().getResourceAsStream(
                  "/resources/images/Mines" + board.getNeighbourCount(i, j) + ".gif"));
          mineField[i][j].setGraphic(new ImageView(image));
        } else if (board.isTileMarked(i, j)) {
          Image image = new Image(getClass().getResourceAsStream("/resources/images/Minesf.gif"));
          mineField[i][j].setGraphic(new ImageView(image));
        }
      }
    }

  }

  public void gameOver() {
    for (int i = 0; i < mineField.length; i++) {
      for (int j = 0; j < mineField[i].length; j++) {
        mineField[i][j].setOnMousePressed(null);
        if (board.getTileStatus(i, j) == Status.BOMB) {
          Image image = new Image(getClass().getResourceAsStream("/resources/images/Minesb.gif"));
          mineField[i][j].setGraphic(new ImageView(image));
        } else if (board.getTileStatus(i, j) == Status.EXPLODED) {
          Image image = new Image(getClass().getResourceAsStream("/resources/images/Minesn.gif"));
          mineField[i][j].setGraphic(new ImageView(image));
        }
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
