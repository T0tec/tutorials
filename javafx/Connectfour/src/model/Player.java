package model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

// Encapsulate player state, like color
public class Player {

    // Color property for current player.
    private final SimpleObjectProperty<Color> colorProperty
            = new SimpleObjectProperty<>(Color.YELLOW);

    /**
     * @return the color of the current player.
     */
    Color getColor() {
        return colorProperty.get();
    }

    /**
     * @return the color of the opposite player
     */
    Color invertedColor() {
        Color cs[] = {Color.YELLOW, Color.RED};
        return cs[getColor() == cs[0] ? 1 : 0];
    }

    /**
     * Change playing sides by switching colors between yellow and red.
     */
    void switchSides() {
        colorProperty.set(invertedColor());
    }
}

