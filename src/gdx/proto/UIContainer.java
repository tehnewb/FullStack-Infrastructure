package gdx.proto;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import entity.EntityList;
import event.EventObserver;
import gdx.proto.events.UIClickEvent;
import gdx.proto.events.UIDragEvent;
import gdx.proto.events.UIMouseMoveEvent;
import util.Value;

/**
 * UIContainer is a container for UI elements. It can contain multiple UI elements (UICells)
 * and allows for resizing and alignment of its child elements.
 *
 * @author Albert Beaupre
 */
public class UIContainer extends UIActor {

    /**
     * The range around the container's edges within which resizing can be initiated.
     * This value determines the width/height of the resize "hotspots" at the edges of the container.
     */
    private static final int RESIZE_BOUND_RANGE = 10;
    /**
     * The number of resize bounds, representing the four edges of the container.
     * There are four possible directions for resizing: LEFT, RIGHT, TOP, and BOTTOM.
     */
    private static final int NUM_RESIZE_BOUNDS = 4;
    /**
     * The hex values to be masked into the edge variable for resizing the container.
     */
    private static final byte leftEdge = 0x1, rightEdge = 0x2, bottomEdge = 0x4, topEdge = 0x8, borderSize = 8;
    /**
     * An array that holds the UICell objects representing the child elements within the UIContainer.
     * Each UICell controls the layout and positioning of its associated UIActor within the container.
     */
    private final EntityList<UICell> cells = new EntityList<>(10);
    private ShapeRenderer debugRenderer;
    /**
     * A flag indicating whether the UIContainer is resizable.
     * If true, the user can resize the container by dragging its edges.
     */
    private boolean resizable;
    private byte edge;

    /**
     * Adds a UIActor to the UIContainer, creating a UICell for layout control.
     *
     * @param actor The UIActor to add to the container.
     * @return The UICell associated with the added UIActor.
     */
    public UICell add(UIActor actor) {
        UICell cell = new UICell(actor);
        actor.setStage(this.getStage());
        actor.setParent(this);
        cells.add(cell);
        return cell;
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
        for (int i = cells.size() - 1; i >= 0; i--) {
            UIActor actor = cells.getEntity(i).getActor();
            if (actor == null || !actor.isVisible()) continue;

            // Check if the click point is within the actor's bounding box.
            if (actor.getBounds().contains(x, y)) {
                return actor; // Return the actor that was clicked.
            }
        }
        return null; // No actor was found at the specified coordinates.
    }

    /**
     * Draws debug shapes and boundaries to visualize the container and its child elements.
     * This method is called when debug mode is enabled to help developers inspect layout and bounds.
     */
    private void drawDebug() {
        if (debugRenderer == null)
            debugRenderer = new ShapeRenderer();
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Draw children cells and their boundaries
        for (UICell cell : cells) {
            if (cell == null || !cell.getActor().isVisible())
                continue;

            UIActor actor = cell.getActor();

            // Draw padding bounds in blue
            debugRenderer.setColor(Color.BLUE);
            debugRenderer.rect(getX() + actor.getX() - cell.leftPadding, getY() + actor.getY() - cell.bottomPadding,
                    actor.getWidth() + cell.rightPadding + cell.leftPadding,
                    actor.getHeight() + cell.topPadding + cell.topPadding);

            // Draw actor bounds in green
            debugRenderer.setColor(Color.GREEN);
            debugRenderer.rect(getX() + actor.getX(), getY() + actor.getY(), actor.getWidth(), actor.getHeight());

            // Draw resize bounds in red (if resizable)
            debugRenderer.setColor(Color.RED);
        }
        debugRenderer.end();
    }

    /**
     * Draws the child UI elements contained within the container.
     *
     * @param batch       The Batch used for rendering.
     * @param parentAlpha The alpha value to apply to the children during rendering.
     */
    private void drawCells(Batch batch, float parentAlpha) {
        for (UICell cell : cells) {
            if (cell == null || !cell.getActor().isVisible())
                continue;

            cell.getActor().draw(batch, parentAlpha);
        }
    }

    private void updateCells(float delta) {
        for (UICell cell : cells) {
            if (cell == null || !cell.getActor().isVisible())
                continue;

            cell.getActor().update(delta);
        }
    }

    /**
     * Draws the UIContainer and its child elements.
     *
     * @param batch       The Batch used for rendering.
     * @param parentAlpha The alpha value to apply to the container and its children during rendering.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (!cells.isEmpty()) drawCells(batch, parentAlpha);
    }

    @Override
    public void update(float delta) {
        updateCells(delta);
        updateChanges();
        if (this.isDebugEnabled()) drawDebug();
    }


    /**
     * Updates the changes made to the container and its children.
     */
    private void updateChanges() {
        for (UICell cell : cells) {
            if (cell == null)
                continue;

            UIActor actor = cell.getActor();
            if (cell.alignment != null) {
                Vector2 alignmentPosition = cell.alignment.calculatePosition(this.getBounds(), actor.getBounds());
                actor.setPosition(alignmentPosition.x, alignmentPosition.y);
            }

            if (cell.snapLeft) {
                float previousX = actor.getX();
                actor.setX(cell.leftPadding);
                actor.setWidth(actor.getWidth() + (previousX - actor.getX()));
            }
            if (cell.snapDown) {
                float previousY = actor.getY();
                actor.setY(cell.bottomPadding);
                actor.setHeight(actor.getHeight() + (previousY - actor.getY()));
            }

            if (cell.snapRight)
                actor.setWidth(getWidth() - actor.getX() - cell.rightPadding);
            if (cell.snapUp)
                actor.setHeight(getHeight() - actor.getY() - cell.topPadding);

            float clampedWidth = MathUtils.clamp(actor.getWidth(), 0, getWidth());
            float clampedHeight = MathUtils.clamp(actor.getHeight(), 0, getHeight());

            if (getWidth() > cell.minWidth && actor.getWidth() < cell.minWidth)
                clampedWidth = cell.minWidth;
            if (getHeight() > cell.minHeight && actor.getHeight() < cell.minHeight)
                clampedHeight = cell.minHeight;
            if (cell.maxWidth > 0 && getWidth() > cell.maxWidth)
                clampedWidth = cell.maxWidth;
            if (cell.maxHeight > 0 && getHeight() > cell.maxHeight)
                clampedHeight = cell.maxHeight;
            actor.setSize(clampedWidth, clampedHeight);

            float clampedX = MathUtils.clamp(actor.getX(), cell.leftPadding, getWidth() - actor.getWidth() - cell.rightPadding);
            float clampedY = MathUtils.clamp(actor.getY(), cell.bottomPadding, getHeight() - actor.getHeight() - cell.topPadding);

            actor.setPosition(clampedX, clampedY);
        }
    }

    @EventObserver
    public void processClick(UIClickEvent event) {
        if (event.stage().equals(UIClickEvent.ClickStage.DOWN)) {
            float width = getWidth(), height = getHeight();
            float x = event.x(), y = event.y();
            float bdr = borderSize;

            UIActor actor = getActorAt(x - getX(), y - getY());
            if (actor != null) {
                getStage().setFocused(actor);
                return;
            }
            edge = 0;
            if (Value.of(x).within(getX() - bdr, getX() + width + bdr) && Value.of(y).within(getY() - bdr, getY() + height + bdr)) {
                if (Value.of(x).within(getX() - bdr, getX() + bdr)) edge |= leftEdge;
                if (Value.of(x).within(getX() + width - bdr, getX() + width + bdr)) edge |= rightEdge;
                if (Value.of(y).within(getY() - bdr, getY() + bdr)) edge |= bottomEdge;
                if (Value.of(y).within(getY() + height - bdr, getY() + height + bdr)) edge |= topEdge;
            }
        }
    }

    @EventObserver
    public void processMouseMove(UIMouseMoveEvent event) {
        float mx = event.screenX();
        float my = event.screenY();

    }

    @EventObserver
    public void processDrag(UIDragEvent event) {
        float deltaX = event.deltaX();
        float deltaY = event.deltaY();
        float limit = borderSize * 2;
        if ((edge & leftEdge) != 0) {
            if (getX() + deltaX > 0 && getWidth() - deltaX > 0 && getWidth() - deltaX > limit) {
                setX(getX() + deltaX);
                setWidth(getWidth() - deltaX);
            }
        }
        if ((edge & rightEdge) != 0) {
            if (getWidth() + deltaX > limit) {
                setWidth(getWidth() + deltaX);
            }
        }
        if ((edge & bottomEdge) != 0) {
            if (getY() + deltaY > 0 && getHeight() - deltaY > 0 && getHeight() - deltaY > limit) {
                setY(getY() + deltaY);
                setHeight(getHeight() - deltaY);
            }
        }
        if ((edge & topEdge) != 0) {
            if (getHeight() + deltaY > limit) {
                setHeight(getHeight() + deltaY);
            }
        }
    }

    /**
     * Checks if the UIContainer is resizable.
     *
     * @return True if resizable, false otherwise.
     */
    public boolean isResizable() {
        return resizable;
    }

    /**
     * Sets whether the UIContainer is resizable.
     *
     * @param resizable True to make the container resizable, false otherwise.
     */
    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

}
