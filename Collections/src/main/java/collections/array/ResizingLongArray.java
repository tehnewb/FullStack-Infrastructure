package collections.array;

/**
 * ResizingLongArray is a class that represents a dynamic array with automatic resizing capabilities for primitive longs.
 * It allows the storage and retrieval of elements at specified indices, automatically resizing the underlying array
 * when necessary to accommodate new elements.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class ResizingLongArray {

    /**
     * The underlying array to store long elements.
     */
    private long[] array;

    /**
     * Constructs a ResizingLongArray with the specified initial size.
     *
     * @param size the initial size of the array.
     */
    public ResizingLongArray(int size) {
        this.array = new long[size];
    }

    /**
     * Sets the long at the specified index. If the index is greater than or equal to the current length of the array,
     * the array is resized to accommodate the new index, and the long is then set at the specified index.
     *
     * @param index the index at which to set the long.
     * @param value the long value to be set at the specified index.
     */
    public void set(int index, long value) {
        if (index >= array.length) {
            long[] copy = new long[index + 1];
            System.arraycopy(array, 0, copy, 0, array.length);
            array = copy;
        }
        array[index] = value;
    }

    /**
     * Retrieves the long at the specified index.
     *
     * @param index the index of the long to retrieve.
     * @return the long at the specified index.
     */
    public long get(int index) {
        return array[index];
    }
}
