package gdx.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GDXSkin extends Skin {

    private static final GDXSkin skin = new GDXSkin();


    public GDXSkin() {
        this.add("default-font", new BitmapFont());
    }

    public static GDXSkin getInstance() {
        return skin;
    }

}
