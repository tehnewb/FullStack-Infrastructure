package ecs;

import collections.bits.LongBits;

/**
 * The `Entity` class represents an entity in an Entity-Component-System (ECS) architecture. Entities are fundamental
 * objects that can have various components attached to them, defining their behavior and data.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class Entity {

    /**
     * Bit flags representing the presence of components attached to this entity.
     */
    private final LongBits componentFlags = new LongBits();
    /**
     * The unique index assigned to this entity within the ECS.
     */
    private int index;

    /**
     * Retrieves the unique index of this entity.
     *
     * @return The unique index assigned to the entity.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the specific index of this Entity.
     *
     * @param index the index to set.
     */
    protected void setIndex(int index) {
        this.index = index;
    }

    /**
     * Retrieves the bit flags representing the presence of components attached to this entity.
     *
     * @return The bit flags indicating the presence of components.
     */
    public LongBits getComponentFlags() {
        return componentFlags;
    }
}