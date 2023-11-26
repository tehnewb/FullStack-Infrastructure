package collections.array;

/**
 * ResizingShortArray is a class that represents a dynamic array with automatic resizing capabilities for primitive shorts.
 * It allows the storage and retrieval of elements at specified indices, automatically resizing the underlying array
 * when necessary to accommodate new elements.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class ResizingShortArray {

    /**
     * The underlying array to store short elements.
     */
    private short[] array;

    /**
     * Constructs a ResizingShortArray with the specified initial size.
     *
     * @param size the initial size of the array.
     */
    public ResizingShortArray(int size) {
        this.array = new short[size];
    }

    /**
     * Sets the short at the specified index. If the index is greater than or equal to the current length of the array,
     * the array is resized to accommodate the new index, and the short is then set at the specified index.
     *
     * @param index the index at which to set the short.
     * @param value the short value to be set at the specified index.
     */
    public void set(int index, short value) {
        if (index >= array.length) {
            short[] copy = new short[index + 1];
            System.arraycopy(array, 0, copy, 0, array.length);
            array = copy;
        }
        array[index] = value;
    }

    /**
     * Retrieves the short at the specified index.
     *
     * @param index the index of the short to retrieve.
     * @return the short at the specified index.
     */
    public short get(int index) {
        return array[index];
    }
}
