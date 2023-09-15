package net;

import account.Account;
import io.netty.channel.Channel;

/**
 * The `Connection` class represents a connection within a Netty-based server.
 * It manages the state of the connection and provides methods to handle different
 * stages of the connection process.
 *
 * @author Albert Beaupre
 */
public class Connection {
    /**
     * The Netty Channel associated with this connection.
     */
    private final Channel channel;
    /**
     * The current state of the server connection.
     */
    private ConnectionState currentState = ConnectionState.ESTABLISHING;
    /**
     * The user account associated with this connection.
     */
    private Account account;

    /**
     * Creates a new `Connection` instance.
     *
     * @param context The Netty Channel for this connection.
     */
    public Connection(Channel channel) {
        this.channel = channel;
    }

    /**
     * Retrieves the ConnectionState associated with this connection.
     *
     * @return The ConnectionState for this connection.
     */
    public ConnectionState getCurrentState() {
        return currentState;
    }

    /**
     * Sets the ConnectionState to the given state.
     *
     * @param state The state to set to this connection.
     */
    public void setCurrentState(ConnectionState state) {
        this.currentState = state;
    }

    /**
     * Retrieves the Netty Channel associated with this connection.
     *
     * @return The Channel for this connection.
     */
    public Channel getChannel() {
        return channel;
    }
}
