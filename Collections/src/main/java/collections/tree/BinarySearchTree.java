package collections.tree;

/**
 * BinarySearchTree class represents a binary search tree data structure.
 * It stores elements of type T (where T extends Comparable<T>) in a hierarchical structure,
 * allowing efficient insertion, deletion, and searching operations.
 *
 * @param <T> The type of elements stored in the binary search tree, must implement Comparable interface.
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class BinarySearchTree<T extends Comparable<T>> {

    private Node<T> root;

    /**
     * Inserts a new element into the binary search tree.
     *
     * @param data The element to be inserted.
     */
    public void insert(T data) {
        // If the tree is empty, create a new root node.
        if (root == null) {
            root = new Node<>(data, null, null);
            return;
        }

        Node<T> current = root;
        Node<T> parent = null;

        // Traverse the tree to find the correct position for insertion.
        while (current != null) {
            parent = current;

            if (data.compareTo(current.data) < 0) {
                current = current.left;
            } else if (data.compareTo(current.data) > 0) {
                current = current.right;
            } else {
                // Element already exists in the tree, no duplicates allowed.
                return;
            }
        }

        // Insert the new element based on its comparison with the parent node.
        if (data.compareTo(parent.data) < 0) {
            parent.left = new Node<>(data, null, null);
        } else {
            parent.right = new Node<>(data, null, null);
        }
    }

    /**
     * Searches for a specified element in the binary search tree.
     *
     * @param data The element to be searched for.
     * @return true if the element is found, false otherwise.
     */
    public boolean search(T data) {
        Node<T> current = root;
        while (current != null) {
            int comparison = data.compareTo(current.data);

            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    /**
     * Deletes a specified element from the binary search tree.
     *
     * @param data The element to be deleted.
     */
    public void delete(T data) {
        // If the tree is empty, nothing to delete.
        if (root == null) {
            return;
        }

        Node<T> current = root;
        Node<T> parent = null;

        // Traverse the tree to find the node to be deleted.
        while (current != null) {
            int comparison = data.compareTo(current.data);

            if (comparison == 0) {
                // Perform deletion based on the number of children the node has.
                performDeletion(current, parent);
                return;
            } else if (comparison < 0) {
                // Search in the left subtree
                parent = current;
                current = current.left;
            } else {
                // Search in the right subtree
                parent = current;
                current = current.right;
            }
        }
    }

    // Helper method to find the in-order successor of a node.
    private Node<T> findSuccessor(Node<T> root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    // Helper method to delete the in-order successor of a node.
    private Node<T> deleteSuccessor(Node<T> root) {
        if (root.left == null) {
            return root.right;
        }
        root.left = deleteSuccessor(root.left);
        return root;
    }

    // Helper method to perform deletion based on the number of children a node has.
    private void performDeletion(Node<T> current, Node<T> parent) {
        if (current.left == null) {
            performLeftDeletion(current, parent);
        } else if (current.right == null) {
            performRightDeletion(current, parent);
        } else {
            performTwoChildrenDeletion(current);
        }
    }

    // Helper method to perform deletion when the node has no left child.
    private void performLeftDeletion(Node<T> current, Node<T> parent) {
        if (parent == null) {
            // Node to be deleted is the root.
            root = current.right;
        } else if (current == parent.left) {
            parent.left = current.right;
        } else {
            parent.right = current.right;
        }
    }

    // Helper method to perform deletion when the node has no right child.
    private void performRightDeletion(Node<T> current, Node<T> parent) {
        if (parent == null) {
            // Node to be deleted is the root.
            root = current.left;
        } else if (current == parent.left) {
            parent.left = current.left;
        } else {
            parent.right = current.left;
        }
    }

    // Helper method to perform deletion when the node has two children.
    private void performTwoChildrenDeletion(Node<T> current) {
        Node<T> successor = findSuccessor(current.right);
        current.data = successor.data;
        current.right = deleteSuccessor(current.right);
    }

    /**
     * Returns a string representation of the binary search tree in in-order traversal.
     *
     * @return String representation of the binary search tree.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (root == null) {
            return result.toString();
        }

        Node<T> current = root;
        Node<T> prev = null;

        while (current != null) {
            if (current.left == null) {
                // Print the current node and its left and right data if they exist
                result.append("Node: ").append(current.data);
                if (current.right != null) {
                    result.append(", Right: ").append(current.right.data);
                }
                result.append("\n");

                current = current.right;
            } else {
                // Find the in-order predecessor
                prev = current.left;
                while (prev.right != null && prev.right != current) {
                    prev = prev.right;
                }

                if (prev.right == null) {
                    // Make the current node the right child of the in-order predecessor
                    prev.right = current;
                    current = current.left;
                } else {
                    // Revert the changes made in the previous block
                    prev.right = null;

                    // Print the current node and its left and right data if they exist
                    result.append("Node: ").append(current.data);
                    result.append(", Left: ").append(current.left.data);
                    if (current.right != null) {
                        result.append(", Right: ").append(current.right.data);
                    }
                    result.append("\n");

                    current = current.right;
                }
            }
        }

        return result.toString();
    }

    /**
     * Private static nested class representing a node in the binary search tree.
     * Each node contains data of type T, a reference to its left child, and a reference to its right child.
     *
     * @param <T> The type of data stored in the node.
     */
    private static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;

        /**
         * Constructor to create a new node with the given data, left child, and right child.
         *
         * @param data  The data to be stored in the node.
         * @param left  The left child of the node.
         * @param right The right child of the node.
         */
        Node(T data, Node<T> left, Node<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
}