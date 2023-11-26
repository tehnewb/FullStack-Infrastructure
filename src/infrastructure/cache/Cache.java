package infrastructure.cache;

import infrastructure.io.buffer.DynamicByteBuffer;
import infrastructure.io.compress.CompressionStrategy;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The Cache class is used to manage a cache of data in the form of CacheFolder objects. It provides methods to add and remove
 * folders, encode the cache, and decode data into a cache.
 *
 * @author Albert Beaupre
 */
public class Cache {

    private final Map<Integer, CacheFolder> cache = new HashMap<>(); // used for folders without a loader
    private int decompressionProgress; // tracks the progress of bytes written while decompressing the cache
    private int compressionProgress; // tracks the progress of bytes written while compressing the cache

    public static void main(String[] args) throws Exception {
        new Cache().decompress(CompressionStrategy.GZIP, Files.readAllBytes(Paths.get("./cache.bin")));
    }

    /**
     * Decodes the given data and creates a Cache instance based on the folders and files within the decoded data.
     *
     * @param data     The encoded cache data.
     * @param strategy The strategy used to decompress the data.
     */
    public void decompress(CompressionStrategy strategy, byte[] data) {
        try (DynamicByteBuffer in = new DynamicByteBuffer(strategy.decompress(data))) {
            int folderCount = in.readByte();
            for (int i = 0; i < folderCount; i++) {
                int folderIndex = in.readByte();
                int folderSize = in.readShort();
                String folderName = in.readString();
                CacheFolder folder = new CacheFolder(folderIndex, folderName);
                for (int j = 0; j < folderSize; j++) {
                    short cacheFileIndex = in.readShort();
                    if (cacheFileIndex == -1) // unused index
                        continue;
                    byte version = in.readByte();
                    byte type = in.readByte();
                    String fileName = in.readString();
                    int cacheFileSize = in.readInt();
                    byte[] cacheFileData = in.readBytes(cacheFileSize);
                    folder.add(new CacheFile(cacheFileIndex, version, type, fileName, cacheFileData));
                    decompressionProgress = (int) ((in.getReadPosition() / (float) in.size()) * 100);
                }
                addFolder(folder);
            }
            decompressionProgress = 100;
        } catch (Exception e) {
            throw new RuntimeException("Could not decode cache", e);
        }
    }

    /**
     * Encodes this Cache by storing the data from the folders and files into a byte array.
     *
     * @param strategy The strategy used to compress the data.
     * @return the byte array with the folders and files within
     */
    public byte[] compress(CompressionStrategy strategy) {
        int bufferSize = calculateCacheBufferSize();
        try (DynamicByteBuffer out = new DynamicByteBuffer(bufferSize, false)) {
            out.writeByte(cache.size());
            for (Map.Entry<Integer, CacheFolder> entry : cache.entrySet()) {
                CacheFolder folder = entry.getValue();
                out.writeByte(folder.getIndex());
                out.writeShort(folder.getSize());
                out.writeString(folder.getName());
                for (int cacheFileID = 0; cacheFileID < folder.getSize(); cacheFileID++) {
                    CacheFile file = folder.get(cacheFileID);
                    if (file == null) {
                        out.writeShort(-1);
                        continue;
                    }
                    out.writeShort(file.getIndex());
                    out.writeByte(file.getVersion());
                    out.writeByte(file.getType());
                    out.writeString(file.getName());
                    out.writeInt(file.getData().length);
                    out.writeBytes(file.getData());
                }
                compressionProgress = (int) ((out.getWritePosition() / (float) bufferSize) * 100);
            }
            compressionProgress = 100;
            return strategy.compress(out.toArray());
        } catch (Exception e) {
            throw new RuntimeException("Could not encode cache", e);
        }
    }

    /**
     * Calculates an estimated buffer size for compressing the cache.
     *
     * @return Estimated buffer size based on the size of the cache.
     */
    private int calculateCacheBufferSize() {
        int bufferSize = 1; // Initial size for the number of folders
        for (CacheFolder folder : cache.values()) {
            bufferSize += 3; // 1 byte for folder index and 2 bytes for folder size
            bufferSize += folder.getName().length(); // Size of folder name
            bufferSize++; // 1 byte for name identifier
            for (int cacheFileID = 0; cacheFileID < folder.getSize(); cacheFileID++) {
                CacheFile file = folder.get(cacheFileID);
                if (file == null) {
                    bufferSize += 2; // 2 bytes for unused index
                    continue;
                }
                bufferSize += 7; // 2 bytes for file index, 1 byte for version, 1 byte for type, and 3 bytes for name length
                bufferSize += file.getName().length(); // Size of file name
                bufferSize++; // 1 byte for name identifier
                bufferSize += 4; // 4 bytes for cache file data length
                bufferSize += file.getData().length; // Size of cache file data
            }
        }
        return bufferSize;
    }


    /**
     * Adds a CacheFolder to the cache with the specified index ID.
     *
     * @param folder The CacheFolder to be added to the cache.
     */
    public void addFolder(CacheFolder folder) {
        cache.put(folder.getIndex(), folder);
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

    /**
     * Get the progress of cache decompression.
     *
     * @return An integer representing the progress percentage of cache decompression.
     */
    public int getDecompressionProgress() {
        return decompressionProgress;
    }

    /**
     * Get the progress of cache compression.
     *
     * @return An integer representing the progress percentage of cache compression.
     */
    public int getCompressionProgress() {
        return compressionProgress;
    }

}
