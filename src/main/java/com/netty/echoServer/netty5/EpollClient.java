package com.netty.echoServer.netty5;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EpollClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(
                    new InetSocketAddress("127.0.0.1",999));
            serverBootstrap.childHandler(
                    new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel)
                            throws Exception {
                            socketChannel.pipeline().addLast(new HelloServerHandler());
                        }
                    }
            );
            ChannelFuture channelFuture =
                    serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}

