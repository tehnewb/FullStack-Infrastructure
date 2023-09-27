package infrastructure.gdx.proto.events;

import infrastructure.gdx.proto.UIActor;

/**
 * Represents an event indicating a change in the visibility of a UIActor.
 */
public record UIVisbilityEvent(UIActor actor, boolean previousValue) {
}