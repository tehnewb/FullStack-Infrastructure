package event;

/**
 * Base class for events that can be used with the EventBus.
 * Events can be marked as closed to indicate that further processing should be stopped.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class Event {

    // Flag to indicate whether the event is closed
    protected boolean closed;

    /**
     * Closes the event, indicating that further processing should be stopped.
     */
    public void close() {
        closed = true;
    }
}