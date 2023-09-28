package infrastructure.gdx.map;

/**
 * This enum represents the cardinal directions used for navigation: NORTH, SOUTH, EAST, and WEST.
 * Each direction has associated delta values for movement in the X (horizontal) and Y (vertical) axes..
 *
 * @author Albert Beaupre
 */
public enum WalkDirection {
    NORTH(0, 1),   // Moving one step north increases the Y coordinate by 1.
    SOUTH(0, -1),  // Moving one step south decreases the Y coordinate by 1.
    EAST(1, 0),    // Moving one step east increases the X coordinate by 1.
    WEST(-1, 0);   // Moving one step west decreases the X coordinate by 1.

    private final int deltaX;  // Stores the change in X coordinate when moving in this direction.
    private final int deltaY;  // Stores the change in Y coordinate when moving in this direction.

    /**
     * Constructs a WalkDirection with the given delta values for X and Y axes.
     *
     * @param deltaX The change in the X coordinate when moving in this direction.
     * @param deltaY The change in the Y coordinate when moving in this direction.
     */
    private WalkDirection(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Gets the change in the X coordinate when moving in this direction.
     *
     * @return The delta value for the X coordinate.
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Gets the change in the Y coordinate when moving in this direction.
     *
     * @return The delta value for the Y coordinate.
     */
    public int getDeltaY() {
        return deltaY;
    }
}
