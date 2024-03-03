package database.resource;

/**
 * The ResourceStrategy interface defines the contract for strategies used to load, save, get, and release resources.
 *
 * @param <K> The type of key used to retrieve the resource
 * @param <R> The type of the resource object
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public interface ResourceStrategy<K, R> {

    /**
     * Load a resource based on the provided key.
     *
     * @param key The key of the resource to load.
     * @return The loaded resource.
     */
    R load(K key);

    /**
     * Save a resource based on the provided key.
     *
     * @param key The key of the resource to save.
     */
    void save(K key);

    /**
     * Get a resource by its key.
     *
     * @param key The key of the resource to retrieve.
     * @return The retrieved resource.
     */
    R get(K key);

    /**
     * Release a resource based on its key.
     *
     * @param key The key of the resource to release.
     * @return The released resource, if applicable.
     */
    R release(K key);
}