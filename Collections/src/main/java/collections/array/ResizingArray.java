package collections.array;

import java.lang.reflect.Array;

/**
 * ResizingArray is a generic class that represents a dynamic array with automatic resizing capabilities.
 * It allows the storage and retrieval of elements at specified indices, automatically resizing the underlying array
 * when necessary to accommodate new elements.
 *
 * @param <T> the type of elements stored in the array.
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class ResizingArray<T> {

    /**
     * The underlying array to store elements of type T.
     */
    private T[] array;

    /**
     * Constructs a ResizingArray with the specified type and initial size.
     *
     * @param type the class object representing the type of elements to be stored.
     * @param size the initial size of the array.
     */
    public ResizingArray(Class<T> type, int size) {
        this.array = (T[]) Array.newInstance(type, size);
    }

    /**
     * Sets the element at the specified index. If the index is greater than or equal to the current length of the array,
     * the array is resized to accommodate the new index, and the element is then set at the specified index.
     *
     * @param index the index at which to set the element.
     * @param value the value to be set at the specified index.
     */
    public void set(int index, T value) {
        if (index >= array.length) {
            T[] copy = (T[]) new Object[index * 2];
            System.arraycopy(array, 0, copy, 0, array.length);
            array = copy;
        }
        array[index] = value;
    }

    /**
     * Retrieves the element at the specified index.
     *
     * @param index the index of the element to retrieve.
     * @return the element at the specified index.
     */
    public T get(int index) {
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
    public T[] getElements() {
        return array;
    }
}