package infrastructure.gdx.map;

/**
 * The `MapObjectType` enum represents the types of objects that can be defined
 * in a Tiled Map Editor (TMX) object layer. These objects can be used to mark specific
 * areas or points of interest within a map, and different types of objects are supported.
 *
 * @author Albert Beaupre
 */
public enum MapObjectType {
    /**
     * Represents an ellipse-shaped object.
     */
    ELLIPSE,

    /**
     * Represents a rectangular-shaped object.
     */
    RECTANGLE,

    /**
     * Represents a polygonal-shaped object, which can have multiple vertices.
     */
    POLYGON
}
