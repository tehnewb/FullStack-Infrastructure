package ui;

/**
 * The Cell class represents a rectangular cell within a grid or layout.
 * It defines various properties and padding for the cell.
 */
public class UICell {

    // The actor this cell wraps
    protected final UIActor actor;
    // Max dimension values
    protected float maxWidth, maxHeight;
    // Padding values for the cell
    protected float leftPadding, rightPadding, topPadding, bottomPadding;
    // Flags to determine if the cell should extend in specific directions
    protected boolean snapLeft, snapRight, snapUp, snapDown;
    // The alignment of the cell
    protected Alignment alignment;

    /**
     * Constructs a new Cell that wraps around the given actor.
     *
     * @param actor The actor to wrap.
     */
    public UICell(UIActor actor) {
        this.actor = actor;
    }

    /**
     * Sets the maximum width this cell can grow to.
     *
     * @param value The maximum width.
     * @return This Cell instance to allow method chaining.
     */
    public UICell maxWidth(float value) {
        this.maxHeight = value;
        return this;
    }

    /**
     * Sets the maximum height this cell can grow to.
     *
     * @param value The maximum height.
     * @return This Cell instance to allow method chaining.
     */
    public UICell maxHeight(float value) {
        this.maxHeight = value;
        return this;
    }

    /**
     * Sets the alignment of this cell to the right.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell right() {
        this.alignment = Alignment.RIGHT;
        return this;
    }

    /**
     * Sets the alignment of this cell to the left.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell left() {
        this.alignment = Alignment.LEFT;
        return this;
    }

    /**
     * Sets the alignment of this cell to the center.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell center() {
        this.alignment = Alignment.CENTER;
        return this;
    }

    /**
     * Sets the alignment of this cell to the top.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell top() {
        this.alignment = Alignment.TOP;
        return this;
    }

    /**
     * Sets the alignment of this cell to the top-center.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell topCenter() {
        this.alignment = Alignment.TOP_CENTER;
        return this;
    }

    /**
     * Sets the alignment of this cell to the top-right.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell topRight() {
        this.alignment = Alignment.TOP_RIGHT;
        return this;
    }

    /**
     * Sets the alignment of this cell to the top-left.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell topLeft() {
        this.alignment = Alignment.TOP_LEFT;
        return this;
    }

    /**
     * Sets the alignment of this cell to the bottom.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell bottom() {
        this.alignment = Alignment.BOTTOM;
        return this;
    }

    /**
     * Sets the alignment of this cell to the bottom-center.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell bottomCenter() {
        this.alignment = Alignment.BOTTOM_CENTER;
        return this;
    }

    /**
     * Sets the alignment of this cell to the bottom-right.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell bottomRight() {
        this.alignment = Alignment.BOTTOM_RIGHT;
        return this;
    }

    /**
     * Sets the alignment of this cell to the bottom-left.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell bottomLeft() {
        this.alignment = Alignment.BOTTOM_LEFT;
        return this;
    }

    /**
     * Sets the alignment of this cell to the right-center.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell rightCenter() {
        this.alignment = Alignment.RIGHT_CENTER;
        return this;
    }

    /**
     * Sets the alignment of this cell to the left-center.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell leftCenter() {
        this.alignment = Alignment.LEFT_CENTER;
        return this;
    }

    /**
     * Set the x-coordinate of the cell's bounds.
     *
     * @param x The x-coordinate for the cell's bounds.
     * @return This Cell instance to allow method chaining.
     */
    public UICell x(float x) {
        this.actor.setX(x);
        return this;
    }

    /**
     * Set the y-coordinate of the cell's bounds.
     *
     * @param y The y-coordinate for the cell's bounds.
     * @return This Cell instance to allow method chaining.
     */
    public UICell y(float y) {
        this.actor.setY(y);
        return this;
    }

    /**
     * Set the width of the cell's bounds.
     *
     * @param width The width for the cell's bounds.
     * @return This Cell instance to allow method chaining.
     */
    public UICell width(float width) {
        this.actor.setWidth(width);
        return this;
    }

    /**
     * Set the height of the cell's bounds.
     *
     * @param height The height for the cell's bounds.
     * @return This Cell instance to allow method chaining.
     */
    public UICell height(float height) {
        this.actor.setHeight(height);
        return this;
    }

    /**
     * Set the overall padding for this cell.
     *
     * @param value The amount of top padding to apply.
     * @return This Cell instance to allow method chaining.
     */
    public UICell pad(float value) {
        this.topPadding = value;
        this.bottomPadding = value;
        this.rightPadding = value;
        this.leftPadding = value;
        return this;
    }

    /**
     * Set the left padding for the cell.
     *
     * @param value The amount of left padding to apply.
     * @return This Cell instance to allow method chaining.
     */
    public UICell padLeft(float value) {
        this.leftPadding = value;
        return this;
    }

    /**
     * Set the right padding for the cell.
     *
     * @param value The amount of right padding to apply.
     * @return This Cell instance to allow method chaining.
     */
    public UICell padRight(float value) {
        this.rightPadding = value;
        return this;
    }

    /**
     * Set the top padding for the cell.
     *
     * @param value The amount of top padding to apply.
     * @return This Cell instance to allow method chaining.
     */
    public UICell padTop(float value) {
        this.topPadding = value;
        return this;
    }

    /**
     * Set the bottom padding for the cell.
     *
     * @param value The amount of bottom padding to apply.
     * @return This Cell instance to allow method chaining.
     */
    public UICell padBottom(float value) {
        this.bottomPadding = value;
        return this;
    }

    /**
     * Set whether the cell should snap to the left.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell snapLeft() {
        this.snapLeft = true;
        return this;
    }

    /**
     * Set whether the cell should snap to the right.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell snapRight() {
        this.snapRight = true;
        return this;
    }

    /**
     * Set whether the cell should snap upwards.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell snapUp() {
        this.snapUp = true;
        return this;
    }

    /**
     * Set whether the cell should snap downwards.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell snapDown() {
        this.snapDown = true;
        return this;
    }

    /**
     * Set whether the cell should snap each directions.
     *
     * @return This Cell instance to allow method chaining.
     */
    public UICell snapAll() {
        this.snapUp = true;
        this.snapDown = true;
        this.snapLeft = true;
        this.snapRight = true;
        return this;
    }

    /**
     * Retrieves the actor currently wrapped by this UICell.
     *
     * @return The actor wrapped by this UICell.
     */
    public UIActor getActor() {
        return actor;
    }
}