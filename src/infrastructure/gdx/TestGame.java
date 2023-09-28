package infrastructure.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import infrastructure.gdx.map.TiledMap;
import infrastructure.gdx.ui.GDXGame;
import infrastructure.gdx.ui.GDXScreen;
import infrastructure.gdx.ui.actors.GDXLabel;
import infrastructure.gdx.ui.styles.GDXLabelStyle;

public class TestGame extends GDXScreen {
    private TiledMap map;
    private Stage stage;
    private OrthographicCamera camera;
    private Player player;

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setWindowedMode(800, 600);
        config.useVsync(true);
        config.setTitle("Top-Down-Shooter");
        new Lwjgl3Application(new GDXGame(new TestGame()), config);
    }

    @Override
    public void create() {
        player = new Player();

        byte[] mapData = Gdx.files.internal("map.tmx").readBytes();
        byte[] tileData = Gdx.files.internal("tileset.png").readBytes();
        map = new TiledMap(mapData, tileData);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.2f;
        stage = new Stage(new ScreenViewport(camera));
        player.setSize(16, 16);

        GDXLabelStyle.defaults(new GDXLabelStyle().color(Color.BLACK).font(new BitmapFont()));
        GDXLabel label = GDXLabel.of("Player");
        stage.addActor(player);

        label.align(Align.center);
        label.setPosition(0, 20);
        player.addActor(label);
    }

    @Override
    public void render(Batch batch) {
        stage.draw();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.setX(player.getX() - 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.setX(player.getX() + 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.setY(player.getY() - 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.setY(player.getY() + 1);
        }

        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void destroy() {

    }

    private class Player extends Group {
        Texture r;

        Player() {
            r = new Texture(Gdx.files.internal("player.png"));
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            map.renderUnderlays(batch, (int) (getX() / 16f), (int) (getY() / 16f), 10);
            super.draw(batch, parentAlpha);
            batch.draw(r, getX(), getY(), getWidth(), getHeight());
            map.renderOverlays(batch, (int) (getX() / 16f), (int) (getY() / 16f), 10);
        }
    }
}
