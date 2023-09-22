package gdx.proto.events;

/**
 * Represents a scroll event indicating the change in scroll wheel values.
 */
public record UIScrollEvent(float deltaX, float deltaY) {
}