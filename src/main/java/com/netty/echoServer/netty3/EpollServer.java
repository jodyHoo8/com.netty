package com.netty.echoServer.netty3;


import com.sun.xml.internal.ws.api.pipe.Codecs;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;


/**
    ChannelPipeline
    ChannelHandler
    SocketChannel

    ChannelInboundHandler
    ChannelOutboundHandler

 */
public class EpollServer {
    public static void main(String[] args) {
        // HTTP, WebSockets, SSL / TLS

        /**
            received from
            SocketChannel
            passed 2 first
            ChannelInboundHandler
            ChannelPipeline

            ChannelInboundHandler processes the data

            raw bytes transformed into an HTTP object
            or some other object.

            next
            ChannelInboundHandler
            in
            ChannelPipeline
            HTTP object
            not the raw data

         */

        /**
            write data back to the SocketChannel
            --  data passed from
                ChannelOutboundHandler 2
                ChannelOutboundHnalder in ChannelPipeline
            until reaches the
            SocketChannel
            ChannelOutboundHandler
            transform the data in the process

            illustration shows the


            ChannelInboundHandlder
            ChannelOutboundHandler

            located in the same list
            ChannelInboundHandler
            decides to write something back to the SocketChannel.

            passes through all

            ChannelOutboundHandler
            ChannelPipeline
            ChannelInboundHandler

         */

//        Create an EventLoopGroup
//        Create and configure a ServerBootstrap
//        Create a ChannelInitializer
//        Start the server
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(new InetSocketAddress("127.0.0.1",9999));
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel)
                    throws Exception {
                    socketChannel.pipeline().addLast(
                        null
                    );
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            // close Future
            channelFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }

    }
}

