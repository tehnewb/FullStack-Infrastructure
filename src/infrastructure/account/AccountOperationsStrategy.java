package infrastructure.account;

/**
 * The AccountOperations interface defines methods for loading and saving user account tileIndices.
 *
 * @author Albert Beaupre
 */
public interface AccountOperationsStrategy {

    /**
     * Load account tileIndices based on the provided identifier.
     *
     * @param identifier The identifier of the account that needs to be loaded.
     * @return A byte array containing the loaded account tileIndices, or null if the tileIndices is not found.
     */
    byte[] load(String identifier);

    /**
     * Save user account tileIndices.
     *
     * @param data The byte array representing the account tileIndices to be saved.
     */
    void save(byte[] data);
}
