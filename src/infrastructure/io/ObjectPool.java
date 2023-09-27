package infrastructure.io;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * A generic object pool implementation that manages and recycles a limited number of objects.
 *
 * @param <T> The type of objects to be managed by the pool.
 * @author Albert Beaupre
 */
public class ObjectPool<T> {
    private final LinkedList<T> objects;
    private final Supplier<T> supplier;

    /**
     * Creates a new ObjectPool with the specified supplier and initialSize.
     *
     * @param supplier    The supplier to provide an object
     * @param initialSize The maximum number of objects the pool can hold.
     */
    public ObjectPool(Supplier<T> supplier, int initialSize) {
        // Initialize a thread-safe blocking queue to hold the objects
        this.objects = new LinkedList<>();
        this.supplier = supplier;
        // Prepopulate the pool with objects up to the specified initialSize
        initializePool(initialSize);
    }

    /**
     * Borrow an object from the pool. If the pool is empty, this method will block until an
     * object becomes available.
     *
     * @return An object from the pool, or null if interrupted.
     */
    public T borrowObject() {
        try {
            return objects.pop();
        } catch (NoSuchElementException e) {
            return createObject();
        }
    }

    /**
     * Return an object to the pool for reuse.
     *
     * @param object The object to be returned to the pool. It must not be null.
     */
    public void returnObject(T object) {
        if (object != null) {
            objects.offer(object);
        }
    }

    /**
     * Initialize the pool by creating and adding objects up to the specified size.
     *
     * @param size The maximum number of objects to prepopulate the pool with.
     */
    private void initializePool(int size) {
        for (int i = 0; i < size; i++) {
            objects.offer(createObject());
        }
    }

    /**
     * Create a new object of type T. Subclasses should override this method to provide
     * custom object creation logic.
     *
     * @return A new instance of the object of type T.
     */
    protected T createObject() {
        // Override this method to create a new object of type T
        return supplier.get();
    }
}
