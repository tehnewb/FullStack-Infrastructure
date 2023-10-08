package infrastructure.gdx.map;

import com.badlogic.gdx.math.*;

/**
 * Represents a map object parsed from a TMX file. This class provides a
 * structured representation of map objects, including their name, position,
 * dimensions, type, and vertices (if applicable).
 *
 * @author Albert Beaupre
 */
public class MapObject implements Shape2D {

    // Fields to store map object properties
    private final Shape2D shape;
    private final String name;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final MapObjectType type;

    /**
     * Constructs a MapObject with all properties specified.
     *
     * @param name     The name of the map object.
     * @param x        The x-coordinate of the map object's position.
     * @param y        The y-coordinate of the map object's position.
     * @param width    The width of the map object.
     * @param height   The height of the map object.
     * @param type     The type of the map object (e.g., point, rectangle, ellipse, polygon).
     * @param vertices The vertices of the map object (used for polygon type).
     */
    public MapObject(String name, float x, float y, float width, float height, MapObjectType type, float[] vertices) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;

        switch (type) {
            case ELLIPSE:
                this.shape = new Ellipse(x, y, width, height);
                break;
            case RECTANGLE:
                this.shape = new Rectangle(x, y, width, height);
                break;
            case POLYGON:
                this.shape = new Polygon(vertices);
                break;
            default:
                this.shape = null;
                break;
        }
    }

    /**
     * Constructs a point MapObject with default type and no vertices.
     *
     * @param name   The name of the point.
     * @param x      The x-coordinate of the point's position.
     * @param y      The y-coordinate of the point's position.
     * @param width  The width of the point (typically zero for points).
     * @param height The height of the point (typically zero for points).
     */
    public MapObject(String name, float x, float y, float width, float height) {
        this(name, x, y, width, height, MapObjectType.RECTANGLE, null);
    }

    /**
     * Constructs a MapObject with the specified type and no vertices.
     *
     * @param name   The name of the map object.
     * @param x      The x-coordinate of the map object's position.
     * @param y      The y-coordinate of the map object's position.
     * @param width  The width of the map object.
     * @param height The height of the map object.
     * @param type   The type of the map object (e.g., rectangle, ellipse).
     */
    public MapObject(String name, float x, float y, float width, float height, MapObjectType type) {
        this(name, x, y, width, height, type, null);
    }

    /**
     * Get the name of the map object.
     *
     * @return The name of the map object.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the x-coordinate of the map object's position.
     *
     * @return The x-coordinate of the map object.
     */
    public float getX() {
        return x;
    }

    /**
     * Get the y-coordinate of the map object's position.
     *
     * @return The y-coordinate of the map object.
     */
    public float getY() {
        return y;
    }

    /**
     * Get the width of the map object.
     *
     * @return The width of the map object.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Get the height of the map object.
     *
     * @return The height of the map object.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Get the type of the map object (e.g., point, rectangle, ellipse, polygon).
     *
     * @return The type of the map object.
     */
    public MapObjectType getType() {
        return type;
    }

    /**
     * Checks if the map object contains the specified point.
     *
     * @param point The point represented as a Vector2.
     * @return True if the map object contains the point; otherwise, false.
     */
    @Override
    public boolean contains(Vector2 point) {
        return shape.contains(point);
    }

    /**
     * Checks if the map object contains the specified point.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return True if the map object contains the point; otherwise, false.
     */
    @Override
    public boolean contains(float x, float y) {
        return shape.contains(x, y);
    }
}