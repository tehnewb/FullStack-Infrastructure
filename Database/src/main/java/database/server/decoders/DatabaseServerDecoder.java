package database.server.decoders;

import buffer.DynamicByteBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class DatabaseServerDecoder extends ByteToMessageDecoder {

    private static final DecoderService[] services = DecoderService.loadServices("database.decoders.server.services");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
        if (!buffer.isReadable() || buffer.readableBytes() == 0)
            return;

        int requestID = buffer.readByte();

        if (requestID < 0 || requestID >= services.length)
            return;

        byte[] data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);

        services[requestID].decode(ctx.channel(), new DynamicByteBuffer(data));
    }
}
