package com.netty.echoServer.netty9;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
    Start the Server
    The final step of booting a Netty TCP server is to start up the server. Starting the TCP server is done with this line:

    The serverBootstrap.bind() method returns a ChannelFuture which can be used to know when the binding of the server (binding to local address and TCP port) is done. By calling sync() on the ChannelFuture the main thread that creates the server waits until the server has started, before continuing. The sync() method also returns a ChannelFuture, by the way.


    The channelRead() method is called whenever data is received from the SocketChannel the HelloServerHandler instance is attached to. As you can see, the channelRead() responds with "Hello " + whatever the client sent to the server.

    The channelReadComplete() method is called when there is no more data to read from the SocketChannel.

    The exceptionCaught() method is called if an exception is thrown while receiving or sending data from the SocketChannel. In here you can decide what should happen, like closing the connection, or responding with an error code etc.
 */
public class HelloServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg) throws Exception {
        ByteBuf inBuffer = (ByteBuf) msg;

        String received = inBuffer.toString(CharsetUtil.UTF_8);
        System.out.println("Server received: " + received);

        ctx.write(Unpooled.copiedBuffer("Hello " + received,
                  CharsetUtil.UTF_8));
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

