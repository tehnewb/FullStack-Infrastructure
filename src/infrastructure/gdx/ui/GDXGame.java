package infrastructure.gdx.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import infrastructure.cache.Cache;
import infrastructure.entity.Tick;
import infrastructure.io.compress.CompressionStrategy;

/**
 * The `GDXGame` class is an implementation of the LibGDX `ApplicationListener` interface,
 * designed to serve as the entry point and main game loop for a LibGDX-based game.
 * It manages the game's screen transitions and provides the core game loop functionality.
 * This class initializes the game, updates the game logic, and renders the game graphics.
 *
 * @author Albert Beaupre
 */
public class GDXGame implements ApplicationListener {
    private static final Cache cache = new Cache(); // Cached assets
    private SpriteBatch batch;    // SpriteBatch for rendering 2D graphics
    private GDXScreen screen;     // Current game screen

    /**
     * Constructs a new `GDXGame` instance with an initial screen.
     *
     * @param initialScreen The initial game screen to set.
     * @throws NullPointerException if the provided screen is null.
     */
    public GDXGame(GDXScreen initialScreen) {
        if (initialScreen == null)
            throw new NullPointerException("GDXGame initial screen cannot be NULL");
        this.screen = initialScreen;
    }

    /**
     * Retrieves the game's cache instance, which is responsible for caching assets.
     *
     * @return The game's Cache instance.
     */
    public static Cache getCache() {
        return cache;
    }

    /**
     * Loads the game's cache from a specified location.
     *
     * @param cacheLocation The location of the cache data to load.
     */
    public static void loadCache(String cacheLocation) {
        FileHandle handle = Gdx.files.internal(cacheLocation);
        byte[] data = new byte[(int) handle.length()];
        handle.readBytes(data, 0, data.length);
        Thread thread = new Thread(() -> cache.decompress(CompressionStrategy.GZIP, data.clone()));
        thread.start(); // Cache doesn't need to be loaded on same libgdx thread
    }

    /**
     * Sets the current game screen.
     *
     * @param screen The GDXScreen to set as the current screen.
     * @throws NullPointerException if the provided screen is null.
     */
    public void setScreen(GDXScreen screen) {
        if (screen == null)
            throw new NullPointerException("Cannot set NULL GDXScreen");

        // Destroy the previous screen (if any)
        if (this.screen != null) this.screen.destroy();

        // Set the new screen and initialize it
        this.screen = screen;
        this.screen.create();
        this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Initializes the game when it is first created.
     */
    @Override
    public void create() {
        this.batch = new SpriteBatch(); // Initialize the SpriteBatch for rendering

        this.screen.create();
    }

    /**
     * The main game loop where rendering and game logic updates occur.
     */
    @Override
    public void render() {
        ScreenUtils.clear(Color.GRAY);

        if (screen == null)
            return;

        if (screen.isPaused())
            return;

        // Calculate the time elapsed since the last frame
        float delta = Gdx.graphics.getDeltaTime();

        // Update the game logic with the time delta
        screen.update(delta);

        // Render the current screen using the SpriteBatch
        batch.begin();
        screen.render(batch);
        batch.end();

        // Update any tick-based game components
        Tick.updateTicks(delta);
    }

    /**
     * Called when the game window is resized.
     *
     * @param width  The new width of the window.
     * @param height The new height of the window.
     */
    @Override
    public void resize(int width, int height) {
        if (screen == null)
            return;
        screen.resize(width, height);
    }

    /**
     * Pauses the game. Called when the game window loses focus or is minimized.
     */
    @Override
    public void pause() {
        if (screen == null)
            return;
        screen.setPaused(true);
    }

    /**
     * Resumes the game. Called when the game window regains focus or is restored.
     */
    @Override
    public void resume() {
        if (screen == null)
            return;
        screen.setPaused(false);
    }

    /**
     * Disposes of any resources used by the game when it is closed or terminated.
     */
    @Override
    public void dispose() {
        if (screen == null)
            return;
        screen.destroy();
    }
}