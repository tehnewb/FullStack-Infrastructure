package database.resource;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ResourceDatabase is a generic class that manages the loading, caching, and accessing of resources
 * using a specified resource loading strategy. It allows for asynchronous loading of resources and
 * keeps track of various resource statistics like load times, access counts, save counts, save times,
 * and exceptions.
 *
 * @param <K> The type of key used to retrieve the resource
 * @param <R> The type of the resource object
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class ResourceDatabase<K, R> {

    private final ExecutorService service;
    private final ResourceStrategy<K, R> strategy;
    private final ConcurrentHashMap<K, ResourceMetrics> monitors;

    /**
     * Constructs a ResourceDatabase with the specified resource loading strategy.
     *
     * @param strategy The resource loading strategy to be used.
     * @throws NullPointerException if the strategy is null.
     */
    public ResourceDatabase(ResourceStrategy<K, R> strategy) {
        this.service = Executors.newSingleThreadExecutor();
        this.strategy = Objects.requireNonNull(strategy, "Cannot construct database with null strategy");
        this.monitors = new ConcurrentHashMap<>();
    }

    /**
     * Asynchronously loads a resource using the specified strategy.
     *
     * @param key The key of the resource.
     * @return A CompletableFuture representing the loaded resource.
     * @throws NullPointerException if the parameter is null.
     */
    public CompletableFuture<R> load(K key) {
        Objects.requireNonNull(key, "Cannot load resource with null key");
        return CompletableFuture.supplyAsync(() -> {
            ResourceMetrics monitor = monitors.computeIfAbsent(key, v -> new ResourceMetrics()); // corresponding monitor
            try {
                Instant begin = Instant.now(); // start load time
                R resource = strategy.load(key); // load resource
                monitor.setLoadTime(Duration.between(begin, Instant.now()).toMillis() / 1000D); // Set load time
                monitor.increaseLoadCount(); // increase load count
                return resource;
            } catch (Exception e) {
                monitor.trackException(e);
                return null;
            }
        }, service);
    }

    /**
     * Asynchronously saves a resource using the specified strategy.
     *
     * @param key The key of the resource.
     * @return A CompletableFuture representing the saved resource.
     * @throws NullPointerException if the parameter is null.
     */
    public CompletableFuture<Void> save(K key) {
        Objects.requireNonNull(key, "Cannot save resource with null key");
        return CompletableFuture.supplyAsync(() -> {
            ResourceMetrics monitor = monitors.computeIfAbsent(key, v -> new ResourceMetrics()); // corresponding monitor
            try {
                Instant begin = Instant.now(); // start save time
                strategy.save(key);
                monitor.setSaveTime(Duration.between(begin, Instant.now()).toMillis() / 1000D); // Set save time
                monitor.increaseSaveCount(); // increase save count
            } catch (Exception e) {
                monitor.trackException(e);
            }
            return null;
        }, service);
    }

    /**
     * Synchronously retrieves a resource for the specified key.
     *
     * @param key The key of the resource to retrieve.
     * @return The retrieved resource or null if an exception occurs.
     * @throws NullPointerException if the key is null.
     */
    public synchronized R get(K key) {
        Objects.requireNonNull(key, "Cannot get resource with null key");
        ResourceMetrics monitor = this.monitors.get(key);
        try {
            R resource = strategy.get(key);
            monitor.increaseAccessCount();
            return resource;
        } catch (Exception e) {
            monitor.trackException(e);
            return null;
        }
    }

    /**
     * Synchronously releases a resource for the specified key.
     *
     * @param key The key of the resource to release.
     * @return The released resource or null if an exception occurs.
     * @throws NullPointerException if the key is null.
     */
    public R release(K key) {
        Objects.requireNonNull(key, "Cannot release resource with null key");
        return this.strategy.release(key);
    }

    /**
     * Retrieves the ResourceMetrics for the specified resource key.
     *
     * @param key The key for which to retrieve the ResourceMetrics.
     * @return The ResourceMetrics associated with the key.
     * @throws NullPointerException if the key is null.
     */
    public synchronized ResourceMetrics getMonitor(K key) {
        return monitors.get(Objects.requireNonNull(key, "Cannot get monitor with null key"));
    }

    /**
     * Shuts down the ExecutorService used for asynchronous loading of resources.
     */
    public void shutdown() {
        service.shutdown();
    }

}