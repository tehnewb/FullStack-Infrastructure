package pool;

/**
 * A factory interface for creating, initializing, and destroying objects managed by the Pool.
 *
 * @param <E> The type of objects managed by the pool.
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public interface PoolFactory<E> {

    /**
     * Creates a new instance of the object to be managed by the Pool.
     *
     * @return A newly created object.
     */
    E create();

    /**
     * Initializes the state of an object before it is placed into the pool or returned to the user.
     *
     * @param object The object to be initialized.
     */
    void init(E object);

    /**
     * Destroys the state of an object when it is no longer needed, preparing it for potential reuse or disposal.
     *
     * @param object The object to be destroyed.
     */
    void destroy(E object);
}