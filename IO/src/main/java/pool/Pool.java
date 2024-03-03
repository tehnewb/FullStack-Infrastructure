package pool;

import collections.stack.FastStack;

/**
 * Implements a generic object pool that manages the recycling and reuse of objects
 * to optimize resource allocation and improve performance. The pool pre-allocates a
 * specified number of objects and reuses them, reducing the overhead of object creation
 * and garbage collection in resource-intensive applications.
 * <p>
 * This pool utilizes a {@link PoolFactory} for creating, initializing, and destroying
 * objects, ensuring that objects are in a consistent state when borrowed from and returned
 * to the pool. A {@link FastStack} is used to store the pooled objects, providing efficient
 * last-in-first-out (LIFO) access.
 * <p>
 * Example usage:
 * <pre>
 * Pool<MyObject> myObjectPool = new Pool<>(new MyObjectFactory(), 10);
 * MyObject myObject = myObjectPool.obtain(); // Get an object from the pool
 * // Use the object...
 * myObjectPool.release(myObject); // Return the object to the pool
 * </pre>
 *
 * @param <E> The type of objects managed by the pool. This generic parameter allows the
 *            pool to manage objects of any class, making the pool versatile and reusable
 *            across different types of applications.
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class Pool<E> {
    private final PoolFactory<E> factory; // Factory to manage object lifecycle
    private final FastStack<E> pool;      // Stack to store pooled objects
    private final int allocationSize;     // Number of objects to allocate initially

    /**
     * Constructs a new Pool instance with a specified factory for object lifecycle management
     * and an initial allocation size. The constructor initializes the pool by populating it
     * with objects created by the factory.
     *
     * @param factory        The {@link PoolFactory} responsible for creating, initializing,
     *                       and destroying objects managed by the pool.
     * @param allocationSize The initial number of objects to pre-allocate and store in the pool.
     */
    public Pool(PoolFactory<E> factory, int allocationSize) {
        this.allocationSize = allocationSize;
        this.factory = factory;
        this.pool = new FastStack<>();
        this.populate(); // Initial population of the pool
    }

    /**
     * Populates the pool with objects up to the specified allocation size. Each object is
     * created using the provided factory. This method is called during pool initialization
     * and can also be called if the pool runs out of objects.
     */
    private void populate() {
        for (int i = 0; i < allocationSize; i++) {
            pool.push(factory.create());
        }
    }

    /**
     * Obtains an object from the pool, ensuring there is always an object available by repopulating
     * the pool if necessary. Before the object is returned to the client, it is initialized using
     * the factory to ensure it is in a proper state for use.
     *
     * @return An initialized object from the pool.
     */
    public E obtain() {
        if (pool.isEmpty()) {
            populate(); // Ensure the pool is not empty
        }
        E object = pool.pop(); // Obtain an object from the pool
        factory.init(object); // Initialize the object before use
        return object;
    }

    /**
     * Releases an object back to the pool. Before the object is returned to the pool, its state is
     * destroyed using the factory to ensure that it does not retain any data from its previous use.
     * This method allows objects to be reused, reducing resource consumption and allocation overhead.
     *
     * @param object The object to be released back into the pool.
     */
    public void release(E object) {
        factory.destroy(object); // Reset the object's state
        pool.push(object); // Return the object to the pool for future reuse
    }
}