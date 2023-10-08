package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXProgressBarStyle class extends LibGDX's ProgressBar.ProgressBarStyle, providing customization options
 * for the appearance of progress bars. It allows you to specify properties such as background drawables,
 * knob drawables, and disabled state drawables for progress bars.
 *
 * @author Albert Beaupre
 */
public class GDXProgressBarStyle extends ProgressBar.ProgressBarStyle {
    private static GDXProgressBarStyle defaults = new GDXProgressBarStyle();

    /**
     * Returns the current GDXProgressBarStyle instance with default settings.
     *
     * @return A new GDXProgressBarStyle instance with default settings.
     */
    public static GDXProgressBarStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXProgressBarStyle instance with default settings.
     *
     * @param style The GDXProgressBarStyle instance with default settings.
     */
    public static void defaults(GDXProgressBarStyle style) {
        defaults = style;
    }

    /**
     * Sets the background drawable for the progress bar.
     *
     * @param background The Drawable for the progress bar's background.
     * @return The GDXProgressBarStyle instance, enabling method chaining.
     */
    public GDXProgressBarStyle background(Drawable background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the disabled background drawable for the progress bar.
     *
     * @param disabledBackground The Drawable for the disabled progress bar's background.
     * @return The GDXProgressBarStyle instance, enabling method chaining.
     */
    public GDXProgressBarStyle disabledBackground(Drawable disabledBackground) {
        this.disabledBackground = disabledBackground;
        return this;
    }

    /**
     * Sets the knob drawable for the progress bar.
     *
     * @param knob The Drawable for the progress bar's knob.
     * @return The GDXProgressBarStyle instance, enabling method chaining.
     */
    public GDXProgressBarStyle knob(Drawable knob) {
        this.knob = knob;
        return this;
    }

    /**
     * Sets the disabled knob drawable for the progress bar.
     *
     * @param disabledKnob The Drawable for the disabled progress bar's knob.
     * @return The GDXProgressBarStyle instance, enabling method chaining.
     */
    public GDXProgressBarStyle disabledKnob(Drawable disabledKnob) {
        this.disabledKnob = disabledKnob;
        return this;
    }

    /**
     * Sets the knobBefore drawable for the progress bar.
     *
     * @param knobBefore The Drawable for the progress bar's knobBefore.
     * @return The GDXProgressBarStyle instance, enabling method chaining.
     */
    public GDXProgressBarStyle knobBefore(Drawable knobBefore) {
        this.knobBefore = knobBefore;
        return this;
    }

    /**
     * Sets the disabled knobBefore drawable for the progress bar.
     *
     * @param disabledKnobBefore The Drawable for the disabled progress bar's knobBefore.
     * @return The GDXProgressBarStyle instance, enabling method chaining.
     */
    public GDXProgressBarStyle disabledKnobBefore(Drawable disabledKnobBefore) {
        this.disabledKnobBefore = disabledKnobBefore;
        return this;
    }

    /**
     * Sets the knobAfter drawable for the progress bar.
     *
     * @param knobAfter The Drawable for the progress bar's knobAfter.
     * @return The GDXProgressBarStyle instance, enabling method chaining.
     */
    public GDXProgressBarStyle knobAfter(Drawable knobAfter) {
        this.knobAfter = knobAfter;
        return this;
    }

    /**
     * Sets the disabled knobAfter drawable for the progress bar.
     *
     * @param disabledKnobAfter The Drawable for the disabled progress bar's knobAfter.
     * @return The GDXProgressBarStyle instance, enabling method chaining.
     */
    public GDXProgressBarStyle disabledKnobAfter(Drawable disabledKnobAfter) {
        this.disabledKnobAfter = disabledKnobAfter;
        return this;
    }

    /**
     * Clones this GDXProgressBarStyle by copying all style variables and returning a new instance.
     *
     * @return a cloned instance of this GDXProgressBarStyle.
     */
    public GDXProgressBarStyle copy() {
        GDXProgressBarStyle style = new GDXProgressBarStyle();
        style.disabledBackground = disabledBackground;
        style.disabledKnobAfter = disabledKnobAfter;
        style.disabledKnobBefore = disabledKnobBefore;
        style.disabledKnob = disabledKnob;
        style.knob = knob;
        style.knobAfter = knobAfter;
        style.knobBefore = knobBefore;
        style.background = background;
        return style;
    }
}
