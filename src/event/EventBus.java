package event;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * EventBus handles the observer-event communications.
 * 
 * <p>
 * An EventBus allows objects to register as observers to receive events and
 * dispatch events to registered observers based on the event type.
 * </p>
 * 
 * <p>
 * Usage:
 * </p>
 * 
 * <pre>
 * EventBus eventBus = new EventBus();
 * eventBus.registerObserver(myObserver);
 * eventBus.post(new MyEvent());
 * </pre>
 * 
 * <p>
 * It provides support for event dispatching, registration of event observers,
 * and unregistering observers. Event handling can be asynchronous depending on
 * the observer's implementation.
 * </p>
 * 
 * <p>
 * This EventBus is designed to be thread-safe for concurrent use, allowing
 * events to be posted from multiple threads safely.
 * </p>
 * 
 * <p>
 * Please note that it is essential to unregister observers when they are no
 * longer needed to avoid memory leaks.
 * </p>
 * 
 * <p>
 * This implementation includes memory management using WeakReferences,
 * performance optimization with CopyOnWriteArrayList, and concurrent control
 * for safe multi-threaded access.
 * </p>
 * 
 * <p>
 * Error handling during event invocation is the responsibility of observers.
 * </p>
 * 
 * @author Albert Beaupre
 * 
 * @see event.Event
 * @see event.EventObserver
 */
public class EventBus {

	private static final List<WeakReference<EventInvoker>> EMPTY_LIST = new CopyOnWriteArrayList<>(); // Used when cannot find a list of invokers

	private final Map<Class<?>, List<WeakReference<EventInvoker>>> invokers = new ConcurrentHashMap<>(); // holds all invokers extracted from observers

	/**
	 * Posts the given {@code event} so that any observers are notified and the
	 * methods that handle the given event are called.
	 * 
	 * @param event the event to post
	 */
	public void post(Event event) {
		List<WeakReference<EventInvoker>> currentInvokers = invokers.getOrDefault(event.getClass(), EMPTY_LIST);
		if (currentInvokers.isEmpty())
			return;

		for (WeakReference<EventInvoker> invokerRef : currentInvokers) {
			EventInvoker invoker = invokerRef.get();
			if (invoker != null) {
				if (event.isConsumed()) return;
				invoker.invoke(event);
			}
		}
	}

	/**
	 * Registers an object as an observer to receive events.
	 * 
	 * <p>
	 * This method scans the methods of the provided {@code object} for those
	 * annotated with {@link EventObserver} and associates them with the
	 * corresponding event types.
	 * </p>
	 * 
	 * @param object the object to extract invokers from
	 * @throws IllegalArgumentException if {@code object} is null
	 */
	public void registerObserver(Object object) {
		if (object == null)
			throw new IllegalArgumentException("Observer object cannot be null.");

		Class<?> clazz = object.getClass();
		for (Method method : clazz.getMethods()) {
			method.setAccessible(true);
			if (method.isAnnotationPresent(EventObserver.class) && method.getParameterCount() == 1) {
				for (Class<?> type : method.getParameterTypes()) {
					if (!Event.class.isAssignableFrom(type)) continue;

					List<WeakReference<EventInvoker>> currentInvokers = invokers.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>());

					EventInvoker invoker = new EventInvoker(object, method);

					currentInvokers.add(new WeakReference<>(invoker));
				}
			}
		}
	}

	/**
	 * Unregisters a specific observer.
	 * 
	 * @param object the object to unregister
	 * @throws IllegalArgumentException if {@code object} is null
	 */
	public void unregisterObserver(Object object) {
		if (object == null)
			throw new IllegalArgumentException("Observer object cannot be null.");

		invokers.values().forEach(invokerList -> {
			invokerList.removeIf(invokerRef -> {
				EventInvoker invoker = invokerRef.get();
				return invoker != null && invoker.object() == object;
			});
		});
	}
}
