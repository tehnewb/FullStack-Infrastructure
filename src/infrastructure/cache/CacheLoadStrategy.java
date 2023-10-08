package infrastructure.cache;

/**
 * The CacheLoadStrategy interface defines a strategy for loading and processing
 * cache files of a specific type. It is used in conjunction with the Cache class
 * to provide custom loading behavior for individual cache files based on their
 * content type.
 *
 * @param <T> The type of object that this strategy loads and returns.
 * @author Albert Beaupre
 * @see Cache
 * @see CacheFile
 */
public interface CacheLoadStrategy<T> {

    /**
     * Loads and processes a cache file based on the specified strategy.
     *
     * @param fileData The CacheFile data representing the file to be loaded and processed.
     * @return An object of type T that represents the loaded and processed data from the cache file.
     * @see CacheFile
     */
    public T load(byte[] fileData);
}