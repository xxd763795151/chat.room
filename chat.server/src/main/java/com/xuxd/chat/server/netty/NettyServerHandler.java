package com.xuxd.chat.server.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * @Auther: 许晓东
 * @Date: 19-6-18 17:51
 * @Description:
 */
public class NettyServerHandler extends ChannelDuplexHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);

    private NettyServer server;

    public NettyServerHandler(NettyServer server) {
        this.server = server;
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
        server.channelInactive(ctx);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        super.connect(ctx, remoteAddress, localAddress, promise);
        LOGGER.info("connect", ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        server.channelRead(ctx, msg);
    }
}

