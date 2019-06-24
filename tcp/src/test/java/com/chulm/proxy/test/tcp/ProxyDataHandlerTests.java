package com.chulm.proxy.test.tcp;

import com.chulm.proxy.tcp.server.ProxyDataHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

public class ProxyDataHandlerTests {
    @Test
    public void writeToChannel(){
        String msg = "test";

        EmbeddedChannel channel = new EmbeddedChannel(new ProxyDataHandler());
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg.getBytes());
        channel.writeInbound(buf.retain());

        Assert.assertTrue(channel.finish());
        Assert.assertTrue(channel.isOpen());

    }
}
