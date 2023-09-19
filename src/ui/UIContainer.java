package ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import util.Value;

import java.util.Arrays;

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
     * An array that holds the UICell objects representing the child elements within the UIContainer.
     * Each UICell controls the layout and positioning of its associated UIActor within the container.
     */
    private final Array<UICell> cells = new Array<>();

    /**
     * A ShapeRenderer used for rendering debug shapes (e.g., borders and resize boundaries).
     * This renderer is initialized when debug mode is enabled to visualize container and child bounds.
     */
    private ShapeRenderer shapeRenderer;

    /**
     * An array of Rectangle objects representing the resize boundaries at the edges of the container.
     * These boundaries define the areas where the user can initiate resizing of the container.
     */
    private Rectangle[] resizeBounds;

    /**
     * An array of boolean values indicating whether each of the four resize boundaries is currently being dragged/resized.
     * Each element corresponds to a direction: [LEFT, RIGHT, TOP, BOTTOM].
     */
    private boolean[] resizing;

    /**
     * A flag indicating whether the container and its children have been updated due to changes in layout or size.
     * When this flag is false, the container and children will be updated to reflect any layout or size changes.
     */
    private boolean hasUpdatedCells;

    /**
     * A flag indicating whether the UIContainer is resizable.
     * If true, the user can resize the container by dragging its edges.
     */
    private boolean resizable;

    /**
     * The x-coordinate where the dragging/resizing of the container started.
     * This value is used to calculate the change in position during resizing.
     */
    private float dragStartX;

    /**
     * The y-coordinate where the dragging/resizing of the container started.
     * This value is used to calculate the change in position during resizing.
     */
    private float dragStartY;


    /**
     * Creates a new UIContainer.
     */
    public UIContainer() {
        this.setTouchable(Touchable.enabled);
    }

    /**
     * Adds a UIActor to the UIContainer, creating a UICell for layout control.
     *
     * @param actor The UIActor to add to the container.
     * @return The UICell associated with the added UIActor.
     */
    public UICell add(UIActor actor) {
        UICell cell = new UICell(actor);
        cells.add(cell);
        return cell;
    }

    /**
     * Draws debug shapes and boundaries to visualize the container and its child elements.
     * This method is called when debug mode is enabled to help developers inspect layout and bounds.
     */
    private void drawDebug() {
        if (shapeRenderer == null)
            shapeRenderer = new ShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Draw container bounds in red
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());

        // Draw children cells and their boundaries
        for (UICell cell : cells) {
            if (cell == null || !cell.getActor().isVisible())
                continue;

            UIActor actor = cell.getActor();

            // Draw padding bounds in blue
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(getX() + actor.getX() - cell.leftPadding, getY() + actor.getY() - cell.bottomPadding,
                    actor.getWidth() + cell.rightPadding + cell.leftPadding,
                    actor.getHeight() + cell.topPadding + cell.topPadding);

            // Draw actor bounds in green
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(getX() + actor.getX(), getY() + actor.getY(), actor.getWidth(), actor.getHeight());

            // Draw resize bounds in red (if resizable)
            shapeRenderer.setColor(Color.RED);
        }
        if (resizable) {
            for (Rectangle r : resizeBounds) {
                shapeRenderer.rect(r.x, r.y, r.width, r.height);
            }
        }
        shapeRenderer.end();
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

    /**
     * Draws the UIContainer and its child elements.
     *
     * @param batch       The Batch used for rendering.
     * @param parentAlpha The alpha value to apply to the container and its children during rendering.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isVisible()) return;

        if (!hasUpdatedCells) updateChanges();
        if (getDebug()) drawDebug();
        if (resizable) calculateResizing();
        if (cells.size > 0) drawCells(batch, parentAlpha);
    }


    /**
     * Updates the changes made to the container and its children.
     */
    private void updateChanges() {
        hasUpdatedCells = true;

        if (resizable) {
            if (resizeBounds == null) {
                resizeBounds = new Rectangle[NUM_RESIZE_BOUNDS];
                resizing = new boolean[NUM_RESIZE_BOUNDS];
            }
            int bh = RESIZE_BOUND_RANGE / 2;
            resizeBounds[0] = new Rectangle(getX() - bh, getY() - bh, bh * 2, getHeight() + bh * 2);
            resizeBounds[1] = new Rectangle(getX() + getWidth() - bh, getY() - bh, bh * 2, getHeight() + bh * 2);
            resizeBounds[2] = new Rectangle(getX() - bh, getY() + getHeight() - bh, getWidth() + bh * 2, bh * 2);
            resizeBounds[3] = new Rectangle(getX() - bh, getY() - bh, getWidth() + bh * 2, bh * 2);
        }


        for (UICell cell : cells) {
            if (cell == null)
                continue;

            UIActor actor = cell.getActor();
            if (cell.alignment != null) {
                Vector2 alignmentPosition = cell.alignment.calculatePosition(this, actor);
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

            if (cell.snapRight) {
                actor.setWidth(getWidth() - actor.getX() - cell.rightPadding);
            }
            if (cell.snapUp) {
                actor.setHeight(getHeight() - actor.getY() - cell.topPadding);
            }

            float clampedX = Value.of(actor.getX()).clamp(cell.leftPadding, getWidth() - actor.getWidth() - cell.rightPadding).floatValue();
            float clampedY = Value.of(actor.getY()).clamp(cell.bottomPadding, getHeight() - actor.getHeight() - cell.topPadding).floatValue();
            actor.setPosition(clampedX, clampedY);
        }
    }

    /**
     * Calculates resizing of the container based on user input.
     */
    private void calculateResizing() {
        int mx = Gdx.input.getX();
        int my = -Gdx.input.getY() + Gdx.graphics.getHeight();

        if (mx > Gdx.graphics.getWidth() - 15 || my > Gdx.graphics.getHeight() - 15 || mx < 15 || my < 15) // mouse is outside window
            return;

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            dragStartY = Gdx.input.getY();
            dragStartX = Gdx.input.getX();
            for (int i = 0; i < NUM_RESIZE_BOUNDS; i++)
                resizing[i] = resizeBounds[i].contains(mx, my);
        }

        boolean topLeft = resizeBounds[0].contains(mx, my) && resizeBounds[2].contains(mx, my);
        boolean topRight = resizeBounds[1].contains(mx, my) && resizeBounds[2].contains(mx, my);
        boolean bottomLeft = resizeBounds[0].contains(mx, my) && resizeBounds[3].contains(mx, my);
        boolean bottomRight = resizeBounds[1].contains(mx, my) && resizeBounds[3].contains(mx, my);

        if (topRight || bottomLeft) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NESWResize);
        } else if (topLeft || bottomRight) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NWSEResize);
        } else if (resizeBounds[0].contains(mx, my) || resizeBounds[1].contains(mx, my)) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
        } else if (resizeBounds[2].contains(mx, my) || resizeBounds[3].contains(mx, my)) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
        }

        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT))
            Arrays.fill(resizing, false);

        float deltaX = Gdx.input.getX() - dragStartX;
        float deltaY = -Gdx.input.getY() + dragStartY;

        if (resizing[0]) {
            float resizeX = Math.abs(getX() + deltaX);
            float resizeWidth = Math.abs(getWidth() - deltaX);
            if (resizeX < getX() + getWidth() - 15 && resizeWidth > 15) {
                setX(resizeX);
                setWidth(resizeWidth);
            }
        }
        if (resizing[1]) {
            float resizeWidth = Math.abs(getWidth() + deltaX);
            if (resizeWidth > 15)
                setWidth(resizeWidth);
        }
        if (resizing[2]) {
            float resizeHeight = Math.abs(getHeight() + deltaY);
            if (resizeHeight > 15)
                setHeight(resizeHeight);
        }
        if (resizing[3]) {
            float resizeY = Math.abs(getY() + deltaY);
            float resizeHeight = Math.abs(getHeight() - deltaY);
            if (resizeY < getY() + getHeight() - 15 && resizeHeight > 15) {
                setY(resizeY);
                setHeight(resizeHeight);
            }
        }

        dragStartY = Gdx.input.getY();
        dragStartX = Gdx.input.getX();
    }

    /**
     * Updates the position of the container's children when the container's position changes.
     */
    protected void positionChanged() {
        hasUpdatedCells = false;
    }

    /**
     * Updates the size of the container's children when the container's size changes.
     */
    protected void sizeChanged() {
        hasUpdatedCells = false;
    }

    /**
     * Updates the scale of the container's children when the container's scale changes.
     */
    protected void scaleChanged() {
        hasUpdatedCells = false;
    }

    /**
     * Updates the rotation of the container's children when the container's rotation changes.
     */
    protected void rotationChanged() {
        hasUpdatedCells = false;
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

    @Override
    public boolean remove() {
        super.remove();

        cells.clear();
        return false;
    }
}
