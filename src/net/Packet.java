package net;

import io.buffer.DynamicByteBuffer;

/**
 * The Packet class represents a network packet that contains data to be sent or received over a network connection.
 * It extends the DynamicByteBuffer class, which is a buffer used for efficiently managing binary data.
 *
 * @author Albert Beaupre
 */
public class Packet extends DynamicByteBuffer {

    // The opcode is an integer value that specifies the type or purpose of the packet.
    private final int opcode;

    // The size is an integer value that represents the size of the packet in bytes.
    private final int size;

    /**
     * Constructs a Packet object with the specified opcode and size. A negative opcode will assume it is a raw packet.
     *
     * @param opcode The opcode that identifies the type of packet.
     * @param size   The size of the packet in bytes. If size is set to -1, the packet is considered "raw" and
     *               can have an indefinite size.
     */
    public Packet(int opcode, int size) {
        super(size, size != -1); // raw packets can have an "indefinite" size
        this.opcode = opcode;
        this.size = size;
    }

    /**
     * Gets the opcode of the packet.
     *
     * @return The opcode value that identifies the type of packet.
     */
    public int getOpcode() {
        return opcode;
    }

    /**
     * Gets the size of the packet in bytes.
     *
     * @return The size of the packet.
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if the packet is a "raw" packet, which means it has a negative opcode.
     *
     * @return True if the packet is raw, false otherwise.
     */
    public boolean isRaw() {
        return size == -1;
    }
}
