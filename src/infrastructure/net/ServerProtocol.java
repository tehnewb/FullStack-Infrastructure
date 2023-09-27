package infrastructure.net;

/**
 * The ServerProtocol interface defines the contract for performing
 * actions over a Netty connection based on the current state of that connection
 *
 * @author Albert Beaupre
 */
public interface ServerProtocol {

    /**
     * Handles the protocol over the provided {@link Connection}
     * using the given message as input.
     *
     * @param connection The Netty connection object over which the update should
     *                   be processed.
     * @param message    The message containing protocol information
     */
    void handle(Connection connection, Object message);
}
