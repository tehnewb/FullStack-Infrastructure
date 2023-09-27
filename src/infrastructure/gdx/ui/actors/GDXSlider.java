package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import infrastructure.gdx.ui.styles.GDXSliderStyle;

/**
 * The `GDXSlider` class extends LibGDX's Slider class, providing additional functionality and customization options
 * for creating and manipulating sliders within a graphical user interface (GUI).
 */
public class GDXSlider extends Slider {

    /**
     * Constructs a `GDXSlider` with the specified minimum, maximum, step size, orientation, and uses the default slider style defined in `GDXSliderStyle.defaults()`.
     *
     * @param min      The minimum value of the slider.
     * @param max      The maximum value of the slider.
     * @param stepSize The step size used for changing the value.
     * @param vertical `true` if the slider is vertical, `false` for horizontal.
     */
    public GDXSlider(float min, float max, float stepSize, boolean vertical) {
        super(min, max, stepSize, vertical, GDXSliderStyle.defaults());
    }

    /**
     * Creates a new `GDXSlider` instance with the specified minimum, maximum, step size, and orientation, using the default style.
     *
     * @param min      The minimum value of the slider.
     * @param max      The maximum value of the slider.
     * @param stepSize The step size used for changing the value.
     * @param vertical `true` if the slider is vertical, `false` for horizontal.
     * @return A new `GDXSlider` instance initialized with the provided parameters and the default style.
     */
    public static GDXSlider of(float min, float max, float stepSize, boolean vertical) {
        return new GDXSlider(min, max, stepSize, vertical);
    }

    /**
     * Sets the style of the slider.
     *
     * @param style The `SliderStyle` object defining the visual style of the slider.
     * @return The `GDXSlider` instance, enabling method chaining.
     */
    public GDXSlider style(GDXSliderStyle style) {
        this.setStyle(style);
        return this;
    }
}