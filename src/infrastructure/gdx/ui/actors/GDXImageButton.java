package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import infrastructure.gdx.ui.styles.GDXImageButtonStyle;

/**
 * The `GDXImageButton` class extends LibGDX's ImageButton class, providing additional functionality and customization options
 * for creating and manipulating image buttons within a graphical user interface (GUI).
 */
public class GDXImageButton extends ImageButton {

    /**
     * Constructs a `GDXImageButton` using the default image button style defined in `GDXImageButtonStyle.defaults()`.
     */
    public GDXImageButton() {
        super(GDXImageButtonStyle.defaults());
    }

    /**
     * Creates a new `GDXImageButton` instance with the default image button style.
     *
     * @return A new `GDXImageButton` instance initialized with the default image button style.
     */
    public static GDXImageButton of() {
        return new GDXImageButton();
    }

    /**
     * Sets the style of the image button.
     *
     * @param style The `GDXImageButtonStyle` object defining the visual style of the image button.
     * @return The `GDXImageButton` instance, enabling method chaining.
     */
    public GDXImageButton style(GDXImageButtonStyle style) {
        this.setStyle(style);
        return this;
    }
}
