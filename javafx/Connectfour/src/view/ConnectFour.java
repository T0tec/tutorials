package view;

import java.net.URL;

import config.Config;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Board;
import model.Model;
import model.ModelEvent;
import model.ModelEventListener;
import model.Player;

/**
 * Implement a JavaFX GUI for the Connect Four game.
 */
public class ConnectFour extends Application {

    private Board board;
    private BorderPane root;

    @Override
    public void start(final Stage primaryStage) {

        // Communicate options for outer window to the window manager.
        primaryStage.setTitle(Config.GAME_TITLE);
        primaryStage.setResizable(true);

        root = new BorderPane();
        Scene scene = new Scene(root, Config.SCENE_WIDTH, Config.SCENE_HEIGHT, true);
        // Default background is black.
        scene.setFill(Color.BLACK);

        // Load CSS resources
        URL resource = getClass().getResource(Config.CSS_STYLES);
        if (resource != null) {
            scene.getStylesheets().add(resource.toExternalForm());
        } else {
            System.err.println("Resource " + Config.CSS_STYLES + " not found!");
        }

        // A board manages a grid of cells.
        board = new Board(new Player(), new Model()); // was final
        root.setCenter(board.getPane());

        // Add a button to the top left corner.
        Button newGameButton = createNewGameButton();
        root.setTop(newGameButton);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Listen to requests for a new game
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                startNewGame();
            }
        });

        // Get a notification when the game has ended.
        board.getModel().subscribe(new ModelEventListener() {
            @Override
            public void eventOccurred(ModelEvent e) {
                if (e.getEventType() == ModelEvent.ModelEventType.GameOver) {
                    String message = " You won! Game over. ";
                    System.out.println(message);
                    Text text = new Text(message);
                    text.setId("fancytext");
                    root.setBottom(text);
                }
            }
        });

    }

    /**
     * Create a button to reset the game and start all over.
     *
     * @return the created button
     */
    private Button createNewGameButton() {
        Button newGameButton = new Button(Config.NEW_GAME_BUTTON);
        newGameButton.setTranslateY(10);
        newGameButton.setTranslateX(10);

        // TODO: move stuff like this to the CSS resources.
        DropShadow effect = new DropShadow();
        effect.setColor(Color.BLUE);
        newGameButton.setEffect(effect);

        return newGameButton;
    }

    private void startNewGame() {
        board = new Board(new Player(), new Model());
        root.setCenter(board.getPane());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
