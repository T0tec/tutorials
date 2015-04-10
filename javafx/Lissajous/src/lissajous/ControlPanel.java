package lissajous;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox {

  private static final String[] FREQ = {"1", "2", "3", "4", "5"};
  private static final int[] phases = {0, 45, 90, 135, 180};
  private final LissajousData data;

  public ControlPanel(final LissajousData data) {
    this.data = data;
    getStyleClass().add("vbox");
    RadioBtnPanel horizFreqRadioBtnPanel = new RadioBtnPanel("Horizontal frequency", FREQ);
    horizFreqRadioBtnPanel.addListener(new ChangeListener<Toggle>() {
      @Override
      public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue,
                          Toggle newValue) {
        int selection = Integer.parseInt((String) newValue.getUserData());
        data.setHorizFreq(selection);
      }
    });
    getChildren().add(horizFreqRadioBtnPanel);
    RadioBtnPanel vertFreqRadioBtnPanel = new RadioBtnPanel("Vertical frequency", FREQ);
    vertFreqRadioBtnPanel.addListener(new ChangeListener<Toggle>() {
      @Override
      public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue,
                          Toggle newValue) {
        int selection = Integer.parseInt((String) newValue.getUserData());
        data.setVertFreq(selection);
      }
    });
    getChildren().add(vertFreqRadioBtnPanel);
    getChildren().add(makePhaseChoice("Phase difference in degrees"));
  }

  private TitlePanel makePhaseChoice(String title) {
    HBox panel = new HBox();
    panel.setSpacing(10);
    panel.getChildren().add(new Label("Phase"));
    ComboBox<Integer> phaseChoice = new ComboBox<>();
    for (int i = 0; i < phases.length; i++) {
      phaseChoice.getItems().add(phases[i]);
    }
    phaseChoice.valueProperty().addListener(new ChangeListener<Integer>() {
      @Override
      public void changed(ObservableValue<? extends Integer> observable, Integer oldValue,
                          Integer newValue) {
        data.setPhaseDifference(newValue);
      }
    });
    panel.getChildren().add(phaseChoice);
    TitlePanel titlePanel = new TitlePanel(title);
    titlePanel.setContent(panel);
    return titlePanel;
  }

}
