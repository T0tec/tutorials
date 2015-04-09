package lissajous;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class LissajousMenu extends MenuBar {

  public LissajousMenu() {
    Menu menu = new Menu("File");
    MenuItem item = new MenuItem("Shutdown");
    item.setAccelerator(KeyCombination.keyCombination("Ctrl+q"));
    item.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        Platform.exit();
      }
    });
    menu.getItems().add(item);
    getMenus().add(menu);
  }

}
