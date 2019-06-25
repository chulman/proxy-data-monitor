package com.chulm.proxy.tcp.utils;

public class HexConverter {

    public static String valueOf(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            String hex = String.format("%02X", b);
            buffer.append(hex);
        }
        return buffer.toString();
    }
}
