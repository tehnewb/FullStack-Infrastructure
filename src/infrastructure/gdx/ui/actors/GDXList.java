package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import infrastructure.gdx.ui.styles.GDXListStyle;

/**
 * The `GDXList` class extends LibGDX's List class, providing additional functionality and customization options
 * for creating and manipulating lists within a graphical user interface (GUI).
 *
 * @param <T> The type of items contained in the list.
 */
public class GDXList<T> extends List<T> {

    /**
     * Constructs a `GDXList` using the default list style defined in `GDXListStyle.defaults()`.
     */
    public GDXList() {
        super(GDXListStyle.defaults());
    }

    /**
     * Creates a new generic `GDXList` instance.
     *
     * @param <T> The type of items contained in the list.
     * @return A new `GDXList` instance.
     */
    public static <T> GDXList<T> of() {
        return new GDXList<T>();
    }

    /**
     * Sets the style of the list.
     *
     * @param style The `GDXListStyle` object defining the visual style of the list.
     * @return The `GDXList` instance, enabling method chaining.
     */
    public GDXList<T> style(GDXListStyle style) {
        this.setStyle(style);
        return this;
    }
}