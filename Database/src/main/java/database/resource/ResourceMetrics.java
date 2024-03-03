package database.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The ResourceMetrics class is responsible for tracking and maintaining various statistics related to a resource,
 * including load count, access count, load time, save count, save time, and exceptions encountered during resource operations.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class ResourceMetrics {

    // A list to store exceptions that may occur during resource operations.
    private List<Exception> exceptions;

    // Count of resource loading operations.
    private long loadCount;

    // Count of resource access operations.
    private long accessCount;

    // Total time taken to load the resource (in seconds).
    private double loadTime;

    // Count of resource saving operations.
    private long saveCount;

    // Total time taken to save the resource (in seconds).
    private double saveTime;

    // Size of the resource.
    private long size;

    /**
     * Get the size of the resource.
     *
     * @return The size of the resource in bytes.
     */
    public long getSize() {
        return size;
    }

    /**
     * Set the size of the resource.
     *
     * @param size The size of the resource in bytes.
     */
    protected void setSize(long size) {
        this.size = size;
    }

    /**
     * Get the count of resource loading operations.
     *
     * @return The count of resource loading operations.
     */
    public long getLoadCount() {
        return loadCount;
    }

    /**
     * Increase the count of resource loading operations by 1.
     */
    public void increaseLoadCount() {
        this.loadCount++;
    }

    /**
     * Get the count of resource access operations.
     *
     * @return The count of resource access operations.
     */
    public long getAccessCount() {
        return accessCount;
    }

    /**
     * Increase the count of resource access operations by 1.
     */
    public void increaseAccessCount() {
        this.accessCount++;
    }

    /**
     * Get the total time taken to load the resource (in seconds).
     *
     * @return The total time taken to load the resource in seconds.
     */
    public double getLoadTime() {
        return loadTime;
    }

    /**
     * Set the total time taken to load the resource (in seconds).
     *
     * @param loadTime The total time taken to load the resource in seconds.
     */
    protected void setLoadTime(double loadTime) {
        this.loadTime = loadTime;
    }

    /**
     * Get the count of resource saving operations.
     *
     * @return The count of resource saving operations.
     */
    public long getSaveCount() {
        return saveCount;
    }

    /**
     * Increase the count of resource saving operations by 1.
     */
    public void increaseSaveCount() {
        this.saveCount++;
    }

    /**
     * Get the total time taken to save the resource (in seconds).
     *
     * @return The total time taken to save the resource in seconds.
     */
    public double getSaveTime() {
        return saveTime;
    }

    /**
     * Set the total time taken to save the resource (in seconds).
     *
     * @param saveTime The total time taken to save the resource in seconds.
     */
    protected void setSaveTime(double saveTime) {
        this.saveTime = saveTime;
    }

    /**
     * Track an exception that occurred during a resource operation.
     *
     * @param exception The exception to be tracked.
     */
    public void trackException(Exception exception) {
        if (Objects.isNull(exceptions))
            this.exceptions = new ArrayList<>();

        this.exceptions.add(exception);
    }

    @Override
    public String toString() {
        return """
                ResourceMetrics{
                exceptions=%s,
                accessCount=%s,
                loadCount=%s,
                loadTime=%ss,
                saveCount=%s,
                saveTime=%ss,
                size=%s
                }
                """.formatted(exceptions, accessCount, loadCount, loadTime, saveCount, saveTime, size);
    }

    /**
     * Get a read-only view of exceptions that occurred during resource operations.
     *
     * @return An unmodifiable list of exceptions.
     */
    public List<Exception> getExceptions() {
        return Collections.unmodifiableList(exceptions);
    }
}