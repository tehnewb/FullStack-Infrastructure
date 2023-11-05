package infrastructure.io.pool;

import infrastructure.collections.faststack.FastStack;

/**
 * A thread-safe generic object pool implementation that manages and recycles a limited number of objects.
 *
 * @param <T> The type of objects to be managed by the pool.
 * @author Albert Beaupre
 */
public class Pool<T> {
    private final FastStack<T> objects;
    private final PoolFactory<T> factory;

    /**
     * Creates a new Pool with the specified object factory and initialSize.
     *
     * @param factory     The object factory for custom object initialization and cleanup.
     * @param initialSize The initial number of objects in the pool.
     */
    public Pool(PoolFactory<T> factory, int initialSize) {
        if (initialSize < 0)
            throw new IllegalArgumentException("Initial pool size cannot be negative.");

        this.objects = new FastStack<>();
        this.factory = factory;
        populatePool(initialSize);
    }

    /**
     * Borrow an object from the pool. If an object is available, it is returned.
     * If the object is stale based on the maximum idle time, it is closed, and a new one is created.
     *
     * @return An object from the pool.
     */
    public T borrowObject() {
        if (objects.isEmpty())
            populatePool(10); // populate pool if it's empty

        T obj = objects.pop(); // grab next pool object
        if (factory.isStale(obj)) { // check if object is stale
            factory.close(obj);
            obj = factory.create();
        }
        factory.init(obj); // initialize object for use
        return obj;
    }

    /**
     * Return an object to the pool for reuse. The object is added back to the pool.
     *
     * @param object The object to be returned to the pool.
     */
    public void returnObject(T object) {
        if (object != null) {
            factory.close(object);
            objects.push(object);
        }
    }

    /**
     * Initializes the pool by filling it will the specified number of objects.
     *
     * @param size The number of objects to fill the pool with.
     */
    private void populatePool(int size) {
        for (int i = 0; i < size; i++) {
            objects.push(factory.create());
        }
    }
}