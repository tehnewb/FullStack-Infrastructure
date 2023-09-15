package net;

/**
 * Enum representing the possible states of the server connection.
 *
 * @author Albert Beaupre
 */
public enum ConnectionState {

    /**
     * The state when the client is establishing a connection to the server.
     */
    ESTABLISHING,

    /**
     * The state when the client is doing a handshake with the server.
     */
    HANDSHAKE,

    /**
     * The state when the client is requesting an update from the server.
     */
    UPDATING,

    /**
     * The state when the client is logging into the server.
     */
    LOGIN,

    /**
     * The state when the client is logged into the server and the server is sending regular packets back and forth.
     */
    ONLINE
}