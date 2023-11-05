package infrastructure.collections;

/**
 * The BitList class represents a collection for managing a list of bits. It allows efficient
 * operations such as setting, clearing, and checking the state of individual bits. The BitList
 * stores bits as long integers, with each bit in the list being represented as a single bit in
 * the long values.
 */
public class BitList {
    private long[] words;
    private int size;

    /**
     * Initializes a new instance of the BitList class with the specified size.
     *
     * @param size The number of bits in the BitList.
     */
    public BitList(int size) {
        words = new long[((size - 1) >> 6) + 1];
    }

    /**
     * Sets the bit at the specified index to 1 (true).
     *
     * @param index The index of the bit to set.
     */
    public void set(int index) {
        int local = index >> 6;
        if (local >= words.length) {
            long[] newArr = new long[this.words.length * 2];
            System.arraycopy(words, 0, newArr, 0, words.length);
            words = newArr;
        }
        words[local] |= (1L << index); // Restores invariants
        size++;
    }

    /**
     * Clears the bit at the specified index, setting it to 0 (false).
     *
     * @param index The index of the bit to clear.
     */
    public void clear(int index) {
        words[index >> 6] &= ~(1L << index);
    }

    /**
     * Gets the state of the bit at the specified index.
     *
     * @param index The index of the bit to check.
     * @return true if the bit is set (1), false if it's clear (0).
     */
    public boolean get(int index) {
        return ((words[index >> 6] >> index) & 1) == 1;
    }

    /**
     * Finds and returns the index of the next set bit (bit with value 1) starting from the specified
     * index.
     *
     * @param fromIndex The starting index for the search.
     * @return The index of the next set bit or -1 if none are found.
     */
    public int nextSetBit(int fromIndex) {
        int u = fromIndex >>> 6;
        if (u >= words.length)
            return -1;
        long word = (words[u] >>> fromIndex);
        if (word != 0)
            return fromIndex + Long.numberOfTrailingZeros(words[u]);

        for (int i = u + 1; i < words.length; i++) {
            word = words[i];
            if (word != 0)
                return i * 64 + Long.numberOfTrailingZeros(word);
        }
        return -1;
    }

    /**
     * Finds and returns the index of the next clear bit (bit with value 0) starting from the specified
     * index.
     *
     * @param fromIndex The starting index for the search.
     * @return The index of the next clear bit or -1 if none are found.
     */
    public int nextClearBit(int fromIndex) {
        int u = fromIndex >>> 6;
        long word = ~(words[u] >>> fromIndex);
        if (word != 0)
            return fromIndex + Long.numberOfTrailingZeros(words[u]);

        for (int i = u + 1; i < words.length; i++) {
            word = ~words[i];
            if (word != 0)
                return i * 64 + Long.numberOfTrailingZeros(word);
        }
        return -1;
    }

    /**
     * Clears all bits in the BitList, setting them to 0.
     */
    public void clearAll() {
        this.words = new long[this.words.length];
    }

    public int size() {
        return size;
    }

    /**
     * Returns a binary string representation of the BitList, showing the bits stored in the long words.
     *
     * @return A binary string representing the contents of the BitList.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (long word : words) {
            sb.append(Long.toBinaryString(word));
        }
        return sb.toString();
    }
}