package event;

/**
 * Events are observed by {@code EventObservers} and trigger when posted to the
 * {@code EventContainer}.
 *
 * @author Albert Beaupre
 * @see event.EventBus
 * @see event.EventObserver
 */
public class Event {

    private boolean consumed; // Flag used to block observers

    /**
     * Consumes this {@code Event} which means any further observers cannot trigger
     * from this event.
     */
    public void consume() {
        this.consumed = true;
    }

    /**
     * Returns the current state of the consumed flag.
     *
     * @return true if consumed; false otherwise
     */
    public boolean isConsumed() {
        return consumed;
    }
}
