package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import infrastructure.gdx.ui.styles.GDXTouchpadStyle;

/**
 * The `GDXTouchpad` class extends LibGDX's Touchpad class, providing additional functionality and customization options
 * for creating and manipulating touchpads within a graphical user interface (GUI).
 */
public class GDXTouchpad extends Touchpad {

    /**
     * Constructs a `GDXTouchpad` with the specified dead zone radius and uses the default touchpad style defined in `GDXTouchpadStyle.defaults()`.
     *
     * @param deadzoneRadius The radius of the dead zone where touch input is ignored.
     */
    public GDXTouchpad(float deadzoneRadius) {
        super(deadzoneRadius, GDXTouchpadStyle.defaults());
    }

    /**
     * Creates a new `GDXTouchpad` instance with the specified dead zone radius and uses the default style.
     *
     * @param deadzoneRadius The radius of the dead zone where touch input is ignored.
     * @return A new `GDXTouchpad` instance initialized with the provided dead zone radius and the default style.
     */
    public static GDXTouchpad of(float deadzoneRadius) {
        return new GDXTouchpad(deadzoneRadius);
    }

    /**
     * Sets the style of the touchpad.
     *
     * @param style The `TouchpadStyle` object defining the visual style of the touchpad.
     * @return The `GDXTouchpad` instance, enabling method chaining.
     */
    public GDXTouchpad style(GDXTouchpadStyle style) {
        this.setStyle(style);
        return this;
    }
}
