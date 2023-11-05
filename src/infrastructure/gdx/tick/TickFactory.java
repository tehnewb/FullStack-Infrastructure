package infrastructure.gdx.tick;

import infrastructure.collections.index.IndexableList;
import infrastructure.io.pool.PoolFactory;

/**
 * The `TickFactory` class provides a factory for creating and managing `Tick` objects.
 * It is responsible for initializing, closing, and reusing `Tick` instances, as well as
 * maintaining a list of active `Tick` objects for updates.
 */
public class TickFactory implements PoolFactory<Tick> {

    // A list to keep track of all active `Tick` instances.
    private static final IndexableList<Tick> Ticks = new IndexableList<>(100);

    /**
     * Updates all active ticks based on the specified delta.
     *
     * @param delta The game delta time.
     */
    public static void updateTicks(float delta) {
        Ticks.forEach(t -> {
            if (t != null)
                t.update(delta);
        });
    }

    /**
     * Starts a `Tick` and adds it to the list of active `Tick` instances for updates.
     *
     * @param tick The `Tick` instance to start.
     */
    public static void start(Tick tick) {
        Ticks.add(tick);
    }

    @Override
    public void init(Tick object) {
        // Initialization can be added here if needed.
    }

    @Override
    public void close(Tick tick) {
        // Reset the `Tick` before returning it to the pool.
        tick.reset();
        Ticks.remove(tick);
    }

    @Override
    public boolean isStale(Tick object) {
        // Implement stale object checking if needed.
        return false;
    }

    @Override
    public Tick create() {
        // Create a new `Tick` instance.
        return new Tick();
    }
}