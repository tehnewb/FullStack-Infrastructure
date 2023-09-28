package infrastructure.gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXTreeStyle class extends LibGDX's Tree.TreeStyle, providing customization options
 * for the appearance of trees. It allows you to specify properties such as expand/collapse icons,
 * over icons, selection drawables, and background drawables.
 *
 * @author Albert Beaupre
 */
public class GDXTreeStyle extends Tree.TreeStyle {
    private static GDXTreeStyle defaults = new GDXTreeStyle();

    /**
     * Returns the current GDXTreeStyle instance with default settings.
     *
     * @return A new GDXTreeStyle instance with default settings.
     */
    public static GDXTreeStyle defaults() {
        return defaults;
    }

    /**
     * Sets the GDXTreeStyle instance with default settings.
     *
     * @param style The GDXTreeStyle instance with default settings.
     */
    public static void defaults(GDXTreeStyle style) {
        defaults = style;
    }

    /**
     * Sets the expand icon drawable for tree nodes.
     *
     * @param plus The Drawable for the expand icon.
     * @return The GDXTreeStyle instance, enabling method chaining.
     */
    public GDXTreeStyle plus(Drawable plus) {
        this.plus = plus;
        return this;
    }

    /**
     * Sets the collapse icon drawable for tree nodes.
     *
     * @param minus The Drawable for the collapse icon.
     * @return The GDXTreeStyle instance, enabling method chaining.
     */
    public GDXTreeStyle minus(Drawable minus) {
        this.minus = minus;
        return this;
    }

    /**
     * Sets the expand icon drawable for tree nodes when hovered over.
     *
     * @param plusOver The Drawable for the expand icon when hovered over.
     * @return The GDXTreeStyle instance, enabling method chaining.
     */
    public GDXTreeStyle plusOver(Drawable plusOver) {
        this.plusOver = plusOver;
        return this;
    }

    /**
     * Sets the collapse icon drawable for tree nodes when hovered over.
     *
     * @param minusOver The Drawable for the collapse icon when hovered over.
     * @return The GDXTreeStyle instance, enabling method chaining.
     */
    public GDXTreeStyle minusOver(Drawable minusOver) {
        this.minusOver = minusOver;
        return this;
    }

    /**
     * Sets the drawable for the tree node when it is hovered over.
     *
     * @param over The Drawable for the tree node when hovered over.
     * @return The GDXTreeStyle instance, enabling method chaining.
     */
    public GDXTreeStyle over(Drawable over) {
        this.over = over;
        return this;
    }

    /**
     * Sets the drawable for the selected tree node.
     *
     * @param selection The Drawable for the selected tree node.
     * @return The GDXTreeStyle instance, enabling method chaining.
     */
    public GDXTreeStyle selection(Drawable selection) {
        this.selection = selection;
        return this;
    }

    /**
     * Sets the background drawable for the tree.
     *
     * @param background The Drawable for the tree's background.
     * @return The GDXTreeStyle instance, enabling method chaining.
     */
    public GDXTreeStyle background(Drawable background) {
        this.background = background;
        return this;
    }
}
