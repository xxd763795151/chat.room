package com.xuxd.chat.client;

import com.xuxd.chat.client.netty.NettyClient;
import com.xuxd.chat.client.netty.NettyClientConfig;

/**
 * Created by dong on 2019/6/19.
 */
public class ChatClient {

    private NettyClient nettyClient;
    private NettyClientConfig nettyClientConfig;

    public ChatClient(NettyClientConfig nettyClientConfig) {
        this.nettyClientConfig = nettyClientConfig;
        this.nettyClient = new NettyClient(nettyClientConfig);
    }

    public void start() {
        this.nettyClient.start();
    }
}
