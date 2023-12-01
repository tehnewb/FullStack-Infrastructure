package pool;

import collections.stack.FastStack;

/**
 * A generic object pool implementation that manages the lifecycle of objects through a provided factory.
 *
 * @param <E> The type of objects managed by the pool.
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class Pool<E> {
    private final PoolFactory<E> factory;
    private final int allocationSize;
    private final FastStack<E> pool;

    /**
     * Constructs a new object pool with the specified factory and allocation size.
     *
     * @param factory        The factory responsible for creating, initializing, and destroying objects in the pool.
     * @param allocationSize The number of objects to allocate initially.
     */
    public Pool(PoolFactory<E> factory, int allocationSize) {
        this.allocationSize = allocationSize;
        this.factory = factory;
        this.pool = new FastStack<>();
        this.populate();
    }

    /**
     * Populates the pool with a specified number of objects by creating them using the factory.
     */
    private void populate() {
        for (int i = 0; i < allocationSize; i++)
            pool.push(factory.create());
    }

    /**
     * Obtains an object from the pool. If the pool is empty, it populates the pool first before obtaining an object.
     * The obtained object is initialized using the factory before being returned.
     *
     * @return An object from the pool.
     */
    public E obtain() {
        if (pool.isEmpty())
            populate();
        E object = pool.pop();
        factory.init(object);
        return object;
    }

    /**
     * Releases an object back to the pool after destroying its state using the factory.
     *
     * @param object The object to be released back to the pool.
     */
    public void release(E object) {
        factory.destroy(object);
        pool.push(object);
    }
}