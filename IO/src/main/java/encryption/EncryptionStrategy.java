package encryption;

/**
 * An interface that defines the contract for encryption and decryption strategies.
 *
 * @author Albert Beaupre
 */
public interface EncryptionStrategy {

    /**
     * Encrypts the given data.
     *
     * @param data The data to be encrypted.
     * @return The encrypted data as a byte array.
     */
    byte[] encrypt(byte[] data);

    /**
     * Decrypts the given data.
     *
     * @param data The data to be decrypted.
     * @return The decrypted data as a byte array.
     */
    byte[] decrypt(byte[] data);

}