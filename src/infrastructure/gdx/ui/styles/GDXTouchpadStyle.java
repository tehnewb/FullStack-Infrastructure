package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXTouchpadStyle class extends LibGDX's Touchpad.TouchpadStyle, providing customization options
 * for the appearance of touchpads. It allows you to specify properties such as background and knob drawables.
 */
public class GDXTouchpadStyle extends Touchpad.TouchpadStyle {
    private static GDXTouchpadStyle defaults;

    /**
     * Returns the current GDXTouchpadStyle instance with default settings.
     *
     * @return A new GDXTouchpadStyle instance with default settings.
     */
    public static GDXTouchpadStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXTouchpadStyle instance with default settings.
     *
     * @param style The GDXTouchpadStyle instance with default settings.
     */
    public static void defaults(GDXTouchpadStyle style) {
        defaults = style;
    }

    /**
     * Sets the background drawable for the touchpad.
     *
     * @param background The Drawable for the touchpad's background.
     * @return The GDXTouchpadStyle instance, enabling method chaining.
     */
    public GDXTouchpadStyle background(Drawable background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the knob drawable for the touchpad.
     *
     * @param knob The Drawable for the touchpad's knob.
     * @return The GDXTouchpadStyle instance, enabling method chaining.
     */
    public GDXTouchpadStyle knob(Drawable knob) {
        this.knob = knob;
        return this;
    }
}
