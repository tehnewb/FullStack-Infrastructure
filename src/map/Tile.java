package map;

/**
 * The Tile class represents a single tile in a game world.
 * Each tile can have collision properties, such as walkability, shootability, and blockage.
 * <p>
 * This class provides methods to manipulate and query collision flags for the tile.
 *
 * @author Albert Beaupre
 */
public class Tile {

    public static final int WALKABLE = 0x1;   // Tile can be walked through
    public static final int BLOCKED = 0x2;    // Tile cannot be walked through
    public static final int SHOOTABLE = 0x4;  // Tile can be shot through

    private final short x, y; // Tile coordinates on 2D plane

    // An integer to store the collision mask
    private byte collisionMask;

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
    }

    /**
     * Sets a collision flag for this tile.
     *
     * @param flag The collision flag to set (e.g., Tile.WALKABLE).
     */
    public void setCollisionFlag(int flag) {
        this.collisionMask |= flag; // Use bitwise OR to set the flag
    }

    /**
     * Clears a collision flag for this tile.
     *
     * @param flag The collision flag to clear (e.g., Tile.WALKABLE).
     */
    public void clearCollisionFlag(int flag) {
        this.collisionMask &= ~flag; // Use bitwise AND with complement to clear the flag
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
        return String.format("Tile[x=%s, y=%s]", x, y);
    }
}
