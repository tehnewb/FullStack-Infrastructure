package database.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ResponseHandler extends SimpleChannelInboundHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.channel().attr(Talker.KEY).set(new Talker(ctx.channel()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf buffer) {
            short UUID = buffer.readShort();
            short stringLength = buffer.readShort();
            String string = buffer.readCharSequence(stringLength, CharsetUtil.UTF_8).toString();

            Talker requester = ctx.channel().attr(Talker.KEY).get();
            requester.respond(new Response(UUID, string));
        }
    }

}
