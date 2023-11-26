package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import infrastructure.gdx.ui.styles.GDXSplitPaneStyle;

/**
 * The `GDXSplitPane` class extends LibGDX's SplitPane class, providing additional functionality and customization options
 * for creating and manipulating split panes within a graphical user interface (GUI).
 */
public class GDXSplitPane extends SplitPane {

    /**
     * Constructs a `GDXSplitPane` with the specified first and second widgets, orientation, and uses the default split pane style defined in `GDXSplitPaneStyle.defaults()`.
     *
     * @param firstWidget  The widget displayed on the first side of the split pane.
     * @param secondWidget The widget displayed on the second side of the split pane.
     * @param vertical     `true` if the split pane is vertical, `false` for horizontal.
     */
    public GDXSplitPane(Actor firstWidget, Actor secondWidget, boolean vertical) {
        super(firstWidget, secondWidget, vertical, GDXSplitPaneStyle.defaults());
    }

    /**
     * Creates a new `GDXSplitPane` instance with the specified first and second widgets, orientation, and uses the default style.
     *
     * @param firstWidget  The widget displayed on the first side of the split pane.
     * @param secondWidget The widget displayed on the second side of the split pane.
     * @param vertical     `true` if the split pane is vertical, `false` for horizontal.
     * @return A new `GDXSplitPane` instance initialized with the provided widgets, orientation, and the default style.
     */
    public static GDXSplitPane of(Actor firstWidget, Actor secondWidget, boolean vertical) {
        return new GDXSplitPane(firstWidget, secondWidget, vertical);
    }

    /**
     * Sets the style of the split pane.
     *
     * @param style The `SplitPaneStyle` object defining the visual style of the split pane.
     * @return The `GDXSplitPane` instance, enabling method chaining.
     */
    public GDXSplitPane style(GDXSplitPaneStyle style) {
        this.setStyle(style);
        return this;
    }
}
