package gdx.ui.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import gdx.ui.styles.GDXButtonStyle;

public class GDXButton extends Button {

    public GDXButton() {
        super(GDXButtonStyle.defaults());
    }

    public static GDXButton of() {
        return new GDXButton();
    }

}
