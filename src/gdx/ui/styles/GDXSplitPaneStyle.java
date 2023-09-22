package gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXSplitPaneStyle class extends LibGDX's SplitPane.SplitPaneStyle, providing customization options
 * for the appearance of split panes. It allows you to specify the handle drawable used for split panes.
 */
public class GDXSplitPaneStyle extends SplitPane.SplitPaneStyle {

    /**
     * Creates a new GDXSplitPaneStyle instance with default settings.
     *
     * @return A new GDXSplitPaneStyle instance with default style properties.
     */
    public static GDXSplitPaneStyle defaults() {
        return new GDXSplitPaneStyle();
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
}
