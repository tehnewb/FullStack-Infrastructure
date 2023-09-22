package gdx.proto;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import entity.Tick;
import gdx.ui.actors.GDXLabel;

public class MyButtonGame extends ApplicationAdapter {
    private Stage stage;

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("My GDX Game");
        config.useVsync(true);
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new MyButtonGame(), config);
    }

    @Override
    public void create() {
        GDXLabel label = GDXLabel.of("Hello");
        label.setPosition(150, 150);

        ScreenViewport viewport = new ScreenViewport();
        stage = new Stage(viewport);
        stage.addActor(label);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Tick.updateTicks(Gdx.graphics.getDeltaTime());
        ScreenUtils.clear(Color.BLACK);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

}
