package com.netty.echoServer.netty4;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

// channel pipeline
// channel handler
public class EpollClient {
    public static void main(String[] args) throws InterruptedException {
        // 1.ini eventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 2.new Bootstrap
            Bootstrap clientBootstrap = new Bootstrap();
            // 3.add EventLoopGroup to Bootstrap
            clientBootstrap.group(group);
            // 4.add NioSocketChannel to channel
            clientBootstrap.channel(NioSocketChannel.class);
            // 5.add InetSocketAddress to remoteAddress
            clientBootstrap.remoteAddress(new InetSocketAddress("127.0.0.1",999));
            // 6.add new Client Handler
            clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel)
                    throws Exception {
                    socketChannel.pipeline().addLast(new ClientHandler());
                }
            });
            ChannelFuture channelFuture = clientBootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}

