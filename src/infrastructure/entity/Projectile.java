package infrastructure.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import infrastructure.io.ObjectPool;

/**
 * The `Projectile` class represents a game entity that can be used for projectiles or bullets in a game.
 *
 * @author Albert Beaupre
 */
public class Projectile extends Actor {

    // Object pool for reusing projectiles
    private static final ObjectPool<Projectile> ProjectilePool = new ObjectPool<>(Projectile::new, 10);

    // Index to identify this projectile within the entity list
    private int index;

    // Texture to represent the projectile's appearance
    private Texture texture;

    // Speed of the projectile's movement
    private float speed;

    // Starting and ending positions for the projectile's movement
    private Vector2 startAt, endAt;

    /**
     * Creates a new instance of the `Projectile` class.
     * This constructor is private, and instances should be created using the `of` method.
     */
    private Projectile() {
        // Private constructor, use `of` method to create instances.
    }

    /**
     * Creates and initializes a new projectile instance with the specified texture.
     *
     * @param texture The texture to be used for rendering the projectile.
     * @return A new `Projectile` instance.
     */
    public static Projectile of(Texture texture) {
        return ProjectilePool.borrowObject().texture(texture);
    }

    /**
     * Sets the texture to be used for rendering the projectile.
     *
     * @param texture The texture to be used for rendering.
     * @return The modified `Projectile` instance.
     */
    public Projectile texture(Texture texture) {
        this.texture = texture;
        return this;
    }

    /**
     * Sets the speed of the projectile's movement.
     *
     * @param speed The speed of the projectile.
     * @return The modified `Projectile` instance.
     */
    public Projectile speed(float speed) {
        this.speed = speed;
        return this;
    }

    /**
     * Sets the starting position of the projectile.
     *
     * @param x The x-coordinate of the starting position.
     * @param y The y-coordinate of the starting position.
     * @return The modified `Projectile` instance.
     */
    public Projectile startAt(float x, float y) {
        if (startAt == null)
            this.startAt = new Vector2(x, y);
        else this.startAt.set(x, y);
        return this;
    }

    /**
     * Sets the ending position of the projectile.
     *
     * @param x The x-coordinate of the ending position.
     * @param y The y-coordinate of the ending position.
     * @return The modified `Projectile` instance.
     */
    public Projectile endAt(float x, float y) {
        if (endAt == null)
            this.endAt = new Vector2(x, y);
        else this.endAt.set(x, y);
        return this;
    }

    /**
     * Renders this projectile using the specified batch.
     *
     * @param batch The batch used for rendering.
     */
    public void render(Batch batch) {
        // TODO Implement rendering logic for the projectile
    }

    /**
     * Updates this projectile based on the specified delta time.
     *
     * @param delta The time elapsed since the last frame in seconds.
     */
    public void update(float delta) {
        // TODO Implement update logic for the projectile
    }
}