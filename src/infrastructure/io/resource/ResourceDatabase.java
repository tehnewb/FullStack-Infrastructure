package infrastructure.io.resource;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * ResourceDatabase is a generic class that manages the loading, caching, and accessing of resources
 * using a specified resource loading strategy. It allows for asynchronous loading of resources and
 * keeps track of various resource statistics like load times, access counts, and exceptions.
 *
 * @param <K> The type of the resource key.
 * @param <R> The type of the resource.
 * @param <P> The type of the resource parameter used for loading.
 * @author Albert Beaupre
 */
public class ResourceDatabase<K, R, P> {

    private final ExecutorService service;
    private final SynchronousQueue<ParameterWrapper<K, P>> prepared;
    private final ResourceStrategy<K, R, P> strategy;
    private final ConcurrentHashMap<K, ResourceMetrics> monitors;

    /**
     * Constructs a ResourceDatabase with the specified resource loading strategy.
     *
     * @param strategy The resource loading strategy to be used.
     * @throws NullPointerException if the strategy is null.
     */
    public ResourceDatabase(ResourceStrategy<K, R, P> strategy) {
        this.service = Executors.newCachedThreadPool();
        this.strategy = Objects.requireNonNull(strategy, "Cannot construct database with null strategy");
        this.prepared = new SynchronousQueue<>();
        this.monitors = new ConcurrentHashMap<>();
    }

    /**
     * Asynchronously prepares a resource for loading by adding it to the prepared queue.
     *
     * @param key       The key of the resource.
     * @param parameter The parameter required for loading the resource.
     * @throws NullPointerException if the parameter is null.
     */
    public final void prepare(K key, P parameter) {
        Objects.requireNonNull(key, "Cannot prepare null resource key");
        Objects.requireNonNull(parameter, "Cannot prepare null resource parameter");
        this.prepared.offer(new ParameterWrapper<>(key, parameter));
    }

    /**
     * Loads all resources that have been prepared in the prepared queue.
     */
    public final void loadPreparedResources() {
        while (!prepared.isEmpty()) {
            ParameterWrapper<K, P> wrapper = prepared.poll();
            load(wrapper.key, wrapper.parameter());
        }
    }

    /**
     * Asynchronously loads a resource using the specified strategy.
     *
     * @param key       The key of the resource.
     * @param parameter The parameter required for loading the resource.
     * @return A CompletableFuture representing the loaded resource.
     * @throws NullPointerException if the parameter is null.
     */
    public CompletableFuture<R> load(K key, P parameter) {
        Objects.requireNonNull(key, "Cannot load resource with null key");
        Objects.requireNonNull(parameter, "Cannot load resource with null parameter");
        return CompletableFuture.supplyAsync(() -> {
            ResourceMetrics monitor = monitors.computeIfAbsent(key, v -> new ResourceMetrics()); // corresponding monitor
            try {
                Instant begin = Instant.now(); // start load time
                R resource = strategy.load(parameter); // load resource
                monitor.setLoadTime(Duration.between(begin, Instant.now()).toMillis() / 1000D); // Set load time
                monitor.increaseLoadCount(); // increase load count
                return resource;
            } catch (Exception e) {
                e.printStackTrace();
                monitor.trackException(e);
                return null;
            }
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
            e.printStackTrace();
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

    /**
     * A record class that holds a resource key and its corresponding parameter for loading.
     *
     * @param <K> The type of the resource key.
     * @param <P> The type of the resource parameter.
     */
    record ParameterWrapper<K, P>(K key, P parameter) {
    }
}