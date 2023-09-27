package infrastructure.gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import infrastructure.gdx.ui.styles.GDXTextFieldStyle;

/**
 * The `GDXTextField` class extends LibGDX's TextField class, providing additional functionality and customization options
 * for creating and manipulating text fields within a graphical user interface (GUI).
 */
public class GDXTextField extends TextField {

    /**
     * Constructs a `GDXTextField` with the specified initial text and uses the default text field style defined in `GDXTextFieldStyle.defaults()`.
     *
     * @param text The initial text content of the text field.
     */
    public GDXTextField(String text) {
        super(text, GDXTextFieldStyle.defaults());
    }

    /**
     * Creates a new `GDXTextField` instance with the specified initial text and uses the default style.
     *
     * @param text The initial text content of the text field.
     * @return A new `GDXTextField` instance initialized with the provided text and the default style.
     */
    public static GDXTextField of(String text) {
        return new GDXTextField(text);
    }

    /**
     * Creates a new `GDXTextField` instance configured as a password field, where characters are hidden using a specified password character.
     *
     * @param passwordCharacter The character to use for masking the input in the password field.
     * @return A new `GDXTextField` instance initialized as a password field with the given password character.
     */
    public static GDXTextField passwordField(char passwordCharacter) {
        return of("").asPassword().passwordCharacter(passwordCharacter);
    }

    /**
     * Creates a new `GDXTextField` instance with an initial message displayed as a hint.
     *
     * @param message The message to display as a hint in the text field.
     * @return A new `GDXTextField` instance initialized with the provided message as a hint.
     */
    public static GDXTextField messageField(String message) {
        return of("").message(message);
    }

    /**
     * Sets the visual style of the text field.
     *
     * @param style The `GDXTextFieldStyle` object defining the visual style of the text field.
     * @return The `GDXTextField` instance, enabling method chaining.
     */
    public GDXTextField style(GDXTextFieldStyle style) {
        this.setStyle(style);
        return this;
    }

    /**
     * Sets the character to use for masking input in a password field.
     *
     * @param c The character to use for masking input in a password field.
     * @return The `GDXTextField` instance, enabling method chaining.
     */
    public GDXTextField passwordCharacter(char c) {
        this.setPasswordCharacter(c);
        return this;
    }

    /**
     * Configures the text field as a password field, where characters are hidden.
     *
     * @return The `GDXTextField` instance, enabling method chaining.
     */
    public GDXTextField asPassword() {
        this.setPasswordMode(true);
        return this;
    }

    /**
     * Sets a message to be displayed as a hint within the text field when it is empty.
     *
     * @param text The message text to display as a hint.
     * @return The `GDXTextField` instance, enabling method chaining.
     */
    public GDXTextField message(String text) {
        this.setMessageText(text);
        return this;
    }
}