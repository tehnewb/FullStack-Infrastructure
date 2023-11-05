package infrastructure.collections.faststack;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A fast implementation of a stack data structure that supports
 * adding and removing elements efficiently, with a focus on
 * performance. It allows for generic elements (of type T) to be
 * pushed and popped from the stack.
 *
 * @param <T> The type of elements stored in the stack.
 * @author Albert Beaupre
 */
public class FastStack<T> implements Iterable<T>, Cloneable {

    public FastStackNode<T> root; // Points to the front of the stack
    public int size;

    /**
     * Pushes an element onto the stack.
     *
     * @param item The element to be pushed onto the stack.
     */
    public FastStackNode<T> push(T item) {
        FastStackNode<T> node = new FastStackNode<>(item, null, root);
        if (root != null)
            this.root.parent = node;
        this.root = node;
        this.size++;
        return node;
    }

    /**
     * Pops and returns the element at the top of the stack.
     *
     * @return The element at the top of the stack, or null if the
     * stack is empty.
     */
    public T pop() {
        if (isEmpty())
            return null;
        T data = root.data;
        this.root = this.root.child;
        this.size--;
        return data;
    }

    /**
     * Removes and returns the top node of the stack.
     *
     * @return The top node of the stack, or null if the stack is empty.
     */
    public FastStackNode<T> popNode() {
        if (isEmpty())
            return null;
        FastStackNode<T> oldRoot = root;
        this.root = this.root.child;
        this.size--;
        return oldRoot;
    }

    /**
     * Removes the specified item from this stack.
     *
     * @param item The item to remove.
     */
    public void remove(T item) {
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            T t = it.next();
            if (t.equals(item)) {
                it.remove();
                break;
            }
        }
    }

    /**
     * Removes a specific node from the stack.
     *
     * @param node The node to be removed from the stack.
     */
    public void removeNode(FastStackNode<T> node) {
        if (node == null || isEmpty())
            return;

        FastStackNode<T> parent = node.parent;
        FastStackNode<T> child = node.child;

        if (parent != null) {
            parent.child = child;
        } else {
            root = root.child;
            if (root != null)
                root.parent = null;
        }
        if (child != null) {
            child.parent = parent;
        }
        size--;
    }

    /**
     * Clears all elements from the stack.
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Checks if the stack is empty.
     *
     * @return true if the stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a stream of elements from the stack. The stream allows
     * for sequential or parallel processing of the elements.
     *
     * @return A Stream of elements in the stack.
     */
    public Stream<T> stream() {
        return StreamSupport.stream(new FastStackSpliterator<>(this, root), false);
    }

    /**
     * Provides an iterator for the stack to allow for iterating
     * over the elements in a for-each loop.
     *
     * @return An iterator for the stack.
     */
    @Override
    public FastStackIterator<T> iterator() {
        return new FastStackIterator<T>(this, root);
    }

    /**
     * Returns a string representation of the elements in the stack. The elements are
     * enclosed in square brackets, separated by commas, and ordered from the top
     * of the stack to the bottom.
     *
     * @return A string representation of the stack.
     */
    @Override
    public String toString() {
        if (isEmpty())
            return "FastStack[]";

        StringBuilder sb = new StringBuilder("FastStack[");
        FastStackNode<T> current = root;

        while (current != null) {
            sb.append(current.data);
            current = current.child;
            if (current != null) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public FastStack<T> clone() {
        FastStack<T> clone = new FastStack<>();
        clone.size = size;
        clone.root = root;
        return clone;
    }
}