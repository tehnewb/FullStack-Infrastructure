package ui;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class UIActor extends Actor {

    public void requestFocus() {
        super.getStage().setScrollFocus(this);
        super.getStage().setKeyboardFocus(this);
    }
}
