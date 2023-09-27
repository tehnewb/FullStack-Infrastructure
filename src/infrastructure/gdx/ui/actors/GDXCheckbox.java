package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import infrastructure.gdx.ui.styles.GDXCheckboxStyle;

/**
 * The `GDXCheckbox` class extends LibGDX's CheckBox class, providing additional functionality and customization options
 * for creating and manipulating checkboxes within a graphical user interface (GUI).
 */
public class GDXCheckbox extends CheckBox {

    /**
     * Constructs a `GDXCheckbox` with the given text and uses the default checkbox style defined in `GDXCheckboxStyle.defaults()`.
     *
     * @param text The text displayed next to the checkbox.
     */
    public GDXCheckbox(String text) {
        super(text, GDXCheckboxStyle.defaults());

        this.getLabelCell().center().padLeft(10);
    }

    /**
     * Creates a new `GDXCheckbox` instance with the specified text.
     *
     * @param text The text content to be displayed next to the checkbox.
     * @return A new `GDXCheckbox` instance initialized with the provided text.
     */
    public static GDXCheckbox of(String text) {
        return new GDXCheckbox(text);
    }

    /**
     * Sets the text displayed next to the checkbox.
     *
     * @param text The text to be displayed next to the checkbox.
     * @return The `GDXCheckbox` instance, enabling method chaining.
     */
    public GDXCheckbox text(String text) {
        this.setText(text);
        return this;
    }

    /**
     * Sets the style of the checkbox.
     *
     * @param style The `GDXCheckboxStyle` object defining the visual style of the checkbox.
     * @return The `GDXCheckbox` instance, enabling method chaining.
     */
    public GDXCheckbox style(GDXCheckboxStyle style) {
        this.setStyle(style);
        return this;
    }
}
