package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import infrastructure.gdx.ui.styles.GDXTextFieldStyle;

/**
 * The `GDXTextArea` class extends LibGDX's TextArea class, providing additional functionality and customization options
 * for creating and manipulating text areas within a graphical user interface (GUI).
 */
public class GDXTextArea extends TextArea {

    /**
     * Constructs a `GDXTextArea` with the specified initial text and uses the default text field style defined in `GDXTextFieldStyle.defaults()`.
     *
     * @param text The initial text content of the text area.
     */
    public GDXTextArea(String text) {
        super(text, GDXTextFieldStyle.defaults());
    }

    /**
     * Creates a new `GDXTextArea` instance with the specified initial text and uses the default style.
     *
     * @param text The initial text content of the text area.
     * @return A new `GDXTextArea` instance initialized with the provided text and the default style.
     */
    public static GDXTextArea of(String text) {
        return new GDXTextArea(text);
    }

    /**
     * Sets the style of the text area.
     *
     * @param style The `TextFieldStyle` object defining the visual style of the text area.
     * @return The `GDXTextArea` instance, enabling method chaining.
     */
    public GDXTextArea style(TextFieldStyle style) {
        this.setStyle(style);
        return this;
    }
}
