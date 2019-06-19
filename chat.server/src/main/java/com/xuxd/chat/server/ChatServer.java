package com.xuxd.chat.server;

import com.xuxd.chat.server.netty.NettyServer;

/**
 * @Auther: 许晓东
 * @Date: 19-6-14 14:45
 * @Description:
 */
public class ChatServer {

    private NettyServer nettyServer;

    ChatServer(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    public void start() {
        this.nettyServer.start();
    }
}
