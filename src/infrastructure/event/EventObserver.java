package infrastructure.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The EventObserver is placed over any method within a class that is used to
 * observe an event.
 * <p>
 * The method must have one parameter: that being the event observed.
 *
 * @author Albert Beaupre
 * @see EventBus
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventObserver {

}
