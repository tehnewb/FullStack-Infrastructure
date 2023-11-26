package infrastructure.ecsprototype.systems.tick;

import infrastructure.ecsprototype.Component;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * The `TickComponent` class is a component designed to enable timed execution of a specific action for
 * an entity within an Entity-Component-System (ECS) architecture. It allows you to schedule an action
 * to be performed after a specified initial delay and then repeatedly at a given time interval.
 * The timing can be configured in various time units, such as milliseconds, seconds, minutes, etc.
 *
 * @author Albert Beaupre
 */
public class TickComponent extends Component {

    public long delay; // The initial delay before the action begins.
    public long interval; // The time interval between repeated executions.
    public long occurrences; // The number of times the action has already executed
    public Instant begin; // The timestamp when the tick starts.
    public TimeUnit unit; // The time unit for delay and interval.
    public Consumer<Integer> action; // The action to be executed.

    /**
     * Constructs a new `TickComponent` with the specified delay, interval, time unit, and action.
     *
     * @param delay    The initial delay before the action begins.
     * @param interval The time interval between repeated executions.
     * @param unit     The time unit for delay and interval.
     * @param action   The action to be executed.
     */
    public TickComponent(long delay, long interval, TimeUnit unit, Consumer<Integer> action) {
        this.delay = delay;
        this.interval = interval;
        this.unit = unit;
        this.action = action;
    }

    /**
     * <pre>
     * Constructs a new `TickComponent` with default values. The default values for this constructor are as follows:
     * - Initial delay: 0
     * - Interval: 0
     * - Time unit: Milliseconds
     * - Action: null (no action assigned)
     * </pre>
     * <p>
     * This constructor allows you to create a `TickComponent` with default settings when no specific configuration is required.
     */
    public TickComponent() {
        this.delay = 0;
        this.interval = 0;
        this.unit = TimeUnit.MILLISECONDS; // Default time unit
        this.action = null; // No action assigned by default
    }

    /**
     * <pre>
     * Creates a new `TickComponent` with default values. The default values for this constructor are as follows:
     * - Initial delay: 0
     * - Interval: 0
     * - Time unit: Milliseconds
     * - Action: null (no action assigned)
     * </pre>
     */
    public static TickComponent create() {
        return new TickComponent();
    }

    /**
     * Set the initial delay before the action begins.
     *
     * @param delay The initial delay in the specified time unit.
     * @return This `TickComponent` instance for method chaining.
     */
    public TickComponent delay(long delay) {
        this.delay = delay;
        return this;
    }

    /**
     * Set the time interval between repeated executions.
     *
     * @param interval The interval in the specified time unit.
     * @return This `TickComponent` instance for method chaining.
     */
    public TickComponent interval(long interval) {
        this.interval = interval;
        return this;
    }

    /**
     * Set the action to be executed.
     *
     * @param action The action as a `Consumer<Integer>`.
     * @return This `TickComponent` instance for method chaining.
     */
    public TickComponent action(Consumer<Integer> action) {
        this.action = action;
        return this;
    }

    /**
     * Set the time unit to be used for both delay and interval to microseconds.
     *
     * @return This `TickComponent` instance for method chaining.
     */
    public TickComponent micros() {
        this.unit = TimeUnit.MICROSECONDS;
        return this;
    }

    /**
     * Set the time unit to be used for both delay and interval to nanoseconds.
     *
     * @return This `TickComponent` instance for method chaining.
     */
    public TickComponent nano() {
        this.unit = TimeUnit.NANOSECONDS;
        return this;
    }

    /**
     * Set the time unit to be used for both delay and interval to milliseconds (default).
     *
     * @return This `TickComponent` instance for method chaining.
     */
    public TickComponent milli() {
        this.unit = TimeUnit.MILLISECONDS;
        return this;
    }

    /**
     * Set the time unit to be used for both delay and interval to seconds.
     *
     * @return This `TickComponent` instance for method chaining.
     */
    public TickComponent seconds() {
        this.unit = TimeUnit.SECONDS;
        return this;
    }

    /**
     * Set the time unit to be used for both delay and interval to minutes.
     *
     * @return This `TickComponent` instance for method chaining.
     */
    public TickComponent minutes() {
        this.unit = TimeUnit.MINUTES;
        return this;
    }

    /**
     * Set the time unit to be used for both delay and interval to hours.
     *
     * @return This `TickComponent` instance for method chaining.
     */
    public TickComponent hours() {
        this.unit = TimeUnit.HOURS;
        return this;
    }

    /**
     * Set the time unit to be used for both delay and interval to days.
     *
     * @return This `TickComponent` instance for method chaining.
     */
    public TickComponent days() {
        this.unit = TimeUnit.DAYS;
        return this;
    }
}
