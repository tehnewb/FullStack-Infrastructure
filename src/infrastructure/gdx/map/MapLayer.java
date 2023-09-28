package infrastructure.gdx.map;

import java.util.HashMap;

/**
 * Represents a map layer parsed from the TMX file.
 *
 * @author Albert Beaupre
 */
public record MapLayer(String name, int[][] tileIndices, HashMap<String, Object> properties) {
}