package com.xuxd.chat.client;

import com.xuxd.chat.client.netty.NettyClient;
import com.xuxd.chat.client.netty.NettyClientConfig;
import com.xuxd.chat.common.netty.AbstractEndpoint;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by dong on 2019/6/19.
 */
public class ChatClient extends AbstractEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClient.class);

    private NettyClient nettyClient;
    private NettyClientConfig nettyClientConfig;

    public ChatClient(NettyClientConfig nettyClientConfig) {
        this.nettyClientConfig = nettyClientConfig;
        this.nettyClient = new NettyClient(nettyClientConfig, this);
    }

    public void start() {
        LOGGER.info("start chat client");
        this.nettyClient.start();
        while (true) {
            try {
                inputPrompt("input: ");
                byte[] input = new byte[65535];
                System.in.read(input);
            } catch (IOException e) {
                LOGGER.error("读取输入失败，退出", e);
                shutdown();
            }
        }
    }

    public void shutdown() {
        nettyClient.close();
        System.exit(0);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        echo((String) msg);
    }
}
