package event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An event bus for managing and dispatching events to registered observers.
 *
 * @author Albert Beaupre
 */
public class EventBus {

    // Map to store event class -> List of invokers
    private final Map<Class<?>, List<EventInvoker>> invokers = new HashMap<>();

    // List of blocked event classes
    private final List<Class<?>> blockedEventClasses = new ArrayList<>();

    /**
     * Posts an event to all registered observers for that event type.
     *
     * @param event The event to be posted.
     */
    public void post(Object event) {
        Class<?> eventClass = event.getClass();

        List<EventInvoker> currentInvokers = invokers.get(eventClass);
        if (currentInvokers == null || currentInvokers.isEmpty())
            return; // No observers for this event

        // Check if the event class is blocked
        if (isEventClassBlocked(eventClass))
            return; // Blocked events are not dispatched

        // Invoke event handling methods of registered observers
        for (EventInvoker invoker : currentInvokers) {
            if (invoker != null) {
                invoker.invoke(event);
            }
        }
    }

    /**
     * Registers an object as an observer and identifies methods with the @EventObserver annotation
     * for handling specific event types.
     *
     * @param object The object to be registered as an observer.
     */
    public void registerObserver(Object object) {
        if (object == null)
            throw new IllegalArgumentException("Observer object cannot be null.");

        Class<?> clazz = object.getClass();
        for (Method method : clazz.getMethods()) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(EventObserver.class) && method.getParameterCount() == 1) {
                for (Class<?> type : method.getParameterTypes()) {
                    List<EventInvoker> currentInvokers = invokers.getOrDefault(type, new ArrayList<EventInvoker>());
                    currentInvokers.add(new EventInvoker(object, method));
                    invokers.put(type, currentInvokers);
                }
            }
        }
    }

    /**
     * Posts an event to a specific observer object, invoking only the appropriate event handler method.
     *
     * @param observer The observer object that should handle the event.
     * @param event    The event to be posted.
     */
    public void postOnly(Object observer, Object event) {
        Class<?> clazz = observer.getClass();
        for (Method method : clazz.getMethods()) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(EventObserver.class) && method.getParameterCount() == 1) {
                for (Class<?> type : method.getParameterTypes()) {
                    if (type.isInstance(event)) {
                        try {
                            method.invoke(observer, event);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * Unregisters an observer object, removing its associated event handlers from the bus.
     *
     * @param object The observer object to be unregistered.
     */
    public void unregisterObserver(Object object) {
        if (object == null)
            throw new IllegalArgumentException("Observer object cannot be null.");

        // Remove invokers associated with the specified object
        invokers.values().forEach(invokerList -> invokerList.removeIf(invoker -> invoker != null && invoker.object() == object));
    }

    /**
     * Blocks events of a specified class from being dispatched by the event bus.
     *
     * @param eventClass The class of events to be blocked.
     */
    public void blockEvents(Class<?> eventClass) {
        if (eventClass != null && !blockedEventClasses.contains(eventClass)) {
            blockedEventClasses.add(eventClass);
        }
    }

    /**
     * Checks if events of a specific class are blocked from being dispatched.
     *
     * @param eventClass The class of events to check.
     * @return True if the class of events is blocked, false otherwise.
     */
    private boolean isEventClassBlocked(Class<?> eventClass) {
        return blockedEventClasses.contains(eventClass);
    }
}
