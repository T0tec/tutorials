package lissajous;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChooseColor extends Stage {

  private final LissajousCanvas canvas;

  public ChooseColor(LissajousCanvas canvas) {
    this.canvas = canvas;
    this.initModality(Modality.NONE);
    this.setTitle("Choose color axes");

    HBox panel = new HBox();
    panel.setSpacing(10);
    panel.setAlignment(Pos.CENTER);
    panel.getChildren().add(new Label("Color axes"));
    final ColorPicker colorPicker = new ColorPicker(this.canvas.getColorAxis());
    colorPicker.setOnAction(new EventHandler() {
      @Override
      public void handle(Event t) {
        ChooseColor.this.canvas.setColorAxis(colorPicker.getValue());
      }
    });
    panel.getChildren().add(colorPicker);

    Scene scene = new Scene(panel, 180, 35);
    setScene(scene);
  }

}
