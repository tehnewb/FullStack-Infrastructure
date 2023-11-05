package infrastructure.collections.faststack;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * A custom Spliterator for traversing the elements of a FastStack in a
 * streaming fashion. It allows for sequential processing of the elements.
 *
 * @param <T> The type of elements in the FastStack.
 * @author Albert Beaupre
 */
public class FastStackSpliterator<T> implements Spliterator<T> {

    private final FastStack<T> stack; // The FastStack to traverse
    private FastStackNode<T> current;  // The current node being processed

    /**
     * Constructs a new FastStackSpliterator with the specified FastStack
     * and the root node as the starting point for traversal.
     *
     * @param stack The FastStack to traverse.
     * @param root  The root node of the FastStack.
     */
    public FastStackSpliterator(FastStack<T> stack, FastStackNode<T> root) {
        this.stack = stack;
        this.current = root;
    }

    /**
     * Tries to advance to the next element in the FastStack and performs
     * the given action on it.
     *
     * @param action The action to be performed on the next element.
     * @return true if there is a next element, false otherwise.
     */
    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (current != null) {
            T data = current.data;
            current = current.child;
            action.accept(data);
            return true;
        }
        return false;
    }

    /**
     * Performs the given action on all remaining elements in the FastStack.
     *
     * @param action The action to be performed on each remaining element.
     */
    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        while (current != null) {
            T data = current.data;
            current = current.child;
            action.accept(data);
        }
    }

    /**
     * Splits this Spliterator, not supported in this implementation.
     *
     * @return null since splitting is not supported.
     */
    @Override
    public Spliterator<T> trySplit() {
        return null;
    }

    /**
     * Estimates the size of the FastStack being traversed.
     *
     * @return The estimated size of the FastStack.
     */
    @Override
    public long estimateSize() {
        return stack.size;
    }

    /**
     * Specifies the characteristics of this Spliterator, indicating that it is
     * ordered and sized.
     *
     * @return A combination of Spliterator characteristics flags.
     */
    @Override
    public int characteristics() {
        return Spliterator.ORDERED | Spliterator.SIZED;
    }
}