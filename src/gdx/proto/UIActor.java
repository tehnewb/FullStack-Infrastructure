package gdx.proto;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import entity.Entity;
import event.EventBus;
import event.EventObserver;
import gdx.proto.events.UIPositionEvent;
import gdx.proto.events.UISizeEvent;
import gdx.proto.events.UIVisbilityEvent;

/**
 * The base class for UI elements in the game.
 * This class serves as a foundation for creating user interface (UI) elements in a game or application.
 * It provides functionality for managing events, positioning, size, visibility, and debug rendering of UI elements.
 */
public abstract class UIActor implements Entity {
    private static final EventBus ActorBus = new EventBus();

    private final Rectangle bounds = new Rectangle();
    private boolean visible = true;
    private boolean debugEnabled;
    private UIActor parent;
    private UIStage stage;
    private int index;

    /**
     * Constructs a new UIActor and registers it with the ActorBus.
     * This constructor is called when a subclass of UIActor is instantiated.
     */
    public UIActor() {
        ActorBus.registerObserver(this);
    }


    /**
     * Posts an event to the ActorBus.
     *
     * @param event The event to be posted.
     */
    public static void postEvent(Object event) {
        ActorBus.post(event);
    }

    /**
     * Posts an event to the ActorBus.
     *
     * @param event The event to be posted.
     */
    public static void postForActor(UIActor actor, Object event) {
        ActorBus.postOnly(actor, event);
    }

    /**
     * Blocks events of a specific type from being processed by the ActorBus.
     *
     * @param type The class representing the type of event to block.
     */
    public static void blockEvent(Class<?> type) {
        ActorBus.blockEvents(type);
    }


    public UIActor getParent() {
        return parent;
    }

    public void setParent(UIActor parent) {
        this.parent = parent;
    }

    /**
     * Draws the UIActor on the screen.
     *
     * @param batch       The batch to draw with.
     * @param parentAlpha The alpha value of the parent actor.
     */
    public abstract void draw(Batch batch, float parentAlpha);

    /**
     * Updates the UIActor's state based on the elapsed time.
     *
     * @param delta The time elapsed since the last update.
     */
    public abstract void update(float delta);

    /**
     * Gets the x-coordinate of the container's bounds.
     *
     * @return The x-coordinate.
     */
    public float getX() {
        return bounds.x;
    }

    /**
     * Sets the x-coordinate of the container's bounds and posts a position change event.
     *
     * @param x The new x-coordinate.
     */
    public void setX(float x) {
        ActorBus.postOnly(this, new UIPositionEvent(getX(), getY(), x, getY()));
    }

    /**
     * Gets the y-coordinate of the container's bounds.
     *
     * @return The y-coordinate.
     */
    public float getY() {
        return bounds.y;
    }

    /**
     * Sets the y-coordinate of the container's bounds and posts a position change event.
     *
     * @param y The new y-coordinate.
     */
    public void setY(float y) {
        ActorBus.postOnly(this, new UIPositionEvent(getX(), getY(), getX(), y));
    }

    /**
     * Sets the position (x, y) of the container's bounds and posts a position change event.
     *
     * @param x The new x-coordinate.
     * @param y The new y-coordinate.
     */
    public void setPosition(float x, float y) {
        ActorBus.postOnly(this, new UIPositionEvent(getX(), getY(), x, y));
    }

    /**
     * Gets the width of the container's bounds.
     *
     * @return The width.
     */
    public float getWidth() {
        return bounds.width;
    }

    /**
     * Sets the width of the container's bounds and posts a size change event.
     *
     * @param width The new width.
     */
    public void setWidth(float width) {
        ActorBus.postOnly(this, new UISizeEvent(getWidth(), getHeight(), width, getHeight()));
    }

    /**
     * Gets the height of the container's bounds.
     *
     * @return The height.
     */
    public float getHeight() {
        return bounds.height;
    }

    /**
     * Sets the height of the container's bounds and posts a size change event.
     *
     * @param height The new height.
     */
    public void setHeight(float height) {
        ActorBus.postOnly(this, new UISizeEvent(getWidth(), getHeight(), getWidth(), height));
    }

    /**
     * Sets the size (width, height) of the container's bounds and posts a size change event.
     *
     * @param width  The new width.
     * @param height The new height.
     */
    public void setSize(float width, float height) {
        ActorBus.postOnly(this, new UISizeEvent(getWidth(), getHeight(), width, height));
    }

    /**
     * Sets the bounds (x, y, width, height) of the container and posts position and size change events.
     *
     * @param x      The new x-coordinate.
     * @param y      The new y-coordinate.
     * @param width  The new width.
     * @param height The new height.
     */
    public void setBounds(float x, float y, float width, float height) {
        ActorBus.postOnly(this, new UIPositionEvent(getX(), getY(), x, y));
        ActorBus.postOnly(this, new UISizeEvent(getWidth(), getHeight(), width, height));
    }

    /**
     * Checks if the UIActor is visible.
     *
     * @return True if the UIActor is visible, false otherwise.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the UIActor.
     *
     * @param visible True to make the UIActor visible, false to hide it.
     */
    public void setVisible(boolean visible) {
        ActorBus.postOnly(this, new UIVisbilityEvent(this, this.visible));
    }

    /**
     * Checks if debug rendering is enabled for the UIActor.
     *
     * @return True if debug rendering is enabled, false otherwise.
     */
    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    /**
     * Sets the debug rendering mode for the UIActor.
     *
     * @param debugEnabled True to enable debug rendering, false to disable it.
     */
    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Handles the position change event for the UIActor.
     * This method is automatically called when the UIActor's position changes.
     *
     * @param event The position change event.
     */
    @EventObserver
    public void positionChanged(UIPositionEvent event) {
        bounds.setPosition(event.newX(), event.newY());
    }

    /**
     * Handles the size change event for the UIActor.
     * This method is automatically called when the UIActor's size changes.
     *
     * @param event The size change event.
     */
    @EventObserver
    public void sizeChanged(UISizeEvent event) {
        bounds.setSize(event.newWidth(), event.newHeight());
    }

    /**
     * Handles the visibility change event for the UIActor.
     * This method is automatically called when the UIActor's visibility changes.
     *
     * @param event The visibility change event.
     */
    @EventObserver
    public void visibilityChanged(UIVisbilityEvent event) {
        event.actor().visible = !event.previousValue();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public UIStage getStage() {
        return stage;
    }

    public void setStage(UIStage stage) {
        this.stage = stage;
    }


}