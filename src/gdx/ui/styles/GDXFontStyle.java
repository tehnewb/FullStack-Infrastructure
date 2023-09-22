package gdx.ui.styles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * The GDXFontStyle class extends FreeTypeFontParameter and provides dedicated methods for customization
 * of font rendering using FreeTypeFontGenerator.
 */
public class GDXFontStyle extends FreeTypeFontParameter {

    /**
     * Sets the font size.
     *
     * @param size The font size to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle size(int size) {
        this.size = size;
        return this;
    }

    /**
     * Sets whether the font should be monospaced.
     *
     * @param mono True if the font should be monospaced, false otherwise.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle mono(boolean mono) {
        this.mono = mono;
        return this;
    }

    /**
     * Sets the hinting style for font rendering.
     *
     * @param hinting The hinting style to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle hinting(FreeTypeFontGenerator.Hinting hinting) {
        this.hinting = hinting;
        return this;
    }

    /**
     * Sets the font color.
     *
     * @param color The font color to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle color(Color color) {
        this.color = color;
        return this;
    }

    /**
     * Sets the gamma correction factor for font rendering.
     *
     * @param gamma The gamma correction factor to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle gamma(float gamma) {
        this.gamma = gamma;
        return this;
    }

    /**
     * Sets the number of render passes for font rendering.
     *
     * @param renderCount The number of render passes to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle renderCount(int renderCount) {
        this.renderCount = renderCount;
        return this;
    }

    /**
     * Sets the border width for the font.
     *
     * @param borderWidth The border width to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle borderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    /**
     * Sets the border color for the font.
     *
     * @param borderColor The border color to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle borderColor(Color borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    /**
     * Sets whether the font border should be straight.
     *
     * @param borderStraight True if the font border should be straight, false otherwise.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle borderStraight(boolean borderStraight) {
        this.borderStraight = borderStraight;
        return this;
    }

    /**
     * Sets the gamma correction factor for font borders.
     *
     * @param borderGamma The gamma correction factor for font borders to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle borderGamma(float borderGamma) {
        this.borderGamma = borderGamma;
        return this;
    }

    /**
     * Sets the X-axis offset for font shadows.
     *
     * @param shadowOffsetX The X-axis offset for font shadows to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle shadowOffsetX(int shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
        return this;
    }

    /**
     * Sets the Y-axis offset for font shadows.
     *
     * @param shadowOffsetY The Y-axis offset for font shadows to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle shadowOffsetY(int shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
        return this;
    }

    /**
     * Sets the color of font shadows.
     *
     * @param shadowColor The color of font shadows to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle shadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
        return this;
    }

    /**
     * Sets additional horizontal spacing between characters.
     *
     * @param spaceX The additional horizontal spacing between characters to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle spaceX(int spaceX) {
        this.spaceX = spaceX;
        return this;
    }

    /**
     * Sets additional vertical spacing between lines of text.
     *
     * @param spaceY The additional vertical spacing between lines of text to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle spaceY(int spaceY) {
        this.spaceY = spaceY;
        return this;
    }

    /**
     * Sets padding at the top of the font.
     *
     * @param padTop The padding at the top of the font to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle padTop(int padTop) {
        this.padTop = padTop;
        return this;
    }

    /**
     * Sets padding at the left of the font.
     *
     * @param padLeft The padding at the left of the font to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle padLeft(int padLeft) {
        this.padLeft = padLeft;
        return this;
    }

    /**
     * Sets padding at the bottom of the font.
     *
     * @param padBottom The padding at the bottom of the font to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle padBottom(int padBottom) {
        this.padBottom = padBottom;
        return this;
    }

    /**
     * Sets padding at the right of the font.
     *
     * @param padRight The padding at the right of the font to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle padRight(int padRight) {
        this.padRight = padRight;
        return this;
    }

    /**
     * Sets the character set to include in the font.
     *
     * @param characters The character set to include in the font.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle characters(String characters) {
        this.characters = characters;
        return this;
    }

    /**
     * Sets whether kerning should be enabled for the font.
     *
     * @param kerning True if kerning should be enabled, false otherwise.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle kerning(boolean kerning) {
        this.kerning = kerning;
        return this;
    }

    /**
     * Sets the PixmapPacker to use for packing glyphs into a texture atlas.
     *
     * @param packer The PixmapPacker to use for glyph packing.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle packer(PixmapPacker packer) {
        this.packer = packer;
        return this;
    }

    /**
     * Sets whether the font should be flipped vertically.
     *
     * @param flip True if the font should be flipped vertically, false otherwise.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle flip(boolean flip) {
        this.flip = flip;
        return this;
    }

    /**
     * Sets whether mipmaps should be generated for the font texture.
     *
     * @param genMipMaps True if mipmaps should be generated, false otherwise.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle genMipMaps(boolean genMipMaps) {
        this.genMipMaps = genMipMaps;
        return this;
    }

    /**
     * Sets the minimum filter for the font texture.
     *
     * @param minFilter The minimum filter to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle minFilter(Texture.TextureFilter minFilter) {
        this.minFilter = minFilter;
        return this;
    }

    /**
     * Sets the maximum filter for the font texture.
     *
     * @param magFilter The maximum filter to set.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle magFilter(Texture.TextureFilter magFilter) {
        this.magFilter = magFilter;
        return this;
    }

    /**
     * Sets whether incremental rendering should be used for the font.
     *
     * @param incremental True if incremental rendering should be used, false otherwise.
     * @return The GDXFontStyle instance for method chaining.
     */
    public GDXFontStyle incremental(boolean incremental) {
        this.incremental = incremental;
        return this;
    }
}
