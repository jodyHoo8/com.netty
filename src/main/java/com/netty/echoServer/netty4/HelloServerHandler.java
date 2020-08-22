package com.netty.echoServer.netty4;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class HelloServerHandler extends ChannelInboundHandlerAdapter {
    // read
    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg)
           throws Exception {
        ByteBuf readBuf = (ByteBuf) msg;
        String received = readBuf.toString(CharsetUtil.UTF_8);
        System.out.println("Server receieved: "+received);
        ctx.write(Unpooled.copiedBuffer("Hello "+received,CharsetUtil.UTF_8));
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
           throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
           .addListener(ChannelFutureListener.CLOSE);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause)
           throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}


