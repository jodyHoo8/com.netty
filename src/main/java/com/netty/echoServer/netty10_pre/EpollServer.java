package com.netty.echoServer.netty10_pre;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
    Codecs
    Netty has the concept of codecs (encoders + decoders).
    A Netty codec converts bytes to message objects (Java objects), or
    message objects to bytes. For instance, a codec might convert
    the raw bytes of an incoming HTTP request to an HTTP object, or
    convert an HTTP response object back to raw bytes.

    A Netty codec object is really just one (or two)
    ChannelHandler implementations. A codec typically consists
    of a ChannelInboundHandler implementation which converts request bytes
    into objects, and a ChannelOutboundHandler which converts response objects
    into bytes.

    Netty comes with codecs for several different protocols,
    like HTTP, WebSockets, SSL / TLS etc. In order to use those protocols
    with Netty, you have to
    add the corresponding protocol codec
    ChannelInboundHandler and
    ChannelOutboundHandler to the ChannelPipeline of the SocketChannel
    you want to use the protocol with. Netty codecs and protocols
    will be covered in more detail in their own tutorials.
 */
public class EpollServer {
    public static void main(String[] args) throws InterruptedException {

        // codecs (encoders + decoders)
        // A Netty codec converts bytes to message objects (Java objects)

        // codec raw bytes
        // HTTP request 2 HTTP object
        // HTTP response object back to raw bytes

        // 1.new EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 2.new Bootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 3.add EventLoopGroup to Bootstrap
            serverBootstrap.group(group);
            // 4.add NioSocketChannel to Bootstrap
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 5.config remoteAddress to Bootstrap
            serverBootstrap.localAddress(
                    new InetSocketAddress("127.0.0.1",9998));
            // 6.config handler to Bootstrap
            serverBootstrap.childHandler(
                // 7.new ChannelInitializer to Bootstrap
                    new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel)
                            throws Exception {
                            // 8.get channelpipeline from socketchannel
                            //   add channelhandler to channelpipeline
                            socketChannel.pipeline().addLast(new ServerHandler());
                }
            });
            // 8.bind sync server ...
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            // 9.channel close sync
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }

    }
}

