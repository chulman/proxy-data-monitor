package com.chulm.proxy.tcp;

import com.chulm.proxy.tcp.client.ProxyClient;
import com.chulm.proxy.tcp.server.ProxyServer;

public class RunProxyApplication {

    private static String PROXY_LOCAL_HOST = "";
    private static int PROXY_LOCAL_PORT = 0;

    private static String PROXY_REMOTE_HOST = "";
    private static int PROXY_REMOTE_PORT = 0;

    public static void main(String[] args) {
        if (args.length >= 0) PROXY_LOCAL_HOST = args[0];
        if (args.length >= 1) PROXY_LOCAL_PORT = Integer.parseInt(args[1]);
        if (args.length >= 2) PROXY_REMOTE_HOST = args[2];
        if (args.length >= 3) PROXY_REMOTE_PORT = Integer.parseInt(args[3]);

        ProxyServer server = new ProxyServer();
        ProxyClient client = new ProxyClient();
        client.setServer(server);
        server.setClient(client);

        boolean isBind = server.bind(PROXY_LOCAL_HOST, PROXY_LOCAL_PORT);

        if (isBind) {
            client.connect(PROXY_REMOTE_HOST, PROXY_REMOTE_PORT);
        }
    }
}
