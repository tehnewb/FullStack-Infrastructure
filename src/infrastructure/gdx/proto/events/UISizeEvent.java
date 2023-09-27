package infrastructure.gdx.proto.events;

/**
 * Represents an event indicating a change in the size of a UIActor.
 */
public record UISizeEvent(float oldWidth, float oldHeight, float newWidth, float newHeight) {
}
