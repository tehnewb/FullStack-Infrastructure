package gdx.proto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class UILabel extends UIActor {

    private BitmapFont font;
    private String text;
    private Alignment alignment;
    private GlyphLayout layout;
    private Rectangle fontBounds;

    public UILabel(String text) {
        this.text = text;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/font.ttf"));

        // Configure the font parameters
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24; // Set the font size
        parameter.color = Color.BLACK; // Set the font color
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 1;
        parameter.shadowOffsetY = 1;

        // Generate the BitmapFont
        this.font = generator.generateFont(parameter);
        this.layout = new GlyphLayout(font, text);
        this.alignment = Alignment.Center;
        this.fontBounds = new Rectangle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Vector2 alignPos = alignment.calculatePosition(getBounds(), fontBounds.setSize(layout.width, layout.height));
        float x = getX() + alignPos.x;
        float y = getX() + alignPos.y + font.getCapHeight();
        if (getParent() != null) {
            x += getParent().getX();
            y += getParent().getY();
        }
        font.draw(batch, text, x, y);
    }

    @Override
    public void update(float delta) {

    }
}
