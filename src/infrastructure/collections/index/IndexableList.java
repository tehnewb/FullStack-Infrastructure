package infrastructure.collections.index;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The IndexableList class represents a list that can store indexable objects and manage their indices.
 *
 * @param <T> the type of indexable to store in the list
 * @author Albert Beaupre
 */
@SuppressWarnings("unchecked")
public class IndexableList<T extends Indexable> implements Collection<T> {
    private final IndexQueue indexQueue;
    private T[] indexables; // Internal array to store indexables
    private int size;

    /**
     * Constructs an IndexableList with the specified capacity.
     *
     * @param capacity the maximum number of indexables the list can hold
     * @throws IllegalArgumentException if the capacity is less than or equal to zero
     */
    public IndexableList(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be greater than zero");
        this.indexables = (T[]) new Indexable[capacity];
        this.indexQueue = new IndexQueue();
    }

    /**
     * Adds an indexable to the list and assigns it the next available index.
     * If the list is full, the array is resized to accommodate more indexables.
     *
     * @param indexable the indexable to add to the list
     * @return true if the indexable was added, false otherwise
     */
    @Override
    public boolean add(T indexable) {
        if (size >= indexables.length) {
            T[] newArr = (T[]) new Indexable[this.indexables.length * 2];
            System.arraycopy(indexables, 0, newArr, 0, indexables.length);
            this.indexables = newArr;
        }

        int index = this.indexQueue.pop();

        indexables[index] = indexable;
        indexable.setIndex(index);
        size++;
        return true;
    }

    /**
     * Removes an indexable from the list and sets its index to -1.
     * If the indexable is not found in the list, no action is taken.
     *
     * @param indexable the indexable to remove from the list
     * @return true if the indexable was removed, false otherwise
     * @throws NullPointerException if the indexable provided is null
     */
    @Override
    public boolean remove(Object indexable) {
        if (indexable == null) throw new NullPointerException("Cannot remove NULL indexable from IndexableList.");

        if (indexable instanceof Indexable) {
            int index = ((Indexable) indexable).getIndex();
            if (index >= 0 && index < indexables.length && indexables[index] == indexable) {
                indexables[index] = null;
                ((Indexable) indexable).setIndex(-1);
                this.indexQueue.push(index);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an indexable from the list and sets its index to -1.
     * If the indexable is not found in the list, no action is taken.
     *
     * @param index the index of the indexable to remove
     * @return the indexable removed
     */
    public T remove(int index) {
        T indexable = indexables[index];
        if (indexable != null) {
            indexables[index] = null;
            this.indexQueue.push(index);
            size--;
        }
        return indexable;
    }

    /**
     * Sets the specified Entity at the specified index of this IndexableList.
     *
     * @param index  The index to place the specified Entity in.
     * @param entity The Entity to place within this IndexableList.
     */
    public void set(int index, T entity) {
        T previous = this.get(index);

        this.indexables[index] = entity;
        if (previous != null) {
            this.size++;

            if (entity == null) {
                this.indexQueue.push(index);
            }
        }
    }

    /**
     * Checks if the list contains all the indexables in the specified collection.
     *
     * @param c the collection of indexables to check for existence in the list
     * @return true if all the indexables in the specified collection are in the list, false otherwise
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
     * Adds all indexables from the specified collection to this list.
     *
     * @param c the collection of indexables to add to the list
     * @return true if any indexables were added to the list, false otherwise
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T indexable : c) {
            if (add(indexable)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Removes all indexables from the list that are also in the specified collection.
     *
     * @param c the collection of indexables to remove from the list
     * @return true if any indexables were removed from the list, false otherwise
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
     * Retains only the indexables in the list that are also in the specified collection.
     * In other words, removes from the list all indexables that are not in the specified collection.
     *
     * @param c the collection of indexables to retain in the list
     * @return true if the list was modified as a result of this operation, false otherwise
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < indexables.length; i++) {
            if (indexables[i] != null && !c.contains(indexables[i])) {
                indexables[i].setIndex(-1);
                indexables[i] = null;
                modified = true;
                size--;
            }
        }
        return modified;
    }


    /**
     * Gets the indexable at the specified index in the list.
     *
     * @param index the index of the indexable to retrieve
     * @return the indexable at the specified index, or null if the index is out of bounds
     */
    public T get(int index) {
        if (index >= 0 && index < indexables.length) return (T) indexables[index];
        else throw new ArrayIndexOutOfBoundsException("Invalid index in IndexableList");
    }

    /**
     * Provides an iterator to iterate over the non-null indexables in the list.
     *
     * @return an iterator for the non-null indexables in the list
     */
    @Override
    public Iterator<T> iterator() {
        return new IndexableIterator();
    }

    /**
     * Returns the number of indexables in the list.
     *
     * @return the number of indexables in the list
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Returns the maximum number of indexables there can be in this list.
     *
     * @return the maximum number of indexables there can be in this list.
     */
    public int capacity() {
        return indexables.length;
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
        if (o instanceof Indexable) {
            int index = ((Indexable) o).getIndex();
            return index >= 0 && index < indexables.length && indexables[index] == o;
        }
        return false;
    }

    /**
     * Returns an array containing all the indexables in the list.
     *
     * @return an array containing all the indexables in the list
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(indexables, size());
    }

    /**
     * Returns an array containing all the indexables in the list.
     *
     * @param a the array into which the indexables are to be stored, if it is big enough; otherwise, a new array is created
     * @return an array containing all the indexables in the list
     */
    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size()) {
            // If the provided array is too small, create a new one of the same type and copy the elements.
            return (T1[]) Arrays.copyOf(indexables, size(), a.getClass());
        }
        // If the provided array is large enough, copy the elements.
        System.arraycopy(indexables, 0, a, 0, size());
        return a;
    }

    /**
     * Removes all indexables from the list.
     */
    @Override
    public void clear() {
        Arrays.fill(indexables, null);
        indexQueue.clear();
        for (int i = 0; i < capacity(); i++)
            this.indexQueue.push(i);
        size = 0;
    }

    /**
     * The IndexableIterator class is a custom iterator for the IndexableList.
     */
    private class IndexableIterator implements Iterator<T> {
        private int currentIndex = 0;

        /**
         * Checks if there are more non-null indexables in the list.
         *
         * @return true if there are more non-null indexables, false otherwise
         */
        @Override
        public boolean hasNext() {
            return currentIndex < indexables.length;
        }

        /**
         * Retrieves the next non-null indexable in the list.
         *
         * @return the next non-null indexable
         * @throws NoSuchElementException if there are no more non-null indexables
         */
        @Override
        public T next() {
            return (T) indexables[currentIndex++];
        }

        @Override
        public void remove() {
            indexables[currentIndex] = null;
        }
    }
}