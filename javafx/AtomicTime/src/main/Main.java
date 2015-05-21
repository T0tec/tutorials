package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.AtomicTimeClockParser;

public class Main extends Application {
  private Label atomicTimeTxt;
  private Button refreshBtn;

  @Override
  public void start(Stage primaryStage) throws Exception {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(15, 15, 15, 15));

    Label atomicTimeLbl = new Label("Atomic Time:");
    grid.add(atomicTimeLbl, 0, 1);

    atomicTimeTxt = new Label();
    atomicTimeTxt.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 13));
    atomicTimeTxt.setText(new AtomicTimeClockParser().getAtomicTime());
    grid.add(atomicTimeTxt, 1, 1);

    VBox mainContainer = new VBox();
    mainContainer.getChildren().add(grid);

    HBox hbCheckBtn = new HBox(10);
    hbCheckBtn.setAlignment(Pos.BOTTOM_RIGHT);
    refreshBtn = new Button("Refresh");
    refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        atomicTimeTxt.setText(new AtomicTimeClockParser().getAtomicTime());
      }
    });

    hbCheckBtn.getChildren().addAll(refreshBtn);

    grid.add(hbCheckBtn, 1, 4);

    BorderPane root = new BorderPane();
    root.setCenter(mainContainer);
    final Scene scene = new Scene(root, 250, 125);

    primaryStage.setTitle("Atomic Time (fetched from time.is/just)");
    primaryStage.setScene(scene);
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
