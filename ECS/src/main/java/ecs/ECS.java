package ecs;

import collections.array.ResizingArray;
import collections.stack.FastStack;

/**
 * The ECS (Entity-Component-System) class represents a simple entity-component-system framework
 * for managing game entities, their components, and systems that process these entities.
 * Entities are represented by unique indices, and components are attached to entities to
 * encapsulate behavior and data. Systems operate on entities based on their components,
 * allowing for modular and efficient game logic.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class ECS<E extends Entity> {

    /**
     * Queue of available entity indices, used for recycling and reusing entity slots.
     */
    private final EntityIndexQueue indicies;
    /**
     * Array of systems that process entities with specific component configurations.
     */
    private final EntitySystem[] systems;
    /**
     * Resizable array containing all entities in the ECS, indexed by their unique entity indices.
     */
    private final ResizingArray<E> entities;
    /**
     * Stack of recycled entities for efficient entity creation and removal.
     */
    private final FastStack<E> pool;
    /**
     * EntityFactory used to handle the creation and destruction of entities.
     */
    private final EntityFactory<E> factory;

    /**
     * Constructs an ECS instance with the specified systems.
     *
     * @param systems The systems responsible for processing entities based on their components.
     */
    public ECS(EntityFactory<E> factory, Class<E> type, EntitySystem... systems) {
        this.factory = factory;
        this.systems = systems;
        this.entities = new ResizingArray<>(type, Long.SIZE);
        this.indicies = new EntityIndexQueue();
        this.pool = new FastStack<>();
    }

    /**
     * Processes all systems, allowing them to update and operate on entities.
     */
    public void process() {
        for (int i = 0; i < systems.length; i++) {
            systems[i].process();
        }
    }

    /**
     * Creates a new entity or reuses a recycled entity from the pool.
     *
     * @return The created or recycled entity.
     */
    public Entity create() {
        int index = indicies.pop();
        E entity = !pool.isEmpty() ? pool.pop() : factory.create();
        entity.setIndex(index);
        entities.set(index, entity);
        return entity;
    }

    /**
     * Removes an entity from the ECS, recycling its index and returning it to the entity pool.
     *
     * @param entity The entity to be removed.
     */
    public void remove(E entity) {
        indicies.push(entity.getIndex());
        pool.push(entity);
        entity.getComponentFlags().clearAll();
        entities.set(entity.getIndex(), null);
        factory.destroy(entity);
    }

    /**
     * Adds a component to an entity, updating relevant systems and their component mappers.
     *
     * @param entity    The entity to which the component will be added.
     * @param component The component to be added.
     */
    public void addComponent(E entity, Component component) {
        entity.getComponentFlags().set(component.getIndex());
        EntitySystem.getSystems(component.getClass()).forEach(system -> {
            system.add(entity.getIndex());
            system.getMapper(component.getClass()).set(entity.getIndex(), component);

            factory.onAddComponent(entity, component);
        });
    }

    /**
     * Removes a component from an entity, updating relevant systems and their component mappers.
     *
     * @param entity    The entity from which the component will be removed.
     * @param component The component to be removed.
     */
    public void removeComponent(E entity, Component component) {
        entity.getComponentFlags().clear(component.getIndex());
        EntitySystem.getSystems(component.getClass()).forEach(system -> {
            if (!system.canStay(entity))
                system.remove(entity.getIndex());

            system.getMapper(component.getClass()).set(entity.getIndex(), null);
            factory.onRemoveComponent(entity, component);
        });
    }

    /**
     * Returns the entity corresponding to the specified ID.
     *
     * @param ID the entity ID
     * @return the entity.
     */
    public E get(int ID) {
        return entities.get(ID);
    }

    /**
     * @return The entity elements.
     */
    public E[] getEntities() {
        return entities.getElements();
    }
}