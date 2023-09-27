package infrastructure.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

/**
 * ServerHandler is a custom channel handler for a Netty-based server.
 * It manages incoming client connections and delegates message handling to a Connection instance.
 *
 * @author Albert Beaupre
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    // AttributeKey to associate a Connection object with a ChannelHandlerContext
    private static final AttributeKey<Connection> ConnectionKey = AttributeKey.newInstance("Connection");

    private final Server server;

    /**
     * Constructs a new ServerHandler with the given server.
     *
     * @param server the parenting server for this handler
     */
    public ServerHandler(Server server) {
        this.server = server;
    }

    /**
     * Invoked when a new client connection is established.
     *
     * @param ctx The ChannelHandlerContext for the new connection.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // A new connection has been established
        System.out.println("New connection from: " + ctx.channel().remoteAddress());

        // Create and associate a Connection instance with this channel context
        ctx.channel().attr(ConnectionKey).set(new Connection(ctx.channel()));
    }

    /**
     * Invoked when a message is received from a client.
     *
     * @param ctx The ChannelHandlerContext associated with the connection.
     * @param msg The incoming message object.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Retrieve the associated Connection and delegate message handling to it
        Connection connection = ctx.channel().attr(ConnectionKey).get();
        server.getProtocol(connection.getCurrentState()).handle(connection, msg);
    }

    /**
     * Invoked when an exception occurs during processing of incoming messages.
     *
     * @param ctx   The ChannelHandlerContext associated with the connection.
     * @param cause The exception that was thrown.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Print the exception trace for debugging purposes
        cause.printStackTrace();
    }
}
