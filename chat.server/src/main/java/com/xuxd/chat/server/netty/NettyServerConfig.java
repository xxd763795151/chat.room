package com.xuxd.chat.server.netty;

import com.xuxd.chat.common.netty.NettyConfig;

import java.util.Map;

import static com.xuxd.chat.common.Constants.Server;

/**
 * @Auther: 许晓东
 * @Date: 19-6-14 16:31
 * @Description:
 */
public class NettyServerConfig extends NettyConfig {

    private boolean reuseAddr = true;

    public NettyServerConfig() {
    }

    public NettyServerConfig(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public boolean isReuseAddr() {
        return reuseAddr;
    }

    @Override
    public void setReuseAddr(boolean reuseAddr) {
        this.reuseAddr = reuseAddr;
    }

    @Override
    public String toString() {
        return "NettyServerConfig{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }

    public void setConfig(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (Server.HOST_S.equals(entry.getKey())) {
                setIp(entry.getValue());
            } else if (Server.PORT_S.equals(entry.getKey())) {
                setPort(Integer.valueOf(entry.getValue()));
            }
        }
    }
}
