package collections.array;

import java.util.Arrays;

/**
 * The SwapOnRemoveArray class is a resizable array-based collection that allows elements to be efficiently
 * added and removed. When an element is removed, it is swapped with the last element in the array to maintain
 * array continuity and improve removal performance.
 *
 * @param <E> the type of elements stored in the array
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class SwapOnRemoveArray<E> {

    private E[] data;   // The array to store elements
    private int size;   // The current number of elements in the array

    /**
     * Constructs a new SwapOnRemoveArray with an initial capacity of 10.
     */
    public SwapOnRemoveArray() {
        this.data = (E[]) new Object[10];
    }

    /**
     * Adds the specified element to the end of this array. If the array is full, it is resized to twice its
     * current capacity to accommodate more elements.
     *
     * @param element the element to be added to the array
     */
    public void add(E element) {
        if (size == data.length) {
            // If the array is full, create a new array with double the capacity
            E[] copy = (E[]) new Object[data.length * 2];
            // Copy the elements from the old array to the new array
            for (int i = 0; i < data.length; i++)
                copy[i] = data[i];
            // Update the reference to the new array
            data = copy;
        }
        // Add the new element to the end of the array
        data[size++] = element;
    }

    /**
     * Removes the element at the specified index from the array. The element is efficiently removed by swapping
     * it with the last element in the array, and then setting the last element to null.
     *
     * @param index the index of the element to be removed
     */
    public void remove(int index) {
        // Swap the element to be removed with the last element in the array
        data[index] = data[--size];
        // Set the last element to null
        data[size] = null;
    }

    /**
     * Removes the specific value from the array. If the value is not found, nothing happens.
     *
     * @param value the value to remove
     */
    public void removeValue(E value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                // Copy the remove method so we don't have a method call
                data[i] = data[--size];
                data[size] = null;
            }
        }
    }

    /**
     * Returns the element at the specified index in the array.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     */
    public E get(int index) {
        return data[index];
    }

    /**
     * Removes all elements from the array, leaving it empty.
     */
    public void clear() {
        Arrays.fill(data, null);
        size = 0;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the array.
     *
     * @param element the element to search for
     * @return the index of the element, or -1 if the element is not found
     */
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Trims the capacity of the array to the current size, reducing memory usage.
     */
    public void trimToSize() {
        if (size < data.length) {
            data = Arrays.copyOf(data, size);
        }
    }

    /**
     * Checks if the array is empty.
     *
     * @return true if the array is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the array is full.
     *
     * @return true if the array is full, false otherwise
     */
    public boolean isFull() {
        return size == data.length;
    }

    /**
     * Returns the current number of elements in the array.
     *
     * @return the size of the array
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the array contains the specified element.
     *
     * @param element the element to be checked for existence in the array
     * @return true if the element is found, false otherwise
     */
    public boolean contains(E element) {
        for (E i : data) {
            if (i.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the array of elements.
     *
     * @return an array containing the elements in this collection
     */
    public E[] getData() {
        E[] copy = (E[]) new Object[size];
        for (int i = 0; i < size; i++)
            copy[i] = data[i];
        return copy;
    }

    /**
     * Returns a string representation of the array.
     *
     * @return a string representation of the array
     */
    @Override
    public String toString() {
        return "SwapOnRemoveArray" + Arrays.toString(data);
    }
}