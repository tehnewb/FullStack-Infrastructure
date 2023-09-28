package infrastructure.cache;

import infrastructure.entity.EntityList;

/**
 * A CacheFolder is responsible for storing and managing multiple Cache Files. It is
 * expandable, allowing the addition of Cache Files as needed. Each CacheFolder has
 * a unique index ID for identification.
 *
 * @author Albert Beaupre
 */
public class CacheFolder {
    private static final int INITIAL_CAPACITY = 10; // Initial capacity of the cacheFiles array
    private final EntityList<CacheFile> files; // Array to store Cache Files.
    private final int index;           // The unique identifier for this CacheFolder.
    private final String name; // The name of the folder

    /**
     * Constructs a new CacheFileFolder instance with the given index ID.
     *
     * @param index The unique identifier for this CacheFolder.
     */
    public CacheFolder(int index, String name) {
        this.index = index;
        this.name = name;
        this.files = new EntityList<>(INITIAL_CAPACITY);
    }

    /**
     * Adds a Cache File to the CacheFolder. If the array is full, it automatically
     * resizes to accommodate additional Cache Files.
     *
     * @param cacheFile The Cache File to add to the CacheFolder.
     */
    public void add(CacheFile cacheFile) {
        files.add(cacheFile);
    }

    /**
     * Retrieves a Cache File at the specified index within the CacheFolder.
     *
     * @param index The index of the Cache File to retrieve.
     * @return The Cache File at the specified index, or null if the index is out of bounds.
     */
    public CacheFile get(int index) {
        return files.get(index);
    }

    /**
     * Retrieves a CacheFile by its name within the CacheFolder.
     *
     * @param fileName The name of the CacheFile to retrieve.
     * @return The CacheFile with the specified name, or null if not found.
     */
    public CacheFile getByName(String fileName) {
        for (CacheFile cacheFile : files) {
            if (cacheFile != null && cacheFile.getName().equals(fileName)) {
                return cacheFile;
            }
        }
        return null;
    }

    /**
     * Removes a Cache File from the CacheFolder at the specified index. If the index
     * is valid, it removes the Cache File and shifts the array to fill the gap.
     *
     * @param index The index of the Cache File to remove.
     * @return The removed Cache File, or null if the index is out of bounds.
     */
    public CacheFile remove(int index) {
        return files.remove(index);
    }

    /**
     * Checks if the CacheFolder contains a Cache File with the given index ID.
     *
     * @param index The index ID to check.
     * @return true if the CacheFolder contains a Cache File with the specified index ID, false otherwise.
     */
    public boolean contains(int index) {
        return index < getSize() && files.get(index) != null;
    }

    /**
     * Retrieves the index at which this CacheFolder is placed within the Cache.
     *
     * @return The index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Retrieves the count of cache files within this CacheFolder.
     *
     * @return The count of cache files.
     */
    public int getSize() {
        return files.capacity();
    }

    /**
     * Retrieves the name of this CacheFolder.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
