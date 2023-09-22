package gdx.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import entity.Tick;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The `GDXGame` class is an implementation of the LibGDX `ApplicationListener` interface,
 * designed to serve as the entry point and main game loop for a LibGDX-based game.
 * <p>
 * It manages the game's screen transitions and provides the core game loop functionality.
 * This class initializes the game, updates the game logic, and renders the game graphics.
 */
public class GDXGame implements ApplicationListener {
    private static final AssetManager Assets = new AssetManager(); // Assets

    private SpriteBatch batch;    // SpriteBatch for rendering 2D graphics
    private boolean paused;       // Flag indicating whether the game is paused
    private GDXScreen screen;     // Current game screen

    /**
     * Retrieves the user-specific application data directory based on the current operating system.
     * On Windows, it uses the APPDATA environment variable. On macOS, it returns "Library/Preferences",
     * and on Linux, it uses the XDG_CONFIG_HOME environment variable. If none of these are available,
     * it falls back to the ".prefs" directory.
     *
     * @return The path to the user's application data directory.
     */
    public static String getUserAppDataDirectory() {
        if (UIUtils.isWindows) {
            String appdata = System.getenv("APPDATA");
            String windir = System.getenv("WINDIR");
            return appdata != null ? appdata // 2000/XP/Vista/7/8/10/11
                    : windir != null ? windir + "/Application Data" // 95/98/Me
                    : ".prefs"; // Default to legacy directory if it's broken

        } else if (UIUtils.isMac) {
            return "Library/Preferences";
        } else if (UIUtils.isLinux) {
            String configHome = System.getenv("XDG_CONFIG_HOME");
            if (configHome != null) {
                Pattern p = Pattern.compile("(?<!\\\\)\\$(\\w+)");
                Matcher m = p.matcher(configHome);
                while (m.find()) {
                    m.reset(configHome = configHome.replaceFirst("\\Q" + m.group() + "\\E", Matcher.quoteReplacement(String.valueOf(System.getenv(m.group(1))))));
                }
            }
            return configHome != null ? configHome : ".config";

        } else return ".prefs";
    }

    /**
     * Retrieves an asset from the game's asset manager by its file name and type.
     *
     * @param fileName The name of the asset file.
     * @param clazz    The class type of the asset.
     * @param <T>      The type of asset to retrieve.
     * @return The loaded asset.
     */
    public static <T> T getAsset(String fileName, Class<T> clazz) {
        return Assets.get(fileName, clazz);
    }

    /**
     * Asynchronously loads an asset of the specified type with the given file name into the game's asset manager.
     * This method queues the asset for loading but does not block the program's execution. The actual loading process
     * may occur in the background.
     *
     * @param fileName The name of the asset file to be loaded.
     * @param type     The class type of the asset to be loaded.
     * @param <T>      The type of asset to be loaded.
     */
    public static <T> void load(String fileName, Class<T> type) {
        Assets.load(fileName, type);
    }

    /**
     * Retrieves the game's asset manager instance, which is responsible for managing and loading game assets
     * such as textures, sounds, and fonts.
     *
     * @return The game's AssetManager instance.
     */
    public static AssetManager getAssets() {
        return Assets;
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
        Assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
        Assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(new InternalFileHandleResolver()));

        this.batch = new SpriteBatch(); // Initialize the SpriteBatch for rendering
    }

    /**
     * The main game loop where rendering and game logic updates occur.
     */
    @Override
    public void render() {
        if (paused)
            return;
        if (screen == null)
            return;

        // Calculate the time elapsed since the last frame
        float delta = Gdx.graphics.getDeltaTime();

        // Render the current screen using the SpriteBatch
        screen.render(batch);

        // Update the game logic with the time delta
        screen.update(delta);

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
        paused = true;
    }

    /**
     * Resumes the game. Called when the game window regains focus or is restored.
     */
    @Override
    public void resume() {
        paused = false;
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