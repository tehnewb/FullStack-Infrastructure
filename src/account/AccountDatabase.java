package account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class represents an asynchronous account database that provides methods to load and save
 * account data. It includes in-memory caching for faster access to frequently accessed accounts.
 * The database operations are performed asynchronously using a thread pool.
 *
 * @author Albert Beaupre
 * @see account.AccountSerializationStrategy
 * @see account.AccountOperationsStrategy
 * @see account.AccountDatabase
 */
public class AccountDatabase {
    private static final Logger logger = LoggerFactory.getLogger(AccountDatabase.class);

    private ExecutorService executor = Executors.newFixedThreadPool(10); // Thread pool for asynchronous tasks
    private Map<String, byte[]> cache = new ConcurrentHashMap<>(); // In-memory cache for account data
    private AccountSerializationStrategy serializer; // Responsible for serializing/deserializing account data
    private AccountOperationsStrategy operations; // Provides methods for loading and saving account data
    private int maxCacheSize = 1000; // Maximum size of the cache
    private Queue<String> cacheEvictionQueue = new ConcurrentLinkedQueue<>(); // Queue for cache eviction
    private ReentrantLock cacheLock = new ReentrantLock(); // Lock for ensuring thread safety during cache updates

    /**
     * Constructs an `AccountWorker` object with given operations and serialization strategies used for saving and loading and serializing and deserializing.
     *
     * @param operationsStrategy    The operations strategy used for loading and saving accounts
     * @param serializationStrategy The serialization strategy used for serializing and deserializing account data
     */
    public AccountDatabase(AccountOperationsStrategy operationsStrategy, AccountSerializationStrategy serializationStrategy) {
        this.operations = operationsStrategy;
        this.serializer = serializationStrategy;
    }

    /**
     * Asynchronously load an account by identifier.
     *
     * @param identifier The identifier of the account to load.
     * @return A CompletableFuture representing the asynchronous loading process.
     * It completes when the account is loaded or an error occurs.
     * @throws IllegalArgumentException if the identifier is null.
     */
    public CompletableFuture<Account> loadAccount(String identifier) {
        if (identifier == null) throw new IllegalArgumentException("Cannot load account with null identifier");

        return CompletableFuture.supplyAsync(() -> {
            // First, try to fetch the account from the cache
            byte[] cachedData = getCachedAccountData(identifier);

            if (cachedData != null) {
                // Account found in cache, populate the account object
                return serializer.deserialize(cachedData);
            } else {
                // Account not found in cache, load it from storage
                byte[] loadedData = operations.load(identifier);

                if (loadedData == null) throw new IllegalStateException("Account data not found in storage");

                Account account = serializer.deserialize(loadedData);

                // Store the loaded data in cache for future use
                setCachedAccountData(identifier, loadedData);

                logger.debug("Account Loaded: " + identifier);

                return account;
            }
        }, executor);
    }

    /**
     * Asynchronously save an account.
     *
     * @param account The Account object to save.
     * @return A CompletableFuture representing the asynchronous saving process.
     * It completes when the account is saved or an error occurs.
     * @throws NullPointerException if the account is null.
     */
    public CompletableFuture<Void> saveAccount(Account account) {
        if (account == null) throw new NullPointerException("Cannot save null account");
        return CompletableFuture.runAsync(() -> {
            // Save account information to storage
            byte[] serializedData = serializer.serialize(account);

            if (serializedData == null) throw new IllegalStateException("Serialization failed");

            operations.save(serializedData);

            // Update the cache with the latest data
            setCachedAccountData(account.getIdentifier(), serializedData);

            logger.debug("Account Saved: " + account.getIdentifier());
        }, executor);
    }

    /**
     * Shutdown the executor service. Call this method when the application is exiting.
     */
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * Retrieve cached account data by account ID.
     *
     * @param identifier The ID of the account to retrieve from cache.
     * @return The cached account data as a byte array, or null if not found in cache.
     */
    private byte[] getCachedAccountData(String identifier) {
        return cache.get(identifier);
    }

    /**
     * Store account data in the cache.
     *
     * @param identifier The ID of the account to store in cache.
     * @param data       The account data to store as a byte array.
     */
    private void setCachedAccountData(String identifier, byte[] data) {
        cacheLock.lock();
        try {
            cache.put(identifier, data);
            cacheEvictionQueue.add(identifier);

            // Evict least recently used items if the cache size exceeds the limit
            if (cache.size() > maxCacheSize) {
                String evictedAccountIdentifier = cacheEvictionQueue.poll();
                if (evictedAccountIdentifier != null) {
                    cache.remove(evictedAccountIdentifier);
                }
            }
        } finally {
            cacheLock.unlock();
        }
    }
}
