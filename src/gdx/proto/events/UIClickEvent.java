package gdx.proto.events;

/**
 * Represents a click event on a UIActor.
 */
public record UIClickEvent(ClickStage stage, int pointer, int button, float x, float y) {

    /**
     * Enumerates the stages of a click event.
     */
    public enum ClickStage {
        UP, // The button was released.
        DOWN, // The button was pressed.
    }

    /**
     * Enumerates the available pointer buttons.
     */
    public enum Pointer {
        LEFT,   // Left mouse button.
        MIDDLE, // Middle mouse button.
        RIGHT   // Right mouse button.
    }
}