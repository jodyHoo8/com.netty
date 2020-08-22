package com.netty.echoServer.netty9;


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

        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap clientBootstrap = new Bootstrap();
            // 1.add group
            clientBootstrap.group(group);
            // 2.add SocketChannel
            clientBootstrap.channel(NioSocketChannel.class);
            // 3.bin address
            clientBootstrap.remoteAddress(new InetSocketAddress("127.0.0.1",9999));
            // 4.new ChannelInitializer
            //   and take channelpipeline from socketchannel
            //   and add channelhandler to channelpipeline
            clientBootstrap.handler(
                    new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel)
                                throws Exception {
                            // 从 SocketChannel 里取 pipeline 出来
                            // 添加 channelHandler 进 channelpipeline
                            socketChannel.pipeline().addLast(
                                    new ClientHandler()
                            );
                        }
                    }
            );
            // use Bootstrap config connect , sync
            ChannelFuture channelFuture = clientBootstrap.connect().sync();

            // close channelFuture , sync
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }

    }
}

