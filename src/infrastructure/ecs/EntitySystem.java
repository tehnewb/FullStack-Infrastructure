package infrastructure.ecs;

import infrastructure.collections.faststack.FastStack;

import java.util.HashMap;

/**
 * The `EntitySystem` class is a fundamental component of an Entity-Component-System (ECS) framework.
 * It is responsible for managing and processing entities based on specific component classes.
 * Entity systems define how entities with certain components should be processed within the ECS framework.
 * <p>
 * This class provides functionality to associate entity systems with component classes,
 * manage the relationships between components and systems, and process entities accordingly.
 * <p>
 * Subclasses of `EntitySystem` should implement the `process` method to define the processing logic
 * for entities within the entity system.
 * <p>
 * Example usage:
 * <pre>
 * class MovementSystem extends EntitySystem {
 *      public MovementSystem() {
 *          super(PositionComponent.class, VelocityComponent.class);
 *      }
 *
 *      public void process(int entityID) {
 *          // Implement logic to process entities with PositionComponent and VelocityComponent.
 *      }
 * }
 * </pre>
 *
 * @author Albert Beaupre
 * @see infrastructure.ecs.ECS
 * @see infrastructure.ecs.Component
 */
public abstract class EntitySystem {

    // Stores the index associated with each entity system class name.
    private static final HashMap<String, Integer> Indices = new HashMap<>();

    // Stores the systems associated with each component type index.
    private static final HashMap<Integer, FastStack<Integer>> CorrespondingSystems = new HashMap<>();

    // The ECS instance to which this entity system belongs.
    protected ECS ecs;

    /**
     * Constructs an entity system with a set of component classes.
     *
     * @param componentClasses An array of component classes that this system processes.
     */
    @SafeVarargs
    public EntitySystem(Class<? extends Component>... componentClasses) {
        for (Class<? extends Component> clazz : componentClasses) {
            // Get the index of the component class.
            int componentIndex = Component.getIndex(clazz);

            // Retrieve or create a stack of system indices associated with the component index.
            FastStack<Integer> systemIndices = CorrespondingSystems.getOrDefault(componentIndex, new FastStack<>());

            // Push the index of this entity system onto the stack.
            systemIndices.push(getIndex(this.getClass()));

            // Update the corresponding systems mapping for the component index.
            CorrespondingSystems.put(componentIndex, systemIndices);
        }
    }

    /**
     * Retrieves the index associated with an entity system class.
     *
     * @param clazz The entity system class for which to retrieve the index.
     * @return The index associated with the entity system class.
     */
    public static int getIndex(Class<? extends EntitySystem> clazz) {
        return Indices.computeIfAbsent(clazz.getName(), v -> Indices.size());
    }

    /**
     * Retrieves a stack copy of entity system indices corresponding to a component index.
     *
     * @param componentIndex The index of the component type.
     * @return A stack copy of entity system indices associated with the component index.
     */
    public static FastStack<Integer> getCorrespondingSystems(int componentIndex) {
        return CorrespondingSystems.getOrDefault(componentIndex, new FastStack<>()).clone();
    }

    /**
     * Defines the processing logic for entities within the entity system.
     *
     * @param entityID The index of the entity to process.
     */
    public abstract void process(int entityID);

}