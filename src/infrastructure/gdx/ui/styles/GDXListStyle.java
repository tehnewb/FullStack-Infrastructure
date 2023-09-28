package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXListStyle class extends LibGDX's List.ListStyle, providing customization options
 * for the appearance of lists. It allows you to specify properties such as font, font colors,
 * drawables for selection, down, over, and background states of the list.
 *
 * @author Albert Beaupre
 */
public class GDXListStyle extends List.ListStyle {
    private static GDXListStyle defaults;

    /**
     * Returns the current GDXListStyle instance with default settings.
     *
     * @return A new GDXListStyle instance with default settings.
     */
    public static GDXListStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXListStyle instance with default settings.
     *
     * @param style The GDXListStyle instance with default settings.
     */
    public static void defaults(GDXListStyle style) {
        defaults = style;
    }

    /**
     * Sets the font used for rendering text in the list.
     *
     * @param font The BitmapFont to use for rendering text.
     * @return The GDXListStyle instance, enabling method chaining.
     */
    public GDXListStyle font(BitmapFont font) {
        this.font = font;
        return this;
    }

    /**
     * Sets the font color for selected items in the list.
     *
     * @param fontColorSelected The Color for selected item font color.
     * @return The GDXListStyle instance, enabling method chaining.
     */
    public GDXListStyle fontColorSelected(Color fontColorSelected) {
        this.fontColorSelected = fontColorSelected;
        return this;
    }

    /**
     * Sets the font color for unselected items in the list.
     *
     * @param fontColorUnselected The Color for unselected item font color.
     * @return The GDXListStyle instance, enabling method chaining.
     */
    public GDXListStyle fontColorUnselected(Color fontColorUnselected) {
        this.fontColorUnselected = fontColorUnselected;
        return this;
    }

    /**
     * Sets the drawable used for item selection in the list.
     *
     * @param selection The Drawable for item selection.
     * @return The GDXListStyle instance, enabling method chaining.
     */
    public GDXListStyle selection(Drawable selection) {
        this.selection = selection;
        return this;
    }

    /**
     * Sets the drawable used for the down (pressed) state of the list.
     *
     * @param down The Drawable for the down state.
     * @return The GDXListStyle instance, enabling method chaining.
     */
    public GDXListStyle down(Drawable down) {
        this.down = down;
        return this;
    }

    /**
     * Sets the drawable used for the over (hovered) state of the list.
     *
     * @param over The Drawable for the over state.
     * @return The GDXListStyle instance, enabling method chaining.
     */
    public GDXListStyle over(Drawable over) {
        this.over = over;
        return this;
    }

    /**
     * Sets the drawable used for the background of the list.
     *
     * @param background The Drawable for the background.
     * @return The GDXListStyle instance, enabling method chaining.
     */
    public GDXListStyle background(Drawable background) {
        this.background = background;
        return this;
    }
}
