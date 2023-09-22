package gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXTextTooltipStyle class extends LibGDX's Tooltip.TooltipStyle, providing customization options
 * for the appearance of text tooltips. It allows you to specify properties such as label style and background.
 */
public class GDXTextTooltipStyle extends TextTooltip.TextTooltipStyle {

    /**
     * Creates a new GDXTextTooltipStyle instance with default settings.
     *
     * @return A new GDXTextTooltipStyle instance with default style properties.
     */
    public static GDXTextTooltipStyle defaults() {
        return new GDXTextTooltipStyle();
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
}
