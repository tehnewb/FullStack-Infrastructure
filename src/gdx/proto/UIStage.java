package gdx.proto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import entity.EntityList;
import gdx.proto.events.*;

/**
 * UIStage is a component of a LibGDX application designed to manage and render UI elements.
 * It provides a convenient way to organize and display UIActor instances within a specific viewport.
 * UIStage also implements the InputProcessor interface, allowing it to handle user input events.
 */
public class UIStage implements InputProcessor {

    // A collection of UIActor instances managed by this UIStage.
    private final EntityList<UIActor> actors = new EntityList<>(10);

    // The batch used for rendering UI elements.
    private final Batch batch;
    private final ShapeRenderer debugRenderer;
    // A temporary vector used for coordinate conversions.
    private final Vector2 tempVector = new Vector2();
    private final Vector2 dragVector = new Vector2();
    // The viewport associated with this UIStage.
    private final Viewport viewport;
    // The alpha value used for transparency when rendering UI elements.
    private float alpha;
    // The UIActor that has keyboard focus.
    private UIActor keyFocused;
    // The UIActor that currently has input focus.
    private UIActor focused;

    /**
     * Creates a new UIStage with the specified viewport.
     *
     * @param viewport The Viewport that defines the screen area where UI elements will be displayed.
     */
    public UIStage(Viewport viewport) {
        // Initialize the rendering batch for UI elements.
        this.batch = new SpriteBatch();
        this.debugRenderer = new ShapeRenderer();
        this.viewport = viewport;

        // Ensure that the viewport is updated to match the screen size.
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    /**
     * Renders all visible UIActor instances within the UIStage.
     * This method sets up the rendering environment, updates the camera, and then draws the UI elements.
     */
    public void draw() {
        // Retrieve the camera associated with the viewport and update it.
        Camera camera = viewport.getCamera();
        camera.update();

        // Set the projection matrix for the rendering batch.
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Iterate through all UIActor instances and render them if they are visible.
        for (UIActor actor : actors) {
            if (actor == null) continue;

            if (actor.isVisible()) {
                actor.draw(batch, alpha);
            }
        }
        batch.end();


        debugRenderer.setProjectionMatrix(camera.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (UIActor actor : actors) {
            if (actor == null) continue;

            if (actor.isDebugEnabled()) {
                Rectangle bounds = actor.getBounds();
                debugRenderer.setColor(Color.GREEN);
                debugRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        }

        if (focused != null && focused.isDebugEnabled()) {
            float x = focused.getX();
            float y = focused.getY();
            Rectangle bounds = focused.getBounds();
            if (focused.getParent() != null) {
                x += focused.getParent().getX();
                y += focused.getParent().getY();
            }
            debugRenderer.setColor(Color.BLUE);
            debugRenderer.rect(x - 5, y - 5, bounds.width + 10, bounds.height + 10);
        }
        debugRenderer.end();
    }

    /**
     * Updates all visible UIActor instances within the UIStage.
     *
     * @param delta The time elapsed since the last frame, in seconds.
     */
    public void update(float delta) {
        // Iterate through all UIActor instances and update them if they are visible.
        for (UIActor actor : actors) {
            if (actor == null) continue;

            if (actor.isVisible()) {
                actor.update(delta);
            }
        }
    }

    /**
     * Adds a UIActor to the UIStage.
     *
     * @param actor The UIActor to be added. Must not be null.
     * @throws NullPointerException if the provided actor is null.
     */
    public void add(UIActor actor) {
        if (actor == null)
            throw new NullPointerException("Cannot add NULL actor to stage");
        this.actors.add(actor);
        actor.setStage(this);
    }

    /**
     * Returns the UIActor that was clicked at the specified screen coordinates.
     *
     * @param x The x-coordinate of the click on the screen.
     * @param y The y-coordinate of the click on the screen.
     * @return The UIActor, which was clicked, or null if no actor was found at the given coordinates.
     */
    public UIActor getActorAt(float x, float y) {
        // Iterate through all UIActor instances and check if the click point is within their bounds.
        for (int i = actors.size() - 1; i >= 0; i--) {
            UIActor actor = actors.getEntity(i);
            if (actor == null || !actor.isVisible()) continue;

            // Check if the click point is within the actor's bounding box.
            if (actor.getBounds().contains(x, y)) {
                return actor; // Return the actor that was clicked.
            }
        }
        return null; // No actor was found at the specified coordinates.
    }

    @Override
    public boolean keyDown(int keyCode) {
        // Post a UIKeyEvent for key press event to the focused UIActor.
        if (keyFocused == null)
            return false;
        UIActor.postForActor(keyFocused, new UIKeyEvent(keyFocused, UIKeyEvent.KeyStage.DOWN, keyCode));
        return true;
    }

    @Override
    public boolean keyUp(int keyCode) {
        // Post a UIKeyEvent for key release event to the focused UIActor.
        if (keyFocused == null)
            return false;
        UIActor.postForActor(keyFocused, new UIKeyEvent(keyFocused, UIKeyEvent.KeyStage.UP, keyCode));
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        // Post a UIKeyEvent for key typed event to the focused UIActor.
        if (keyFocused == null)
            return false;
        UIActor.postForActor(keyFocused, new UIKeyEvent(keyFocused, UIKeyEvent.KeyStage.TYPED, character));
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Determine which UIActor was clicked and post a UIClickEvent for touch-down event.
        this.viewport.unproject(tempVector.set(screenX, screenY));
        this.dragVector.set(tempVector.x, tempVector.y);
        this.focused = this.getActorAt(tempVector.x, tempVector.y);
        if (focused == null)
            return false;
        UIActor.postForActor(focused, new UIClickEvent(UIClickEvent.ClickStage.DOWN, pointer, button, tempVector.x, tempVector.y));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Post a UIClickEvent for touch-up event to the currently focused UIActor.
        this.viewport.unproject(tempVector.set(screenX, screenY));
        if (focused == null)
            return false;
        UIActor.postForActor(focused, new UIClickEvent(UIClickEvent.ClickStage.UP, pointer, button, tempVector.x, tempVector.y));
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        this.viewport.unproject(tempVector.set(screenX, screenY));
        float deltaX = tempVector.x - dragVector.x;
        float deltaY = tempVector.y - dragVector.y;
        this.dragVector.set(tempVector.x, tempVector.y);
        if (focused == null)
            return false;
        UIActor.postForActor(focused, new UIDragEvent(deltaX, deltaY));
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        viewport.unproject(tempVector.set(screenX, screenY));
        UIActor actor = this.getActorAt(tempVector.x, tempVector.y);
        if (actor == null)
            return false;
        UIActor.postForActor(actor, new UIMouseMoveEvent(tempVector.x, tempVector.y));
        return true;
    }

    @Override
    public boolean scrolled(float deltaX, float deltaY) {
        if (focused == null)
            return false;
        UIActor.postForActor(focused, new UIScrollEvent(deltaX, deltaY));
        return true;
    }

    /**
     * Retrieves the viewport associated with this UIStage.
     *
     * @return The viewport used for rendering UI elements.
     */
    public Viewport getViewport() {
        return viewport;
    }

    /**
     * Retrieves the debug renderer used for drawing debug shapes around UI elements.
     *
     * @return The ShapeRenderer used for debugging UI elements.
     */
    public ShapeRenderer getDebugRenderer() {
        return debugRenderer;
    }

    /**
     * Retrieves the batch used for rendering UI elements.
     *
     * @return The Batch used for rendering UI elements.
     */
    public Batch getBatch() {
        return batch;
    }

    /**
     * Retrieves the UIActor that currently has keyboard focus.
     *
     * @return The UIActor with keyboard focus, or null if no actor has focus.
     */
    public UIActor getKeyFocused() {
        return keyFocused;
    }

    /**
     * Retrieves the UIActor that currently has input focus.
     *
     * @return The UIActor with input focus, or null if no actor has focus.
     */
    public UIActor getFocused() {
        return focused;
    }

    public void setFocused(UIActor focused) {
        this.focused = focused;
    }
}