package collections.array;

/**
 * ResizingByteArray is a class that represents a dynamic array with automatic resizing capabilities for primitive bytes.
 * It allows the storage and retrieval of elements at specified indices, automatically resizing the underlying array
 * when necessary to accommodate new elements.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class ResizingByteArray {

    /**
     * The underlying array to store byte elements.
     */
    private byte[] array;

    /**
     * Constructs a ResizingByteArray with the specified initial size.
     *
     * @param size the initial size of the array.
     */
    public ResizingByteArray(int size) {
        this.array = new byte[size];
    }

    /**
     * Sets the byte at the specified index. If the index is greater than or equal to the current length of the array,
     * the array is resized to accommodate the new index, and the byte is then set at the specified index.
     *
     * @param index the index at which to set the byte.
     * @param value the byte value to be set at the specified index.
     */
    public void set(int index, byte value) {
        if (index >= array.length) {
            byte[] copy = new byte[index + 1];
            System.arraycopy(array, 0, copy, 0, array.length);
            array = copy;
        }
        array[index] = value;
    }

    /**
     * Retrieves the byte at the specified index.
     *
     * @param index the index of the byte to retrieve.
     * @return the byte at the specified index.
     */
    public byte get(int index) {
        return array[index];
    }

    /**
     * @return The length of elements within this ResizingArray.
     */
    public int length() {
        return array.length;
    }

    /**
     * @return The elements within this ResizingArray.
     */
    public byte[] getElements() {
        return array;
    }
}
