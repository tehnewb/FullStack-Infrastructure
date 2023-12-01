package resource;

/**
 * The ResourceStrategy interface defines the contract for strategies used to load, save, get, and release resources.
 *
 * @param <K> The type of the resource key.
 * @param <R> The type of the resource.
 * @param <P> The type of the resource parameter used for loading.
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public interface ResourceStrategy<K, R, P> {

    /**
     * Load a resource based on the provided parameter.
     *
     * @param parameter The parameter required for loading the resource.
     * @return The loaded resource.
     */
    R load(P parameter);

    /**
     * Save a resource for future use or caching, if applicable.
     *
     * @param resource The resource to be saved.
     */
    void save(R resource);

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