package event;

/**
 * An interface for objects that listen for and respond to specific events.
 *
 * @param <E> The type of events that the listener can handle.
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public interface EventListener<E extends Event> extends Comparable<EventListener<E>> {

    /**
     * Called when an event occurs. Implementing classes should define how they respond
     * to the specific type of event they are interested in.
     *
     * @param event The event object containing information about the event.
     */
    void update(E event);

    /**
     * Retrieves the priority of the event listener.
     *
     * <p>The priority value indicates the order in which listeners should be notified
     * when multiple listeners are subscribed to the same event type. Higher priority
     * values indicate that the listener should be notified earlier.
     *
     * @return The priority value of the event listener.
     */
    default int priority() {
        return 0;
    }

    /**
     * Compares this event listener with another based on priority.
     *
     * <p>The default implementation uses the priority values to determine the order.
     * Listeners with higher priority values come first.
     *
     * @param o The other event listener to compare to.
     * @return A negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     */
    @Override
    default int compareTo(EventListener<E> o) {
        return Integer.compare(o.priority(), priority());
    }
}