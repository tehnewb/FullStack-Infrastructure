package infrastructure.collections.faststack;

/**
 * Represents a node in the FastStack data structure. Each node holds a
 * piece of data, and maintains references to its parent and child nodes.
 *
 * @param <T> The type of data stored in the node.
 * @author Albert Beaupre
 */
public class FastStackNode<T> {

    public T data;               // The data stored in the node
    public FastStackNode<T> parent; // Reference to the parent node
    public FastStackNode<T> child;  // Reference to the child node

    /**
     * Constructs a new FastStackNode with the specified data and links to
     * its parent and child nodes.
     *
     * @param data   The data to be stored in the node.
     * @param parent The parent node of this node.
     * @param child  The child node of this node.
     */
    public FastStackNode(T data, FastStackNode<T> parent, FastStackNode<T> child) {
        this.data = data;
        this.parent = parent;
        this.child = child;
    }

    @Override
    public String toString() {
        return "FastStackNode[" + data + "]";
    }
}