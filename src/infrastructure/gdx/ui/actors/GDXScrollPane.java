package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import infrastructure.gdx.ui.styles.GDXScrollPaneStyle;

/**
 * The `GDXScrollPane` class extends LibGDX's ScrollPane class, providing additional functionality and customization options
 * for creating and manipulating scroll panes within a graphical user interface (GUI).
 */
public class GDXScrollPane extends ScrollPane {

    /**
     * Constructs a `GDXScrollPane` with the specified content actor.
     *
     * @param actor The content actor displayed within the scroll pane.
     */
    public GDXScrollPane(Actor actor) {
        super(actor, GDXScrollPaneStyle.defaults());
    }

    /**
     * Creates a new `GDXScrollPane` instance with the specified content actor.
     *
     * @param actor The content actor displayed within the scroll pane.
     * @return A new `GDXScrollPane` instance initialized with the provided content actor.
     */
    public static GDXScrollPane of(Actor actor) {
        return new GDXScrollPane(actor);
    }

    /**
     * Sets the style of the scroll pane.
     *
     * @param style The `ScrollPaneStyle` object defining the visual style of the scroll pane.
     * @return The `GDXScrollPane` instance, enabling method chaining.
     */
    public GDXScrollPane style(GDXScrollPaneStyle style) {
        this.setStyle(style);
        return this;
    }
}
