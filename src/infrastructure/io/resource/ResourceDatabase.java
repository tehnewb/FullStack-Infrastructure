package infrastructure.io.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * This class represents an asynchronous resource database that provides methods to load and save
 * resource data. It includes in-memory caching for faster access to frequently accessed resources.
 * The database operations are performed asynchronously using a thread pool.
 * <p>
 * The database supports different types of resources, and you can register operations strategies
 * for each resource type. The operations strategies define how resources are loaded from and saved
 * to the database.
 *
 * @author Albert Beaupre
 * @see ResourceOperationsStrategy
 */

@SuppressWarnings("ALL")
public class ResourceDatabase {
    private static final Logger logger = LoggerFactory.getLogger(ResourceDatabase.class);
    private final ExecutorService executor = Executors.newFixedThreadPool(10); // Thread pool for asynchronous tasks
    private final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>(); // In-memory cache for resource data
    private final ConcurrentHashMap<Class<?>, ResourceOperationsStrategy<?>> operators = new ConcurrentHashMap<>();

    /**
     * Asynchronously load a resource by its identifier.
     *
     * @param identifier The identifier of the resource to load.
     * @param type       The class of the resource type.
     * @return A CompletableFuture representing the asynchronous loading process.
     * It completes when the resource is loaded or an error occurs.
     * @throws IllegalArgumentException if the identifier is null.
     */
    public synchronized <T> CompletableFuture<T> load(String identifier, Class<T> type) {
        if (identifier == null) throw new IllegalArgumentException("Cannot load resource with null identifier");

        return CompletableFuture.supplyAsync(new Supplier<T>() {
            ResourceOperationsStrategy<T> operations = (ResourceOperationsStrategy<T>) operators.get(type);

            @Override
            public T get() {
                // First, try to fetch the resource from the cache
                T cachedData = (T) getCachedResourceData(identifier, type);

                if (cachedData != null) {
                    // Resource found in cache, return it
                    return (T) cachedData;
                } else {
                    // Resource not found in cache, load it from storage
                    T resource = operations.load(identifier);

                    if (resource == null) throw new IllegalStateException("Resource not found in storage");

                    // Store the loaded resource in cache for future use
                    setCachedResourceData(identifier, resource);

                    logger.debug("Resource Loaded: " + identifier);

                    return resource;
                }
            }
        }, executor);
    }

    /**
     * Asynchronously save a resource.
     *
     * @param identifier The identifier of the resource to save.
     * @param resource   The Resource object to save.
     * @param type       The class of the resource type.
     * @return A CompletableFuture representing the asynchronous saving process.
     * It completes when the resource is saved or an error occurs.
     * @throws NullPointerException if the resource is null.
     */
    public synchronized <T> CompletableFuture<Void> save(String identifier, T resource, Class<T> type) {
        ResourceOperationsStrategy<T> operations = (ResourceOperationsStrategy<T>) operators.get(type);

        if (resource == null) throw new NullPointerException("Cannot save null resource");
        return CompletableFuture.runAsync(() -> {
            // Save the resource to storage
            operations.save(identifier, resource);

            logger.debug("Resource Saved: " + identifier);
        }, executor);
    }

    /**
     * Shutdown the executor service. Call this method when the application is exiting.
     */
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * Retrieve cached resource data by its identifier.
     *
     * @param identifier The ID of the resource to retrieve from cache.
     * @return The cached resource data, or null if not found in cache.
     */
    public <T> T getCachedResourceData(String identifier, Class<T> type) {
        return type.cast(cache.get(identifier));
    }

    /**
     * Store resource data in the cache.
     *
     * @param identifier The ID of the resource to store in cache.
     * @param resource   The resource object to store in cache.
     */
    private void setCachedResourceData(String identifier, Object resource) {
        cache.put(identifier, resource);
    }

    /**
     * Register operations strategy for a specific resource type.
     *
     * @param type       The class of the resource type.
     * @param operations The operations strategy for that resource type.
     */
    public <T> void registerOperations(Class<T> type, ResourceOperationsStrategy<T> operations) {
        this.operators.put(type, operations);
    }
}
