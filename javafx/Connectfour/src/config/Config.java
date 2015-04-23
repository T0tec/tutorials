
package config;

/**
 * Configurable global settings.
 */
public interface Config {
    // Configurable strings
    // TODO: move these to a properties file or resource bundle
    public static final String GAME_TITLE = "Connect Four";
    public static final String NEW_GAME_BUTTON = "New Game";
    public static final String CSS_STYLES = "/styles/styles.css";

    // Board dimensions in cells.
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;

    // Dimension in pixels of a single board square.
    public static final int CELL = 100;
    // The radius of a played disk
    public static final int DISK = CELL / 2 - 3;

    // The time for one animation in milliseconds
    public static final int ANIM = 1000;

    // Window dimensions
    public static final int SCENE_WIDTH = 900;
    public static final int SCENE_HEIGHT = 900;
}
