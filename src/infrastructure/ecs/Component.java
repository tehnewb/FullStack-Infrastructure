package infrastructure.ecs;

import java.util.HashMap;

/**
 * The `Component` class is a fundamental element of an Entity-Component-System (ECS) framework.
 * It represents the individual pieces of data that can be attached to entities. Components
 * contain data but have no behavior on their own.
 * <p>
 * Each component has a unique index within the ECS framework, allowing efficient storage
 * and retrieval of component data associated with entities.
 * <p>
 * Subclasses of `Component` are expected to define the data and properties specific to a
 * particular aspect of an entity, such as position, velocity, or health.
 * <p>
 * Example usage:
 * <pre>
 *     class PositionComponent extends Component {
 *          public int x;
 *          public int y;
 *     }
 * </pre>
 *
 * @author Albert Beaupre
 * @see infrastructure.ecs.EntitySystem
 * @see infrastructure.ecs.ECS
 */
public abstract class Component {

    // Stores the index associated with each component class name.
    private static final HashMap<String, Integer> Indices = new HashMap<>();

    // The unique index of this component within the ECS framework.
    protected final int index;

    /**
     * Constructs a component and assigns it a unique index.
     */
    public Component() {
        this.index = getIndex(this.getClass());
    }

    /**
     * Retrieves the unique index associated with a component class.
     *
     * @param clazz The component class for which to retrieve the index.
     * @return The unique index associated with the component class.
     */
    public static int getIndex(Class<? extends Component> clazz) {
        return Indices.computeIfAbsent(clazz.getName(), v -> Indices.size());
    }

    /**
     * Gets the unique index assigned to this component.
     *
     * @return The unique index of this component within the ECS framework.
     */
    public int getIndex() {
        return index;
    }
}