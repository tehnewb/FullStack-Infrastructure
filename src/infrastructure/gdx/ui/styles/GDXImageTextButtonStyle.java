package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXImageTextButtonStyle class extends LibGDX's ImageTextButton.ImageTextButtonStyle, providing customization options
 * for the appearance of image text buttons. It allows you to specify different drawables for various
 * states of the image text button, such as up, down, over, and disabled states.
 *
 * @author Albert Beaupre
 */
public class GDXImageTextButtonStyle extends ImageTextButton.ImageTextButtonStyle {

    private static GDXImageTextButtonStyle defaults = new GDXImageTextButtonStyle();


    /**
     * Returns the current GDXImageTextButtonStyle instance with default settings.
     *
     * @return A new GDXImageTextButtonStyle instance with default settings.
     */
    public static GDXImageTextButtonStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXImageTextButtonStyle instance with default settings.
     *
     * @param style The GDXImageTextButtonStyle instance with default settings.
     */
    public static void defaults(GDXImageTextButtonStyle style) {
        defaults = style;
    }

    /**
     * Sets the drawable for the image text button in its up (default) state.
     *
     * @param imageUp The Drawable for the up state.
     * @return The GDXImageTextButtonStyle instance, enabling method chaining.
     */
    public GDXImageTextButtonStyle imageUp(Drawable imageUp) {
        this.imageUp = imageUp;
        return this;
    }

    /**
     * Sets the drawable for the image text button when it is pressed (down) state.
     *
     * @param imageDown The Drawable for the down state.
     * @return The GDXImageTextButtonStyle instance, enabling method chaining.
     */
    public GDXImageTextButtonStyle imageDown(Drawable imageDown) {
        this.imageDown = imageDown;
        return this;
    }

    /**
     * Sets the drawable for the image text button when it is hovered over (over state).
     *
     * @param imageOver The Drawable for the over state.
     * @return The GDXImageTextButtonStyle instance, enabling method chaining.
     */
    public GDXImageTextButtonStyle imageOver(Drawable imageOver) {
        this.imageOver = imageOver;
        return this;
    }

    /**
     * Sets the drawable for the image text button when it is disabled.
     *
     * @param imageDisabled The Drawable for the disabled state.
     * @return The GDXImageTextButtonStyle instance, enabling method chaining.
     */
    public GDXImageTextButtonStyle imageDisabled(Drawable imageDisabled) {
        this.imageDisabled = imageDisabled;
        return this;
    }

    /**
     * Sets the drawable for the image text button when it is checked (on) state.
     *
     * @param imageChecked The Drawable for the checked (on) state.
     * @return The GDXImageTextButtonStyle instance, enabling method chaining.
     */
    public GDXImageTextButtonStyle imageChecked(Drawable imageChecked) {
        this.imageChecked = imageChecked;
        return this;
    }

    /**
     * Sets the drawable for the image text button when it is checked (on) and in the pressed (down) state.
     *
     * @param imageCheckedDown The Drawable for the checked (on) and down state.
     * @return The GDXImageTextButtonStyle instance, enabling method chaining.
     */
    public GDXImageTextButtonStyle imageCheckedDown(Drawable imageCheckedDown) {
        this.imageCheckedDown = imageCheckedDown;
        return this;
    }

    /**
     * Sets the drawable for the image text button when it is checked (on) and hovered over (over state) state.
     *
     * @param imageCheckedOver The Drawable for the checked (on) and hover (over) state.
     * @return The GDXImageTextButtonStyle instance, enabling method chaining.
     */
    public GDXImageTextButtonStyle imageCheckedOver(Drawable imageCheckedOver) {
        this.imageCheckedOver = imageCheckedOver;
        return this;
    }
}
