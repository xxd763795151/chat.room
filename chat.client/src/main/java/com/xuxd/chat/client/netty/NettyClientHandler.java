package com.xuxd.chat.client.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * Created by dong on 2019/6/19.
 */
public class NettyClientHandler extends ChannelDuplexHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientHandler.class);

    private NettyClient client;

    public NettyClientHandler(NettyClient client) {
        this.client = client;
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        super.bind(ctx, localAddress, promise);
        LOGGER.info("bind {}", localAddress);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        LOGGER.info("registered {}", ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        LOGGER.info("channelActive {}", ctx);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        super.connect(ctx, remoteAddress, localAddress, promise);
        LOGGER.info("connect", ctx);
    }
}
