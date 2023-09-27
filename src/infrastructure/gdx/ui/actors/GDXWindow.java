package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import infrastructure.gdx.ui.styles.GDXWindowStyle;

/**
 * The `GDXWindow` class extends LibGDX's Window class, providing additional functionality and customization options
 * for creating and manipulating windows within a graphical user interface (GUI).
 */
public class GDXWindow extends Window {

    /**
     * Constructs a `GDXWindow` with the specified title and uses the default window style defined in `GDXWindowStyle.defaults()`.
     *
     * @param title The title of the window.
     */
    public GDXWindow(String title) {
        super(title, GDXWindowStyle.defaults());

        this.getTitleLabel().setAlignment(Align.center);
        this.setResizable(true);
        this.setResizeBorder(10);
    }

    /**
     * Creates a new `GDXWindow` instance with the specified title and uses the default style.
     *
     * @param title The title of the window.
     * @return A new `GDXWindow` instance initialized with the provided title and the default style.
     */
    public static GDXWindow of(String title) {
        return new GDXWindow(title);
    }

    /**
     * Sets the style of the window.
     *
     * @param style The `WindowStyle` object defining the visual style of the window.
     * @return The `GDXWindow` instance, enabling method chaining.
     */
    public GDXWindow style(WindowStyle style) {
        this.setStyle(style);
        return this;
    }
}
