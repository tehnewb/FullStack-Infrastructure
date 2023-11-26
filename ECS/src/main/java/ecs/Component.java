package ecs;

import java.util.HashMap;

/**
 * The `Component` class is a base class for components in an Entity-Component-System (ECS) architecture.
 * Components represent data and behavior that can be attached to entities. Each component is assigned a unique index
 * within the ECS to efficiently identify and manage them.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class Component {

    /**
     * A map that associates component class names with unique integer indices.
     */
    private static final HashMap<String, Integer> components = new HashMap<>();

    /**
     * The unique index assigned to this component within the ECS.
     */
    private final int index;

    /**
     * Constructs a new `Component` instance, assigning a unique index based on its class.
     */
    public Component() {
        this.index = getComponentIndex(this.getClass());
    }

    /**
     * Retrieves the unique index for a given component class.
     * If the class has not been encountered before, a new index is assigned.
     *
     * @param clazz The component class for which to retrieve the index.
     * @return The unique index assigned to the component class.
     */
    public static int getComponentIndex(Class<? extends Component> clazz) {
        return components.computeIfAbsent(clazz.getName(), v -> components.size() + 1);
    }

    /**
     * Retrieves the unique index of this component.
     *
     * @return The unique index of the component.
     */
    public int getIndex() {
        return index;
    }
}