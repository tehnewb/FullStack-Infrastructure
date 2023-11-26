package ecs;

import collections.array.ResizingArray;

/**
 * The `ComponentMapper` class is a specialized array for mapping components to their respective entities in an
 * Entity-Component-System (ECS) architecture. It extends the `ResizingArray` class to efficiently store and retrieve
 * components based on their entity indices.
 *
 * @param <T> The type of components to be stored in the mapper.
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class ComponentMapper<T> extends ResizingArray<T> {

    /**
     * Constructs a new `ComponentMapper` instance for a specific component type.
     *
     * @param type The class representing the type of components to be stored.
     */
    public ComponentMapper(Class<T> type) {
        super(type, Short.SIZE);
    }
}