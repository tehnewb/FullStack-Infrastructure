package infrastructure.gdx.map;

import java.util.HashMap;

/**
 * Represents a map layer parsed from the TMX file.
 *
 * @author Albert Beaupre
 */
public class MapLayer {

    private final String name; // The name of the layer.
    private final int[][] tileIndices; // The indices of tiles in the layer.
    private final HashMap<String, Object> properties; // Custom properties associated with the layer.

    /**
     * Constructs a {@code MapLayer} with the specified name, tile indices, and properties.
     *
     * @param name        The name of the layer.
     * @param tileIndices The indices of tiles in the layer.
     * @param properties  Custom properties associated with the layer.
     */
    public MapLayer(String name, int[][] tileIndices, HashMap<String, Object> properties) {
        this.name = name;
        this.tileIndices = tileIndices;
        this.properties = properties;
    }

    /**
     * Gets the name of the layer.
     *
     * @return The name of the layer.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the indices of tiles in the layer.
     *
     * @return The indices of tiles in the layer.
     */
    public int[][] getTileIndices() {
        return tileIndices;
    }

    /**
     * Gets the custom properties associated with the layer.
     *
     * @return The custom properties associated with the layer.
     */
    public HashMap<String, Object> getProperties() {
        return properties;
    }
}
