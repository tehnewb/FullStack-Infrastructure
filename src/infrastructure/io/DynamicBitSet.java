package infrastructure.io;

import java.util.Arrays;

/**
 * A dynamic bit set implementation that allows the manipulation of a set of bits
 * with dynamic resizing capabilities.
 */
public class DynamicBitSet {
    private static final int DEFAULT_SIZE = 64; // Default initial size
    private static final int GROW_SIZE = 64; // Default grow size
    private long[] bits; // The underlying array to store bits
    private int size; // The number of bits in the bit set

    /**
     * Constructs a new DynamicBitSet with the default initial size.
     */
    public DynamicBitSet() {
        bits = new long[DEFAULT_SIZE];
        size = 0;
    }

    /**
     * Constructs a new DynamicBitSet with a specified initial size.
     *
     * @param initialSize The initial size of the bit set.
     * @throws IllegalArgumentException If the initial size is not positive.
     */
    public DynamicBitSet(int initialSize) {
        if (initialSize <= 0) {
            throw new IllegalArgumentException("Initial size must be positive");
        }
        bits = new long[(initialSize + 63) / 64];
        size = 0;
    }

    /**
     * Sets the bit at the specified index to the given value.
     *
     * @param index The index of the bit to set.
     * @param value The value to set the bit to (true or false).
     * @throws IndexOutOfBoundsException If the index is negative or out of bounds.
     */
    public void set(int index, boolean value) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        }

        if (index >= size()) {
            resize(index + GROW_SIZE);
        }

        if (value) {
            bits[index / 64] |= (1L << (index % 64));
        } else {
            bits[index / 64] &= ~(1L << (index % 64));
        }
    }

    /**
     * Sets the bit at the specified index to true.
     *
     * @param index The index of the bit to set.
     * @throws IndexOutOfBoundsException If the index is negative or out of bounds.
     */
    public void set(int index) {
        set(index, true);
    }

    /**
     * Retrieves the value of the bit at the specified index.
     *
     * @param index The index of the bit to retrieve.
     * @return The value of the bit at the specified index (true or false).
     * @throws IndexOutOfBoundsException If the index is negative or out of bounds.
     */
    public boolean get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return (bits[index / 64] & (1L << (index % 64))) != 0;
    }

    /**
     * Returns the number of bits in the bit set.
     *
     * @return The number of bits in the bit set.
     */
    public int size() {
        return size;
    }

    /**
     * Clears all bits in the bit set, setting them to false.
     */
    public void clear() {
        Arrays.fill(bits, 0);
        size = 0;
    }

    /**
     * Resizes the bit set to the specified new size. If the new size is greater
     * than the current size, additional bits are initialized to false.
     *
     * @param newSize The new size of the bit set.
     * @throws IllegalArgumentException If the new size is not positive.
     */
    public void resize(int newSize) {
        if (newSize <= 0) {
            throw new IllegalArgumentException("New size must be positive");
        }

        int newLength = (newSize + 63) / 64;
        long[] newBits = new long[newLength];
        System.arraycopy(bits, 0, newBits, 0, Math.min(bits.length, newBits.length));
        bits = newBits;
        size = newSize;
    }

    /**
     * Counts the number of set (true) bits in the bit set.
     *
     * @return The count of set bits in the bit set.
     */
    public int cardinality() {
        int count = 0;
        for (int i = 0; i < bits.length; i++) {
            count += Long.bitCount(bits[i]);
        }
        return count;
    }

    /**
     * Returns the index of the next set (true) bit starting from the specified
     * index (inclusive).
     *
     * @param fromIndex The index to start searching from.
     * @return The index of the next set bit, or -1 if none is found.
     * @throws IndexOutOfBoundsException If the fromIndex is negative or out of bounds.
     */
    public int nextSetBit(int fromIndex) {
        if (fromIndex < 0 || fromIndex >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        int startWord = fromIndex / 64;
        int startBit = fromIndex % 64;

        for (int i = startWord; i < bits.length; i++) {
            long word = bits[i];
            word >>>= startBit;

            if (word != 0) {
                return i * 64 + Long.numberOfTrailingZeros(word);
            }

            startBit = 0; // After the first word, check from the beginning of subsequent words
        }

        return -1; // No set bit found
    }

    /**
     * Returns the index of the next clear (false) bit starting from the specified
     * index (inclusive).
     *
     * @param fromIndex The index to start searching from.
     * @return The index of the next clear bit, or -1 if none is found.
     * @throws IndexOutOfBoundsException If the fromIndex is negative or out of bounds.
     */
    public int nextClearBit(int fromIndex) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("Index out of bounds");

        if (fromIndex >= size())
            resize(fromIndex + GROW_SIZE);

        int startWord = fromIndex / 64;
        int startBit = fromIndex % 64;

        for (int i = startWord; i < bits.length; i++) {
            long word = ~bits[i];
            word >>>= startBit;

            if (word != 0) {
                return i * 64 + Long.numberOfTrailingZeros(word);
            }

            startBit = 0; // After the first word, check from the beginning of subsequent words
        }

        return -1; // No clear bit found
    }

    /**
     * Clears the bit at the specified index, setting it to false.
     *
     * @param index The index of the bit to clear.
     * @throws IndexOutOfBoundsException If the index is negative or out of bounds.
     */
    public void clear(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        bits[index / 64] &= ~(1L << (index % 64));
    }
}