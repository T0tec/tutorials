package lissajous;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class RadioBtnPanel extends TitlePanel {

  private final ToggleGroup choices;

  public RadioBtnPanel(String title, String[] values) {
    super(title);
    choices = new ToggleGroup();
    setContent(makeChoiceGroup(values));
  }

  private HBox makeChoiceGroup(String[] values) {
    HBox panel = new HBox();
    panel.setSpacing(10);
    for (String value : values) {
      RadioButton choice = new RadioButton(value);
      choice.setToggleGroup(choices);
      choice.setUserData(value);
      panel.getChildren().add(choice);
    }
    return panel;
  }

  public void addListener(ChangeListener<Toggle> cl) {
    choices.selectedToggleProperty().addListener(cl);
  }
}
