package infrastructure.gdx.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

/**
 * The `TiledMap` class is designed to parse Tiled Map Editor (TMX) files and extract
 * essential information such as map layers and objects. It uses the LibGDX library for XML
 * parsing and provides an easy-to-use interface for accessing map data.
 *
 * @author Albert Beaupre
 */
public class TiledMap {

    private final Element root;
    private final HashMap<String, MapLayer> underlays;
    private final HashMap<String, MapLayer> overlays;
    private final HashMap<String, MapObject> objects;
    private final int mapWidth;
    private final int mapHeight;
    private final int tileWidth;
    private final int tileHeight;
    private final String mapOrientation;
    private Texture[] tiles;

    /**
     * Constructs a `TiledMap` instance to parse the provided TMX file data and tileset.
     *
     * @param tmxData The TMX file data as a byte array.
     * @param tileSet The tileset data as a byte array.
     */
    public TiledMap(byte[] tmxData, byte[] tileSet) {
        // Initialize XML reader for parsing.
        XmlReader reader = new XmlReader();

        // Parse the TMX file data.
        this.root = reader.parse(new ByteArrayInputStream(tmxData));
        this.underlays = new HashMap<>();
        this.overlays = new HashMap<>();
        this.objects = new HashMap<>();
        this.mapWidth = root.getIntAttribute("width", 0);
        this.mapHeight = root.getIntAttribute("height", 0);
        this.tileWidth = root.getIntAttribute("tilewidth", 0);
        this.tileHeight = root.getIntAttribute("tileheight", 0);
        this.mapOrientation = root.getAttribute("orientation", "");
        this.parseMapLayers();
        this.parseObjectLayers();
        this.splitIntoTiles(tileSet);
    }

    /**
     * Renders the underlays using the specified batch.
     *
     * @param batch The batch used for rendering.
     */
    public void renderUnderlays(Batch batch, int centerX, int centerY, int tileRadius) {
        for (MapLayer layer : underlays.values()) {
            if (layer == null)
                continue;
            for (int my = centerY - tileRadius; my < centerY + tileRadius; my++) {
                for (int mx = centerX - tileRadius; mx < centerX + tileRadius; mx++) {
                    if (mx < 0 || my < 0 || mx >= mapWidth || my >= mapHeight)
                        continue;
                    int tileIndex = layer.getTileIndices()[my][mx] - 1;
                    if (tileIndex <= 0)
                        continue;
                    Texture texture = this.tiles[tileIndex];

                    // Render the tile at the specified position.
                    batch.draw(texture, mx * tileWidth, my * tileHeight, tileWidth, tileHeight);
                }
            }
        }
    }

    /**
     * Renders the overlays using the specified batch.
     *
     * @param batch The batch used for rendering.
     */
    public void renderOverlays(Batch batch, int centerX, int centerY, int tileRadius) {
        for (MapLayer layer : overlays.values()) {
            if (layer == null)
                continue;
            for (int my = centerY - tileRadius; my < centerY + tileRadius; my++) {
                for (int mx = centerX - tileRadius; mx < centerX + tileRadius; mx++) {
                    if (mx < 0 || my < 0 || mx >= mapWidth || my >= mapHeight)
                        continue;
                    int tileIndex = layer.getTileIndices()[my][mx] - 1;
                    if (tileIndex <= 0)
                        continue;
                    Texture texture = this.tiles[tileIndex];

                    // Render the tile at the specified position.
                    batch.draw(texture, mx * tileWidth, my * tileHeight, tileWidth, tileHeight);
                }
            }
        }
    }

    /**
     * Splits the provided tileset data into individual textures for each tile.
     *
     * @param tileSet The tileset data as a byte array.
     */
    private void splitIntoTiles(byte[] tileSet) {
        Pixmap pixmap = new Pixmap(tileSet, 0, tileSet.length);
        int rows = pixmap.getWidth() / this.getTileWidth();
        int columns = pixmap.getHeight() / this.getTileHeight();
        this.tiles = new Texture[rows * columns];

        int index = 0;
        for (int y = 0; y < columns; y++) {
            for (int x = 0; x < rows; x++) {
                int startX = x * tileWidth;
                int startY = y * tileHeight;
                Pixmap subPixmap = new Pixmap(tileWidth, tileHeight, pixmap.getFormat());
                subPixmap.drawPixmap(pixmap, 0, 0, startX, startY, tileWidth, tileHeight);
                this.tiles[index++] = new Texture(subPixmap);
                subPixmap.dispose(); // Dispose of the temporary Pixmap
            }
        }

        pixmap.dispose();
    }

    /**
     * Parses the map layers from the TMX file and adds them to the `layers` list.
     */
    private void parseMapLayers() {
        Array<Element> mapLayers = root.getChildrenByName("layer");
        if (mapLayers != null) {
            for (Element layerElement : mapLayers) {
                HashMap<String, Object> layerProperties = new HashMap<>();
                String layerName = layerElement.getAttribute("name", "");
                String layerData = layerElement.getChildByName("data").getText();
                Element properties = layerElement.getChildByName("properties");
                boolean overlay = false;
                if (properties != null) {
                    for (Element property : properties.getChildrenByName("property")) {
                        String name = property.getAttribute("name");
                        String valueName = property.getAttribute("value").trim();
                        Object value;
                        switch (property.getAttribute("type")) {
                            case "bool":
                                value = Boolean.parseBoolean(valueName);
                                break;
                            case "file":
                                value = Gdx.files.internal(valueName);
                                break;
                            case "color":
                                value = Color.valueOf(valueName);
                                break;
                            case "int":
                                value = Integer.parseInt(valueName);
                                break;
                            case "float":
                                value = Float.parseFloat(valueName);
                                break;
                            default:
                                value = property.getAttribute("value");
                                break;
                        }
                        layerProperties.put(name, value);
                    }
                }
                int[][] tileData = parseLayerIndices(layerData);
                MapLayer mapLayer = new MapLayer(layerName, tileData, layerProperties);
                if (layerProperties.containsKey("overlay")) {
                    overlays.put(layerName, mapLayer);
                } else {
                    underlays.put(layerName, mapLayer);
                }
            }
        }
    }

    /**
     * Parses object layers from the TMX file and adds them to the `objects` list.
     */
    private void parseObjectLayers() {
        Element objectLayers = root.getChildByName("objectgroup");
        if (objectLayers != null) {
            for (Element objectGroup : objectLayers.getChildrenByName("object")) {
                String objectName = objectGroup.getAttribute("name", "");
                float x = objectGroup.getFloatAttribute("x", 0);
                float y = objectGroup.getFloatAttribute("y", 0);
                float width = objectGroup.getFloatAttribute("width", 0);
                float height = objectGroup.getFloatAttribute("height", 0);

                MapObject mapObject = new MapObject(objectName, x, y, width, height);

                // Check object type and parse accordingly
                String objectType = objectGroup.getAttribute("type", "");
                if ("ellipse".equalsIgnoreCase(objectType)) {
                    mapObject = new MapObject(objectName, x, y, width, height, MapObjectType.ELLIPSE);
                } else if ("polygon".equalsIgnoreCase(objectType) || "polyline".equalsIgnoreCase(objectType)) {
                    // Polygon or Polyline object
                    Element objectPolygon = objectGroup.getChildByName("polygon");
                    Element objectPolyline = objectGroup.getChildByName("polyline");

                    if (objectPolygon != null || objectPolyline != null) {
                        String points = objectPolygon.getAttribute("points", "");
                        mapObject = new MapObject(objectName, x, y, width, height, MapObjectType.POLYGON, parsePolygonOrPolylinePoints(points));
                    }
                } else if ("rectangle".equalsIgnoreCase(objectType)) {
                    mapObject = new MapObject(objectName, x, y, width, height, MapObjectType.RECTANGLE);
                }
                objects.put(objectName, mapObject);
            }
        }
    }

    /**
     * Parses the tile indices for a map layer from a comma-separated string.
     *
     * @param layerData The comma-separated tile indices data.
     * @return A 2D array of tile indices for the layer.
     */
    private int[][] parseLayerIndices(String layerData) {
        String[] tileStrings = layerData.trim().split(",");
        int[][] data = new int[mapHeight][mapWidth];
        int index = 0;
        for (int y = mapHeight - 1; y >= 0; y--) {
            for (int x = 0; x < mapWidth; x++) {
                data[y][x] = Integer.parseInt(tileStrings[index].trim());
                index++;
            }
        }
        return data;
    }

    /**
     * Parses the points data for polygon or polyline objects.
     *
     * @param points The points data as a string.
     * @return An array of vertex coordinates for the object.
     */
    private float[] parsePolygonOrPolylinePoints(String points) {
        String[] pointPairs = points.trim().split(" ");
        int numVertices = pointPairs.length;
        float[] vertices = new float[numVertices * 2]; // Each vertex has x and y, so 2 values per vertex.

        for (int i = 0; i < numVertices; i++) {
            String pair = pointPairs[i];
            String[] xy = pair.split(",");
            if (xy.length == 2) {
                vertices[i * 2] = Float.parseFloat(xy[0].trim()); // x-coordinate
                vertices[i * 2 + 1] = Float.parseFloat(xy[1].trim()); // y-coordinate
            }
        }
        return vertices;
    }

    /**
     * Gets the map of map layers parsed from the TMX file that are rendered over the player.
     *
     * @return A map of overlay layers.
     */
    public HashMap<String, MapLayer> getOverlays() {
        return overlays;
    }

    /**
     * Gets the map of map layers parsed from the TMX file that are rendered under the player.
     *
     * @return A map of underlay layers.
     */
    public HashMap<String, MapLayer> getUnderlays() {
        return underlays;
    }

    /**
     * Gets the map of map objects parsed from the TMX file.
     *
     * @return A map of `MapObject` objects.
     */
    public HashMap<String, MapObject> getObjects() {
        return objects;
    }

    /**
     * Gets the width of the parsed map in tiles.
     *
     * @return The map width in tiles.
     */
    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * Gets the height of the parsed map in tiles.
     *
     * @return The map height in tiles.
     */
    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * Gets the width of a tile in pixels.
     *
     * @return The tile width in pixels.
     */
    public int getTileWidth() {
        return tileWidth;
    }

    /**
     * Gets the height of a tile in pixels.
     *
     * @return The tile height in pixels.
     */
    public int getTileHeight() {
        return tileHeight;
    }

    /**
     * Gets the orientation of the parsed map (e.g., "orthogonal" or "isometric").
     *
     * @return The map orientation as a string.
     */
    public String getMapOrientation() {
        return mapOrientation;
    }
}