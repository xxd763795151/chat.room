package com.xuxd.chat.client;

import com.xuxd.chat.client.netty.NettyClient;
import com.xuxd.chat.client.netty.NettyClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dong on 2019/6/19.
 */
public class ChatClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClient.class);

    private NettyClient nettyClient;
    private NettyClientConfig nettyClientConfig;

    public ChatClient(NettyClientConfig nettyClientConfig) {
        this.nettyClientConfig = nettyClientConfig;
        this.nettyClient = new NettyClient(nettyClientConfig);
    }

    public void start() {
        LOGGER.info("启动聊天室");
        this.nettyClient.start();
    }
}
