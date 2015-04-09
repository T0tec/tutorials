package sample;

import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// The user should choose a random number of exactly two digits.
// He then should subtract that number with the sum of the digits and the character
// that stands besides the value obtained should be your number if you click the 'show' button.
public class MindTrick extends Application {

  private Label[][] numberLabels;
  private Label[][] symbolLabels;
  private final int ROWS = 10;
  private final int COLUMNS = 10;
  private final int LABEL_WIDTH_HEIGHT = 25;
  private Random random = new Random();
  private Button btnShowResult;
  private Label lblShowResult;

  @Override
  public void start(final Stage primaryStage) {
    numberLabels = new Label[ROWS][COLUMNS];
    symbolLabels = new Label[ROWS][COLUMNS];

    GridPane centerPane = new GridPane();

    for (int i = 0; i < numberLabels.length; i++) {
      for (int j = 0; j < numberLabels[i].length; j++) {
        numberLabels[i][j] = new Label("" + (i * 10 + j));
        numberLabels[i][j].setPrefSize(LABEL_WIDTH_HEIGHT, LABEL_WIDTH_HEIGHT);
        numberLabels[i][j].setStyle(
            "-fx-border-width: 0.5 ; -fx-border-color: black ; -fx-background-color: white;");
        numberLabels[i][j].setAlignment(Pos.CENTER);
        centerPane.add(numberLabels[i][j], j * 2, i);
      }
    }

    for (int i = 0; i < symbolLabels.length; i++) {
      for (int j = 0; j < symbolLabels[i].length; j++) {
        symbolLabels[i][j] = new Label();
        symbolLabels[i][j].setPrefSize(LABEL_WIDTH_HEIGHT, LABEL_WIDTH_HEIGHT);
        symbolLabels[i][j].setStyle(
            "-fx-border-width: 0.5 ; -fx-border-color: black ; -fx-background-color: red; -fx-font-family: serif; -fx-font-size: 14");
        symbolLabels[i][j].setAlignment(Pos.CENTER);
        symbolLabels[i][j].setText(("" + (char) (random.nextInt(51) + 74)));
        centerPane.add(symbolLabels[i][j], j * 2 + 1, i);
      }
    }
    int column = COLUMNS - 2;
    for (int i = 1; i < symbolLabels.length; i++) {
      symbolLabels[i][column].setText(symbolLabels[0][COLUMNS - 1].getText());
      column--;
    }

    VBox rightPane = new VBox();
    rightPane.setSpacing(LABEL_WIDTH_HEIGHT);

    btnShowResult = new Button("Show");
    btnShowResult.setPrefWidth(LABEL_WIDTH_HEIGHT * 3);
    btnShowResult.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent t) {
        lblShowResult.setText(symbolLabels[0][COLUMNS - 1].getText());
        btnShowResult.setDisable(true);
      }
    });

    lblShowResult = new Label();
    lblShowResult.setPrefSize(LABEL_WIDTH_HEIGHT * 3, LABEL_WIDTH_HEIGHT * 3);
    lblShowResult.setAlignment(Pos.CENTER);
    lblShowResult
        .setStyle("-fx-background-color: darkgrey; -fx-font-family: serif; -fx-font-size: 24");

    rightPane.getChildren().add(btnShowResult);
    rightPane.getChildren().add(lblShowResult);

    HBox root = new HBox();
    root.setSpacing(LABEL_WIDTH_HEIGHT * 2);
    root.setPadding(
        new Insets(LABEL_WIDTH_HEIGHT, LABEL_WIDTH_HEIGHT, LABEL_WIDTH_HEIGHT, LABEL_WIDTH_HEIGHT));
    root.getChildren().add(centerPane);
    root.getChildren().add(rightPane);

    Scene scene = new Scene(root);
    primaryStage.setTitle("Mind Trick");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.sizeToScene();
    primaryStage.show();
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
