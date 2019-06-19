package com.xuxd.chat.server;

import com.xuxd.chat.server.netty.NettyServer;
import com.xuxd.chat.server.netty.NettyServerConfig;

/**
 * @Auther: 许晓东
 * @Date: 19-6-14 14:45
 * @Description: 聊天服务器初始化
 */
public class ChatServer {

    private NettyServer nettyServer;
    private NettyServerConfig nettyServerConfig;

    ChatServer(NettyServerConfig config) {
        this.nettyServerConfig = config;
        this.nettyServer = new NettyServer(nettyServerConfig);
    }

    public void start() {
        this.nettyServer.start();
    }
}
