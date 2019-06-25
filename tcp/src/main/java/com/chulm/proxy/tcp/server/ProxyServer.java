package com.chulm.proxy.tcp.server;

import com.chulm.proxy.tcp.client.ProxyClient;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ProxyServer {

    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;

    private ChannelFuture cf;
    private final ProxyServerChannelHandler handler = new ProxyServerChannelHandler();

    public ProxyServer(){
        parentGroup = new NioEventLoopGroup(1);
        childGroup = new NioEventLoopGroup();
    }

    public boolean bind(String host, int port){
        ServerBootstrap sb = new ServerBootstrap();
        sb.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
//                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(handler);
                    }
                });

        try {
            cf = sb.bind(host, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cf.isSuccess();
    }

    public void write(Object msg){
        cf.channel().writeAndFlush(msg);
    }

    public void shutdown(){
        if(cf.channel().isActive()){
            cf.channel().close();
        }
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }

    public void setClient(ProxyClient client){
        handler.setClient(client);
    }
}
