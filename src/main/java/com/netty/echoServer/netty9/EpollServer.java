package com.netty.echoServer.netty9;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
    SocketChannel
    channelpipeline
    add channelhandler
    --  ChannelInboundHandler
    --  ChannelOutboundHandler

    Create an EventLoopGroup
    Create and configure a ServerBootstrap
    Create a ChannelInitializer
    Start the server
 */
public class EpollServer {
    public static void main(String[] args) throws InterruptedException {

        // 1.new eventloopgroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 2.new ServerBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 3.add bootstrap group
            serverBootstrap.group(group);
            // 4.add socketchannel
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 5.bind localaddress
            serverBootstrap.localAddress(new InetSocketAddress("127.0.0.1",9999));
            // 6.
            serverBootstrap.childHandler(
                new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel)
                        throws Exception {
                        socketChannel.pipeline().addLast(
                            new HelloServerHandler()
                        );
                    }
                }
            );
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }

    }
}

