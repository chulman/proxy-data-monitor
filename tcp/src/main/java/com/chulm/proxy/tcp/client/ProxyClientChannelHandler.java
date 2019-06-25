package com.chulm.proxy.tcp.client;

import com.chulm.proxy.tcp.server.ProxyServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;


public class ProxyClientChannelHandler extends ChannelInboundHandlerAdapter {
    private InternalLogger logger = InternalLoggerFactory.getInstance(getClass());
    private ProxyServer server;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("========================== " + ctx.channel().remoteAddress().toString() + " ==========================");
        //write to file or console
        ByteBuf buf = (ByteBuf)msg;
        System.out.println(ByteBufUtil.prettyHexDump(buf));
        //write to client
        server.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void setServer(ProxyServer server) {
        this.server = server;
    }
}
