package io.resource;

/**
 * The ResourceOperationsStrategy interface defines methods for loading and saving resource data of a specific type.
 * Implementations of this interface specify how resource data is managed, loaded, and saved.
 *
 * @param <T> The type of resource this strategy handles.
 */
public interface ResourceOperationsStrategy<T> {

    /**
     * Load resource data based on the provided identifier.
     * <p>
     * This method is responsible for retrieving resource data associated with a unique identifier.
     *
     * @param identifier The unique identifier of the resource to load.
     * @return An instance of the resource type loaded from the specified identifier,
     * or null if the resource data is not found or cannot be loaded.
     */
    T load(String identifier);

    /**
     * Serialize and save resource data.
     * <p>
     * This method takes a resource object, serializes it into a format suitable for storage,
     * and saves it using a specific identifier.
     *
     * @param identifier The unique identifier under which the resource will be saved.
     * @param resource   The resource object to be serialized and saved.
     */
    void save(String identifier, T resource);
}
