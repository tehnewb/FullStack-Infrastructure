package ui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import entity.Tick;

public class MyButtonGame extends ApplicationAdapter {
    private Stage stage;
    private SpriteBatch batch;
    private UIContainer container;
    private UIActor actor;

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("My GDX Game");
        config.useVsync(true);
        config.setWindowedMode(800, 600);
        new Lwjgl3Application(new MyButtonGame(), config);

    }

    @Override
    public void create() {
        container = new UIContainer();
        container.setBounds(150, 150, 200, 200);
        container.setDebug(true);
        container.setResizable(true);


        UIContainer container2 = new UIContainer();
        container2.setBounds(350, 350, 200, 200);
        container2.setDebug(true);
        container2.setResizable(true);

        actor = new UIActor();
        actor.setBounds(125, 125, 25, 25);
        container.add(actor).snapAll().pad(25);

        stage = new Stage();
        stage.addActor(container);
        stage.addActor(container2);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Tick.updateTicks(Gdx.graphics.getDeltaTime());
        ScreenUtils.clear(0, 0, 0, 1, true);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

}
