package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXButtonStyle class extends LibGDX's Button.ButtonStyle, providing customization options for button appearance.
 * It allows you to specify various drawables for different button states and set offsets for pressed, unpressed, and checked states.
 *
 * @author Albert Beaupre
 */
public class GDXButtonStyle extends Button.ButtonStyle {

    private static GDXButtonStyle defaults;

    /**
     * Returns the current GDXButtonStyle instance with default settings.
     *
     * @return A new GDXButtonStyle instance with default drawable settings.
     */
    public static GDXButtonStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXButtonStyle instance with default settings.
     *
     * @param style The GDXButtonStyle instance with default settings.
     */
    public static void defaults(GDXButtonStyle style) {
        defaults = style;
    }

    /**
     * Sets the drawable for the up (default) state of the button.
     *
     * @param up The Drawable for the up state.
     * @return The GDXButtonStyle instance, enabling method chaining.
     */
    public GDXButtonStyle up(Drawable up) {
        this.up = up;
        return this;
    }

    /**
     * Sets the drawable for the down state of the button (when pressed).
     *
     * @param down The Drawable for the down state.
     * @return The GDXButtonStyle instance, enabling method chaining.
     */
    public GDXButtonStyle down(Drawable down) {
        this.down = down;
        return this;
    }

    /**
     * Sets the drawable for the over state of the button (when hovered over).
     *
     * @param over The Drawable for the over state.
     * @return The GDXButtonStyle instance, enabling method chaining.
     */
    public GDXButtonStyle over(Drawable over) {
        this.over = over;
        return this;
    }

    /**
     * Sets the drawable for the focused state of the button (when focused).
     *
     * @param focused The Drawable for the focused state.
     * @return The GDXButtonStyle instance, enabling method chaining.
     */
    public GDXButtonStyle focused(Drawable focused) {
        this.focused = focused;
        return this;
    }

    /**
     * Sets the drawable for the disabled state of the button.
     *
     * @param disabled The Drawable for the disabled state.
     * @return The GDXButtonStyle instance, enabling method chaining.
     */
    public GDXButtonStyle disabled(Drawable disabled) {
        this.disabled = disabled;
        return this;
    }

    /**
     * Sets the drawable for the checked state of the button.
     *
     * @param checked The Drawable for the checked state.
     * @return The GDXButtonStyle instance, enabling method chaining.
     */
    public GDXButtonStyle checked(Drawable checked) {
        this.checked = checked;
        return this;
    }

    /**
     * Sets the drawable for the checked over state of the button.
     *
     * @param checkedOver The Drawable for the checked over state.
     * @return The GDXButtonStyle instance, enabling method chaining.
     */
    public GDXButtonStyle checkedOver(Drawable checkedOver) {
        this.checkedOver = checkedOver;
        return this;
    }

    /**
     * Sets the drawable for the checked down state of the button.
     *
     * @param checkedDown The Drawable for the checked down state.
     * @return The GDXButtonStyle instance, enabling method chaining.
     */
    public GDXButtonStyle checkedDown(Drawable checkedDown) {
        this.checkedDown = checkedDown;
        return this;
    }

    /**
     * Sets the drawable for the checked focused state of the button.
     *
     * @param checkedFocused The Drawable for the checked focused state.
     * @return The GDXButtonStyle instance, enabling method chaining.
     */
    public GDXButtonStyle checkedFocused(Drawable checkedFocused) {
        this.checkedFocused = checkedFocused;
        return this;
    }

    /**
     * Sets the X and Y-axis offsets for the pressed state of the button.
     *
     * @param pressedOffsetX The X-axis offset for the pressed state.
     * @param pressedOffsetY The Y-axis offset for the pressed state.
     * @return The GDXButtonStyle instance, enabling method chaining.
     */
    public GDXButtonStyle pressedOffset(float pressedOffsetX, float pressedOffsetY) {
        this.pressedOffsetX = pressedOffsetX;
        this.pressedOffsetY = pressedOffsetY;
        return this;
    }

    /**
     * Sets the X and Y-axis offsets for the unpressed state of the button.
     *
     * @param unpressedOffsetX The X-axis offset for the unpressed state.
     * @param unpressedOffsetY The Y-axis offset for the unpressed state.
     * @return The GDXButtonStyle instance, enabling method chaining.
     */
    public GDXButtonStyle unpressedOffset(float unpressedOffsetX, float unpressedOffsetY) {
        this.unpressedOffsetX = unpressedOffsetX;
        this.unpressedOffsetY = unpressedOffsetY;
        return this;
    }

    /**
     * Sets the X and Y-axis offsets for the checked state of the button.
     *
     * @param checkedOffsetX The X-axis offset for the checked state.
     * @param checkedOffsetY The Y-axis offset for the checked state.
     * @return The GDXButtonStyle instance, enabling method chaining.
     */
    public GDXButtonStyle checkedOffset(float checkedOffsetX, float checkedOffsetY) {
        this.checkedOffsetX = checkedOffsetX;
        this.checkedOffsetY = checkedOffsetY;
        return this;
    }
}