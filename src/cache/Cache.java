package cache;

import io.buffer.DynamicByteBuffer;
import io.compress.CompressionStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * The Cache class is used to manage a cache of data in the form of CacheFolder objects. It provides methods to add and remove
 * folders, encode the cache, and decode data into a cache.
 *
 * @author Albert Beaupre
 */
public class Cache {
    private final Map<Integer, CacheFolder> cache = new HashMap<>();


    /**
     * Decodes the given data and creates a Cache instance based on the folders and files within the decoded data.
     *
     * @param data the encoded cache data
     * @return the Cache instance with the folders and files
     */
    public static Cache decode(CompressionStrategy strategy, byte[] data) {
        Cache cache = new Cache();
        try (DynamicByteBuffer in = new DynamicByteBuffer(strategy.decompress(data))) {
            int folderCount = in.readByte();
            for (int i = 0; i < folderCount; i++) {
                int folderIndex = in.readByte();
                int folderSize = in.readShort();
                String folderName = in.readString();
                CacheFolder folder = new CacheFolder(folderIndex, folderName);
                for (int j = 0; j < folderSize; j++) {
                    int cacheFileIndex = in.readShort();
                    String fileName = in.readString();
                    int cacheFileSize = in.readInt();
                    byte[] cacheFileData = in.readBytes(cacheFileSize);
                    folder.add(new CacheFile(cacheFileIndex, fileName, cacheFileData));
                }
                cache.addFolder(folder);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not decode cache", e);
        }
        return cache;
    }

    /**
     * Encodes this Cache by storing the data from the folders and files into a byte array.
     *
     * @return the byte array with the folders and files within
     */
    public byte[] encode(CompressionStrategy strategy) {
        try (DynamicByteBuffer out = new DynamicByteBuffer()) {
            out.writeByte(cache.size());
            for (Map.Entry<Integer, CacheFolder> entry : cache.entrySet()) {
                CacheFolder folder = entry.getValue();
                out.writeByte(folder.getIndex());
                out.writeShort(folder.getSize());
                out.writeString(folder.getName());
                for (int cacheFileID = 0; cacheFileID < folder.getSize(); cacheFileID++) {
                    CacheFile file = folder.get(cacheFileID);
                    out.writeShort(file.index());
                    out.writeString(file.name());
                    out.writeInt(file.data().length);
                    out.writeBytes(file.data());
                }
            }
            return strategy.compress(out.toArray());
        } catch (Exception e) {
            throw new RuntimeException("Could not encode cache", e);
        }
    }

    /**
     * Adds a CacheFolder to the cache with the specified index ID.
     *
     * @param CacheFolder The CacheFolder to be added to the cache.
     */
    public void addFolder(CacheFolder CacheFolder) {
        cache.put(CacheFolder.getIndex(), CacheFolder);
    }

    /**
     * Removes a CacheFolder from the cache by its index ID.
     *
     * @param indexID The index ID of the CacheFolder to be removed.
     */
    public void removeFolder(int indexID) {
        cache.remove(indexID);
    }

    /**
     * Retrieves a CacheFolder from the cache by its index ID.
     *
     * @param index The index ID of the CacheFolder to retrieve.
     * @return The CacheFolder associated with the provided index ID, or null if not found.
     */
    public CacheFolder getFolder(int index) {
        return cache.get(index);
    }

    /**
     * Returns the size of the cache, which is the number of CacheFolder objects stored in it.
     *
     * @return The size of the cache.
     */
    public int getSize() {
        return cache.size();
    }

    /**
     * Clears the entire cache, removing all CacheFolder objects.
     */
    public void clearCache() {
        cache.clear();
    }

}
