package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXScrollPaneStyle class extends LibGDX's ScrollPane.ScrollPaneStyle, providing customization options
 * for the appearance of scroll panes. It allows you to specify properties such as background drawables,
 * corner drawables, horizontal scroll drawables, and vertical scroll drawables for scroll panes.
 *
 * @author Albert Beaupre
 */
public class GDXScrollPaneStyle extends ScrollPane.ScrollPaneStyle {
    private static GDXScrollPaneStyle defaults = new GDXScrollPaneStyle();

    /**
     * Returns the current GDXScrollPaneStyle instance with default settings.
     *
     * @return A new GDXScrollPaneStyle instance with default settings.
     */
    public static GDXScrollPaneStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXScrollPaneStyle instance with default settings.
     *
     * @param style The GDXScrollPaneStyle instance with default settings.
     */
    public static void defaults(GDXScrollPaneStyle style) {
        defaults = style;
    }

    /**
     * Sets the background drawable for the scroll pane.
     *
     * @param background The Drawable for the scroll pane's background.
     * @return The GDXScrollPaneStyle instance, enabling method chaining.
     */
    public GDXScrollPaneStyle background(Drawable background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the corner drawable for the scroll pane.
     *
     * @param corner The Drawable for the scroll pane's corner.
     * @return The GDXScrollPaneStyle instance, enabling method chaining.
     */
    public GDXScrollPaneStyle corner(Drawable corner) {
        this.corner = corner;
        return this;
    }

    /**
     * Sets the horizontal scroll bar drawable for the scroll pane.
     *
     * @param hScroll The Drawable for the horizontal scroll bar.
     * @return The GDXScrollPaneStyle instance, enabling method chaining.
     */
    public GDXScrollPaneStyle hScroll(Drawable hScroll) {
        this.hScroll = hScroll;
        return this;
    }

    /**
     * Sets the horizontal scroll knob drawable for the scroll pane.
     *
     * @param hScrollKnob The Drawable for the horizontal scroll knob.
     * @return The GDXScrollPaneStyle instance, enabling method chaining.
     */
    public GDXScrollPaneStyle hScrollKnob(Drawable hScrollKnob) {
        this.hScrollKnob = hScrollKnob;
        return this;
    }

    /**
     * Sets the vertical scroll bar drawable for the scroll pane.
     *
     * @param vScroll The Drawable for the vertical scroll bar.
     * @return The GDXScrollPaneStyle instance, enabling method chaining.
     */
    public GDXScrollPaneStyle vScroll(Drawable vScroll) {
        this.vScroll = vScroll;
        return this;
    }

    /**
     * Sets the vertical scroll knob drawable for the scroll pane.
     *
     * @param vScrollKnob The Drawable for the vertical scroll knob.
     * @return The GDXScrollPaneStyle instance, enabling method chaining.
     */
    public GDXScrollPaneStyle vScrollKnob(Drawable vScrollKnob) {
        this.vScrollKnob = vScrollKnob;
        return this;
    }

    /**
     * Clones this GDXScrollPaneStyle by copying all style variables and returning a new instance.
     *
     * @return a cloned instance of this GDXScrollPaneStyle.
     */
    public GDXScrollPaneStyle copy() {
        GDXScrollPaneStyle style = new GDXScrollPaneStyle();
        style.hScroll = hScroll;
        style.vScroll = vScroll;
        style.hScrollKnob = hScrollKnob;
        style.vScrollKnob = vScrollKnob;
        style.corner = corner;
        style.background = background;
        return style;
    }
}
