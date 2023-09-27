package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import infrastructure.gdx.ui.styles.GDXTextTooltipStyle;

/**
 * The `GDXTextTooltip` class extends LibGDX's TextTooltip class, providing additional functionality and customization options
 * for creating and manipulating text tooltips within a graphical user interface (GUI).
 */
public class GDXTextTooltip extends TextTooltip {

    /**
     * Constructs a `GDXTextTooltip` with the specified text, tooltip manager, and uses the default text tooltip style defined in `GDXTextTooltipStyle.defaults()`.
     *
     * @param text    The text content of the tooltip.
     * @param manager The tooltip manager responsible for managing tooltips.
     */
    public GDXTextTooltip(String text, TooltipManager manager) {
        super(text, manager, GDXTextTooltipStyle.defaults());
    }

    /**
     * Creates a new `GDXTextTooltip` instance with the specified text, tooltip manager, and uses the default style.
     *
     * @param text    The text content of the tooltip.
     * @param manager The tooltip manager responsible for managing tooltips.
     * @return A new `GDXTextTooltip` instance initialized with the provided text, tooltip manager, and the default style.
     */
    public static GDXTextTooltip of(String text, TooltipManager manager) {
        return new GDXTextTooltip(text, manager);
    }

    /**
     * Sets the style of the text tooltip.
     *
     * @param style The `TextTooltipStyle` object defining the visual style of the text tooltip.
     * @return The `GDXTextTooltip` instance, enabling method chaining.
     */
    public GDXTextTooltip style(GDXTextTooltipStyle style) {
        this.setStyle(style);
        return this;
    }
}
