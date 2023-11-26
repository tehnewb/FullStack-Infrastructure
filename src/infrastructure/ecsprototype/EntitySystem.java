package infrastructure.ecsprototype;

import infrastructure.collections.TypeArray;
import infrastructure.collections.faststack.FastStack;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
 * @see infrastructure.ecsprototype.ECS
 * @see infrastructure.ecsprototype.Component
 */
public abstract class EntitySystem {

    // Stores the index associated with each entity system class name.
    private static final HashMap<String, Integer> Indices = new HashMap<>();

    // Stores the systems associated with each component type index.
    private static final HashMap<Integer, FastStack<Integer>> CorrespondingSystems = new HashMap<>();
    public int[] ids = new int[10];
    // The ECS instance to which this entity system belongs.
    protected ECS ecs;
    private int size;


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

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            // Look for any type array fields
            if (field.getType().isAssignableFrom(TypeArray.class)) {
                if (field.getGenericType() instanceof ParameterizedType genericType) {
                    Type type = genericType.getActualTypeArguments()[0];
                    try {
                        //set type array field to the specific type
                        field.set(this, field.getType().getDeclaredConstructors()[0].newInstance(type));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
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

    public void add(int element) {
        if (size == ids.length) {
            int[] copy = new int[ids.length * 2];
            System.arraycopy(ids, 0, copy, 0, ids.length);
            ids = copy;
        }
        ids[size++] = element;
    }

    public <T> TypeArray<T> getArray(Class<T> clazz) {
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            // Look for any type array fields
            if (field.getType().isAssignableFrom(TypeArray.class)) {
                if (field.getGenericType() instanceof ParameterizedType genericType) {
                    Type type = genericType.getActualTypeArguments()[0];
                    if (type.equals(clazz)) {
                        try {
                            return (TypeArray<T>) field.get(this);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean process() {
        for (int i = 0; i < size; i++) {
            process(ids[i]);
        }
        return true;
    }

    public void process2() {
        int[] ids = new int[size];
        for (int i = 0; i < size; i++) {
            ids[i] = this.ids[i];
            process(ids[i]);
        }
    }

    /**
     * Defines the processing logic for entities within the entity system.
     *
     * @param entityID The index of the entity to process.
     */
    public abstract void process(int entityID);

}