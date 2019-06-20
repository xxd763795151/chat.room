package com.xuxd.chat.client.netty;

import com.xuxd.chat.common.netty.NettyConfig;

import java.util.Map;

/**
 * Created by dong on 2019/6/19.
 */
public class NettyClientConfig extends NettyConfig {

    private boolean reuseAddr = false;
    private boolean keepAlive = true;

    public NettyClientConfig() {
        this.ip = "localhost";
        this.workerThreads = 2; // p2p的两个就行
    }

    public void setConfig(Map<String, String> map) {

    }

    @Override
    public boolean isReuseAddr() {
        return reuseAddr;
    }

    @Override
    public void setReuseAddr(boolean reuseAddr) {
        this.reuseAddr = reuseAddr;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }
}
