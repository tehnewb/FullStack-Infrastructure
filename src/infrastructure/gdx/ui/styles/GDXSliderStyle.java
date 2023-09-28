package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXSliderStyle class extends LibGDX's Slider.SliderStyle, providing customization options
 * for the appearance of sliders. It allows you to specify properties such as background drawables,
 * knob drawables, and drawables for different states (over, down) of the slider components.
 *
 * @author Albert Beaupre
 */
public class GDXSliderStyle extends Slider.SliderStyle {
    private static GDXSliderStyle defaults = new GDXSliderStyle();

    /**
     * Returns the current GDXSliderStyle instance with default settings.
     *
     * @return A new GDXSliderStyle instance with default settings.
     */
    public static GDXSliderStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXSliderStyle instance with default settings.
     *
     * @param style The GDXSliderStyle instance with default settings.
     */
    public static void defaults(GDXSliderStyle style) {
        defaults = style;
    }

    /**
     * Sets the background drawable for the slider when hovered over (over state).
     *
     * @param backgroundOver The Drawable for the slider's background in the over state.
     * @return The GDXSliderStyle instance, enabling method chaining.
     */
    public GDXSliderStyle backgroundOver(Drawable backgroundOver) {
        this.backgroundOver = backgroundOver;
        return this;
    }

    /**
     * Sets the background drawable for the slider when pressed (down state).
     *
     * @param backgroundDown The Drawable for the slider's background in the down state.
     * @return The GDXSliderStyle instance, enabling method chaining.
     */
    public GDXSliderStyle backgroundDown(Drawable backgroundDown) {
        this.backgroundDown = backgroundDown;
        return this;
    }

    /**
     * Sets the knob drawable for the slider when hovered over (over state).
     *
     * @param knobOver The Drawable for the slider's knob in the over state.
     * @return The GDXSliderStyle instance, enabling method chaining.
     */
    public GDXSliderStyle knobOver(Drawable knobOver) {
        this.knobOver = knobOver;
        return this;
    }

    /**
     * Sets the knob drawable for the slider when pressed (down state).
     *
     * @param knobDown The Drawable for the slider's knob in the down state.
     * @return The GDXSliderStyle instance, enabling method chaining.
     */
    public GDXSliderStyle knobDown(Drawable knobDown) {
        this.knobDown = knobDown;
        return this;
    }

    /**
     * Sets the drawable for the knobBefore part of the slider when hovered over (over state).
     *
     * @param knobBeforeOver The Drawable for the knobBefore part in the over state.
     * @return The GDXSliderStyle instance, enabling method chaining.
     */
    public GDXSliderStyle knobBeforeOver(Drawable knobBeforeOver) {
        this.knobBeforeOver = knobBeforeOver;
        return this;
    }

    /**
     * Sets the drawable for the knobBefore part of the slider when pressed (down state).
     *
     * @param knobBeforeDown The Drawable for the knobBefore part in the down state.
     * @return The GDXSliderStyle instance, enabling method chaining.
     */
    public GDXSliderStyle knobBeforeDown(Drawable knobBeforeDown) {
        this.knobBeforeDown = knobBeforeDown;
        return this;
    }

    /**
     * Sets the drawable for the knobAfter part of the slider when hovered over (over state).
     *
     * @param knobAfterOver The Drawable for the knobAfter part in the over state.
     * @return The GDXSliderStyle instance, enabling method chaining.
     */
    public GDXSliderStyle knobAfterOver(Drawable knobAfterOver) {
        this.knobAfterOver = knobAfterOver;
        return this;
    }

    /**
     * Sets the drawable for the knobAfter part of the slider when pressed (down state).
     *
     * @param knobAfterDown The Drawable for the knobAfter part in the down state.
     * @return The GDXSliderStyle instance, enabling method chaining.
     */
    public GDXSliderStyle knobAfterDown(Drawable knobAfterDown) {
        this.knobAfterDown = knobAfterDown;
        return this;
    }
}
