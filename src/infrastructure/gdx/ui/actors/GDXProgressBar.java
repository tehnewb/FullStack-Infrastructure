package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import infrastructure.gdx.ui.styles.GDXProgressBarStyle;

/**
 * The `GDXProgressBar` class extends LibGDX's ProgressBar class, providing additional functionality and customization options
 * for creating and manipulating progress bars within a graphical user interface (GUI).
 */
public class GDXProgressBar extends ProgressBar {

    /**
     * Constructs a `GDXProgressBar` with the specified minimum, maximum, step size, and orientation, using the default progress bar style defined in `GDXProgressBarStyle.defaults()`.
     *
     * @param min      The minimum value of the progress bar.
     * @param max      The maximum value of the progress bar.
     * @param stepSize The step size used for changing the value.
     * @param vertical `true` if the progress bar is vertical, `false` for horizontal.
     */
    public GDXProgressBar(float min, float max, float stepSize, boolean vertical) {
        super(min, max, stepSize, vertical, GDXProgressBarStyle.defaults());
    }

    /**
     * Creates a new `GDXProgressBar` instance with the specified minimum, maximum, step size, and orientation.
     *
     * @param min      The minimum value of the progress bar.
     * @param max      The maximum value of the progress bar.
     * @param stepSize The step size used for changing the value.
     * @param vertical `true` if the progress bar is vertical, `false` for horizontal.
     * @return A new `GDXProgressBar` instance initialized with the provided parameters.
     */
    public static GDXProgressBar of(float min, float max, float stepSize, boolean vertical) {
        return new GDXProgressBar(min, max, stepSize, vertical);
    }

    /**
     * Sets the style of the progress bar.
     *
     * @param style The `GDXProgressBarStyle` object defining the visual style of the progress bar.
     * @return The `GDXProgressBar` instance, enabling method chaining.
     */
    public GDXProgressBar style(GDXProgressBarStyle style) {
        this.setStyle(style);
        return this;
    }

    /**
     * Sets the value of the progress bar.
     *
     * @param value The value to set the progress bar at.
     * @return The `GDXProgressBar` instance, enabling method chaining.
     */
    public GDXProgressBar value(float value) {
        this.setValue(value);
        return this;
    }
}
