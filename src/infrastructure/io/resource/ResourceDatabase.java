package infrastructure.io.resource;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * ResourceDatabase is a generic class that manages the loading, caching, and accessing of resources
 * using a specified resource loading strategy. It allows for asynchronous loading of resources and
 * keeps track of various resource statistics like load times, access counts, save counts, save times,
 * and exceptions.
 *
 * @param <K> The type of the resource key.
 * @param <R> The type of the resource.
 * @param <P> The type of the resource parameter used for loading.
 * @author Albert Beaupre
 */
public class ResourceDatabase<K, R, P> {

    private final ExecutorService service;
    private final ConcurrentLinkedQueue<ParameterWrapper<K, P>> preparedToLoad;
    private final ConcurrentLinkedQueue<K> preparedToSave;
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
        this.preparedToLoad = new ConcurrentLinkedQueue<>();
        this.preparedToSave = new ConcurrentLinkedQueue<>();
        this.monitors = new ConcurrentHashMap<>();
    }

    /**
     * Asynchronously prepares a resource for loading by adding it to the prepared queue.
     *
     * @param key       The key of the resource.
     * @param parameter The parameter required for loading the resource.
     * @throws NullPointerException if the parameter is null.
     */
    public final void prepareToLoad(K key, P parameter) {
        Objects.requireNonNull(key, "Cannot prepare to load null resource key");
        Objects.requireNonNull(parameter, "Cannot prepare to load null resource parameter");
        this.preparedToLoad.offer(new ParameterWrapper<>(key, parameter));
    }

    /**
     * Asynchronously prepares a resource for saving by adding its key to the prepared queue.
     *
     * @param key The key of the resource to save.
     * @throws NullPointerException if the key is null.
     */
    public final void prepareToSave(K key) {
        Objects.requireNonNull(key, "Cannot prepare to save null resource key");
        this.preparedToSave.offer(key);
    }

    /**
     * Loads all resources that have been prepared in the load queue.
     */
    public final void loadPreparedResources() {
        while (!preparedToLoad.isEmpty()) {
            ParameterWrapper<K, P> wrapper = preparedToLoad.poll();
            load(wrapper.key, wrapper.parameter());
        }
    }

    /**
     * Saves all resources that have been prepared in the save queue.
     */
    public final void savePreparedResources() {
        while (!preparedToSave.isEmpty()) {
            save(preparedToSave.poll());
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
     * Asynchronously saves a resource using the specified strategy.
     *
     * @param key The key of the resource to save.
     * @return A CompletableFuture representing the saved resource.
     * @throws NullPointerException if the key is null.
     */
    public CompletableFuture<Void> save(K key) {
        Objects.requireNonNull(key, "Cannot save resource with null key");
        return CompletableFuture.supplyAsync(() -> {
            ResourceMetrics monitor = monitors.computeIfAbsent(key, v -> new ResourceMetrics()); // corresponding monitor
            try {
                Instant begin = Instant.now(); // start save time
                strategy.save(strategy.get(key));
                monitor.setSaveTime(Duration.between(begin, Instant.now()).toMillis() / 1000D); // Set save time
                monitor.increaseSaveCount(); // increase save count
            } catch (Exception e) {
                e.printStackTrace();
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
    private record ParameterWrapper<K, P>(K key, P parameter) {
    }
}