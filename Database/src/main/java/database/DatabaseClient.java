package database;

import buffer.DynamicByteBuffer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class DatabaseClient extends ChannelInboundHandlerAdapter {
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

    private final String token;
    private final String username;
    private PublicKey publicKey;

    public DatabaseClient(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public static void main(String[] args) throws Exception {
        DatabaseClient client = new DatabaseClient("TEST_USERNAME", "TEST_TOKEN");
        client.start("localhost", 8888);
    }

    public void start(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(DatabaseClient.this);
                        }
                    })
                    .connect().sync().channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
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
            encryptedBuffer.writeString(token);
            encryptedBuffer.writeString(username);

            CIPHER.init(Cipher.ENCRYPT_MODE, publicKey);
            ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(CIPHER.doFinal(encryptedBuffer.toTrimmedArray())));
        }
    }
}
