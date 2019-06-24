package com.chulm.proxy.tcp;

public class RunProxyApplication {

    private static String  PROXY_HOST = "";
    private static int PROXY_PORT = 0;

    private static String CONNECT_HOST = "";
    private static int CONNECT_PORT = 0;

    public static void main(String[] args){
        if(args.length >=0) PROXY_HOST = args[0];
        if(args.length >=1) PROXY_PORT = Integer.parseInt(args[1]);
        if(args.length >=2) CONNECT_HOST = args[2];
        if(args.length >=3) CONNECT_PORT = Integer.parseInt(args[3]);


    }
}
