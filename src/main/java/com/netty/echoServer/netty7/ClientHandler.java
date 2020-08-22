package com.netty.echoServer.netty7;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ClientHandler extends SimpleChannelInboundHandler {
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.writeAndFlush(
                Unpooled.copiedBuffer("Netty Rocks!",
                        CharsetUtil.UTF_8));
    }
    // read buf content
    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext,
                             Object o) {
        System.out.println("Client received: "+o.toString());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext,
                                Throwable cause) {
        cause.printStackTrace();
        channelHandlerContext.close();
    }
}

