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
  private static final int[] FAZES = {0, 45, 90, 135, 180};
  private final LissajousData data;

  public ControlPanel(final LissajousData data) {
    this.data = data;
    getStyleClass().add("vbox");
    radioBtnPanel horizFreqRadioBtnPanel = new radioBtnPanel("Horizontal frequency", FREQ);
    horizFreqRadioBtnPanel.addListener(new ChangeListener<Toggle>() {
      @Override
      public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue,
                          Toggle newValue) {
        int selectie = Integer.parseInt((String) newValue.getUserData());
        data.setHorizFreq(selectie);
      }
    });
    getChildren().add(horizFreqRadioBtnPanel);
    radioBtnPanel vertFreqRadioBtnPanel = new radioBtnPanel("Vertical frequency", FREQ);
    vertFreqRadioBtnPanel.addListener(new ChangeListener<Toggle>() {
      @Override
      public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue,
                          Toggle newValue) {
        int selectie = Integer.parseInt((String) newValue.getUserData());
        data.setVertFreq(selectie);
      }
    });
    getChildren().add(vertFreqRadioBtnPanel);
    getChildren().add(makePhaseChoice("Phase difference in degrees"));
  }

  private TitlePanel makePhaseChoice(String titel) {
    HBox panel = new HBox();
    panel.setSpacing(10);
    panel.getChildren().add(new Label("Phase"));
    ComboBox<Integer> keuzeFaze = new ComboBox<>();
    for (int i = 0; i < FAZES.length; i++) {
      keuzeFaze.getItems().add(FAZES[i]);
    }
    keuzeFaze.valueProperty().addListener(new ChangeListener<Integer>() {
      @Override
      public void changed(ObservableValue<? extends Integer> observable, Integer oldValue,
                          Integer newValue) {
        data.setPhaseDifference(newValue);
      }
    });
    panel.getChildren().add(keuzeFaze);
    TitlePanel titlePanel = new TitlePanel(titel);
    titlePanel.setContent(panel);
    return titlePanel;
  }

}
