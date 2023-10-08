package infrastructure.gdx.ui;

/**
 * The `GDXScreen` class defines a contract that must be implemented by all screen classes
 * in a LibGDX-based game. A screen represents a distinct state or view within the game, such as
 * the main menu, gameplay, settings, or other screens.
 * <p>
 * Implementing classes are responsible for rendering, updating, and managing their specific
 * screen's logic and graphics.
 */
public abstract class GDXScreen {

    private GDXGame game;
    private boolean paused;

    /**
     * Called to render the contents of the screen.
     */
    public abstract void render();

    /**
     * Called to update the logic of the screen. This method typically handles game logic,
     * physics calculations, and input processing.
     *
     * @param delta The time elapsed since the last frame, in seconds.
     */
    public abstract void update(float delta);

    /**
     * Called when the screen's size has changed, such as when the game window is resized.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    public abstract void resize(int width, int height);

    /**
     * Called when the screen is first created. This method is used for initializing resources,
     * setting up the initial state, and preparing the screen for rendering and updates.
     */
    public abstract void create();

    /**
     * Called when the screen is being destroyed or switched to another screen. Implementations
     * should release any resources, clean up, and perform necessary cleanup operations.
     */
    public abstract void destroy();

    /**
     * Check if the screen is currently paused.
     *
     * @return True if the screen is paused, false otherwise.
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Set the paused state of the screen. When a screen is paused, its update method will not
     * be called until it is resumed.
     *
     * @param paused True to pause the screen, false to resume it.
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Get the game instance associated with this screen.
     *
     * @return The game instance.
     */
    public GDXGame getGame() {
        return game;
    }

    /**
     * Set the game instance associated with this screen. This allows screens to access and
     * interact with the broader game context.
     *
     * @param game The game instance.
     */
    public void setGame(GDXGame game) {
        this.game = game;
    }
}