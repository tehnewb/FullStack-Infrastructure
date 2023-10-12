package infrastructure.io.pool;

/**
     * The PoolFactory interface defines methods for initializing objects, closing objects, creating objects,
     * and checking for staleness.
     */
    public interface PoolFactory<T> {
        /**
         * Initialize an object before it is placed in the pool.
         *
         * @param object The object to initialize.
         */
        void init(T object);

        /**
         * Close and release resources associated with an object.
         *
         * @param object The object to close.
         */
        void close(T object);

        /**
         * Check if an object is stale, idle or unused for a certain period of time, and during this idle time,
         * it may have become outdated, no longer valid, or not fit for reuse.
         *
         * @param object The object to check for staleness.
         * @return true if the object is stale, false otherwise.
         */
        boolean isStale(T object);

        /**
         * Create a new instance of the object.
         *
         * @return A new instance of the object.
         */
        T create();
    }