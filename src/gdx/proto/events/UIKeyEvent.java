package gdx.proto.events;

import gdx.proto.UIActor;

/**
 * Represents a keyboard event associated with a UIActor.
 */
public record UIKeyEvent(UIActor actor, KeyStage stage, int keyCode) {

    /**
     * Enumerates the stages of a keyboard event.
     */
    public static enum KeyStage {
        UP,     // A key was released.
        DOWN,   // A key was pressed.
        TYPED,  // A key was typed.
    }
}
