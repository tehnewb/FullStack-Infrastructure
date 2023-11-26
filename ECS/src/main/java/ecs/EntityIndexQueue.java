package ecs;

import java.util.Arrays;

/**
 * The `EntityIndexQueue` class represents a queue of unused indices in an Entity-Component-System (ECS) architecture.
 * It is used for recycling and reusing entity indices, providing an efficient mechanism for managing available indices.
 *
 * @author Albert Beaupre
 * @version 1.0
 * @since 1.0
 */
public class EntityIndexQueue {

    /**
     * An array to store the unused indices in the queue.
     */
    private int[] queue;

    /**
     * The current index for enqueueing new values.
     */
    private int enqueue;

    /**
     * The current index for dequeueing values from the queue.
     */
    private int dequeue;

    /**
     * Constructs a new `EntityIndexQueue` instance with an initial capacity.
     */
    public EntityIndexQueue() {
        // Initialize the queue with an initial capacity of Long.SIZE.
        this.queue = new int[Long.SIZE];
    }

    /**
     * Pushes an index back into the queue for reuse.
     * If the specified value is greater than the previous popped values, the next value popped will be greater.
     *
     * @param index The index to push back into the queue.
     * @see #pop()
     */
    public void push(int index) {
        // If the enqueue index exceeds the current array length, double the array size.
        if (enqueue >= queue.length) {
            int[] newArray = new int[queue.length * 2];
            System.arraycopy(queue, 0, newArray, 0, queue.length);
            queue = newArray;
        }

        // Adjust the dequeue index if the pushed index is greater.
        if (index >= dequeue)
            dequeue = index + 1;

        // Add the index to the queue and increment the enqueue index.
        queue[enqueue++] = index;
    }

    /**
     * Retrieves the value at the specified index in the queue.
     *
     * @param index The index to retrieve from the queue.
     * @return The value at the specified index in the queue.
     */
    public int get(int index) {
        return queue[index];
    }

    /**
     * Pops an index from the queue.
     * If the queue is not empty, the most recently pushed index is returned.
     * If the queue is empty, a new index is generated.
     *
     * @return The popped index.
     */
    public int pop() {
        if (enqueue > 0) {
            // Calculate the index to pop and retrieve the old index.
            int index = (enqueue % this.queue.length) - 1;
            int oldIndex = this.queue[index];
            // Clear the old index and decrement the enqueue index.
            this.queue[--enqueue] = 0;
            return oldIndex;
        } else {
            // If the queue is empty, generate a new index.
            return dequeue++;
        }
    }

    /**
     * Clears the queue, resetting the enqueue and dequeue indices.
     */
    public void clear() {
        enqueue = 0;
        dequeue = 0;
        // Fill the queue array with zeros.
        Arrays.fill(queue, 0);
    }
}