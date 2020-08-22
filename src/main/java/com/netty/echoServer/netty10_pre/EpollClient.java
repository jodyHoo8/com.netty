package com.netty.echoServer.netty10_pre;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EpollClient {
    public static void main(String[] args) throws InterruptedException {

        // 1.new EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 2.new Bootstrap
            Bootstrap clientBootstrap =  new Bootstrap();
            // 3.add EventLoopGroup to Bootstrap
            clientBootstrap.group(group);
            // 4.add SocketChannel to Bootstrap
            clientBootstrap.channel(NioSocketChannel.class);
            // 5.add remote address to Bootstrap
            clientBootstrap.remoteAddress(new InetSocketAddress("127.0.0.1",9998));
            // 6.add ChannelInitializer to Bootstrap as handler
            //   in initChannel method
            //   take channelpipeline from socketchannel
            //   add clienthandler to channelpipeline
            clientBootstrap.handler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel)
                            throws Exception {
                        socketChannel.pipeline().addLast(
                            new ClientHandler()
                        );
                    }
                }
            );
            ChannelFuture channelFuture = clientBootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }

    }
}

