package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import infrastructure.gdx.ui.styles.GDXTreeStyle;

/**
 * The `GDXTree` class extends LibGDX's Tree class, providing additional functionality and customization options
 * for creating and manipulating tree widgets within a graphical user interface (GUI).
 *
 * @param <N> The type of nodes in the tree.
 * @param <V> The type of values associated with the nodes.
 */
public class GDXTree<N extends Tree.Node, V> extends Tree<N, V> {

    /**
     * Constructs a `GDXTree` with the default tree style defined in `GDXTreeStyle.defaults()`.
     */
    public GDXTree() {
        super(GDXTreeStyle.defaults());
    }

    /**
     * Creates a new `GDXTree` instance with the default style.
     *
     * @param <N> The type of nodes in the tree.
     * @param <V> The type of values associated with the nodes.
     * @return A new `GDXTree` instance initialized with the default style.
     */
    public static <N extends Tree.Node, V> GDXTree<N, V> of() {
        return new GDXTree<N, V>();
    }

    /**
     * Sets the style of the tree.
     *
     * @param style The `TreeStyle` object defining the visual style of the tree.
     * @return The `GDXTree` instance, enabling method chaining.
     */
    public GDXTree<N, V> style(GDXTreeStyle style) {
        this.setStyle(style);
        return this;
    }
}
