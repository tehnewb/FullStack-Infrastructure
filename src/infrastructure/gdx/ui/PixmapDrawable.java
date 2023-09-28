package infrastructure.gdx.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import java.nio.ByteBuffer;

/**
 * The PixmapDrawable class is a versatile drawable in the LibGDX framework that
 * combines the capabilities of a Pixmap and a Drawable. It allows you to create
 * a drawable object from image data stored in a Pixmap, which can be easily used
 * within LibGDX's Scene2D framework or other graphical components.
 *
 * @author Albert Beaupre
 * @see com.badlogic.gdx.graphics.Pixmap
 * @see com.badlogic.gdx.graphics.Texture
 * @see com.badlogic.gdx.scenes.scene2d.utils.Drawable
 */
public class PixmapDrawable extends Pixmap implements Drawable {
    private float leftWidth, rightWidth, topHeight, bottomHeight, minWidth, minHeight;
    private Texture texture;

    /**
     * Creates a new PixmapDrawable with the specified width, height, and format.
     *
     * @param width  The width of the Pixmap.
     * @param height The height of the Pixmap.
     * @param format The format of the Pixmap (e.g., Format.RGBA8888).
     */
    public PixmapDrawable(int width, int height, Format format) {
        super(width, height, format);
        this.texture = new Texture(this, Format.RGBA8888, true);
    }

    /**
     * Creates a new PixmapDrawable from encoded image data.
     *
     * @param encodedData An array of encoded image data.
     * @param offset      The offset within the data array.
     * @param len         The length of the data to use.
     */
    public PixmapDrawable(byte[] encodedData, int offset, int len) {
        super(encodedData, offset, len);
        this.texture = new Texture(this, Format.RGBA8888, true);
    }

    /**
     * Creates a new PixmapDrawable from a ByteBuffer containing encoded image data.
     *
     * @param encodedData A ByteBuffer containing encoded image data.
     * @param offset      The offset within the ByteBuffer.
     * @param len         The length of the data to use.
     */
    public PixmapDrawable(ByteBuffer encodedData, int offset, int len) {
        super(encodedData, offset, len);
        this.texture = new Texture(this, Format.RGBA8888, true);
    }

    /**
     * Creates a new PixmapDrawable from a ByteBuffer containing encoded image data.
     *
     * @param encodedData A ByteBuffer containing encoded image data.
     */
    public PixmapDrawable(ByteBuffer encodedData) {
        super(encodedData);
        this.texture = new Texture(this, Format.RGBA8888, true);
    }

    /**
     * Creates a new PixmapDrawable from a file.
     *
     * @param file A file handle to the image file.
     */
    public PixmapDrawable(FileHandle file) {
        super(file);
        this.texture = new Texture(this, Format.RGBA8888, true);
    }

    /**
     * Draw the PixmapDrawable on a batch at the specified position and size.
     *
     * @param batch  The Batch to draw on.
     * @param x      The x-coordinate of the position.
     * @param y      The y-coordinate of the position.
     * @param width  The width of the drawn PixmapDrawable.
     * @param height The height of the drawn PixmapDrawable.
     */
    @Override
    public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float x, float y, float width, float height) {
        if (texture != null) {
            batch.draw(texture, x, y, width, height);
        }
    }

    public NinePatchDrawable toNinePatch(int left, int right, int top, int bottom) {
        return new NinePatchDrawable(new NinePatch(texture, left, right, top, bottom));
    }

    /**
     * Get the Texture associated with this PixmapDrawable.
     *
     * @return The Texture instance.
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Set a new Texture for this PixmapDrawable. This can be useful if you want to
     * replace the internal Texture with a different one.
     *
     * @param texture The new Texture to set.
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Get the left width of the drawable (for use in Scene2D).
     *
     * @return The left width.
     */
    @Override
    public float getLeftWidth() {
        return leftWidth;
    }

    /**
     * Set the left width of the drawable (for use in Scene2D).
     *
     * @param leftWidth The left width to set.
     */
    @Override
    public void setLeftWidth(float leftWidth) {
        this.leftWidth = leftWidth;
    }

    /**
     * Get the right width of the drawable (for use in Scene2D).
     *
     * @return The right width.
     */
    @Override
    public float getRightWidth() {
        return rightWidth;
    }

    /**
     * Set the right width of the drawable (for use in Scene2D).
     *
     * @param rightWidth The right width to set.
     */
    @Override
    public void setRightWidth(float rightWidth) {
        this.rightWidth = rightWidth;
    }

    /**
     * Get the top height of the drawable (for use in Scene2D).
     *
     * @return The top height.
     */
    @Override
    public float getTopHeight() {
        return topHeight;
    }

    /**
     * Set the top height of the drawable (for use in Scene2D).
     *
     * @param topHeight The top height to set.
     */
    @Override
    public void setTopHeight(float topHeight) {
        this.topHeight = topHeight;
    }

    /**
     * Get the bottom height of the drawable (for use in Scene2D).
     *
     * @return The bottom height.
     */
    @Override
    public float getBottomHeight() {
        return bottomHeight;
    }

    /**
     * Set the bottom height of the drawable (for use in Scene2D).
     *
     * @param bottomHeight The bottom height to set.
     */
    @Override
    public void setBottomHeight(float bottomHeight) {
        this.bottomHeight = bottomHeight;
    }

    /**
     * Get the minimum width of the drawable (for use in Scene2D).
     *
     * @return The minimum width.
     */
    @Override
    public float getMinWidth() {
        return minWidth;
    }

    /**
     * Set the minimum width of the drawable (for use in Scene2D).
     *
     * @param minWidth The minimum width to set.
     */
    @Override
    public void setMinWidth(float minWidth) {
        this.minWidth = minWidth;
    }

    /**
     * Get the minimum height of the drawable (for use in Scene2D).
     *
     * @return The minimum height.
     */
    @Override
    public float getMinHeight() {
        return minHeight;
    }

    /**
     * Set the minimum height of the drawable (for use in Scene2D).
     *
     * @param minHeight The minimum height to set.
     */
    @Override
    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }

    /**
     * Disposes of the internal resources associated with this PixmapDrawable.
     * It's important to call this method when you no longer need the drawable
     * to release its resources and avoid memory leaks.
     */
    public void dispose() {
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }

}