package com.netty.echoServer.netty8;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

// server inbound channel handler
public class HelloServerHandler extends ChannelInboundHandlerAdapter {
    // read enter here.
    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg)
           throws Exception {
        ByteBuf inBuf = (ByteBuf) msg;
        String received = inBuf.toString(CharsetUtil.UTF_8);
        System.out.println("Server received: "+received);
        ctx.write(Unpooled.copiedBuffer("Hello"+received,CharsetUtil.UTF_8));
    }
    // read complete
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
           throws Exception {
        System.out.println("Netty Client read complete.");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
           .addListener(ChannelFutureListener.CLOSE);
    }
    // exception caught
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause)
           throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

