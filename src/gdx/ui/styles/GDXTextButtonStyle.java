package gdx.ui.styles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * The GDXTextButtonStyle class extends LibGDX's TextButton.TextButtonStyle, providing customization options
 * for the appearance of text buttons. It allows you to specify different properties such as font, font color,
 * and font color for various states of the text button, such as up, down, over, and disabled states.
 */
public class GDXTextButtonStyle extends TextButton.TextButtonStyle {

    /**
     * Creates a new GDXTextButtonStyle instance with default settings.
     *
     * @return A new GDXTextButtonStyle instance with default style properties.
     */
    public static GDXTextButtonStyle defaults() {
        return new GDXTextButtonStyle();
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
}
