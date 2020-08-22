package com.netty.echoServer.netty10_pre;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

// inbound handler
public class ClientHandler extends SimpleChannelInboundHandler {
    // write context to client side
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        // write content ...
        channelHandlerContext.writeAndFlush(
                Unpooled.copiedBuffer("Netty Rocks!", CharsetUtil.UTF_8));
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                Object o) throws Exception {
        System.out.println("Client received: " + o.toString());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext,
                                Throwable cause) {
        cause.printStackTrace();
        channelHandlerContext.close();
    }
}

