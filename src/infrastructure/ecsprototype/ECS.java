package infrastructure.ecsprototype;

import infrastructure.collections.TypeArray;
import infrastructure.collections.faststack.FastStack;
import infrastructure.collections.index.IndexQueue;

import java.util.Arrays;

/**
 * The `ECS` (Entity-Component-System) class represents a fundamental framework for managing entities and their
 * associated components within a game or simulation system. It facilitates efficient entity creation, management,
 * and the execution of various systems to process these entities and their components.
 *
 * @author Albert Beaupre
 */
@SuppressWarnings("ALL")
public class ECS {

    /**
     * The capacity at which the entity array starts at.
     */
    private static final int BeginningEntityCapacity = 262_144;

    /**
     * The capacity at which the component array starts at.
     */
    private static final int BeginningComponentCapacity = 16;

    /**
     * This is used to enqueue and dequeue entity indices.
     * If an entity is created, this will dequeue an index and assign it to that entity.
     * If an entity is destroyed, the index of that entity will be enqueued to this.
     * <p>
     * If no indices have been enqueued to this, then it will produce an in
     */
    private final IndexQueue entityIndices = new IndexQueue();

    /**
     * This stack contains all entities that have been destroyed and will be re-used.
     * If this stack is empty, a new instance is created instead.
     */
    private final FastStack<Entity> entityPool = new FastStack<>();
    /**
     * This is the main storage of every entity system.
     */
    public EntitySystem[] systems;
    /**
     * A 2-Dimensional array that holds components for every entity.
     * The first array value will be the unique component type.
     * The second array value will hold the component instances for an individual entity.
     * <p>
     * For example, <br>
     * The position component will have index 0 <br>
     * The entity will have index 13 <br>
     * <p>
     * To retrieve the component for that entity, you do like so:
     * <pre>
     *     int PositionIndex = 0;
     *     int EntityIndex = 2;
     *     Component component = components[PositionIndex][EntityIndex]
     *
     * </pre>
     */
    protected Component[][] components = new Component[BeginningComponentCapacity][BeginningEntityCapacity];
    /**
     * This is the main storage of every existing entity.
     * The index of the entity determines the index it is placed within this array.
     */
    protected Entity[] entities = new Entity[BeginningEntityCapacity];
    /**
     * This is used to flag entities corresponding to the system.
     * The first array value will be the unique system type.
     * The second array value will hold the boolean instances for an individual entity.
     */
    protected boolean[][] systemFlags;

    /**
     * This holds the amount of times an entity has their system flag set.
     * The first array value will be the unique system type.
     * The second array value will hold the amount of times the flag has been set.
     */
    protected byte[][] systemFlagCount;

    /**
     * Constructs an `ECS` instance with the specified entity systems.
     *
     * @param arr An array of entity systems that define how entities are processed.
     */
    public ECS(EntitySystem... arr) {
        // Initialize the array of entity systems.
        this.systems = new EntitySystem[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int index = EntitySystem.getIndex(arr[i].getClass());
            this.systems[index] = arr[i];
            this.systems[index].ecs = this;
        }

        // Initialize the system flags array to track entity-system associations.
        this.systemFlags = new boolean[arr.length][BeginningEntityCapacity];

        // Initialize the system flag count array to keep track of flags set for entities.
        this.systemFlagCount = new byte[arr.length][BeginningEntityCapacity];
    }

    /**
     * Processes all entities using the registered entity systems.
     * This method iterates through all registered entity systems and processes entities
     * based on their individual system flags. Entities that have the system flag set
     * are processed by the corresponding entity system.
     */
    public boolean process() {
        for (int systemID = 0; systemID < systems.length; systemID++) {
            systems[systemID].process();
        }
        return true;
    }

    public boolean process2() {
        for (int systemID = 0; systemID < systems.length; systemID++) {
            systems[systemID].process2();
        }
        return true;
    }

    /**
     * Creates a new Entity and assigns it a unique index.
     *
     * @return The newly created Entity.
     */
    public Entity create() {
        // Pop an available index from the entity index queue.
        int newlyAssignedIndex = entityIndices.pop();

        // If the assigned index is beyond the current array capacity, expand the arrays.
        if (newlyAssignedIndex >= this.entities.length) {
            this.entities = Arrays.copyOf(this.entities, this.entities.length * 2);
            for (int i = 0; i < components.length; i++)
                this.components[i] = Arrays.copyOf(this.components[i], this.entities.length);
            for (int i = 0; i < systemFlags.length; i++)
                this.systemFlags[i] = Arrays.copyOf(this.systemFlags[i], this.entities.length);
            for (int i = 0; i < systemFlagCount.length; i++)
                this.systemFlagCount[i] = Arrays.copyOf(this.systemFlagCount[i], this.entities.length);
        }

        // Create a new Entity instance or reuse one from the entity pool.
        Entity entity;
        if (entityPool.isEmpty()) {
            entity = new Entity(this, newlyAssignedIndex);
        } else {
            entity = entityPool.pop();
            entity.setIndex(newlyAssignedIndex);
        }

        // Store the entity in the entities array and return it.
        entities[newlyAssignedIndex] = entity;
        return entity;
    }

    /**
     * Destroys an entity, making its index available for reuse.
     *
     * @param entity The entity to destroy.
     */
    public void destroy(Entity entity) {
        int index = entity.index;
        // Push the entity's index back to the entity index queue for reuse.
        entityIndices.push(index);

        // Mark the entity as null in the entities array.
        entities[index] = null;

        for (int i = 0; i < components.length; i++)
            this.components[i][index] = null;
        for (int i = 0; i < systemFlags.length; i++)
            this.systemFlags[i][index] = false;
        for (int i = 0; i < systemFlagCount.length; i++)
            this.systemFlagCount[i][index] = 0;

        // Reset the entity's index and push it back to the entity pool for potential reuse.
        entity.index = -1;
        entityPool.push(entity);
    }

    /**
     * Retrieves an entity by its index.
     *
     * @param index The index of the entity to retrieve.
     * @return The entity corresponding to the given index.
     */
    public Entity get(int index) {
        // Return the entity at the specified index.
        return entities[index];
    }

    /**
     * Adds a component to a specific entity.
     *
     * @param entityID  The ID of the entity.
     * @param component The component to add.
     * @return The ECS instance for method chaining.
     */
    public ECS addComponent(int entityID, Component component) {
        // Get the index of the component type.
        int index = component.index;

        // If the index exceeds the component array capacity, expand the arrays.
        if (index >= this.components.length)
            this.components = Arrays.copyOf(this.components, this.components.length * 2);

        // Add the component to the components array for the specified entity.
        this.components[index][entityID] = component;

        // Get the list of system IDs associated with this component and update system flags.
        FastStack<Integer> systemIDs = EntitySystem.getCorrespondingSystems(component.index);
        while (!systemIDs.isEmpty()) {
            int systemID = systemIDs.pop();
            EntitySystem system = systems[systemID];

            system.add(entityID);

            TypeArray arr = system.getArray(component.getClass());
            if (arr != null) {
                arr.set(entityID, component);
            }


            this.systemFlags[systemID][entityID] = true;
            this.systemFlagCount[systemID][entityID]++;
        }

        // Return the ECS instance for method chaining.
        return this;
    }

    /**
     * Removes a component from a specific entity.
     *
     * @param entityID       The ID of the entity.
     * @param componentIndex The component to remove.
     * @return The ECS instance for method chaining.
     */
    public ECS removeComponent(int entityID, int componentIndex) {

        // Remove the component from the components array for the specified entity.
        this.components[componentIndex][entityID] = null;

        // Get the list of system IDs associated with this component and update system flags.
        FastStack<Integer> systemIDs = EntitySystem.getCorrespondingSystems(componentIndex);
        while (!systemIDs.isEmpty()) {
            int systemID = systemIDs.pop();
            this.systemFlagCount[systemID][entityID]--;

            // If no more flags are set for the entity, reset the system flag.
            if (this.systemFlagCount[systemID][entityID] <= 0) {
                this.systemFlagCount[systemID][entityID] = 0;
                this.systemFlags[systemID][entityID] = false;
            }
        }

        // Return the ECS instance for method chaining.
        return this;
    }

    /**
     * Retrieves a component for a specific entity and component type.
     *
     * @param entityID       The ID of the entity.
     * @param componentIndex The index of the component type.
     * @param component      The component class to cast to.
     * @param <T>            The component type.
     * @return The component associated with the entity and component type.
     */
    public <T> T getComponent(int entityID, int componentIndex, Class<? extends Component> clazz) {
        return (T) components[componentIndex][entityID];
    }

}
