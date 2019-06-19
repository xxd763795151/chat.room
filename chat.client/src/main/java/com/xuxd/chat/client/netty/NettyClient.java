package com.xuxd.chat.client.netty;

import com.xuxd.chat.common.netty.NettyRemoting;

/**
 * Created by dong on 2019/6/19.
 * client通信功能，基于netty实例
 */
public class NettyClient implements NettyRemoting {

    private NettyClientConfig nettyClientConfig;

    public NettyClient(NettyClientConfig nettyClientConfig) {
        this.nettyClientConfig = nettyClientConfig;
    }


    public void start() {

    }
}
