package gdx.proto.events;

/**
 * Represents a drag event indicating the change in position due to dragging.
 */
public record UIDragEvent(float deltaX, float deltaY) {
}