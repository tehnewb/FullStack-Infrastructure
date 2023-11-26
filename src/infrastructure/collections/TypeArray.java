package infrastructure.collections;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * The `TypeArray` class is a generic data structure for storing elements of a specified type.
 * It provides dynamic resizing and random access functionality. The purpose of this class is to wrap
 * objects that are usually cast, so they do not need casting in the future, which reduces overhead.
 * This is suggested for large quantities of objects needing cast.
 *
 * @param <T> The type of elements to be stored in the array.
 * @author Albert Beaupre
 */
public class TypeArray<T> {
    private T[] data;

    /**
     * Constructs a new `TypeArray` with an initial capacity of 16.
     *
     * @param type The class object representing the type of elements to be stored.
     */
    public TypeArray(Class<T> type) {
        // Create an array of the specified type and initial capacity
        data = (T[]) Array.newInstance(type, 16);
    }

    /**
     * Constructs a new `TypeArray` with the specified size as the internal array's capacity.
     *
     * @param type The class object representing the type of elements to be stored.
     * @param size The size of the internal array.
     */
    public TypeArray(Class<T> type, int size) {
        data = (T[]) Array.newInstance(type, size);
    }

    /**
     * Adds an element to the array. If the array is full, it will be resized to double its current capacity.
     *
     * @param element The element to be added to the array.
     */
    public void set(int index, T element) {
        if (index >= data.length)
            data = Arrays.copyOf(data, Math.max(data.length * 2, index));
        data[index] = element;
    }

    /**
     * Retrieves an element at the specified index from the array.
     *
     * @param index The index at which the element is to be retrieved.
     * @return The element at the specified index.
     * @throws IndexOutOfBoundsException if the provided index is out of bounds.
     */
    public T get(int index) {
        return data[index];
    }
}