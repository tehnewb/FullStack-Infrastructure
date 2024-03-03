package pool;

/**
 * Defines a contract for factory classes responsible for the lifecycle management of objects
 * within a pool. The pool pattern is commonly used to manage resources that are expensive
 * to create or allocate, such as database connections or threads. By reusing objects instead
 * of creating new ones for each request, applications can achieve significant performance
 * improvements and resource savings.
 * <p>
 * Implementations of this interface should provide specific logic for creating, initializing,
 * and destroying instances of a particular type, ensuring that objects are in a consistent
 * state when they are borrowed from and returned to the pool.
 *
 * @param <E> The type of objects managed by the pool. This generic parameter allows the
 *            PoolFactory interface to be used for any type of object, making it flexible
 *            and reusable across different contexts where object pooling is beneficial.
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public interface PoolFactory<E> {

    /**
     * Creates and returns a new instance of the object type E. This method is called by the pool
     * when a new object needs to be added to the pool, either during initialization or when the
     * demand for objects exceeds the current supply in the pool.
     * <p>
     * Implementations should ensure that the object is properly constructed before returning it.
     *
     * @return A newly created instance of type E.
     */
    E create();

    /**
     * Initializes an object of type E before it is placed into the pool or before it is returned
     * to a client from the pool. Initialization can include setting the initial state, resetting
     * any data from previous use, or performing any necessary configuration steps.
     *
     * @param object The object to be initialized. Implementations should ensure that the object
     *               is in a valid state for use after this method completes.
     */
    void init(E object);

    /**
     * Cleans up or destroys an object of type E when it is removed from the pool permanently,
     * either to manage the pool size or because the object is no longer valid for use. Destruction
     * can involve releasing resources, such as closing file streams or database connections, clearing
     * sensitive information, or preparing the object for garbage collection.
     *
     * @param object The object to be destroyed. Implementations should ensure that the object
     *               is properly cleaned up and is in a state that prevents any resource leaks.
     */
    void destroy(E object);
}