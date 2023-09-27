package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXImageButtonStyle class extends LibGDX's ImageButton.ImageButtonStyle, providing customization options
 * for the appearance of image buttons. It allows you to specify different drawables for various
 * states of the image button, such as up, down, over, and disabled states.
 */
public class GDXImageButtonStyle extends ImageButton.ImageButtonStyle {

    private static GDXImageButtonStyle defaults;


    /**
     * Returns the current GDXImageButtonStyle instance with default settings.
     *
     * @return A new GDXImageButtonStyle instance with default settings.
     */
    public static GDXImageButtonStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXImageButtonStyle instance with default settings.
     *
     * @param style The GDXImageButtonStyle instance with default settings.
     */
    public static void defaults(GDXImageButtonStyle style) {
        defaults = style;
    }

    /**
     * Sets the drawable for the image button in its up (default) state.
     *
     * @param imageUp The Drawable for the up state.
     * @return The GDXImageButtonStyle instance, enabling method chaining.
     */
    public GDXImageButtonStyle imageUp(Drawable imageUp) {
        this.imageUp = imageUp;
        return this;
    }

    /**
     * Sets the drawable for the image button when it is pressed (down) state.
     *
     * @param imageDown The Drawable for the down state.
     * @return The GDXImageButtonStyle instance, enabling method chaining.
     */
    public GDXImageButtonStyle imageDown(Drawable imageDown) {
        this.imageDown = imageDown;
        return this;
    }

    /**
     * Sets the drawable for the image button when it is hovered over (over state).
     *
     * @param imageOver The Drawable for the over state.
     * @return The GDXImageButtonStyle instance, enabling method chaining.
     */
    public GDXImageButtonStyle imageOver(Drawable imageOver) {
        this.imageOver = imageOver;
        return this;
    }

    /**
     * Sets the drawable for the image button when it is disabled.
     *
     * @param imageDisabled The Drawable for the disabled state.
     * @return The GDXImageButtonStyle instance, enabling method chaining.
     */
    public GDXImageButtonStyle imageDisabled(Drawable imageDisabled) {
        this.imageDisabled = imageDisabled;
        return this;
    }

    /**
     * Sets the drawable for the image button when it is checked (on) state.
     *
     * @param imageChecked The Drawable for the checked (on) state.
     * @return The GDXImageButtonStyle instance, enabling method chaining.
     */
    public GDXImageButtonStyle imageChecked(Drawable imageChecked) {
        this.imageChecked = imageChecked;
        return this;
    }

    /**
     * Sets the drawable for the image button when it is checked (on) and in the pressed (down) state.
     *
     * @param imageCheckedDown The Drawable for the checked (on) and down state.
     * @return The GDXImageButtonStyle instance, enabling method chaining.
     */
    public GDXImageButtonStyle imageCheckedDown(Drawable imageCheckedDown) {
        this.imageCheckedDown = imageCheckedDown;
        return this;
    }

    /**
     * Sets the drawable for the image button when it is checked (on) and hovered over (over state).
     *
     * @param imageCheckedOver The Drawable for the checked (on) and over state.
     * @return The GDXImageButtonStyle instance, enabling method chaining.
     */
    public GDXImageButtonStyle imageCheckedOver(Drawable imageCheckedOver) {
        this.imageCheckedOver = imageCheckedOver;
        return this;
    }
}
