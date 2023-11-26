package infrastructure.account;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The `Account` class represents a user account and provides methods to manage its data.
 * It uses a HashMap to store data associated with variable names and employs a ReadWriteLock
 * to control concurrent access to the account data.
 *
 * @author Albert Beaupre
 * @see AccountDatabase
 */
public class Account {
    // A IntHashMap to store the account's data, where variable names are keys and values are associated data.
    private final Map<String, Object> data = new HashMap<>();

    // A ReadWriteLock to ensure safe concurrent access to the accountData map.
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    // The unique identifier for this account.
    private final String identifier;

    /**
     * Constructs an `Account` object with the specified identifier.
     *
     * @param identifier The unique identifier for this account.
     */
    public Account(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Creates a new account instance with the given identifier.
     *
     * @param identifier The unique identifier for this account.
     * @return the account created
     */
    public static Account create(String identifier) {
        return new Account(identifier);
    }

    /**
     * Sets a variable and its associated value to the account.
     *
     * @param variableName The name of the variable to set.
     * @param value        The value associated with the variable.
     */
    public Account set(String variableName, Object value) {
        // Obtain a write lock to modify the accountData map.
        lock.writeLock().lock();
        try {
            // Add the variable and its value to the map.
            data.put(variableName, value);
        } finally {
            // Release the write lock.
            lock.writeLock().unlock();
        }
        return this;
    }

    /**
     * Removes a variable from the account.
     *
     * @param variableName The name of the variable to remove.
     */
    public Account remove(String variableName) {
        // Obtain a write lock to modify the accountData map.
        lock.writeLock().lock();
        try {
            // Remove the variable from the map.
            data.remove(variableName);
        } finally {
            // Release the write lock.
            lock.writeLock().unlock();
        }
        return this;
    }

    /**
     * Retrieves the value of a variable associated with the given name.
     *
     * @param variableName The name of the variable to retrieve.
     * @return The value associated with the variable, or null if not found.
     */
    public <T> T get(String variableName, Class<T> clazz) {
        // Obtain a read lock to access the accountData map.
        lock.readLock().lock();
        try {
            // Retrieve and return the value associated with the variable name.
            return clazz.cast(data.get(variableName));
        } finally {
            // Release the read lock.
            lock.readLock().unlock();
        }
    }

    /**
     * Retrieves the value of a variable associated with the given name.
     *
     * @param variableName The name of the variable to retrieve.
     * @return The value associated with the variable, or null if not found.
     */
    public <T> T get(String variableName, T defaultValue, Class<T> clazz) {
        // Obtain a read lock to access the accountData map.
        lock.readLock().lock();
        try {
            // Retrieve and return the value associated with the variable name.
            return clazz.cast(data.getOrDefault(variableName, defaultValue));
        } finally {
            // Release the read lock.
            lock.readLock().unlock();
        }
    }

    /**
     * Returns the unique identifier of the account.
     *
     * @return The account's identifier.
     */
    public String getIdentifier() {
        return identifier;
    }
}
