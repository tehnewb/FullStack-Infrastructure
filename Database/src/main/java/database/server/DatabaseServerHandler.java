package database.server;

import buffer.DynamicByteBuffer;
import database.logging.ConsoleColor;
import database.logging.DatabaseLogger;
import database.server.decoders.DatabaseServerDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.net.SocketException;
import java.security.*;

public class DatabaseServerHandler extends ChannelInboundHandlerAdapter {
    protected static final Cipher CIPHER;
    protected static final KeyFactory FACTORY;
    protected static final KeyPairGenerator GENERATOR;
    protected static int KEY_SIZE = 2048;

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


    private final DatabaseServer server;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public DatabaseServerHandler(DatabaseServer server) {
        this.server = server;


        GENERATOR.initialize(KEY_SIZE);
        KeyPair pair = GENERATOR.generateKeyPair();

        this.publicKey = pair.getPublic();
        this.privateKey = pair.getPrivate();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.channel().attr(DatabaseServer.KEY).set(server);

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
            DatabaseAdministrator administrator = server.getAdministrator(token);

            if (decryptedBuffer.isEmpty())
                //It's possible a packet came with missing information
                return;

            String username = decryptedBuffer.readString();

            if (administrator == DatabaseAdministrator.INVALID) {
                System.err.println("Attempt to breach administrator access from " + ctx.channel().remoteAddress());
                return;
            }

            if (administrator.getUsername().equals(username)) {
                // We need to ensure the token matches the username in case of packet injection
                administrator.grantAccess();
                System.out.println("Granted Access to " + administrator.getUsername());

                ctx.channel().pipeline().addFirst(new DatabaseServerDecoder());
            } else {
                ctx.close();
                System.err.println("Possible breach or missing packet information from " + ctx.channel().remoteAddress());
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
