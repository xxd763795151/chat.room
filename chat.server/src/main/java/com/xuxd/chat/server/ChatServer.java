package com.xuxd.chat.server;

import com.xuxd.chat.server.netty.NettyServer;
import com.xuxd.chat.server.netty.NettyServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: 许晓东
 * @Date: 19-6-14 14:45
 * @Description: 聊天服务器初始化
 */
public class ChatServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServer.class);

    private NettyServer nettyServer;
    private NettyServerConfig nettyServerConfig;

    ChatServer(NettyServerConfig config) {
        this.nettyServerConfig = config;
        this.nettyServer = new NettyServer(nettyServerConfig);
    }

    public void start() {
        LOGGER.info("start chat room");
        nettyServer.start();

        LOGGER.info("start completed");
        try {
            nettyServer.getChannel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("error: " + e);
        } finally {
            close();
        }
    }

    public void close() {
        nettyServer.close();
    }
}
