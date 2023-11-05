package infrastructure.gdx.map;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface MapObjectFactory {

    void renderObject(Batch batch, int x, int y, int width, int height);

}
