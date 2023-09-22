package gdx.proto.events;

import gdx.proto.UIActor;

/**
 * Represents an event indicating a change in the visibility of a UIActor.
 */
public record UIVisbilityEvent(UIActor actor, boolean previousValue) {
}