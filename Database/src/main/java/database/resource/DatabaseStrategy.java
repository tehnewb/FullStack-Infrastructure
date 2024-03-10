package database.resource;

/**
 * The DatabaseStrategy interface defines the contract for strategies used
 * to load, save, get, create, and release resources.
 *
 * @param <K> The type of key used to retrieve the resource
 * @param <R> The type of the resource object
 * @param <P> The type of parameters required to construct the resource.
 * @author Albert Beaupre
 * @version 1.0
 */
public interface DatabaseStrategy<K, R, P> {

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

    /**
     * Creates a resource based on the provided parameters.
     *
     * @param key The key of the resource to create.
     * @param parameter The parameters required to create the resource.
     * @return The created resource.
     */
    R create(K key, P parameter);
}