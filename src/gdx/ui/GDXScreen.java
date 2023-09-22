package gdx.ui;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * The `GDXScreen` interface defines a contract that must be implemented by all screen classes
 * in a LibGDX-based game. A screen represents a distinct state or view within the game, such as
 * the main menu, gameplay, settings, or other screens.
 * <p>
 * Implementing classes are responsible for rendering, updating, and managing their specific
 * screen's logic and graphics.
 */
public interface GDXScreen {

    /**
     * Called to render the contents of the screen using the provided Batch.
     *
     * @param batch The Batch used for rendering 2D graphics.
     */
    void render(Batch batch);

    /**
     * Called to update the logic of the screen. This method typically handles game logic,
     * physics calculations, and input processing.
     *
     * @param delta The time elapsed since the last frame, in seconds.
     */
    void update(float delta);

    /**
     * Called when the screen's size has changed, such as when the game window is resized.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    void resize(float width, float height);

    /**
     * Called when the screen is first created. This method is used for initializing resources,
     * setting up the initial state, and preparing the screen for rendering and updates.
     */
    void create();

    /**
     * Called when the screen is being destroyed or switched to another screen. Implementations
     * should release any resources, clean up, and perform necessary cleanup operations.
     */
    void destroy();
}
