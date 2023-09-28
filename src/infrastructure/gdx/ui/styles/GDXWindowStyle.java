package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXWindowStyle class extends LibGDX's Window.WindowStyle, providing customization options
 * for the appearance of windows. It allows you to specify properties such as background drawable,
 * title font, title font color, and stage background drawable for windows.
 *
 * @author Albert Beaupre
 */
public class GDXWindowStyle extends Window.WindowStyle {
    private static GDXWindowStyle defaults;

    /**
     * Returns the current GDXWindowStyle instance with default settings.
     *
     * @return A new GDXWindowStyle instance with default settings.
     */
    public static GDXWindowStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXWindowStyle instance with default settings.
     *
     * @param style The GDXWindowStyle instance with default settings.
     */
    public static void defaults(GDXWindowStyle style) {
        defaults = style;
    }

    /**
     * Sets the background drawable for the window.
     *
     * @param background The Drawable for the window's background.
     * @return The GDXWindowStyle instance, enabling method chaining.
     */
    public GDXWindowStyle background(Drawable background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the font used for rendering the window's title.
     *
     * @param titleFont The BitmapFont to use for rendering the title.
     * @return The GDXWindowStyle instance, enabling method chaining.
     */
    public GDXWindowStyle titleFont(BitmapFont titleFont) {
        this.titleFont = titleFont;
        return this;
    }

    /**
     * Sets the font color for the window's title.
     *
     * @param titleFontColor The Color for the title font color.
     * @return The GDXWindowStyle instance, enabling method chaining.
     */
    public GDXWindowStyle titleFontColor(Color titleFontColor) {
        this.titleFontColor = titleFontColor;
        return this;
    }

    /**
     * Sets the background drawable for the stage behind the window.
     *
     * @param stageBackground The Drawable for the stage background.
     * @return The GDXWindowStyle instance, enabling method chaining.
     */
    public GDXWindowStyle stageBackground(Drawable stageBackground) {
        this.stageBackground = stageBackground;
        return this;
    }
}
