package gdx.ui.styles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import gdx.ui.GDXSkin;

/**
 * The GDXLabelStyle class extends LibGDX's Label.LabelStyle, providing customization options for the appearance of labels.
 * It allows you to specify the font, text color, and background drawable of a label.
 */
public class GDXLabelStyle extends Label.LabelStyle {

    /**
     * Constructs a GDXLabelStyle with the default font from the GDXSkin.
     */
    public GDXLabelStyle() {
        // Set the default font from GDXSkin as the font for this style.
        this.font(GDXSkin.getInstance().getFont("default-font"));
        this.color(Color.WHITE);
    }

    /**
     * Creates a new GDXLabelStyle instance with default settings.
     *
     * @return A new GDXLabelStyle instance with default font settings.
     */
    public static GDXLabelStyle defaults() {
        return new GDXLabelStyle();
    }

    /**
     * Sets the background drawable of the label style, allowing you to customize its appearance.
     *
     * @param background The Drawable object representing the background appearance.
     * @return The GDXLabelStyle instance, enabling method chaining.
     */
    public GDXLabelStyle background(Drawable background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the font used for rendering the label's text.
     *
     * @param font The BitmapFont to use for rendering the text.
     * @return The GDXLabelStyle instance, enabling method chaining.
     */
    public GDXLabelStyle font(BitmapFont font) {
        this.font = font;
        return this;
    }

    /**
     * Sets the text color of the label.
     *
     * @param color The Color object representing the desired text color.
     * @return The GDXLabelStyle instance, enabling method chaining.
     */
    public GDXLabelStyle color(Color color) {
        this.fontColor = color;
        return this;
    }
}