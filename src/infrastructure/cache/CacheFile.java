package infrastructure.cache;

import infrastructure.collections.index.Indexable;

/**
 * The CacheFile class represents a game file stored within a Cache Index. It is
 * designed to efficiently store and manage binary data associated with the game.
 * <p>
 * Each CacheFile is identified by an index, contains a version number, a name,
 * and the actual binary data of the file. The class provides methods to access
 * and manipulate these properties.
 * <p>
 * This class is used in game development and caching systems to store and retrieve
 * game assets efficiently. It is intended for use in scenarios where quick access
 * to binary data files is required.
 *
 * @author Albert Beaupre
 */
public class CacheFile implements Indexable {

    /**
     * The index that uniquely identifies this CacheFile within a Cache Index.
     */
    private int index;

    /**
     * The version number of the CacheFile, typically indicating the version or
     * revision of the game asset.
     */
    private byte version;

    /**
     * The type of CacheFile, which can be used to categorize the file's content.
     */
    private byte type;

    /**
     * The name of the CacheFile, which can be used to identify and reference it.
     */
    private String name;

    /**
     * The binary data of the CacheFile, which contains the actual contents of
     * the game asset.
     */
    private byte[] data;

    /**
     * Constructs an empty CacheFile with the specified name.
     *
     * @param name The name of the CacheFile.
     */
    public CacheFile(String name) {
        this.name = name;
    }

    /**
     * Constructs a new CacheFile with the specified parameters.
     *
     * @param index   The index that uniquely identifies this CacheFile.
     * @param version The version number of the CacheFile.
     * @param type    The type of CacheFile.
     * @param name    The name of the CacheFile.
     * @param data    The binary data of the CacheFile.
     */
    public CacheFile(int index, int version, int type, String name, byte[] data) {
        this.index = index;
        this.version = (byte) version;
        this.type = (byte) type;
        this.name = name;
        this.data = data;
    }

    /**
     * Gets the type of the CacheFile.
     *
     * @return The type of this CacheFile.
     */
    public byte getType() {
        return type;
    }

    /**
     * Sets the type of the CacheFile.
     *
     * @param type The type to set.
     */
    public void setType(byte type) {
        this.type = type;
    }

    /**
     * Gets the index that uniquely identifies this CacheFile within a Cache Index.
     *
     * @return The index of this CacheFile.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the index that uniquely identifies this CacheFile within a Cache Index.
     *
     * @param index The index to set.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Gets the version number of the CacheFile.
     *
     * @return The version number of this CacheFile.
     */
    public byte getVersion() {
        return version;
    }

    /**
     * Sets the version number of the CacheFile.
     *
     * @param version The version number to set.
     */
    public void setVersion(byte version) {
        this.version = version;
    }

    /**
     * Gets the name of the CacheFile.
     *
     * @return The name of this CacheFile.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the CacheFile.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the binary data of the CacheFile.
     *
     * @return The binary data of this CacheFile.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the binary data of the CacheFile.
     *
     * @param data The binary data to set.
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Returns a string representation of this CacheFile, which is its name.
     *
     * @return The name of this CacheFile as a string.
     */
    @Override
    public String toString() {
        return name;
    }
}