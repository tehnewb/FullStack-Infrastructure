package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import infrastructure.gdx.ui.styles.GDXButtonStyle;

/**
 * The `GDXButton` class extends LibGDX's Button class, providing additional functionality and customization options
 * for creating and manipulating buttons within a graphical user interface (GUI).
 */
public class GDXButton extends Button {

    /**
     * Constructs a `GDXButton` using the default button style defined in `GDXButtonStyle.defaults()`.
     */
    public GDXButton() {
        super(GDXButtonStyle.defaults());
    }

    /**
     * Creates a new `GDXButton` instance with the default button style.
     *
     * @return A new `GDXButton` instance initialized with the default button style.
     */
    public static GDXButton of() {
        return new GDXButton();
    }

    /**
     * Sets the style of the button.
     *
     * @param style The `GDXButtonStyle` object defining the visual style of the button.
     * @return The `GDXButton` instance, enabling method chaining.
     */
    public GDXButton style(GDXButtonStyle style) {
        this.setStyle(style);
        return this;
    }
}