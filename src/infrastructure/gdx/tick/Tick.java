package infrastructure.gdx.tick;

import infrastructure.collections.index.Indexable;

import java.util.function.Predicate;

/**
 * The `Tick` class is designed for creating timed actions that can be executed at regular intervals.
 * It offers a mechanism to schedule and manage actions with a specified delay between executions.
 *
 * @author Albert Beaupre
 */
public class Tick implements Indexable {

    // A predicate to determine if this `Tick` should stop executing.
    private Predicate<Tick> stopIf;

    private int index;

    // The delay before the tick starts
    private float delay;

    // The delay in seconds between action executions.
    private float interval;

    // The current duration elapsed since the last action execution.
    private float duration;

    // The number of times this `Tick` has executed its action.
    private short occurrences;

    // The maximum number of occurrences before stopping (default is -1 for unlimited).
    private int occurFor = -1;

    // The action to execute at each interval.
    private Runnable action;

    // A flag to indicate if this `Tick` is stopped.
    private boolean stopped;

    // A flag to determine if this `Tick` is paused.
    private boolean paused;

    /**
     * Private constructor for creating a new instance of `Tick`. Use the `of` method to obtain instances
     * from the object pool for efficient resource management.
     */
    Tick() {
        // Private constructor, use `of` method to create instances.
    }

    /**
     * Obtains a new `Tick` instance with a specified action. The `of` method
     * retrieves objects from the object pool for efficient memory management.
     *
     * @param action The action to be executed.
     * @return A `Tick` instance.
     */
    public static Tick of(Runnable action) {
        return new Tick().action(action);
    }

    /**
     * Sets a predicate to determine if this `Tick` should stop executing.
     *
     * @param stopIf The predicate that controls stopping conditions.
     * @return This `Tick` instance for method chaining.
     */
    public Tick stopIf(Predicate<Tick> stopIf) {
        this.stopIf = stopIf;
        return this;
    }

    /**
     * Sets the maximum number of occurrences before stopping (default is -1 for unlimited).
     *
     * @param occur The maximum number of occurrences before stopping.
     * @return This `Tick` instance for method chaining.
     */
    public Tick occurFor(int occur) {
        this.occurFor = occur;
        return this;
    }

    /**
     * Updates the `Tick` object with the given time delta. This method is typically
     * called each frame to update the timing of the action.
     *
     * @param delta The time elapsed since the last frame.
     */
    public void update(float delta) {
        // Check if the provided predicate determines that the `Tick` should stop.
        if (stopIf != null && stopIf.test(this)) {
            this.stop();
            return;
        }

        if (this.stopped) return;
        if (this.paused) return;

        this.duration += delta;

        if (this.duration >= delay && this.duration >= this.interval) {
            this.delay = -1;
            action.run();
            this.duration = 0;
            this.occurrences++;

            // Check if the maximum number of occurrences has been reached.
            if (occurFor > 0 && this.occurrences >= occurFor)
                this.stop();
        }
    }

    /**
     * Sets the action to be executed by this `Tick` instance. The action will be
     * executed at each interval determined by the delay.
     *
     * @param action The action to execute.
     * @return This `Tick` instance for method chaining.
     */
    public Tick action(Runnable action) {
        this.action = action;
        return this;
    }

    /**
     * Retrieves the action currently associated with this `Tick`.
     *
     * @return The action to execute.
     */
    public Runnable getAction() {
        return action;
    }

    /**
     * Sets the delay before the tick starts.
     *
     * @param delay The delay in seconds.
     * @return This `Tick` instance for method chaining.
     */
    public Tick delay(float delay) {
        this.delay = delay;
        return this;
    }

    /**
     * Sets the interval between action executions. The interval is the time between
     * each execution of this `Tick`'s action. This method can be used to change
     * the interval even while the `Tick` is running.
     *
     * @param between The seconds between each action call.
     * @return This `Tick` instance for method chaining.
     */
    public Tick interval(float between) {
        this.interval = between;
        return this;
    }

    /**
     * Stops this `Tick` from updating any further and returns it to the object pool
     * for reuse. After stopping a `Tick`, it can be reinitialized and reused with
     * the `of` method.
     */
    public void stop() {
        this.stopped = true;
    }

    /**
     * Adds this `Tick` to the list of active `Tick` instances for updates.
     */
    public void start() {
        TickFactory.start(this);
    }

    /**
     * Pauses this `Tick`, preventing it from executing its action until it is resumed.
     */
    public void pause() {
        this.paused = true;
    }

    /**
     * Checks if this `Tick` has been stopped.
     *
     * @return `true` if the `Tick` is stopped, otherwise `false`.
     */
    public boolean isStopped() {
        return stopped;
    }

    /**
     * Retrieves the number of times this `Tick` has executed its action.
     *
     * @return The count of action executions.
     */
    public short getOccurrences() {
        return occurrences;
    }

    /**
     * Resets the `Tick` object for reuse. This method should be called before
     * reusing a `Tick` instance obtained from the object pool. It clears any
     * previous state, making the `Tick` ready for a new use.
     */
    public void reset() {
        this.delay = 0;
        this.duration = 0;
        this.occurFor = -1;
        this.paused = false;
        this.stopped = false;
        this.action = null;
    }

    @Override
    public String toString() {
        return String.format("index=%s, delay=%s, occurFor=%s", index, delay, occurFor);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }
}
