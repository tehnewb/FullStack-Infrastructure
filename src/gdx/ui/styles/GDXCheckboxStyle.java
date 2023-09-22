package gdx.ui.styles;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * The GDXCheckboxStyle class extends LibGDX's CheckBox.CheckBoxStyle, providing customization options
 * for the appearance of checkbox widgets. It allows you to specify different drawables for various
 * states of the checkbox, such as checked (on), unchecked (off), hover, and disabled states.
 */
public class GDXCheckboxStyle extends CheckBox.CheckBoxStyle {

    /**
     * Creates a new GDXCheckboxStyle instance with default settings.
     *
     * @return A new GDXCheckboxStyle instance with default drawable settings.
     */
    public static GDXCheckboxStyle defaults() {
        return new GDXCheckboxStyle();
    }

    /**
     * Sets the drawable for the checkbox when it is checked (on).
     *
     * @param checkboxOn The Drawable for the checked (on) state.
     * @return The GDXCheckboxStyle instance, enabling method chaining.
     */
    public GDXCheckboxStyle checkboxOn(Drawable checkboxOn) {
        this.checkboxOn = checkboxOn;
        return this;
    }

    /**
     * Sets the drawable for the checkbox when it is unchecked (off).
     *
     * @param checkboxOff The Drawable for the unchecked (off) state.
     * @return The GDXCheckboxStyle instance, enabling method chaining.
     */
    public GDXCheckboxStyle checkboxOff(Drawable checkboxOff) {
        this.checkboxOff = checkboxOff;
        return this;
    }

    /**
     * Sets the drawable for the checkbox when it is checked (on) and hovered over.
     *
     * @param checkboxOnOver The Drawable for the checked (on) and hover state.
     * @return The GDXCheckboxStyle instance, enabling method chaining.
     */
    public GDXCheckboxStyle checkboxOnOver(Drawable checkboxOnOver) {
        this.checkboxOnOver = checkboxOnOver;
        return this;
    }

    /**
     * Sets the drawable for the checkbox when it is unchecked (off) and hovered over.
     *
     * @param checkboxOver The Drawable for the unchecked (off) and hover state.
     * @return The GDXCheckboxStyle instance, enabling method chaining.
     */
    public GDXCheckboxStyle checkboxOver(Drawable checkboxOver) {
        this.checkboxOver = checkboxOver;
        return this;
    }

    /**
     * Sets the drawable for the checkbox when it is checked (on) but disabled.
     *
     * @param checkboxOnDisabled The Drawable for the checked (on) and disabled state.
     * @return The GDXCheckboxStyle instance, enabling method chaining.
     */
    public GDXCheckboxStyle checkboxOnDisabled(Drawable checkboxOnDisabled) {
        this.checkboxOnDisabled = checkboxOnDisabled;
        return this;
    }

    /**
     * Sets the drawable for the checkbox when it is unchecked (off) but disabled.
     *
     * @param checkboxOffDisabled The Drawable for the unchecked (off) and disabled state.
     * @return The GDXCheckboxStyle instance, enabling method chaining.
     */
    public GDXCheckboxStyle checkboxOffDisabled(Drawable checkboxOffDisabled) {
        this.checkboxOffDisabled = checkboxOffDisabled;
        return this;
    }
}
