package infrastructure.collections.index;

import java.util.Arrays;

/**
 * A simple queue data structure designed for managing integer indices.
 * Pushing indices to this IndexQueue that have not been popped will cause some indices to never be used.
 * <p>
 * For example, if you have never popped any indices from this IndexQueue, but you push(17),
 * then the dequeue value will increase to 18 so that the next pop() value will be 18 and any
 * index less than that will never be popped. The purpose of this is to increase operational speed
 * by avoiding a loop to search the internal array for unused indices.
 * </p>
 *
 * @author Albert Beaupre
 */
public class IndexQueue {

    private int[] queue; // An array to store the unused indices.
    private int enqueue; // The current index for enqueue
    private int dequeue; // The current index for dequeue

    /**
     * Constructs an IndexQueue with the specified initial capacity.
     *
     * @param capacity The initial capacity of the IndexQueue.
     */
    public IndexQueue(int capacity) {
        this.queue = new int[capacity];
    }

    /**
     * Constructs an IndexQueue with a default initial capacity of 64.
     */
    public IndexQueue() {
        this(64);
    }

    /**
     * Pushes an index onto the queue.
     * If an index is greater than the previously dequeued value,
     * the dequeue then becomes 1 index greater than the index just pushed.
     *
     * <p>
     * For example: if you push(17), then the next number you dequeue will be 18.
     * </p>
     *
     * @param index The index to be enqueued.
     */
    public void push(int index) {
        if (enqueue >= queue.length) {
            int[] newArray = new int[queue.length * 2];
            System.arraycopy(queue, 0, newArray, 0, queue.length);
            queue = newArray;
        }

        if (index >= dequeue)
            dequeue = index + 1;

        queue[enqueue++] = index;
    }

    /**
     * Gets the value at a specific index in the queue.
     *
     * @param index The index in the queue.
     * @return The value at the specified index.
     */
    public int get(int index) {
        return queue[index];
    }

    /**
     * Pops and returns the next available index from the queue.
     *
     * @return The dequeued index.
     */
    public int pop() {
        if (enqueue > 0) {
            int index = (enqueue % this.queue.length) - 1;
            int oldIndex = this.queue[index];
            this.queue[--enqueue] = 0;
            return oldIndex;
        } else {
            return dequeue++;
        }
    }

    /**
     * Clears the IndexQueue, resetting all indices and the underlying array.
     */
    public void clear() {
        enqueue = 0;
        dequeue = 0;
        queue = new int[this.queue.length];
    }

    /**
     * Gets the number of indices currently in the queue.
     *
     * @return The number of indices in the queue.
     */
    public int size() {
        return enqueue;
    }

    /**
     * Returns a string representation of the IndexQueue.
     *
     * @return A string representing the state of the IndexQueue.
     */
    @Override
    public String toString() {
        return "IndexQueue [" +
                "queue=" + Arrays.toString(Arrays.copyOf(queue, enqueue)) +
                ", enqueue=" + enqueue +
                ", dequeue=" + dequeue +
                "]";
    }
}