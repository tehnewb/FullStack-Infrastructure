package infrastructure.collections.faststack;

import java.util.Iterator;

/**
 * An iterator for iterating over the elements of a FastStack.
 *
 * @param <T> The type of elements stored in the FastStack.
 * @author Albert Beaupre
 */
public class FastStackIterator<T> implements Iterator<T> {
    private final FastStack<T> stack; // Reference to the FastStack being iterated
    private FastStackNode<T> current;

    /**
     * Constructs a FastStackIterator for the specified FastStack.
     *
     * @param stack The FastStack to iterate over.
     */
    public FastStackIterator(FastStack<T> stack, FastStackNode<T> root) {
        this.stack = stack;
        this.current = root;
    }

    /**
     * Checks if there are more elements to iterate over.
     *
     * @return true if there are more elements to iterate, false otherwise.
     */
    @Override
    public boolean hasNext() {
        return current.child != null;
    }

    /**
     * Retrieves the next element in the iteration.
     *
     * @return The next element in the iteration.
     * @throws java.util.NoSuchElementException if there are no more elements to iterate.
     */
    @Override
    public T next() {
        return (current = current.child).data;
    }

    /**
     * Removes the last element returned by the iterator from the underlying FastStack.
     * This method can be called only once per call to `next()`.
     */
    @Override
    public void remove() {
        stack.removeNode(current);
        current = current.child;
    }
}