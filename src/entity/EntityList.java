package entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The EntityList class represents a list that can store entities and manage their indices.
 *
 * @param <T> the type of entity to store in the list
 * @author Albert Beaupre
 */
@SuppressWarnings("unchecked")
public class EntityList<T extends Entity> implements Collection<T> {
    private final Entity[] entities; // Internal array to store entities
    private long unusedIndices;
    private int size;

    /**
     * Constructs an EntityList with the specified capacity.
     *
     * @param capacity the maximum number of entities the list can hold
     * @throws IllegalArgumentException if the capacity is less than or equal to zero
     */
    public EntityList(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be greater than zero");
        this.entities = new Entity[capacity];
        this.unusedIndices = (1L << capacity) - 1;
    }

    /**
     * Adds an entity to the list and assigns it the next available index.
     * If the list is full, the entity will not be added.
     *
     * @param entity the entity to add to the list
     * @return true if the entity was added, false otherwise
     * @throws IllegalStateException if the list is full
     */
    @Override
    public boolean add(T entity) {
        if (unusedIndices == 0)
            throw new IllegalStateException("EntityList is full. Cannot add more entities.");

        int index = Long.numberOfTrailingZeros(unusedIndices);
        unusedIndices &= ~(1L << index); // Mark the index as used
        entities[index] = entity;
        entity.setIndex(index);
        size++;
        return true;
    }

    /**
     * Removes an entity from the list and sets its index to -1.
     * If the entity is not found in the list, no action is taken.
     *
     * @param entity the entity to remove from the list
     * @return true if the entity was removed, false otherwise
     * @throws NullPointerException if the entity provided is null
     */
    @Override
    public boolean remove(Object entity) {
        if (entity == null)
            throw new NullPointerException("Cannot remove NULL entity from EntityList.");

        if (entity instanceof Entity) {
            int index = ((Entity) entity).getIndex();
            if (index >= 0 && index < entities.length && entities[index] == entity) {
                entities[index] = null;
                unusedIndices |= (1L << index); // Mark the index as unused
                ((Entity) entity).setIndex(-1);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the list contains all the entities in the specified collection.
     *
     * @param c the collection of entities to check for existence in the list
     * @return true if all the entities in the specified collection are in the list, false otherwise
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds all entities from the specified collection to this list.
     *
     * @param c the collection of entities to add to the list
     * @return true if any entities were added to the list, false otherwise
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T entity : c) {
            if (add(entity)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Removes all entities from the list that are also in the specified collection.
     *
     * @param c the collection of entities to remove from the list
     * @return true if any entities were removed from the list, false otherwise
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Retains only the entities in the list that are also in the specified collection.
     * In other words, removes from the list all entities that are not in the specified collection.
     *
     * @param c the collection of entities to retain in the list
     * @return true if the list was modified as a result of this operation, false otherwise
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] != null && !c.contains(entities[i])) {
                entities[i] = null;
                unusedIndices |= (1L << i); // Mark the index as unused
                ((Entity) entities[i]).setIndex(-1);
                modified = true;
                size--;
            }
        }
        return modified;
    }


    /**
     * Gets the entity at the specified index in the list.
     *
     * @param index the index of the entity to retrieve
     * @return the entity at the specified index, or null if the index is out of bounds
     */
    @SuppressWarnings("unchecked")
    public T getEntity(int index) {
        if (index >= 0 && index < entities.length) return (T) entities[index];
        else throw new ArrayIndexOutOfBoundsException("Invalid index in EntityList");
    }

    /**
     * Provides an iterator to iterate over the non-null entities in the list.
     *
     * @return an iterator for the non-null entities in the list
     */
    @Override
    public Iterator<T> iterator() {
        return new EntityIterator();
    }

    /**
     * Returns the number of entities in the list.
     *
     * @return the number of entities in the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Checks if the list contains the specified object.
     *
     * @param o the object to check for existence in the list
     * @return true if the object is in the list, false otherwise
     */
    @Override
    public boolean contains(Object o) {
        if (o instanceof Entity) {
            int index = ((Entity) o).getIndex();
            return index >= 0 && index < entities.length && entities[index] == o;
        }
        return false;
    }

    /**
     * Returns an array containing all of the entities in the list.
     *
     * @return an array containing all of the entities in the list
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(entities, size());
    }

    /**
     * Returns an array containing all of the entities in the list.
     *
     * @param a the array into which the entities are to be stored, if it is big enough; otherwise, a new array is created
     * @return an array containing all of the entities in the list
     */
    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size()) {
            // If the provided array is too small, create a new one of the same type and copy the elements.
            return (T1[]) Arrays.copyOf(entities, size(), a.getClass());
        }
        // If the provided array is large enough, copy the elements.
        System.arraycopy(entities, 0, a, 0, size());
        return a;
    }

    /**
     * Removes all entities from the list.
     */
    @Override
    public void clear() {
        Arrays.fill(entities, null);
        unusedIndices = (1L << entities.length) - 1;
    }

    /**
     * The EntityIterator class is a custom iterator for the EntityList.
     */
    private class EntityIterator implements Iterator<T> {
        private int currentIndex = 0;

        /**
         * Checks if there are more non-null entities in the list.
         *
         * @return true if there are more non-null entities, false otherwise
         */
        @Override
        public boolean hasNext() {
            return currentIndex < entities.length;
        }

        /**
         * Retrieves the next non-null entity in the list.
         *
         * @return the next non-null entity
         * @throws NoSuchElementException if there are no more non-null entities
         */
        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            return (T) entities[currentIndex++];
        }
    }
}
