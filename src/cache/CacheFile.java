package cache;

/**
 * Each CacheFile is associated with an index ID and contains the actual binary data
 * of the file. It is used to store game files within a Cache Index for efficient
 * retrieval and management.
 *
 * @author Albert Beaupre
 */
public record CacheFile(int index, String name, byte[] data) {

    @Override
    public String toString() {
        return name;
    }
}