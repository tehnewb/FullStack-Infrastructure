package net;

import account.Account;
import io.netty.channel.ChannelHandlerContext;

/**
 * The `Connection` class represents a connection within a Netty-based server.
 * It manages the state of the connection and provides methods to handle different
 * stages of the connection process.
 *
 * @author Albert Beaupre
 */
public class Connection {
    /**
     * The Netty ChannelHandlerContext associated with this connection.
     */
    private final ChannelHandlerContext context;
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
     * @param context The Netty ChannelHandlerContext for this connection.
     */
    public Connection(ChannelHandlerContext context) {
        this.context = context;
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
     * Retrieves the Netty ChannelHandlerContext associated with this connection.
     *
     * @return The ChannelHandlerContext for this connection.
     */
    public ChannelHandlerContext getContext() {
        return context;
    }
}
