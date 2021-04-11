package com.xuxd.chat.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author 许晓东
 * @date 2021-04-12 0:10
 * @Description:
 */
public class SystemUtils {

    public static List<String> getLocalInetAddress() {
        List<String> inetAddressList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface networkInterface = enumeration.nextElement();
                if (!networkInterface.isUp() || networkInterface.isLoopback()) {
                    continue;
                }
                Enumeration<InetAddress> addrs = networkInterface.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    inetAddressList.add(addrs.nextElement().getHostAddress());
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException("get local inet address fail", e);
        }

        return inetAddressList;
    }

    public static long getPID() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        if (processName != null && processName.length() > 0) {
            try {
                return Long.parseLong(processName.split("@")[0]);
            } catch (Exception e) {
                return 0;
            }
        }

        return 0;
    }

    public static String getClientId() {
        List<String> addrs = getLocalInetAddress();
        String clientId = addrs.isEmpty() ? String.valueOf(System.currentTimeMillis()) : addrs.get(0);
        return clientId + "@" + getPID();
    }
}
