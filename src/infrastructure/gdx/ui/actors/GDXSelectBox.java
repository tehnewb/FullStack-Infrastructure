package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import infrastructure.gdx.ui.styles.GDXSelectBoxStyle;

/**
 * The `GDXSelectBox` class extends LibGDX's SelectBox class, providing additional functionality and customization options
 * for creating and manipulating select boxes within a graphical user interface (GUI).
 *
 * @param <T> The type of items contained in the select box.
 */
public class GDXSelectBox<T> extends SelectBox<T> {

    /**
     * Constructs a `GDXSelectBox` using the default select box style defined in `GDXSelectBoxStyle.defaults()`.
     */
    public GDXSelectBox() {
        super(GDXSelectBoxStyle.defaults());
    }

    /**
     * Creates a new `GDXSelectBox` instance with the default style.
     *
     * @param <T> The type of items contained in the select box.
     * @return A new `GDXSelectBox` instance with the default style.
     */
    public static <T> GDXSelectBox<T> of() {
        return new GDXSelectBox<T>();
    }

    /**
     * Sets the style of the select box.
     *
     * @param style The `SelectBoxStyle` object defining the visual style of the select box.
     * @return The `GDXSelectBox` instance, enabling method chaining.
     */
    public GDXSelectBox<T> style(GDXSelectBoxStyle style) {
        this.setStyle(style);
        return this;
    }
}
