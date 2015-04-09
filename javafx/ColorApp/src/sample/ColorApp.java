/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
public class ColorApp extends Application implements EventHandler<MouseEvent> {

  private Label lblRed;
  private Label lblGreen;
  private Label lblBlue;
  private Label lblColor;
  private Slider sliderRed;
  private Slider sliderGreen;
  private Slider sliderBlue;
  private CheckBox checkboxGray;
  private boolean gray = false;

  @Override
  public void start(Stage primaryStage) {
    //create sliders
    sliderRed = addSlider();
    sliderGreen = addSlider();
    sliderBlue = addSlider();
    //create labels
    lblRed = new Label("Red:");
    lblGreen = new Label("Green:");
    lblBlue = new Label("Blue:");
    //create checkbox
    checkboxGray = new CheckBox("Gray");
    checkboxGray.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent t) {
        gray = !gray;
        double gem = (sliderRed.getValue() + sliderBlue.getValue() + sliderGreen.getValue()) / 3.0;
        sliderRed.setValue(gem);
        sliderGreen.setValue(gem);
        sliderBlue.setValue(gem);
        lblColor.setStyle(
            "-fx-background-color: rgb(" + (int) sliderRed.getValue() + "," + (int) sliderGreen
                .getValue() + "," + (int) sliderBlue.getValue() + ")");

      }
    });

    lblColor = new Label();
    lblColor.setPrefSize(100, 100);
    lblColor.setStyle("-fx-background-color: rgb(0,0,0)");

    GridPane root = new GridPane();
    root.setPadding(new Insets(40, 40, 40, 40));
    root.setHgap(40);
    root.setVgap(40);
    root.add(lblRed, 0, 0);
    root.add(sliderRed, 1, 0);
    root.add(lblGreen, 0, 1);
    root.add(sliderGreen, 1, 1);
    root.add(lblBlue, 0, 2);
    root.add(sliderBlue, 1, 2);
    root.add(checkboxGray, 1, 3);
    root.add(lblColor, 2, 0, 3, 3);

    Scene scene = new Scene(root);
    primaryStage.setTitle("ColorApp");
    primaryStage.setScene(scene);
    primaryStage.sizeToScene();
    primaryStage.setResizable(false);
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

  private Slider addSlider() {
    Slider slider = new Slider(0, 255, 0);
    slider.setMajorTickUnit(100);
    slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);
    slider.setBlockIncrement(1);
    slider.setOnMouseReleased(this);
    return slider;
  }

  @Override
  public void handle(MouseEvent t) {
    if (gray) {
      if (t.getSource().equals(sliderRed)) {
        sliderBlue.setValue(sliderRed.getValue());
        sliderGreen.setValue(sliderRed.getValue());
      } else if (t.getSource().equals(sliderGreen)) {
        sliderRed.setValue(sliderGreen.getValue());
        sliderBlue.setValue(sliderGreen.getValue());
      } else {
        sliderRed.setValue(sliderBlue.getValue());
        sliderGreen.setValue(sliderBlue.getValue());
      }
    }
    lblColor.setStyle(
        "-fx-background-color: rgb(" + (int) sliderRed.getValue() + "," + (int) sliderGreen.getValue()
        + "," + (int) sliderBlue.getValue() + ")");
  }
}
