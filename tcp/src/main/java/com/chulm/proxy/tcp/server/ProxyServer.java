package com.chulm.proxy.tcp.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ProxyServer {

    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;

    private ChannelFuture cf;

    public ProxyServer(){
        parentGroup = new NioEventLoopGroup(1);
        childGroup = new NioEventLoopGroup();
    }

    public void bind(String host, int port){
        ServerBootstrap sb = new ServerBootstrap();
        sb.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
//                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                    }
                });

        try {
            cf = sb.bind(10081).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown(){
        if(cf.channel().isActive()){
            cf.channel().close();
        }
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }
}
