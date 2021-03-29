package com.xuxd.chat.client.netty;

import com.xuxd.chat.client.ChatClient;
import com.xuxd.chat.common.netty.NettyRemoting;
import com.xuxd.chat.common.netty.decoder.MsgFrameDecoder;
import com.xuxd.chat.common.netty.decoder.MsgPackDecoder;
import com.xuxd.chat.common.netty.encoder.MsgFrameEncoder;
import com.xuxd.chat.common.netty.encoder.MsgPackEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dong on 2019/6/19.
 * client通信功能，基于netty实例
 */
public class NettyClient implements NettyRemoting {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    private NettyClientConfig nettyClientConfig;
    private Bootstrap bootstrap;
    private EventLoopGroup workerGroup;
    private ChatClient chatClient;
    private Channel channel;

    public NettyClient(NettyClientConfig nettyClientConfig) {
        this.nettyClientConfig = nettyClientConfig;
    }

    public NettyClient(NettyClientConfig nettyClientConfig, ChatClient chatClient) {
        this.nettyClientConfig = nettyClientConfig;
        this.chatClient = chatClient;
    }

    public void start() {
        bootstrap = new Bootstrap();
        workerGroup = new NioEventLoopGroup(nettyClientConfig.getWorkerThreads(), new DefaultThreadFactory("ChatClientWorker"));

        final ChannelHandler handler = new NettyClientHandler(chatClient);
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, nettyClientConfig.isReuseAddr())
                .option(ChannelOption.SO_KEEPALIVE, nettyClientConfig.isKeepAlive())
                .remoteAddress(nettyClientConfig.getIp(), nettyClientConfig.getPort())
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                new MsgFrameDecoder()
                                , new MsgFrameEncoder()
                                , new MsgPackEncoder()
                                , new MsgPackDecoder()
                                , handler
                        );
                    }
                });
        ChannelFuture future = bootstrap.connect().syncUninterruptibly();
        channel = future.channel();
    }

    public void close() {
        workerGroup.shutdownGracefully();

    }

    public Channel getChannel() {
        return channel;
    }

}
