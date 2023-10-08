package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXTextTooltipStyle class extends LibGDX's Tooltip.TooltipStyle, providing customization options
 * for the appearance of text tooltips. It allows you to specify properties such as label style and background.
 *
 * @author Albert Beaupre
 */
public class GDXTextTooltipStyle extends TextTooltip.TextTooltipStyle {
    private static GDXTextTooltipStyle defaults = new GDXTextTooltipStyle();

    /**
     * Returns the current GDXTextTooltipStyle instance with default settings.
     *
     * @return A new GDXTextTooltipStyle instance with default settings.
     */
    public static GDXTextTooltipStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXTextTooltipStyle instance with default settings.
     *
     * @param style The GDXTextTooltipStyle instance with default settings.
     */
    public static void defaults(GDXTextTooltipStyle style) {
        defaults = style;
    }

    /**
     * Sets the label style for rendering text within the tooltip.
     *
     * @param labelStyle The Label.LabelStyle to use for rendering tooltip text.
     * @return The GDXTextTooltipStyle instance, enabling method chaining.
     */
    public GDXTextTooltipStyle label(Label.LabelStyle labelStyle) {
        this.label = labelStyle;
        return this;
    }

    /**
     * Sets the background drawable for the tooltip.
     *
     * @param background The Drawable for the tooltip's background.
     * @return The GDXTextTooltipStyle instance, enabling method chaining.
     */
    public GDXTextTooltipStyle background(Drawable background) {
        this.background = background;
        return this;
    }

    /**
     * Clones this GDXTextTooltipStyle by copying all style variables and returning a new instance.
     *
     * @return a cloned instance of this GDXTextTooltipStyle.
     */
    public GDXTextTooltipStyle copy() {
        GDXTextTooltipStyle style = new GDXTextTooltipStyle();
        style.background = background;
        style.label = label;
        return style;
    }
}
