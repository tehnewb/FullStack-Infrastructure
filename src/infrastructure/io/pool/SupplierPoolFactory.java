package infrastructure.io.pool;

import java.util.function.Supplier;

/**
 * A factory for creating objects using a Supplier as the source.
 *
 * @param <T> The type of objects to be created.
 */
public class SupplierPoolFactory<T> implements PoolFactory<T> {
    private final Supplier<T> supplier;

    /**
     * Creates a new SupplierPoolFactory with the specified supplier.
     *
     * @param supplier The supplier used to create objects.
     */
    public SupplierPoolFactory(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Create a new SupplierPoolFactory using the provided supplier.
     *
     * @param supplier The supplier used to create objects.
     * @param <T>      The type of objects to be created.
     * @return A new SupplierPoolFactory instance.
     */
    public static <T> SupplierPoolFactory<T> of(Supplier<T> supplier) {
        return new SupplierPoolFactory<>(supplier);
    }

    /**
     * Create a new instance of the object using the provided supplier.
     *
     * @return A new instance of the object.
     */
    @Override
    public T create() {
        return supplier.get();
    }

    /**
     * Initialize an object before it is placed in the pool.
     *
     * @param object The object to initialize.
     */
    @Override
    public void init(T object) {
        // You can perform initialization steps here, if needed.
    }

    /**
     * Close and release resources associated with an object.
     *
     * @param object The object to close.
     */
    @Override
    public void close(T object) {
        // You can perform cleanup steps here, if needed.
    }

    /**
     * Check if an object is stale (based on maximum idle time).
     *
     * @param object The object to check for staleness.
     * @return true if the object is stale, false otherwise.
     */
    @Override
    public boolean isStale(T object) {
        // You can implement staleness checking logic here, if needed.
        return false; // Example: Always return false, indicating that objects never become stale.
    }
}
