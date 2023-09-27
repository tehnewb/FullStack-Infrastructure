package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import infrastructure.gdx.ui.styles.GDXTextButtonStyle;

/**
 * The `GDXTextButton` class extends LibGDX's TextButton class, providing additional functionality and customization options
 * for creating and manipulating text buttons within a graphical user interface (GUI).
 */
public class GDXTextButton extends TextButton {

    /**
     * Constructs a `GDXTextButton` with the given text and uses the default button style defined in `GDXTextButtonStyle.defaults()`.
     *
     * @param text The text displayed on the button.
     */
    public GDXTextButton(String text) {
        super(text, GDXTextButtonStyle.defaults());
    }

    /**
     * Creates a new `GDXTextButton` instance with the specified text.
     *
     * @param text The text content to be displayed on the button.
     * @return A new `GDXTextButton` instance initialized with the provided text.
     */
    public static GDXTextButton of(String text) {
        return new GDXTextButton(text);
    }

    /**
     * Sets the text displayed on the button.
     *
     * @param text The text to be displayed on the button.
     * @return The `GDXTextButton` instance, enabling method chaining.
     */
    public GDXTextButton text(String text) {
        this.setText(text);
        return this;
    }

    /**
     * Sets the style of the text button.
     *
     * @param style The `GDXTextButtonStyle` object defining the visual style of the text button.
     * @return The `GDXTextButton` instance, enabling method chaining.
     */
    public GDXTextButton style(GDXTextButtonStyle style) {
        this.setStyle(style);
        return this;
    }
}
