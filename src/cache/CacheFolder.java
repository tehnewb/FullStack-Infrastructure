package cache;

import java.util.Arrays;

/**
 * A CacheFolder is responsible for storing and managing multiple Cache Files. It is
 * expandable, allowing the addition of Cache Files as needed. Each CacheFolder has
 * a unique index ID for identification.
 *
 * @author Albert Beaupre
 */
public class CacheFolder {
    private static final int INITIAL_CAPACITY = 10; // Initial capacity of the cacheFiles array
    private static final int GROW_SIZE = 16; // Initial capacity of the cacheFiles array
    private final int index;           // The unique identifier for this CacheFolder.
    private int size;              // Number of Cache Files stored.
    private CacheFile[] cacheFiles; // Array to store Cache Files.

    /**
     * Constructs a new CacheFileFolder instance with the given index ID.
     *
     * @param index The unique identifier for this CacheFolder.
     */
    public CacheFolder(int index) {
        this.index = index;
        cacheFiles = new CacheFile[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds a Cache File to the CacheFolder. If the array is full, it automatically
     * resizes to accommodate additional Cache Files.
     *
     * @param cacheFile The Cache File to add to the CacheFolder.
     */
    public void add(CacheFile cacheFile) {
        if (size == cacheFiles.length) {
            // If the array is full, increase its capacity
            cacheFiles = Arrays.copyOf(cacheFiles, cacheFiles.length + GROW_SIZE);
        }
        cacheFiles[size++] = cacheFile;
    }

    /**
     * Retrieves a Cache File at the specified index within the CacheFolder.
     *
     * @param index The index of the Cache File to retrieve.
     * @return The Cache File at the specified index, or null if the index is out of bounds.
     */
    public CacheFile get(int index) {
        if (index >= 0 && index < size) {
            return cacheFiles[index];
        }
        return null;
    }

    /**
     * Checks if the CacheFolder contains a Cache File with the given index ID.
     *
     * @param index The index ID to check.
     * @return true if the CacheFolder contains a Cache File with the specified index ID, false otherwise.
     */
    public boolean contains(int index) {
        return index < size && cacheFiles[index] != null;
    }

    public int getIndex() {
        return index;
    }

    public int getSize() {
        return size;
    }
}
