package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXSplitPaneStyle class extends LibGDX's SplitPane.SplitPaneStyle, providing customization options
 * for the appearance of split panes. It allows you to specify the handle drawable used for split panes.
 *
 * @author Albert Beaupre
 */
public class GDXSplitPaneStyle extends SplitPane.SplitPaneStyle {
    private static GDXSplitPaneStyle defaults = new GDXSplitPaneStyle();

    /**
     * Returns the current GDXSplitPaneStyle instance with default settings.
     *
     * @return A new GDXSplitPaneStyle instance with default settings.
     */
    public static GDXSplitPaneStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXSplitPaneStyle instance with default settings.
     *
     * @param style The GDXSplitPaneStyle instance with default settings.
     */
    public static void defaults(GDXSplitPaneStyle style) {
        defaults = style;
    }

    /**
     * Sets the handle drawable for the split pane.
     *
     * @param handle The Drawable for the split pane's handle.
     * @return The GDXSplitPaneStyle instance, enabling method chaining.
     */
    public GDXSplitPaneStyle handle(Drawable handle) {
        this.handle = handle;
        return this;
    }

    /**
     * Clones this GDXSplitPaneStyle by copying all style variables and returning a new instance.
     *
     * @return a cloned instance of this GDXSplitPaneStyle.
     */
    public GDXSplitPaneStyle copy() {
        GDXSplitPaneStyle style = new GDXSplitPaneStyle();
        style.handle = handle;
        return style;
    }
}
