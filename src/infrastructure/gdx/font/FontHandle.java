package infrastructure.gdx.font;

import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * The FontHandle class extends the LibGDX FileHandle class and is used for handling font tileIndices as a byte array.
 * It provides methods to read the font tileIndices as an input stream and get its length.
 *
 * @author Albert Beaupre
 */
public class FontHandle extends FileHandle {

    private final byte[] data; // The byte array containing the font tileIndices.

    /**
     * Constructs a FontHandle object with the given font tileIndices.
     *
     * @param data The byte array containing the font tileIndices.
     */
    public FontHandle(byte[] data) {
        this.data = data;
    }

    /**
     * Returns an input stream for reading the font tileIndices.
     *
     * @return An input stream containing the font tileIndices.
     */
    @Override
    public InputStream read() {
        return new ByteArrayInputStream(data);
    }

    /**
     * Returns the length of the font tileIndices in bytes.
     *
     * @return The length of the font tileIndices.
     */
    @Override
    public long length() {
        return data.length;
    }

    /**
     * This method is not implemented in this class and returns null.
     *
     * @return Null, as this method is not implemented.
     */
    @Override
    public String nameWithoutExtension() {
        return null;
    }

    /**
     * Returns a string representation of this FontHandle object.
     *
     * @return A string representation of the FontHandle.
     */
    @Override
    public String toString() {
        return "";
    }
}
