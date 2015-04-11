
package model;

/**
 * The event which occurs when the model changes.
 */
public class ModelEvent {

    /**
     * Differentiate model events.
     * For now we only implement the game-over event.
     */
    public enum ModelEventType {
        GameOver
    }

    private final ModelEventType type;

    /**
     * Create a new model event.
     * @param type The model event type.
     */
    public ModelEvent(ModelEventType type) {
        this.type = type;
    }

    /**
     * @return The current model event type.
     */
    public ModelEventType getEventType() {
        return type;
    }
}
