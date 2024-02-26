package database.client;

import buffer.DynamicByteBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class DatabaseClientHandler extends ChannelInboundHandlerAdapter {
    private static final Cipher CIPHER;
    private static final KeyFactory FACTORY;
    private static final KeyPairGenerator GENERATOR;

    static {
        try {
            CIPHER = Cipher.getInstance("RSA");
            FACTORY = KeyFactory.getInstance("RSA");
            GENERATOR = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private final DatabaseClient client;
    private PublicKey publicKey;

    public DatabaseClientHandler(DatabaseClient client) {
        this.client = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        // The client immediately receives the public key on connection
        if (packet instanceof ByteBuf buffer) {
            byte[] data = new byte[buffer.readableBytes()];
            buffer.readBytes(data);
            this.publicKey = FACTORY.generatePublic(new X509EncodedKeySpec(data));

            // The client immediately returns the token to access the server functionality
            DynamicByteBuffer encryptedBuffer = new DynamicByteBuffer();
            encryptedBuffer.writeString(client.getToken());
            encryptedBuffer.writeString(client.getUsername());

            CIPHER.init(Cipher.ENCRYPT_MODE, publicKey);
            ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(CIPHER.doFinal(encryptedBuffer.toTrimmedArray())));
        }
    }
}
