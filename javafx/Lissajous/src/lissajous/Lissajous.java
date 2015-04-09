package lissajous;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Lissajous extends Application {

  @Override
  public void start(Stage primaryStage) {
    LissajousData data = new LissajousData();

    BorderPane root = new BorderPane();
    root.setTop(new LissajousMenu());
    ControlPanel controlPanel = new ControlPanel(data);
    root.setLeft(controlPanel);
    LissajousCanvas canvas = new LissajousCanvas(data, 200, 200);
    data.setCanvas(canvas);
    root.setCenter(canvas);
    Scene scene = new Scene(root);
    scene.getStylesheets().add("/style/LissajousCSS.css");

    primaryStage.setTitle("Lissajous figures");
    primaryStage.setScene(scene);
    primaryStage.sizeToScene();
    primaryStage.show();
  }


  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

}
