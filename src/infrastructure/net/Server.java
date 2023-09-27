package infrastructure.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * A high-volume Netty server for handling a large number of incoming connections.
 *
 * @author Albert Beaupre
 */
public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    static {
        // Configure the logger from slf4j
        BasicConfigurator.configure();
    }

    private final Map<ConnectionState, ServerProtocol> protocols = new HashMap<>(); // The protocols mapped to this server
    private final int port;
    private final int backlog; // Adjust the backlog size based on your needs
    private final int numWorkerThreads; // Adjust the number of worker threads based on your hardware capabilities

    /**
     * Creates a new instance of the Server.
     *
     * @param port             The port on which the server should listen.
     * @param backlog          The maximum number of pending incoming connections in the backlog.
     * @param numWorkerThreads The number of worker threads for handling incoming connections.
     */
    public Server(int port, int backlog, int numWorkerThreads) {
        this.port = port;
        this.backlog = backlog;
        this.numWorkerThreads = numWorkerThreads;
    }

    /**
     * Starts the Netty server.
     *
     * @throws Exception If an error occurs during server initialization or operation.
     */
    public void start() throws Exception {
        // Create separate event loop groups for boss (acceptor) and worker (I/O) threads.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("boss"));
        EventLoopGroup workerGroup = new NioEventLoopGroup(numWorkerThreads, new DefaultThreadFactory("worker"));

        try {
            // Create the Netty ServerBootstrap
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // Use NIO for socket channel
                    .handler(new LoggingHandler(LogLevel.INFO)) // Add a logging handler for server events
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast("handler", new ServerHandler(Server.this));
                        }
                    }).option(ChannelOption.SO_BACKLOG, backlog) // Adjust the backlog size
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // Enable keep-alive for client connections

            // Bind and start the server
            ChannelFuture future = bootstrap.bind(port).sync();
            logger.info("Initialized server on port " + port);

            // Wait until the server socket is closed
            future.channel().closeFuture().sync();
        } finally {
            // Gracefully shut down the event loop groups
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    /**
     * Register a protocol with the server.
     *
     * @param state    The state at which the server is in for a connection
     * @param protocol The protocol instance to register.
     */
    public void registerProtocol(ConnectionState state, ServerProtocol protocol) {
        protocols.put(state, protocol);
    }

    /**
     * Get a protocol instance by its class.
     *
     * @param state The connection state.
     * @return The protocol instance if registered, otherwise null.
     */
    public ServerProtocol getProtocol(ConnectionState state) {
        return protocols.get(state);
    }

}
