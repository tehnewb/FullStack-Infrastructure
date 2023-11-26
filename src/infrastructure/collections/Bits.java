package infrastructure.collections;

import java.util.Arrays;

/**
 * This class should be chosen over java.util.BitSet if you want slightly faster operations with bits.
 * This class is also unsafe compared to java.util.BitSet as there are no checks for incorrect parameters
 * and no errors thrown specific to this class if an illegal operation has occurred.
 * <p>
 * The Bits class is suggested for use cases when the user needs to manage more than 64 bits.
 * Bits can be set, cleared, and retrieved at specific indices. Available set and cleared bits
 * can also be retrieved.
 */
public class Bits {
    private long[] words;
    private int size;

    /**
     * Initializes a new instance of the Bits class with the specified size.
     *
     * @param size The number of bits in the Bits.
     */
    public Bits(int size) {
        words = new long[((size - 1) >> 6) + 1];
    }

    /**
     * Sets the bit at the specified index to 1 (true).
     *
     * @param index The index of the bit to set.
     */
    public void set(int index) {
        if (index >= words.length * 64) words = Arrays.copyOf(words, words.length * 2);

        long word = words[index >> 6];
        long mask = 1L << index;

        if ((word & mask) == 0) {
            words[index >> 6] = word | mask;
            size++;
        }
    }

    /**
     * Clears the bit at the specified index, setting it to 0 (false).
     *
     * @param index The index of the bit to clear.
     */
    public void clear(int index) {
        long word = words[index >> 6];
        long mask = 1L << index;

        if ((word & mask) != 0) {
            words[index >> 6] = word & ~mask;
            size--;
        }
    }

    /**
     * Gets the state of the bit at the specified index.
     *
     * @param index The index of the bit to check.
     * @return true if the bit is set (1), false if it's clear (0).
     */
    public boolean get(int index) {
        if (index >= size) {
            return false; // Return false for out-of-bounds indices
        }
        return ((words[index >> 6] & (1L << index)) != 0);
    }

    /**
     * Finds and returns the index of the next set bit (bit with value 1) starting from the specified index.
     *
     * @param fromIndex The starting index for the search.
     * @return The index of the next set bit or -1 if none are found.
     */
    public int nextSetBit(int fromIndex) {
        int u = fromIndex >>> 6;
        if (u >= words.length) return -1;
        long mask = words[u] >>> fromIndex;
        if (mask != 0) return fromIndex + Long.numberOfTrailingZeros(mask);

        for (int i = u + 1; i < words.length; i++) {
            if ((mask = words[i]) != 0) {
                return i * 64 + Long.numberOfTrailingZeros(mask);
            }
        }

        return -1;
    }

    /**
     * Finds and returns the index of the next clear bit (bit with value 0) starting from the specified index.
     *
     * @param fromIndex The starting index for the search.
     * @return The index of the next clear bit or -1 if none are found.
     */
    public int nextClearBit(int fromIndex) {
        int u = fromIndex >>> 6;

        if (u >= words.length) return Math.min(fromIndex, words.length << 6);

        long mask = ~(words[u] >>> fromIndex);

        if (mask != 0) return fromIndex + Long.numberOfTrailingZeros(mask);

        for (int i = u + 1; i < words.length; i++) {
            if ((mask = ~words[i]) != 0) {
                return i * 64 + Long.numberOfTrailingZeros(mask);
            }
        }
        return -1;
    }

    /**
     * Clears all bits in the Bits, setting them to 0.
     */
    public void clearAll() {
        Arrays.fill(words, 0);
    }

    public int size() {
        return size;
    }

    /**
     * Returns a binary string representation of the Bits, showing the bits stored in the long words.
     *
     * @return A binary string representing the contents of the Bits.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Bits[");
        for (long word : words) {
            String binaryString = Long.toBinaryString(word);
            int padding = 64 - binaryString.length();
            String paddedBinaryString = "0".repeat(padding) + binaryString;
            sb.append(" ");
            sb.append(paddedBinaryString);
        }
        sb.append("]");
        return sb.toString();
    }

}