package infrastructure.cache;

import infrastructure.io.buffer.DynamicByteBuffer;
import infrastructure.io.compress.CompressionStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

/**
 * The Cache class is used to manage a cache of data in the form of CacheFolder objects. It provides methods to add and remove
 * folders, encode the cache, and decode data into a cache.
 *
 * @author Albert Beaupre
 */
public class Cache {
    private final Map<Integer, CacheFolder> cache = new HashMap<>();
    private float progress;

    /**
     * Decodes the given data and creates a Cache instance based on the folders and files within the decoded data.
     *
     * @param data     the encoded cache data
     * @param strategy the strategy used to decompress the data
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

                    progress = (in.getReadPosition() / (float) in.size());
                }
                addFolder(folder);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not decode cache", e);
        }
    }

    /**
     * Encodes this Cache by storing the data from the folders and files into a byte array.
     *
     * @return the byte array with the folders and files within
     */
    public byte[] compress(CompressionStrategy strategy) {
        try (DynamicByteBuffer out = new DynamicByteBuffer(1024, true)) {
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
            }
            return strategy.compress(out.toArray());
        } catch (Exception e) {
            throw new RuntimeException("Could not encode cache", e);
        }
    }

    /**
     * Calculates a CRC32 checksum for the entire Cache instance.
     *
     * @return A CRC32 checksum value as a long.
     */
    public long calculateChecksum() {
        CRC32 crc32 = new CRC32();

        // Iterate through CacheFolders and their CacheFiles
        for (CacheFolder folder : cache.values()) {
            crc32.update(folder.getIndex());
            crc32.update(folder.getName().getBytes());

            for (int i = 0; i < folder.getSize(); i++) {
                CacheFile file = folder.get(i);
                if (file == null) { // unused index
                    crc32.update(0);
                    continue;
                }
                crc32.update(file.getIndex());
                crc32.update(file.getVersion());
                crc32.update(file.getType());
                crc32.update(file.getName().getBytes());
                crc32.update(file.getData());
            }
        }

        return crc32.getValue();
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

    /**
     * Retrieves the current progress of the cache loading is at between starting the load and ending.
     *
     * @return The current progress of the cache loading is at.
     */
    public float getProgress() {
        return progress;
    }

}
