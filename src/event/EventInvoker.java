package event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class is used as a wrapper for events that invoke certain methods.
 *
 * @author Albert Beaupre
 */
record EventInvoker(Object object, Method method) {

    /**
     * Invokes the method of this {@code EventInvoker} using the given {@code event}
     * as the argument for the method.
     *
     * @param event the event as parameter
     */
    public void invoke(Event event) {
        try {
            method.invoke(object, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
