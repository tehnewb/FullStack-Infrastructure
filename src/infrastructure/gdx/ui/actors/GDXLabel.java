package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import infrastructure.gdx.ui.styles.GDXLabelStyle;

/**
 * The `GDXLabel` class extends LibGDX's Label class, providing additional functionality and customization options
 * for creating and manipulating text labels within a graphical user interface (GUI).
 */
public class GDXLabel extends Label {

    /**
     * Constructs a `GDXLabel` with the given text and uses the default skin defined in `GDXSkin.getInstance()`.
     *
     * @param text The initial text content of the label.
     */
    public GDXLabel(CharSequence text) {
        super(text, GDXLabelStyle.defaults());
    }

    /**
     * Creates a new `GDXLabel` instance with the specified text.
     *
     * @param text The text content to be displayed in the label.
     * @return A new `GDXLabel` instance initialized with the provided text.
     */
    public static GDXLabel of(String text) {
        return new GDXLabel(text);
    }

    /**
     * Sets the alignment of the text within the label.
     *
     * @param alignment The integer code representing the alignment (e.g., Align.left, Align.center, Align.right).
     * @return The `GDXLabel` instance, allowing method chaining.
     */
    public GDXLabel align(int alignment) {
        super.setAlignment(alignment);
        return this;
    }

    /**
     * Sets the text content of the label.
     *
     * @param text The `CharSequence` containing the text to be displayed in the label.
     * @return The `GDXLabel` instance, enabling method chaining.
     */
    public GDXLabel text(CharSequence text) {
        super.setText(text);
        return this;
    }

    /**
     * Sets the style of the label.
     *
     * @param style The `GDXLabelStyle` object defining the visual style of the label.
     * @return The `GDXLabel` instance, enabling method chaining.
     */
    public GDXLabel style(GDXLabelStyle style) {
        this.setStyle(style);
        return this;
    }
}