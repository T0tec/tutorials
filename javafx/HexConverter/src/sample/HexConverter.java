package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class HexConverter extends Application {

  private Button convertBtn;
  private TextField txtFldHexValue;
  private TextField txtFldDecValue;

  private static final int HORIZONTAL_GAP = 8;

  @Override
  public void start(Stage primaryStage) {
    convertBtn = new Button();
    convertBtn.setText("Convert");
    convertBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          txtFldDecValue.setText(String.valueOf(Integer.parseInt(txtFldHexValue.getText().trim(), 16)));
        } catch (Exception e) {
          txtFldHexValue.setText("");
          txtFldDecValue.setText("");
        }
      }
    });

    txtFldHexValue = new TextField();
    txtFldHexValue.setPrefColumnCount(3);

    txtFldDecValue = new TextField();
    txtFldDecValue.setPrefColumnCount(10);
    txtFldDecValue.setEditable(false);

    HBox root = new HBox();
    root.setAlignment(Pos.CENTER);
    root.setSpacing(10);
    root.setPadding(new Insets(HORIZONTAL_GAP, HORIZONTAL_GAP, HORIZONTAL_GAP, HORIZONTAL_GAP));
    root.getChildren().add(new Label("Hexadecimal number:"));
    root.getChildren().add(txtFldHexValue);
    root.getChildren().add(new Label("Decimal number:"));
    root.getChildren().add(txtFldDecValue);
    root.getChildren().add(convertBtn);

    Scene scene = new Scene(root);

    primaryStage.setTitle("Hex Converter");
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
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
