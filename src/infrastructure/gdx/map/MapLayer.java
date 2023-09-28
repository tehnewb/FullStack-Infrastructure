package infrastructure.gdx.map;

/**
 * Represents a map layer parsed from the TMX file.
 *
 * @author Albert Beaupre
 */
public record MapLayer(String name, int[][] tileIndices) {
}