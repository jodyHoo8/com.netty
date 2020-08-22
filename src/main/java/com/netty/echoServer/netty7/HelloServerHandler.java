package com.netty.echoServer.netty7;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class HelloServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg)
        throws Exception {
        ByteBuf inBuf = (ByteBuf)msg;
        String received = inBuf.toString(CharsetUtil.UTF_8);
        System.out.println("Server received: "+received);
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
        throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause)
           throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

