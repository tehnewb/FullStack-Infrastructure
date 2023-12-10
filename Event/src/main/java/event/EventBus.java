package event;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * EventBus facilitates the communication between different components in a decoupled manner.
 * It allows objects to subscribe to and publish events of specific types.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class EventBus {

    // A mapping of event types to their corresponding subscribers
    private final Map<Class<?>, PriorityQueue<EventListener>> subscribers;

    /**
     * Creates a new EventBus with an empty subscriber mapping.
     */
    public EventBus() {
        this.subscribers = new HashMap<>();
    }

    /**
     * Subscribes a listener to receive events of a specific type.
     *
     * @param eventType The class representing the type of events to subscribe to.
     * @param listener  The listener that will be notified when events of the specified type occur.
     * @param <T>       The type of events to subscribe to.
     */
    public synchronized <T extends Event> void subscribe(Class<T> eventType, EventListener<T> listener) {
        // If the event type has no subscribers yet, create a new priority queue for them
        subscribers
                .computeIfAbsent(eventType, k -> new PriorityQueue<>())
                .add(listener);
    }

    /**
     * Publishes an event to all subscribers interested in that event type.
     * If an event is marked as closed, it will not be processed further.
     *
     * @param event The event to be published.
     * @param <T>   The type of the event.
     */
    public synchronized <T extends Event> void publish(T event) {
        // Retrieve the subscribers for the given event type
        PriorityQueue<EventListener> listeners = subscribers.get(event.getClass());

        if (listeners != null) {
            // Iterate over subscribers and notify them about the event
            for (EventListener listener : listeners) {
                // If the event is closed, stop further processing
                if (event.closed)
                    return;
                listener.update(event);
            }
        }
    }

}