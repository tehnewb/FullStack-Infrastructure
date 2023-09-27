package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import infrastructure.gdx.ui.styles.GDXImageButtonStyle;
import infrastructure.gdx.ui.styles.GDXImageTextButtonStyle;

/**
 * The `GDXImageTextButton` class extends LibGDX's ImageTextButton class, providing additional functionality and customization options
 * for creating and manipulating image-text buttons within a graphical user interface (GUI).
 */
public class GDXImageTextButton extends ImageTextButton {

    /**
     * Constructs a `GDXImageTextButton` with the given text and uses the default image-text button style defined in `GDXImageTextButtonStyle.defaults()`.
     *
     * @param text The text displayed on the button.
     */
    public GDXImageTextButton(String text) {
        super(text, GDXImageTextButtonStyle.defaults());
    }

    /**
     * Creates a new `GDXImageTextButton` instance with the specified text.
     *
     * @param text The text content to be displayed on the button.
     * @return A new `GDXImageTextButton` instance initialized with the provided text.
     */
    public static GDXImageTextButton of(String text) {
        return new GDXImageTextButton(text);
    }

    /**
     * Sets the text displayed on the image-text button.
     *
     * @param text The text to be displayed on the button.
     * @return The `GDXImageTextButton` instance, enabling method chaining.
     */
    public GDXImageTextButton text(String text) {
        this.setText(text);
        return this;
    }

    /**
     * Sets the style of the image-text button.
     *
     * @param style The `GDXImageButtonStyle` object defining the visual style of the image-text button.
     * @return The `GDXImageTextButton` instance, enabling method chaining.
     */
    public GDXImageTextButton style(GDXImageButtonStyle style) {
        this.setStyle(style);
        return this;
    }
}