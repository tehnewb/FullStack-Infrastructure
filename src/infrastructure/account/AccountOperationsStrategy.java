package infrastructure.account;

/**
 * The AccountOperations interface defines methods for loading and saving user account data.
 *
 * @author Albert Beaupre
 */
public interface AccountOperationsStrategy {

    /**
     * Load account data based on the provided identifier.
     *
     * @param identifier The identifier of the account that needs to be loaded.
     * @return A byte array containing the loaded account data, or null if the data is not found.
     */
    byte[] load(String identifier);

    /**
     * Save user account data.
     *
     * @param data The byte array representing the account data to be saved.
     */
    void save(byte[] data);
}
