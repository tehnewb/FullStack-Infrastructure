package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXTextButtonStyle class extends LibGDX's TextButton.TextButtonStyle, providing customization options
 * for the appearance of text buttons. It allows you to specify different properties such as font, font color,
 * and font color for various states of the text button, such as up, down, over, and disabled states.
 *
 * @author Albert Beaupre
 */
public class GDXTextButtonStyle extends TextButton.TextButtonStyle {
    private static GDXTextButtonStyle defaults = new GDXTextButtonStyle();

    /**
     * Returns the current GDXTextButtonStyle instance with default settings.
     *
     * @return A new GDXTextButtonStyle instance with default settings.
     */
    public static GDXTextButtonStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXTextButtonStyle instance with default settings.
     *
     * @param style The GDXTextButtonStyle instance with default settings.
     */
    public static void defaults(GDXTextButtonStyle style) {
        defaults = style;
    }

    /**
     * Sets the font used for rendering the text on the button.
     *
     * @param font The BitmapFont to use for rendering text.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle font(BitmapFont font) {
        this.font = font;
        return this;
    }

    /**
     * Sets the font color for the text button in its default (up) state.
     *
     * @param fontColor The Color for the default (up) state font color.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle fontColor(Color fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    /**
     * Sets the font color for the text button in the pressed (down) state.
     *
     * @param downFontColor The Color for the pressed (down) state font color.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle downFontColor(Color downFontColor) {
        this.downFontColor = downFontColor;
        return this;
    }

    /**
     * Sets the font color for the text button when it is hovered over (over state).
     *
     * @param overFontColor The Color for the hover (over) state font color.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle overFontColor(Color overFontColor) {
        this.overFontColor = overFontColor;
        return this;
    }

    /**
     * Sets the font color for the text button when it is focused.
     *
     * @param focusedFontColor The Color for the focused state font color.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle focusedFontColor(Color focusedFontColor) {
        this.focusedFontColor = focusedFontColor;
        return this;
    }

    /**
     * Sets the font color for the text button when it is disabled.
     *
     * @param disabledFontColor The Color for the disabled state font color.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle disabledFontColor(Color disabledFontColor) {
        this.disabledFontColor = disabledFontColor;
        return this;
    }

    /**
     * Sets the font color for the text button when it is checked (on).
     *
     * @param checkedFontColor The Color for the checked (on) state font color.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle checkedFontColor(Color checkedFontColor) {
        this.checkedFontColor = checkedFontColor;
        return this;
    }

    /**
     * Sets the font color for the text button in the checked (on) and pressed (down) state.
     *
     * @param checkedDownFontColor The Color for the checked (on) and pressed (down) state font color.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle checkedDownFontColor(Color checkedDownFontColor) {
        this.checkedDownFontColor = checkedDownFontColor;
        return this;
    }

    /**
     * Sets the font color for the text button in the checked (on) and hovered over (over state) state.
     *
     * @param checkedOverFontColor The Color for the checked (on) and hover (over) state font color.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle checkedOverFontColor(Color checkedOverFontColor) {
        this.checkedOverFontColor = checkedOverFontColor;
        return this;
    }

    /**
     * Sets the drawable for the up (default) state of the button.
     *
     * @param up The Drawable for the up state.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle up(Drawable up) {
        this.up = up;
        return this;
    }

    /**
     * Sets the drawable for the down state of the button (when pressed).
     *
     * @param down The Drawable for the down state.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle down(Drawable down) {
        this.down = down;
        return this;
    }

    /**
     * Sets the drawable for the over state of the button (when hovered over).
     *
     * @param over The Drawable for the over state.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle over(Drawable over) {
        this.over = over;
        return this;
    }

    /**
     * Sets the drawable for the focused state of the button (when focused).
     *
     * @param focused The Drawable for the focused state.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle focused(Drawable focused) {
        this.focused = focused;
        return this;
    }

    /**
     * Sets the drawable for the disabled state of the button.
     *
     * @param disabled The Drawable for the disabled state.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle disabled(Drawable disabled) {
        this.disabled = disabled;
        return this;
    }

    /**
     * Sets the drawable for the checked state of the button.
     *
     * @param checked The Drawable for the checked state.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle checked(Drawable checked) {
        this.checked = checked;
        return this;
    }

    /**
     * Sets the drawable for the checked over state of the button.
     *
     * @param checkedOver The Drawable for the checked over state.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle checkedOver(Drawable checkedOver) {
        this.checkedOver = checkedOver;
        return this;
    }

    /**
     * Sets the drawable for the checked down state of the button.
     *
     * @param checkedDown The Drawable for the checked down state.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle checkedDown(Drawable checkedDown) {
        this.checkedDown = checkedDown;
        return this;
    }

    /**
     * Sets the drawable for the checked focused state of the button.
     *
     * @param checkedFocused The Drawable for the checked focused state.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle checkedFocused(Drawable checkedFocused) {
        this.checkedFocused = checkedFocused;
        return this;
    }

    /**
     * Sets the X and Y-axis offsets for the pressed state of the button.
     *
     * @param pressedOffsetX The X-axis offset for the pressed state.
     * @param pressedOffsetY The Y-axis offset for the pressed state.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle pressedOffset(float pressedOffsetX, float pressedOffsetY) {
        this.pressedOffsetX = pressedOffsetX;
        this.pressedOffsetY = pressedOffsetY;
        return this;
    }

    /**
     * Sets the X and Y-axis offsets for the unpressed state of the button.
     *
     * @param unpressedOffsetX The X-axis offset for the unpressed state.
     * @param unpressedOffsetY The Y-axis offset for the unpressed state.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle unpressedOffset(float unpressedOffsetX, float unpressedOffsetY) {
        this.unpressedOffsetX = unpressedOffsetX;
        this.unpressedOffsetY = unpressedOffsetY;
        return this;
    }

    /**
     * Sets the X and Y-axis offsets for the checked state of the button.
     *
     * @param checkedOffsetX The X-axis offset for the checked state.
     * @param checkedOffsetY The Y-axis offset for the checked state.
     * @return The GDXTextButtonStyle instance, enabling method chaining.
     */
    public GDXTextButtonStyle checkedOffset(float checkedOffsetX, float checkedOffsetY) {
        this.checkedOffsetX = checkedOffsetX;
        this.checkedOffsetY = checkedOffsetY;
        return this;
    }

    /**
     * Clones this GDXTextButtonStyle by copying all style variables and returning a new instance.
     *
     * @return a cloned instance of this GDXTextButtonStyle.
     */
    public GDXTextButtonStyle copy() {
        GDXTextButtonStyle style = new GDXTextButtonStyle();
        style.font = font;
        style.fontColor = fontColor;
        style.checkedDownFontColor = checkedDownFontColor;
        style.checkedFocusedFontColor = checkedFocusedFontColor;
        style.downFontColor = downFontColor;
        style.overFontColor = overFontColor;
        style.focusedFontColor = focusedFontColor;
        style.disabledFontColor = disabledFontColor;
        style.checkedFontColor = checkedFontColor;
        style.checkedOverFontColor = checkedOverFontColor;
        style.up = up;
        style.down = down;
        style.over = over;
        style.focused = focused;
        style.disabled = disabled;
        style.checked = checked;
        style.checkedOver = checkedOver;
        style.checkedDown = checkedDown;
        style.checkedFocused = checkedFocused;
        style.pressedOffsetX = pressedOffsetX;
        style.pressedOffsetY = pressedOffsetY;
        style.unpressedOffsetX = unpressedOffsetX;
        style.unpressedOffsetY = unpressedOffsetY;
        style.checkedOffsetX = checkedOffsetX;
        style.checkedOffsetY = checkedOffsetY;
        return style;
    }
}
