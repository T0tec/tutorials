package lissajous;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class radioBtnPanel extends TitlePanel {

  private final ToggleGroup choices;

  public radioBtnPanel(String title, String[] waarden) {
    super(title);
    choices = new ToggleGroup();
    setContent(makeChoiceGroup(waarden));
  }

  private HBox makeChoiceGroup(String[] values) {
    HBox paneel = new HBox();
    paneel.setSpacing(10);
    for (String value : values) {
      RadioButton choice = new RadioButton(value);
      choice.setToggleGroup(choices);
      choice.setUserData(value);
      paneel.getChildren().add(choice);
    }
    return paneel;
  }

  public void addListener(ChangeListener<Toggle> cl) {
    choices.selectedToggleProperty().addListener(cl);
  }
}
