package infrastructure.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The {@code EventInvoker} class represents an object that can be used to invoke a specific
 * method on a given object with a provided event as an argument.
 *
 * @author Albert Beaupre
 */
public class EventInvoker {

    private final Object object; // The object on which the method will be invoked.
    private final Method method; // The method to be invoked.

    /**
     * Constructs an {@code EventInvoker} with the specified object and method.
     *
     * @param object The object on which the method will be invoked.
     * @param method The method to be invoked.
     */
    public EventInvoker(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    /**
     * Invokes the method of this {@code EventInvoker} using the given {@code event}
     * as the argument for the method.
     *
     * @param event The event to be passed as an argument to the method.
     */
    public void invoke(Object event) {
        try {
            method.invoke(object, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // Handle any exceptions that may occur during method invocation.
            e.printStackTrace();
        }
    }

    /**
     * Gets the object on which the method will be invoked.
     *
     * @return The object on which the method will be invoked.
     */
    public Object getObject() {
        return object;
    }

    /**
     * Gets the method to be invoked.
     *
     * @return The method to be invoked.
     */
    public Method getMethod() {
        return method;
    }
}