package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXSelectBoxStyle class extends LibGDX's SelectBox.SelectBoxStyle, providing customization options
 * for the appearance of select boxes. It allows you to specify properties such as fonts, font colors,
 * backgrounds, scroll styles, list styles, and various states' backgrounds.
 *
 * @author Albert Beaupre
 */
public class GDXSelectBoxStyle extends SelectBox.SelectBoxStyle {
    private static GDXSelectBoxStyle defaults;

    /**
     * Returns the current GDXSelectBoxStyle instance with default settings.
     *
     * @return A new GDXSelectBoxStyle instance with default settings.
     */
    public static GDXSelectBoxStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXSelectBoxStyle instance with default settings.
     *
     * @param style The GDXSelectBoxStyle instance with default settings.
     */
    public static void defaults(GDXSelectBoxStyle style) {
        defaults = style;
    }


    /**
     * Sets the font used for rendering text in the select box.
     *
     * @param font The BitmapFont to use for text rendering.
     * @return The GDXSelectBoxStyle instance, enabling method chaining.
     */
    public GDXSelectBoxStyle font(BitmapFont font) {
        this.font = font;
        return this;
    }

    /**
     * Sets the font color for the text in the select box.
     *
     * @param fontColor The Color for the text font color.
     * @return The GDXSelectBoxStyle instance, enabling method chaining.
     */
    public GDXSelectBoxStyle fontColor(Color fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    /**
     * Sets the font color for text in the select box when hovered over.
     *
     * @param overFontColor The Color for the text font color when hovered over.
     * @return The GDXSelectBoxStyle instance, enabling method chaining.
     */
    public GDXSelectBoxStyle overFontColor(Color overFontColor) {
        this.overFontColor = overFontColor;
        return this;
    }

    /**
     * Sets the font color for disabled text in the select box.
     *
     * @param disabledFontColor The Color for disabled text font color.
     * @return The GDXSelectBoxStyle instance, enabling method chaining.
     */
    public GDXSelectBoxStyle disabledFontColor(Color disabledFontColor) {
        this.disabledFontColor = disabledFontColor;
        return this;
    }

    /**
     * Sets the background drawable for the select box.
     *
     * @param background The Drawable for the select box's background.
     * @return The GDXSelectBoxStyle instance, enabling method chaining.
     */
    public GDXSelectBoxStyle background(Drawable background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the scroll style for the select box's scroll pane.
     *
     * @param scrollStyle The ScrollPane.ScrollPaneStyle to use for the scroll pane.
     * @return The GDXSelectBoxStyle instance, enabling method chaining.
     */
    public GDXSelectBoxStyle scrollStyle(ScrollPane.ScrollPaneStyle scrollStyle) {
        this.scrollStyle = scrollStyle;
        return this;
    }

    /**
     * Sets the list style for the select box's list.
     *
     * @param listStyle The List.ListStyle to use for the list.
     * @return The GDXSelectBoxStyle instance, enabling method chaining.
     */
    public GDXSelectBoxStyle listStyle(List.ListStyle listStyle) {
        this.listStyle = listStyle;
        return this;
    }

    /**
     * Sets the background drawable for the select box when hovered over.
     *
     * @param backgroundOver The Drawable for the select box's background when hovered over.
     * @return The GDXSelectBoxStyle instance, enabling method chaining.
     */
    public GDXSelectBoxStyle backgroundOver(Drawable backgroundOver) {
        this.backgroundOver = backgroundOver;
        return this;
    }

    /**
     * Sets the background drawable for the open state of the select box.
     *
     * @param backgroundOpen The Drawable for the select box's background when open.
     * @return The GDXSelectBoxStyle instance, enabling method chaining.
     */
    public GDXSelectBoxStyle backgroundOpen(Drawable backgroundOpen) {
        this.backgroundOpen = backgroundOpen;
        return this;
    }

    /**
     * Sets the background drawable for the disabled state of the select box.
     *
     * @param backgroundDisabled The Drawable for the select box's background when disabled.
     * @return The GDXSelectBoxStyle instance, enabling method chaining.
     */
    public GDXSelectBoxStyle backgroundDisabled(Drawable backgroundDisabled) {
        this.backgroundDisabled = backgroundDisabled;
        return this;
    }
}
