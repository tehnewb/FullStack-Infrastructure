package infrastructure.gdx.io;

import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * The `ByteFileHandle` class is a custom implementation of the LibGDX `FileHandle` class,
 * designed to read data from a byte array rather than an actual file. This can be useful in cases
 * where you need to work with data that is not stored in a physical file, such as when dealing with
 * in-memory assets or dynamically generated content.
 *
 * @author Albert Beaupre
 */
public class ByteFileHandle extends FileHandle {

    private final byte[] data; // The byte array containing the font data.

    /**
     * Constructs a new `ByteFileHandle` instance with the provided byte array.
     *
     * @param data The byte array containing the data to be read.
     */
    public ByteFileHandle(byte[] data) {
        this.data = data;
    }

    /**
     * Returns an `InputStream` that allows reading the data from the internal byte array.
     *
     * @return An `InputStream` for reading the data.
     */
    @Override
    public InputStream read() {
        return new ByteArrayInputStream(data);
    }

    /**
     * Returns the length of the data contained in the byte array.
     *
     * @return The length of the data.
     */
    @Override
    public long length() {
        return data.length;
    }

    /**
     * This method is not implemented in this class, and it always returns null.
     *
     * @return Always returns null.
     */
    @Override
    public String nameWithoutExtension() {
        return null;
    }

    /**
     * Returns a string representation of this `ByteFileHandle`. This method is not implemented in
     * this class, and it always returns an empty string.
     *
     * @return An empty string.
     */
    @Override
    public String toString() {
        return "";
    }
}