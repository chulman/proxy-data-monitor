package com.chulm.proxy.tcp.client;

import com.chulm.proxy.tcp.server.ProxyServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class ProxyClient {

    private EventLoopGroup group;
    private ChannelFuture cf;
    private final ProxyClientChannelHandler handler = new ProxyClientChannelHandler();

    public boolean connect(String host, int port) {
        group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        ChannelPipeline cp = sc.pipeline();
                        cp.addLast(handler);
                    }
                });

        try {
            cf = b.connect(host, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cf.isSuccess();
    }

    public ChannelFuture write(Object msg) {
        return cf.channel().writeAndFlush(msg);
    }

    public void close() {
        cf.channel().close();
        group.shutdownGracefully();
    }

    public void setServer(ProxyServer server){
        handler.setServer(server);
    }

}
