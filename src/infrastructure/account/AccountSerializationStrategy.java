package infrastructure.account;

/**
 * The AccountSerializer interface defines methods for serializing and deserializing user account tileIndices.
 *
 * @author Albert Beaupre
 */
public interface AccountSerializationStrategy {

    /**
     * Deserialize account tileIndices from a byte array.
     *
     * @param data The byte array containing serialized account tileIndices.
     * @return An Account object representing the deserialized account tileIndices.
     */
    Account deserialize(byte[] data);

    /**
     * Serialize an Account object into a byte array.
     *
     * @param account The Account object to be serialized.
     * @return A byte array containing the serialized account tileIndices.
     */
    byte[] serialize(Account account);

}
