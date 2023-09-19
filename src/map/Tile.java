package map;

/**
 * The Tile class represents a single tile in a game world.
 * Each tile can have collision properties, such as walkability, shootability, and blockage.
 * <p>
 * This class provides methods to manipulate and query collision and layer flags for the tile.
 *
 * @author Albert Beaupre
 */
public class Tile {

    public static final byte WALKABLE = 0x1;   // Tile can be walked through (Collision Mask)
    public static final byte BLOCKED = 0x2;    // Tile cannot be walked through (Collision Mask)
    public static final byte SHOOTABLE = 0x4;  // Tile can be shot through (Collision Mask)
    public static final byte BEHIND = 0x1; // Tile is drawn behind player (Layer Mask)
    public static final byte FRONT = 0x2; // Tile is drawn in front of player (Layer Mask)

    // Tile coordinates on 2D plane
    private final short x, y;

    // An integer to store the collision mask
    private byte collisionMask;

    // An integer to store the layer mask
    private byte layerMask;

    /**
     * Constructs a new Tile object with no collision flags set.
     *
     * @param x The x-coordinate of the tile in the game world.
     * @param y The y-coordinate of the tile in the game world.
     */
    public Tile(int x, int y) {
        this.x = (short) x;
        this.y = (short) y;
        this.collisionMask = WALKABLE;
        this.layerMask = BEHIND; // Initialize layerMask with a default value
    }

    /**
     * Sets a collision flag for this tile.
     *
     * @param flag The collision flag to set (e.g., Tile.WALKABLE).
     */
    public void setCollisionFlag(int flag) {
        this.collisionMask |= (byte) flag; // Use bitwise OR to set the flag
    }

    /**
     * Clears a collision flag for this tile.
     *
     * @param flag The collision flag to clear (e.g., Tile.WALKABLE).
     */
    public void clearCollisionFlag(int flag) {
        this.collisionMask &= (byte) ~flag; // Use bitwise AND with complement to clear the flag
    }

    /**
     * Checks if a specific collision flag is set for this tile.
     *
     * @param flag The collision flag to check (e.g., Tile.WALKABLE).
     * @return true if the flag is set, false otherwise.
     */
    public boolean hasCollisionFlag(int flag) {
        return (this.collisionMask & flag) != 0; // Use bitwise AND to check if the flag is set
    }

    /**
     * Sets a layer flag for this tile.
     *
     * @param flag The layer flag to set (e.g., Tile.LAYER_BEHIND).
     */
    public void setLayerFlag(int flag) {
        this.layerMask |= (byte) flag; // Use bitwise OR to set the flag
    }

    /**
     * Clears a layer flag for this tile.
     *
     * @param flag The layer flag to clear (e.g., Tile.LAYER_BEHIND).
     */
    public void clearLayerFlag(int flag) {
        this.layerMask &= (byte) ~flag; // Use bitwise AND with complement to clear the flag
    }

    /**
     * Checks if a specific layer flag is set for this tile.
     *
     * @param flag The layer flag to check (e.g., Tile.LAYER_BEHIND).
     * @return true if the flag is set, false otherwise.
     */
    public boolean hasLayerFlag(int flag) {
        return (this.layerMask & flag) != 0; // Use bitwise AND to check if the flag is set
    }

    /**
     * Gets the x-coordinate of the tile.
     *
     * @return The x-coordinate of the tile.
     */
    public short getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the tile.
     *
     * @return The y-coordinate of the tile.
     */
    public short getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Tile[x=%s, y=%s, collisionMask=%s, layerMask=%s]", x, y, collisionMask, layerMask);
    }
}
