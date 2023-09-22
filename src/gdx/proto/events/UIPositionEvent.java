package gdx.proto.events;

/**
 * Represents an event indicating a change in the position of a UIActor.
 */
public record UIPositionEvent(float oldX, float oldY, float newX, float newY) {
}