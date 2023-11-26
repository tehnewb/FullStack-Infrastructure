package infrastructure.ecsprototype;

import infrastructure.collections.faststack.FastStackNode;

/**
 * The `Entity` class is a fundamental building block of an Entity-Component-System (ECS) framework.
 * An entity represents an object or entity in the game world, and it serves as a container for attaching
 * various components that define its properties and behaviors.
 * <p>
 * Entities are managed by the ECS framework, and they can be created, destroyed, and processed by
 * different entity systems. Entities hold a unique index that allows efficient access to their data and
 * identification within the ECS.
 * <p>
 * Example Usage:
 * <pre>
 *      ECS ecs = new ECS(new MovementSystem(), RenderSystem());
 *      Entity player = ecs.create();
 *      player.addComponent(new PositionComponent(0, 0));
 *      player.addComponent(new VelocityComponent(2, 0));
 * </pre>
 *
 * @see infrastructure.ecsprototype.ECS
 * @see infrastructure.ecsprototype.Component
 * @see infrastructure.collections.faststack.FastStackNode
 */
public class Entity {

    // The ECS instance to which this entity belongs.
    private final ECS ecs;

    // A node that allows efficient management of entities within collections.
    public FastStackNode<Entity> node;

    // The unique index of this entity within the ECS framework.
    protected int index;

    /**
     * Constructs an entity within the ECS framework.
     *
     * @param ecs   The ECS instance to which this entity belongs.
     * @param index The unique index of this entity.
     */
    public Entity(ECS ecs, int index) {
        this.ecs = ecs;
        this.index = index;
    }

    /**
     * Retrieves a component associated with this entity based on the component's index and type.
     *
     * @param componentIndex The index of the component type.
     * @param cast           The component class to cast to.
     * @param <T>            The component type.
     * @return The component associated with the entity and component type.
     */
    public <T> T get(int componentIndex, Class<? extends Component> cast) {
        return ecs.getComponent(index, componentIndex, cast);
    }

    /**
     * Adds a component to this entity.
     *
     * @param component The component to add.
     * @return The entity, allowing method chaining.
     */
    public Entity add(Component component) {
        ecs.addComponent(index, component);
        return this;
    }

    /**
     * Removes a component from this entity based on the component's index.
     *
     * @param componentIndex The index of the component to remove.
     * @return The entity, allowing method chaining.
     */
    public Entity remove(int componentIndex) {
        ecs.removeComponent(index, componentIndex);
        return this;
    }

    /**
     * Retrieves the unique index associated with this entity.
     *
     * @return The unique index of this entity within the ECS framework.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the index of this entity, typically used when reusing an entity.
     *
     * @param index The new index to assign to this entity.
     */
    protected void setIndex(int index) {
        this.index = index;
    }
}