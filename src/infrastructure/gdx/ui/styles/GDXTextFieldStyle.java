package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import infrastructure.gdx.assets.GDXTextureFolder;

/**
 * The GDXTextFieldStyle class extends LibGDX's TextField.TextFieldStyle, providing customization options
 * for the appearance of text fields. It allows you to specify properties such as fonts, font colors,
 * backgrounds, cursor drawables, selection drawables, and message fonts for text fields.
 */
public class GDXTextFieldStyle extends TextField.TextFieldStyle {
    private static GDXTextFieldStyle defaults;

    /**
     * Returns the current GDXTextFieldStyle instance with default settings.
     *
     * @return A new GDXTextFieldStyle instance with default settings.
     */
    public static GDXTextFieldStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXTextFieldStyle instance with default settings.
     *
     * @param style The GDXTextFieldStyle instance with default settings.
     */
    public static void defaults(GDXTextFieldStyle style) {
        defaults = style;
    }

    /**
     * Sets the font used for rendering text in the text field.
     *
     * @param font The BitmapFont to use for text rendering.
     * @return The GDXTextFieldStyle instance, enabling method chaining.
     */
    public GDXTextFieldStyle font(BitmapFont font) {
        this.font = font;
        return this;
    }

    /**
     * Sets the font color for the text in the text field.
     *
     * @param fontColor The Color for the text font color.
     * @return The GDXTextFieldStyle instance, enabling method chaining.
     */
    public GDXTextFieldStyle fontColor(Color fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    /**
     * Sets the font color for focused text in the text field.
     *
     * @param focusedFontColor The Color for focused text font color.
     * @return The GDXTextFieldStyle instance, enabling method chaining.
     */
    public GDXTextFieldStyle focusedFontColor(Color focusedFontColor) {
        this.focusedFontColor = focusedFontColor;
        return this;
    }

    /**
     * Sets the font color for disabled text in the text field.
     *
     * @param disabledFontColor The Color for disabled text font color.
     * @return The GDXTextFieldStyle instance, enabling method chaining.
     */
    public GDXTextFieldStyle disabledFontColor(Color disabledFontColor) {
        this.disabledFontColor = disabledFontColor;
        return this;
    }

    /**
     * Sets the background drawable for the text field.
     *
     * @param background The Drawable for the text field's background.
     * @return The GDXTextFieldStyle instance, enabling method chaining.
     */
    public GDXTextFieldStyle background(Drawable background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the background drawable for the text field when focused.
     *
     * @param focusedBackground The Drawable for the focused text field's background.
     * @return The GDXTextFieldStyle instance, enabling method chaining.
     */
    public GDXTextFieldStyle focusedBackground(Drawable focusedBackground) {
        this.focusedBackground = focusedBackground;
        return this;
    }

    /**
     * Sets the background drawable for the disabled text field.
     *
     * @param disabledBackground The Drawable for the disabled text field's background.
     * @return The GDXTextFieldStyle instance, enabling method chaining.
     */
    public GDXTextFieldStyle disabledBackground(Drawable disabledBackground) {
        this.disabledBackground = disabledBackground;
        return this;
    }

    /**
     * Sets the cursor drawable for the text field.
     *
     * @param cursor The Drawable for the text field's cursor.
     * @return The GDXTextFieldStyle instance, enabling method chaining.
     */
    public GDXTextFieldStyle cursor(Drawable cursor) {
        this.cursor = cursor;
        return this;
    }

    /**
     * Sets the selection drawable for the text field.
     *
     * @param selection The Drawable for the text field's selection.
     * @return The GDXTextFieldStyle instance, enabling method chaining.
     */
    public GDXTextFieldStyle selection(Drawable selection) {
        this.selection = selection;
        return this;
    }

    /**
     * Sets the font used for rendering the text field's message (hint text).
     *
     * @param messageFont The BitmapFont to use for rendering the message.
     * @return The GDXTextFieldStyle instance, enabling method chaining.
     */
    public GDXTextFieldStyle messageFont(BitmapFont messageFont) {
        this.messageFont = messageFont;
        return this;
    }

    /**
     * Sets the font color for the text field's message (hint text).
     *
     * @param messageFontColor The Color for the message font color.
     * @return The GDXTextFieldStyle instance, enabling method chaining.
     */
    public GDXTextFieldStyle messageFontColor(Color messageFontColor) {
        this.messageFontColor = messageFontColor;
        return this;
    }
}
