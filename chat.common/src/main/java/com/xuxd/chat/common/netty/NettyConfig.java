package com.xuxd.chat.common.netty;

import java.util.Map;

/**
 * @Auther: 许晓东
 * @Date: 19-6-14 16:43
 * @Description:
 */
public abstract class NettyConfig {

    protected String ip = "0.0.0.0";
    protected int port = 8888;
    protected int bossThreads = 1;
    protected int workerThreads = Runtime.getRuntime().availableProcessors() + 2;
    protected boolean noDelay = true;
    protected boolean reuseAddr = true;
    protected int backlog = 1024;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBossThreads() {
        return bossThreads;
    }

    public void setBossThreads(int bossThreads) {
        this.bossThreads = bossThreads;
    }

    public int getWorkerThreads() {
        return workerThreads;
    }

    public void setWorkerThreads(int workerThreads) {
        this.workerThreads = workerThreads;
    }

    public boolean isNoDelay() {
        return noDelay;
    }

    public void setNoDelay(boolean noDelay) {
        this.noDelay = noDelay;
    }

    public boolean isReuseAddr() {
        return reuseAddr;
    }

    public void setReuseAddr(boolean reuseAddr) {
        this.reuseAddr = reuseAddr;
    }

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    public abstract void setConfig(Map<String, String> map);
}
