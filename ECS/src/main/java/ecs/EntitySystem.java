package ecs;

import collections.array.SwapOnRemoveIntArray;
import collections.bits.Bits;
import collections.bits.LongBits;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The `EntitySystem` class is an abstract base class for systems in an Entity-Component-System (ECS) architecture.
 * It manages entities, their components, and provides a framework for processing entities based on specific components.
 * Subclasses should extend this class and implement the `process` method to define the logic executed on each entity.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public abstract class EntitySystem {

    /**
     * A map that associates component class names with sets of systems that depend on those components.
     */
    private static final HashMap<String, HashSet<EntitySystem>> systems = new HashMap<>();

    /**
     * A set of active and disabled entity indices processed by this system.
     */
    private final Bits active;

    /**
     * A map of component mappers associated with their respective component classes.
     */
    private final HashMap<String, ComponentMapper> mappers;

    /**
     * An array of entity indices currently processed by this system.
     */
    private final SwapOnRemoveIntArray entities;

    /**
     * Bit flags indicating the presence of components for each entity processed by this system.
     */
    private final LongBits componentFlags;

    /**
     * Constructs an `EntitySystem` instance with the specified component classes.
     *
     * @param classes The component classes that this system depends on.
     */
    @SafeVarargs
    public EntitySystem(Class<? extends Component>... classes) {
        this.mappers = new HashMap<>();
        this.entities = new SwapOnRemoveIntArray();
        this.active = new Bits(Long.SIZE);
        this.componentFlags = new LongBits();

        // Initialize component mappers and associate systems with component classes
        for (var c : classes) {
            componentFlags.set(Component.getComponentIndex(c));
            mappers.put(c.getName(), new ComponentMapper(c));
            HashSet<EntitySystem> arr = systems.computeIfAbsent(c.getName(), v -> new HashSet<>());
            arr.add(this);
        }

        // Set type array fields to the corresponding component mappers
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            // Look for any component mapper fields
            if (field.getType().isAssignableFrom(ComponentMapper.class)) {
                if (field.getGenericType() instanceof ParameterizedType genericType) {
                    Type type = genericType.getActualTypeArguments()[0];
                    try {
                        // Set type array field to the specific type
                        field.set(this, mappers.get(type.getTypeName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Retrieves a set of systems associated with a given component class.
     *
     * @param clazz The component class.
     * @return An unmodifiable set of systems that depend on the specified component class.
     */
    public static Set<EntitySystem> getSystems(Class<? extends Component> clazz) {
        return Collections.unmodifiableSet(systems.get(clazz.getName()));
    }

    /**
     * Retrieves the component mapper associated with a given component class.
     *
     * @param clazz The component class.
     * @return The component mapper for the specified component class.
     */
    public ComponentMapper getMapper(Class<? extends Component> clazz) {
        return mappers.get(clazz.getName());
    }

    /**
     * Adds an entity to the system, marking it as active for processing.
     *
     * @param entityID The index of the entity to be added.
     */
    public void add(int entityID) {
        if (active.get(entityID))
            return;
        active.set(entityID);
        entities.add(entityID);
    }

    /**
     * Removes an entity from the system, marking it as inactive for processing.
     *
     * @param entityID The index of the entity to be removed.
     */
    public void remove(int entityID) {
        if (active.get(entityID)) {
            active.clear(entityID);
            entities.removeValue(entityID);
        }
    }

    /**
     * Checks if an entity can stay in the system based on its component flags.
     *
     * @param entity The entity to be checked.
     * @return `true` if the entity meets the component requirements, otherwise `false`.
     */
    public boolean canStay(Entity entity) {
        return componentFlags.anyMatch(entity.getComponentFlags());
    }

    /**
     * Processes all active entities, invoking the specific logic implemented in the subclass.
     */
    protected void process() {
        for (int i = 0; i < entities.size(); i++) {
            process(entities.get(i));
        }
    }

    /**
     * Abstract method to be implemented by subclasses, defining the logic to be executed on each entity.
     *
     * @param entityID The index of the entity to be processed.
     */
    public abstract void process(int entityID);
}