package buffer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Function;

/**
 * A dynamic byte buffer that provides methods to store and retrieve
 * various data types with a growing internal array.
 *
 * @author Albert Beaupre
 */
public class DynamicByteBuffer implements Cloneable {
    private static final int DEFAULT_CAPACITY = 128; // Default capacity of DynamicByteBuffer
    private boolean growing;
    private byte[] data;
    private int size;
    private int readPosition;
    private int writePosition;
    private int bitBuffer;
    private int bitPosition;

    /**
     * Constructs a DynamicByteBuffer that copies everything from the provided
     * DynamicByteBuffer.
     *
     * @param buffer The buffer to copy everything from.
     */
    public DynamicByteBuffer(DynamicByteBuffer buffer) {
        this.data = buffer.data;
        this.readPosition = buffer.readPosition;
        this.writePosition = buffer.writePosition;
        this.bitBuffer = buffer.bitBuffer;
        this.bitPosition = buffer.bitPosition;
        this.size = buffer.size;
        this.growing = buffer.growing;
    }

    /**
     * Constructs a DynamicByteBuffer with an initial capacity of 128 bytes.
     */
    public DynamicByteBuffer() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs a DynamicByteBuffer with the specified initial capacity.
     *
     * @param initialCapacity The initial capacity of the byte array.
     * @throws IllegalArgumentException if initial capacity is non-positive.
     */
    public DynamicByteBuffer(int initialCapacity) {
        this(initialCapacity, true);
    }

    /**
     * Constructs a DynamicByteBuffer with the specified initial capacity.
     *
     * @param initialCapacity The initial capacity of the byte array.
     * @param growing         The flag to determine if the internal byte array should grow when reaching its limit.
     * @throws IllegalArgumentException if initial capacity is non-positive.
     */
    public DynamicByteBuffer(int initialCapacity, boolean growing) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive.");
        }
        this.growing = growing;
        data = new byte[initialCapacity];
        size = 0;
        readPosition = 0;
        writePosition = 0;
    }

    /**
     * Constructs a DynamicByteBuffer with the specified data byte array.
     *
     * @param data The byte array to fill as the internal array.
     */
    public DynamicByteBuffer(byte[] data) {
        this.data = data;
        this.readPosition = 0;
        this.writePosition = 0;
        this.size = data.length;
    }

    /**
     * Ensures the capacity for additional bytes. If the current capacity is not enough to
     * accommodate the requested additional bytes, the internal byte array is resized.
     *
     * @param additionalBytes The number of additional bytes needed.
     * @throws RuntimeException if this DynamicByteBuffer has not been set to grow and has reached its limit
     */
    private void ensureCapacity(int additionalBytes) {
        if (size + additionalBytes > data.length) {
            if (growing) {
                byte[] newData = new byte[data.length * 2];
                System.arraycopy(data, 0, newData, 0, size);
                data = newData;
            } else {
                throw new RuntimeException("DynamicByteBuffer not set to grow and has reached its limit");
            }
        }
    }


    /**
     * This method is used to trim off any excess bytes not being used within the internal array.
     */
    public void trim() {
        this.data = Arrays.copyOfRange(data, readPosition, writePosition);
    }

    /**
     * Checks index bounds for retrieving values.
     *
     * @param index    The index from which to retrieve the value.
     * @param byteSize The size of the value in bytes.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    private void checkIndexBounds(int index, int byteSize) {
        if (index < 0 || index + byteSize > size) {
            throw new IndexOutOfBoundsException("Cannot read past capacity");
        }
    }

    /**
     * Starts bit access by resetting the bit buffer and position.
     */
    public void beginBitAccess() {
        bitPosition = writePosition * Byte.SIZE;
    }

    /**
     * Finishes bit access by writing any remaining bits to the byte array.
     * If the bit buffer is not empty, it is written to the byte array.
     */
    public void finishBitAccess() {
        writePosition = (bitPosition + 7) / Byte.SIZE;
    }

    /**
     * Writes a specified number of bits from the given integer value to the dynamic byte buffer.
     * Bits are written from the least significant bit (LSB) to the most significant bit (MSB).
     *
     * @param value   The integer value from which to extract bits.
     * @param numBits The number of bits to write. Must be between 1 and 32, inclusive.
     * @throws IllegalArgumentException if the number of bits is not within the valid range.
     */
    public void writeBits(int value, int numBits) {
        if (numBits < 1 || numBits > 32)
            throw new IllegalArgumentException("Invalid number of bits to write.");

        Function<Integer, Integer> bitMask = index -> (1 << index) - 1;

        int bytePos = bitPosition >> 3;
        int bitOffset = 8 - (bitPosition & 7);

        bitPosition += numBits;
        writePosition = (bitPosition + 7) / Byte.SIZE;

        byte b;
        for (; numBits > bitOffset; bitOffset = Byte.SIZE) {
            b = getByte(bytePos);
            setByte(bytePos, (byte) (b & ~bitMask.apply(bitOffset)));
            setByte(bytePos++, (byte) (b | (value >> (numBits - bitOffset)) & bitMask.apply(bitOffset)));
            numBits -= bitOffset;
        }
        b = getByte(bytePos);
        if (numBits == bitOffset) {
            setByte(bytePos, (byte) (b & ~bitMask.apply(bitOffset)));
            setByte(bytePos, (byte) (b | value & bitMask.apply(bitOffset)));
        } else {
            setByte(bytePos, (byte) (b & ~(bitMask.apply(numBits) << (bitOffset - numBits))));
            setByte(bytePos, (byte) (b | (value & bitMask.apply(numBits)) << (bitOffset - numBits)));
        }
    }

    /**
     * Sets the specified byte value at the given index.
     *
     * @param index The index to set the value at.
     * @param value The byte value to set.
     */
    public void setByte(int index, byte value) {
        this.data[index] = value;
    }

    /**
     * Retrieves the byte value at the specified index.
     *
     * @param index The index of the byte value.
     * @return the byte value.
     */
    public byte getByte(int index) {
        return data[index];
    }

    /**
     * Stores a string value in the dynamic byte array at the current write position.
     *
     * @param string The string to store.
     */
    public void writeString(String string) {
        writeBytes(string.getBytes(StandardCharsets.UTF_8));
        writeByte(0);
    }

    /**
     * Retrieves a String value from the dynamic byte array at the current read position and increments the read position.
     *
     * @return The string at the current read position.
     */
    public String readString() {
        StringBuilder builder = new StringBuilder();
        char c;
        while ((c = (char) readByte()) != 0)
            builder.append(c);
        return builder.toString();
    }

    /**
     * Stores a byte value in the dynamic byte array at the current write position.
     *
     * @param value The byte value to store.
     */
    public void writeByte(int value) {
        ensureCapacity(Byte.BYTES);
        data[writePosition] = (byte) value;
        writePosition += Byte.BYTES;
        size = Math.max(size, writePosition);
    }

    /**
     * Retrieves a byte value from the dynamic byte array at the current read position
     * and increments the read position.
     *
     * @return The byte value at the current read position.
     * @throws IndexOutOfBoundsException if the read position is out of bounds.
     */
    public byte readByte() {
        byte value = readByte(readPosition);
        readPosition += Byte.BYTES;
        return value;
    }

    /**
     * Stores a short value in the dynamic byte array at the current write position.
     *
     * @param value The short value to store.
     */
    public void writeShort(int value) {
        ensureCapacity(Short.BYTES);
        data[writePosition] = (byte) ((value >> 8) & 0xFF);
        data[writePosition + 1] = (byte) (value & 0xFF);
        writePosition += Short.BYTES;
        size = Math.max(size, writePosition);
    }

    /**
     * Retrieves a short value from the dynamic byte array at the current read position
     * and increments the read position.
     *
     * @return The short value at the current read position.
     * @throws IndexOutOfBoundsException if the read position is out of bounds.
     */
    public short readShort() {
        short value = readShort(readPosition);
        readPosition += Short.BYTES;
        return value;
    }

    /**
     * Stores an integer value in the dynamic byte array at the current write position.
     *
     * @param value The integer value to store.
     */
    public void writeInt(int value) {
        ensureCapacity(Integer.BYTES);
        data[writePosition] = (byte) ((value >> 24) & 0xFF);
        data[writePosition + 1] = (byte) ((value >> 16) & 0xFF);
        data[writePosition + 2] = (byte) ((value >> 8) & 0xFF);
        data[writePosition + 3] = (byte) (value & 0xFF);
        writePosition += Integer.BYTES;
        size = Math.max(size, writePosition);
    }

    /**
     * Retrieves an integer value from the dynamic byte array at the current read position
     * and increments the read position.
     *
     * @return The integer value at the current read position.
     * @throws IndexOutOfBoundsException if the read position is out of bounds.
     */
    public int readInt() {
        int value = readInt(readPosition);
        readPosition += Integer.BYTES;
        return value;
    }

    /**
     * Stores a long value in the dynamic byte array at the current write position.
     *
     * @param value The long value to store.
     */
    public void writeLong(long value) {
        ensureCapacity(Long.BYTES);
        data[writePosition] = (byte) ((value >> 56) & 0xFF);
        data[writePosition + 1] = (byte) ((value >> 48) & 0xFF);
        data[writePosition + 2] = (byte) ((value >> 40) & 0xFF);
        data[writePosition + 3] = (byte) ((value >> 32) & 0xFF);
        data[writePosition + 4] = (byte) ((value >> 24) & 0xFF);
        data[writePosition + 5] = (byte) ((value >> 16) & 0xFF);
        data[writePosition + 6] = (byte) ((value >> 8) & 0xFF);
        data[writePosition + 7] = (byte) (value & 0xFF);
        writePosition += Long.BYTES;
        size = Math.max(size, writePosition);
    }

    /**
     * Retrieves a long value from the dynamic byte array at the current read position
     * and increments the read position.
     *
     * @return The long value at the current read position.
     * @throws IndexOutOfBoundsException if the read position is out of bounds.
     */
    public long readLong() {
        long value = readLong(readPosition);
        readPosition += Long.BYTES;
        return value;
    }

    /**
     * Stores a float value in the dynamic byte array at the current write position.
     *
     * @param value The float value to store.
     */
    public void writeFloat(float value) {
        ensureCapacity(Float.BYTES);
        int intValue = Float.floatToIntBits(value);
        writeInt(intValue);
    }

    /**
     * Retrieves a float value from the dynamic byte array at the current read position
     * and increments the read position.
     *
     * @return The float value at the current read position.
     * @throws IndexOutOfBoundsException if the read position is out of bounds.
     */
    public float readFloat() {
        int intValue = readInt(readPosition);
        readPosition += Integer.BYTES;
        return Float.intBitsToFloat(intValue);
    }

    /**
     * Stores a double value in the dynamic byte array at the current write position.
     *
     * @param value The double value to store.
     */
    public void writeDouble(double value) {
        ensureCapacity(Double.BYTES);
        long longValue = Double.doubleToLongBits(value);
        writeLong(longValue);
    }

    /**
     * Retrieves a double value from the dynamic byte array at the current read position
     * and increments the read position.
     *
     * @return The double value at the current read position.
     * @throws IndexOutOfBoundsException if the read position is out of bounds.
     */
    public double readDouble() {
        long longValue = readLong(readPosition);
        readPosition += Long.BYTES;
        return Double.longBitsToDouble(longValue);
    }

    /**
     * Retrieves a byte array value from the dynamic byte array at the current read position.
     *
     * @param length The length of the byte array to retrieve.
     * @return A byte array containing the data from the dynamic byte array.
     * @throws IndexOutOfBoundsException if the read position is out of bounds.
     */
    public byte[] readBytes(int length) {
        checkIndexBounds(readPosition, length);
        byte[] byteArray = new byte[length];
        System.arraycopy(data, readPosition, byteArray, 0, length);
        readPosition += length;
        return byteArray;
    }

    /**
     * Stores a byte array value in the dynamic byte array at the current write position.
     *
     * @param byteArray The byte array value to store.
     */
    public void writeBytes(byte[] byteArray) {
        ensureCapacity(byteArray.length);
        System.arraycopy(byteArray, 0, data, writePosition, byteArray.length);
        writePosition += byteArray.length;
        size = Math.max(size, writePosition);
    }

    /**
     * Stores a byte array value in the dynamic byte array within the specified range.
     *
     * @param byteArray  The byte array value to store.
     * @param startIndex The starting index within the dynamic byte array.
     * @param endIndex   The ending index within the dynamic byte array.
     */
    public void writeBytes(byte[] byteArray, int startIndex, int endIndex) {
        int length = endIndex - startIndex;
        ensureCapacity(length);

        System.arraycopy(byteArray, startIndex, data, writePosition, length);
        writePosition += length;
        size = Math.max(size, writePosition);
    }

    /**
     * Retrieves a byte value from the dynamic byte array at the specified index.
     *
     * @param index The index from which to retrieve the byte value.
     * @return The byte value at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public byte readByte(int index) {
        checkIndexBounds(index, Byte.BYTES);
        return data[index];
    }

    /**
     * Retrieves a short value from the dynamic byte array at the specified index.
     *
     * @param index The index from which to retrieve the short value.
     * @return The short value at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public short readShort(int index) {
        checkIndexBounds(index, Short.BYTES);
        return (short) ((data[index] << 8) | (data[index + 1] & 0xFF));
    }

    /**
     * Retrieves an integer value from the dynamic byte array at the specified index.
     *
     * @param index The index from which to retrieve the integer value.
     * @return The integer value at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public int readInt(int index) {
        checkIndexBounds(index, Integer.BYTES);
        int intValue = 0;
        intValue |= (data[index] & 0xFF) << 24;
        intValue |= (data[index + 1] & 0xFF) << 16;
        intValue |= (data[index + 2] & 0xFF) << 8;
        intValue |= (data[index + 3] & 0xFF);
        return intValue;
    }

    /**
     * Retrieves a long value from the dynamic byte array at the specified index.
     *
     * @param index The index from which to retrieve the long value.
     * @return The long value at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public long readLong(int index) {
        checkIndexBounds(index, Long.BYTES);
        long longValue = 0;
        longValue |= (long) (data[index] & 0xFF) << 56;
        longValue |= (long) (data[index + 1] & 0xFF) << 48;
        longValue |= (long) (data[index + 2] & 0xFF) << 40;
        longValue |= (long) (data[index + 3] & 0xFF) << 32;
        longValue |= (long) (data[index + 4] & 0xFF) << 24;
        longValue |= (long) (data[index + 5] & 0xFF) << 16;
        longValue |= (long) (data[index + 6] & 0xFF) << 8;
        longValue |= (data[index + 7] & 0xFF);
        return longValue;
    }

    /**
     * Retrieves a float value from the dynamic byte array at the specified index.
     *
     * @param index The index from which to retrieve the float value.
     * @return The float value at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public float readFloat(int index) {
        int intValue = readInt(index);
        return Float.intBitsToFloat(intValue);
    }

    /**
     * Retrieves a double value from the dynamic byte array at the specified index.
     *
     * @param index The index from which to retrieve the double value.
     * @return The double value at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public double readDouble(int index) {
        long longValue = readLong(index);
        return Double.longBitsToDouble(longValue);
    }

    /**
     * Retrieves a byte array value from the dynamic byte array at the specified index.
     *
     * @param index  The index from which to retrieve the byte array value.
     * @param length The length of the byte array to retrieve.
     * @return A byte array containing the data from the dynamic byte array.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public byte[] readBytes(int index, int length) {
        checkIndexBounds(index, length);
        byte[] byteArray = new byte[length];
        System.arraycopy(data, index, byteArray, 0, length);
        return byteArray;
    }


    /**
     * Retrieves the current read position.
     *
     * @return The current read position.
     */
    public int getReadPosition() {
        return readPosition;
    }

    /**
     * Sets the read position to the specified index.
     *
     * @param index The index to set the read position to.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public void setReadPosition(int index) {
        checkIndexBounds(index, 0);
        readPosition = index;
    }

    /**
     * Retrieves the current write position.
     *
     * @return The current write position.
     */
    public int getWritePosition() {
        return writePosition;
    }

    /**
     * Sets the write position to the specified index.
     *
     * @param index The index to set the write position to.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public void setWritePosition(int index) {
        checkIndexBounds(index, 0);
        writePosition = index;
    }

    /**
     * Skips the number of read bytes from the given value.
     *
     * @param value the number to skip read bytes.
     */
    public void skipRead(int value) {
        this.readPosition += value;
    }

    /**
     * Returns the remaining bytes available to read.
     *
     * @return available bytes to read.
     */
    public int readableBytes() {
        return size() - readPosition;
    }

    /**
     * Returns the current size of the dynamic byte array.
     *
     * @return The current size of the dynamic byte array.
     */
    public int size() {
        return size;
    }

    /**
     * Clears the content of the dynamic byte array, resetting its size to zero, while keeping the allocated memory.
     */
    public void clear() {
        size = 0;
        readPosition = 0;
        writePosition = 0;
    }

    /**
     * @return True if there are no available bytes; False otherwise
     */
    public boolean isEmpty() {
        return readableBytes() == 0;
    }

    /**
     * Converts the dynamic byte array to a regular byte array of the current size with
     * trailing bytes on the end.
     *
     * @return A byte array containing the data stored in the dynamic byte array.
     */
    public byte[] toArray() {
        return data;
    }

    /**
     * Converts the dynamic byte array to a regular byte array with no trailing bytes.
     *
     * @return A byte array containing the data stored in the dynamic byte array.
     */
    public byte[] toTrimmedArray() {
        trim();
        return data;
    }

    /**
     * Compares this DynamicByteBuffer to another object for equality.
     *
     * @param o The object to compare to this DynamicByteBuffer.
     * @return True if the provided object is equal to this DynamicByteBuffer, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicByteBuffer that = (DynamicByteBuffer) o;
        if (size != that.size) return false;
        for (int i = 0; i < size; i++) {
            if (data[i] != that.data[i]) return false;
        }
        return true;
    }

    /**
     * Generates a hash code for this DynamicByteBuffer.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result = 31 * size;
        for (int i = 0; i < size; i++) {
            result = 31 * result + data[i];
        }
        return result;
    }

    /**
     * Returns a string representation of this DynamicByteBuffer.
     *
     * @return A string containing the size and content of the DynamicByteBuffer.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DynamicByteBuffer [size=").append(size).append(", data=");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]).append(" ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Creates a deep clone of the current DynamicByteBuffer instance,
     * including its byte data and configuration settings.
     *
     * @return A new DynamicByteBuffer instance that is a deep clone of the current instance.
     */
    @Override
    public DynamicByteBuffer clone() {
        DynamicByteBuffer clonedBuffer = new DynamicByteBuffer(this.data.length, this.growing);
        System.arraycopy(this.data, 0, clonedBuffer.data, 0, this.data.length);

        clonedBuffer.size = this.size;
        clonedBuffer.readPosition = this.readPosition;
        clonedBuffer.writePosition = this.writePosition;
        clonedBuffer.bitBuffer = this.bitBuffer;
        clonedBuffer.bitPosition = this.bitPosition;
        return clonedBuffer;
    }

}
