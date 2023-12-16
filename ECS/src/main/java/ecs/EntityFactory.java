package ecs;

/**
 * The {@code EntityFactory} interface defines methods for creating, destroying,
 * and handling component-related events for entities within an ECS (Entity-Component-System) framework.
 *
 * @param <E> The type parameter representing the entity class.
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public interface EntityFactory<E extends Entity> {

    /**
     * Creates a new instance of the entity.
     *
     * @return A new instance of the entity.
     */
    E create();

    /**
     * Destroys the specified entity, allowing for cleanup and resource release.
     *
     * @param entity The entity to be destroyed.
     */
    void destroy(E entity);

    /**
     * Called when a new component is added to the specified entity.
     * Implementations can use this method to perform any necessary logic or updates
     * associated with the addition of a component.
     *
     * @param entity    The entity to which the component is added.
     * @param component The component that is added to the entity.
     */
    void onAddComponent(E entity, Component component);

    /**
     * Called when a component is removed from the specified entity.
     * Implementations can use this method to perform any necessary logic or updates
     * associated with the removal of a component.
     *
     * @param entity    The entity from which the component is removed.
     * @param component The component that is removed from the entity.
     */
    void onRemoveComponent(E entity, Component component);
}