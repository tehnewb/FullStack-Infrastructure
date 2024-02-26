package database;

import buffer.DynamicByteBuffer;
import collections.queue.UniqueIndexQueue;
import database.decoders.DatabaseServerDecoder;
import database.logging.ConsoleColor;
import database.logging.DatabaseLogger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class DatabaseServer extends ChannelInboundHandlerAdapter {

    public static final AttributeKey<DatabaseServer> KEY = AttributeKey.valueOf("Database");
    private static final Cipher CIPHER;
    private static final KeyFactory FACTORY;
    private static final KeyPairGenerator GENERATOR;
    private static int KEY_SIZE = 2048;

    static {
        System.setOut(new DatabaseLogger(ConsoleColor.GREEN, System.out));
        System.setErr(new DatabaseLogger(ConsoleColor.RED, System.err));


        try {
            CIPHER = Cipher.getInstance("RSA");
            FACTORY = KeyFactory.getInstance("RSA");
            GENERATOR = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private final HashMap<String, DatabaseAdministrator> administrators = new HashMap<>();
    private final UniqueIndexQueue uniqueIndexQueue = new UniqueIndexQueue();

    private PublicKey publicKey;
    private PrivateKey privateKey;


    public DatabaseServer() {
        GENERATOR.initialize(KEY_SIZE);
        KeyPair pair = GENERATOR.generateKeyPair();

        this.publicKey = pair.getPublic();
        this.privateKey = pair.getPrivate();
    }

    public static void main(String[] args) throws Exception {
        DatabaseServer server = new DatabaseServer();
        server.administrators.put("TEST_TOKEN", new DatabaseAdministrator("TEST_USERNAME", "TEST_TOKEN"));
        server.start(8888);

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

    public void saveConfiguration(File file) {
        DynamicByteBuffer buffer = new DynamicByteBuffer();
        buffer.writeString(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        buffer.writeString(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        buffer.writeInt(KEY_SIZE);
        buffer.writeInt(administrators.size());
        for (Map.Entry<String, DatabaseAdministrator> entry : this.administrators.entrySet()) {
            DatabaseAdministrator administrator = entry.getValue();

            buffer.writeString(administrator.getUsername());
            buffer.writeString(administrator.getToken());
        }

        try {
            Files.write(file.toPath(), buffer.toTrimmedArray(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Could not save database to " + file + " - " + e.getMessage());
        }
    }

    public void loadConfiguration(File file) {
        try {
            DynamicByteBuffer buffer = new DynamicByteBuffer(Files.readAllBytes(file.toPath()));

            String publicKey = buffer.readString();
            String privateKey = buffer.readString();

            byte[] publicKeyData = Base64.getDecoder().decode(publicKey);
            byte[] privateKeyData = Base64.getDecoder().decode(privateKey);

            this.publicKey = FACTORY.generatePublic(new X509EncodedKeySpec(publicKeyData));
            this.privateKey = FACTORY.generatePrivate(new PKCS8EncodedKeySpec(privateKeyData));

            KEY_SIZE = buffer.readInt();
            int adminSize = buffer.readInt();

            for (int i = 0; i < adminSize; i++) {
                String username = buffer.readString();
                String token = buffer.readString();

                this.administrators.put(token, new DatabaseAdministrator(username, token));
            }
        } catch (IOException e) {
            System.err.println("Could not load configuration from " + file + " - " + e);
        } catch (InvalidKeySpecException e) {
            System.err.println("Could not load configuration due to invalid RSA keys" + " - " + e);
        }
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
                            ch.pipeline().addFirst(DatabaseServer.this);
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

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.channel().attr(KEY).set(this);

        // Send over the public key for the client to use
        ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(publicKey.getEncoded()));

        System.out.println("Received connection from " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if (packet instanceof ByteBuf buffer) {
            // The first message from the client is the token
            byte[] data = new byte[buffer.readableBytes()];
            buffer.readBytes(data);

            CIPHER.init(Cipher.DECRYPT_MODE, privateKey);
            DynamicByteBuffer decryptedBuffer = new DynamicByteBuffer(CIPHER.doFinal(data));
            String token = decryptedBuffer.readString();
            DatabaseAdministrator administrator = administrators.getOrDefault(token, DatabaseAdministrator.INVALID);

            if (decryptedBuffer.isEmpty())
                //It's possible a packet came with missing information
                return;

            String username = decryptedBuffer.readString();

            if (administrator == DatabaseAdministrator.INVALID) {
                System.out.println("Attempt to breach administrator access from " + ctx.channel().remoteAddress());
                return;
            }

            if (administrator.getUsername().equals(username)) {
                // We need to ensure the token matches the username in case of packet injection
                administrator.grantAccess();
                System.out.println("Granted Access to " + administrator.getUsername());

                ctx.channel().pipeline().addFirst(new DatabaseServerDecoder());
            } else {
                ctx.close();
                System.out.println("Possible breach or missing packet information from " + ctx.channel().remoteAddress());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof SocketException) {
            System.err.println("Connection from " + ctx.channel().remoteAddress() + " was forced closed");
        } else {
            cause.printStackTrace(); // Log other exceptions
        }
        ctx.close();
    }

}
