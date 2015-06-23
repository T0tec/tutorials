package main;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.FileDownloadTask;


public class App extends Application {

  private MenuBar topMenu;
  private TextField urlTxtFld;
  private TextField downloadDirectoryTxtFld;

  private Button browseBtn;
  private Button downloadBtn;
  private ProgressBar downloadBar;

  @Override
  public void start(final Stage primaryStage) throws Exception {
    primaryStage.setTitle("File download application");

    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    Label urlLbl = new Label("URL:");
    grid.add(urlLbl, 0, 1);

    urlTxtFld = new TextField();
    grid.add(urlTxtFld, 1, 1);

    Label downloadDirectoryLbl = new Label("Save to directory:");
    grid.add(downloadDirectoryLbl, 0, 2);

    downloadDirectoryTxtFld = new TextField();
    grid.add(downloadDirectoryTxtFld, 1, 2);

    browseBtn = new Button("Browse");
    grid.add(browseBtn, 2, 2);

    downloadBtn = new Button("Download");

    downloadBar = new ProgressBar(0.0);

    BorderPane root = new BorderPane();
    VBox topContainer = new VBox();
    createMenu(primaryStage);
    topContainer.getChildren().add(topMenu);
    root.setTop(topContainer);

    VBox mainContainer = new VBox();
    mainContainer.getChildren().add(grid);

    HBox hbCheckBtn = new HBox(10);
    hbCheckBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbCheckBtn.getChildren().addAll(downloadBtn, downloadBar);

    grid.add(hbCheckBtn, 1, 4);
    root.setCenter(mainContainer);

    final Scene scene = new Scene(root, 500, 250);

    browseBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose download directory");
        directoryChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );
        File file = directoryChooser.showDialog(primaryStage);
        if (file != null) {
          downloadDirectoryTxtFld.setText(file.getAbsolutePath());
        }
      }
    });

    downloadBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (urlTxtFld.getText().isEmpty()) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.initOwner(primaryStage);
          alert.setTitle("Missing URL!");
          alert.setContentText("Please enter an URL!");
          alert.show();
          return;
        }

        if (downloadDirectoryTxtFld.getText().isEmpty()) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.initOwner(primaryStage);
          alert.setTitle("Missing download directory!");
          alert.setContentText("Please enter a valid download directory!");
          alert.show();
          return;
        }

        // TODO: check if URL is valid and file is online/exists (RESPONSE 200)
//        final URL url = new URL(urlTxtFld.getText().trim());
//
//        if (false) {
//          Alert alert = new Alert(Alert.AlertType.ERROR);
//          alert.initOwner(primaryStage);
//          alert.setTitle("File does not exist!");
//          alert.setContentText("The given file does not exist!");
//          alert.show();
//          return;
//        }

        final FileDownloadTask fileDownloadTask = new FileDownloadTask(urlTxtFld.getText(), downloadDirectoryTxtFld.getText());

        Thread thread = new Thread(fileDownloadTask);
        thread.setDaemon(true);
        thread.start();

        downloadBar.progressProperty().bind(fileDownloadTask.progressProperty());

        fileDownloadTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
          @Override
          public void handle(WorkerStateEvent event) {
            downloadBtn.setDisable(false);
            if (!fileDownloadTask.isCancelled()) {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.initOwner(primaryStage);
              alert.setTitle("File is download!");
              alert.setContentText("The file has been successfully downloaded!");
              alert.show();
            } else {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.initOwner(primaryStage);
              alert.setTitle("Failed to download file!");
              alert.setContentText("The file has NOT been downloaded!");
              alert.show();
            }
          }
        });
      }
    });

    primaryStage.setScene(scene);
    addDragNDrop(primaryStage);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  private void createMenu(final Stage stage) {
    topMenu = new MenuBar();

    Menu mainMenu = new Menu("Main");

    MenuItem exitMenu = createMenuItem("Exit", "Alt+x");
    exitMenu.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Platform.exit();
      }
    });

    Menu helpMenu = new Menu("Help");
    MenuItem about = createMenuItem("About", "F1");
    about.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(stage);

        VBox box = new VBox();
        box.setAlignment(Pos.TOP_LEFT);
        box.setPadding(new Insets(10));
        box.getChildren().addAll(new Text("Developed by t0tec (t0tec.olmec@gmail.com)"));
        Scene myDialogScene = new Scene(box);

        dialog.setTitle("About");
        dialog.setScene(myDialogScene);
        dialog.show();
      }
    });

    mainMenu.getItems().add(exitMenu);
    helpMenu.getItems().add(about);

    topMenu.getMenus().addAll(mainMenu, helpMenu);
  }

  private MenuItem createMenuItem(String text, String keyCombination) {
    MenuItem item = new MenuItem(text);
    item.setAccelerator(KeyCombination.keyCombination(keyCombination));
    return item;
  }

  private void addDragNDrop(final Stage stage) {
    stage.getScene().setOnDragOver(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasString()) {
          event.acceptTransferModes(TransferMode.COPY);
        } else {
          event.consume();
        }
      }
    });

    // Dropping over surface
    stage.getScene().setOnDragDropped(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
          success = true;
          urlTxtFld.setText(db.getString());
        }
        event.setDropCompleted(success);
        event.consume();
      }
    });
  }

  public static void main(String[] args) {
    launch(args);
  }
}
