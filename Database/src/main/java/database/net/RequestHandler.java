package database.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class RequestHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object message) {
        if (message instanceof ByteBuf buffer) {
            byte requestID = buffer.readByte();
            short UUID = buffer.readShort();
            short stringLength = buffer.readShort();
            String string = buffer.readCharSequence(stringLength, CharsetUtil.UTF_8).toString();

            ByteBuf response = Talker.getService(requestID).generateResponse(UUID, string, buffer);
            ctx.channel().writeAndFlush(response);
        }
    }
}
