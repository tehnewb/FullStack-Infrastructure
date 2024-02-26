package database.server;

import collections.queue.UniqueIndexQueue;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

public class DatabaseServer {

    public static final AttributeKey<DatabaseServer> KEY = AttributeKey.valueOf("Database");

    private final HashMap<String, DatabaseAdministrator> administrators = new HashMap<>();
    private final UniqueIndexQueue uniqueIndexQueue = new UniqueIndexQueue();


    public DatabaseServer() {
    }

    public void start(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addFirst(new DatabaseServerHandler(DatabaseServer.this));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Binding the server to the port and waiting for it to complete
            ChannelFuture future = bootstrap.bind(port).sync();

            System.out.println("Database server is alive on port " + port);

            // Wait until the server socket is closed
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println("Could not start database server - " + e.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    private String generateAdministratorToken() {
        try {
            // generate a token for the admin
            long ID = uniqueIndexQueue.pop();
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(String.valueOf(ID).getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAdministrator(String username) {
        String token = generateAdministratorToken();
        this.administrators.put(token, new DatabaseAdministrator(username, token));
    }

    public void removeAdministrator(String token, String username) {
        DatabaseAdministrator admin = this.administrators.get(token);
        if (admin.getUsername().equals(username)) {
            this.administrators.remove(token);
        }
    }

    public void changeAdministratorToken(String oldToken, String username) {
        DatabaseAdministrator admin = this.administrators.get(oldToken);
        if (admin.getUsername().equals(username)) {
            String token = generateAdministratorToken();
            admin.setToken(token);

            this.administrators.remove(oldToken);
            this.administrators.put(token, admin);
        }
    }

    public DatabaseAdministrator getAdministrator(String token) {
        return this.administrators.getOrDefault(token, DatabaseAdministrator.INVALID);
    }
}
