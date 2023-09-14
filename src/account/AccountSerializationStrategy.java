package account;

/**
 * The AccountSerializer interface defines methods for serializing and deserializing user account data.
 *
 * @author Albert Beaupre
 */
public interface AccountSerializationStrategy {

    /**
     * Deserialize account data from a byte array.
     *
     * @param data The byte array containing serialized account data.
     * @return An Account object representing the deserialized account data.
     */
    Account deserialize(byte[] data);

    /**
     * Serialize an Account object into a byte array.
     *
     * @param account The Account object to be serialized.
     * @return A byte array containing the serialized account data.
     */
    byte[] serialize(Account account);

}
