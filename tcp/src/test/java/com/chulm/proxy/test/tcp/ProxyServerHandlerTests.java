package com.chulm.proxy.test.tcp;

import com.chulm.proxy.tcp.client.ProxyClient;
import com.chulm.proxy.tcp.server.ProxyServerChannelHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProxyServerHandlerTests {
    @Mock
    private ProxyClient proxyClient;

    @Before
    public void setClient() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void writeToChannel() {
        String msg = "test";

        ProxyServerChannelHandler handler = new ProxyServerChannelHandler();
        handler.setClient(proxyClient);

        EmbeddedChannel channel = new EmbeddedChannel(handler);
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg.getBytes());
        channel.writeInbound(buf.retain());

        Assert.assertFalse(channel.finish());
        Assert.assertFalse(channel.isOpen());
    }
}
