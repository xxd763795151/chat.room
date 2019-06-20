package com.xuxd.chat.client.netty;

import com.xuxd.chat.common.netty.NettyRemoting;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * Created by dong on 2019/6/19.
 * client通信功能，基于netty实例
 */
public class NettyClient implements NettyRemoting {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    private NettyClientConfig nettyClientConfig;
    private Bootstrap bootstrap;
    private EventLoopGroup workerGroup;

    public NettyClient(NettyClientConfig nettyClientConfig) {
        this.nettyClientConfig = nettyClientConfig;
    }


    public void start() {
        try {
            bootstrap = new Bootstrap();
            workerGroup = new NioEventLoopGroup(nettyClientConfig.getWorkerThreads(), new DefaultThreadFactory("ChatClientWorker"));

            final NettyClient client = this;
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_REUSEADDR, nettyClientConfig.isReuseAddr())
                    .option(ChannelOption.SO_KEEPALIVE, nettyClientConfig.isKeepAlive())
                    .remoteAddress(nettyClientConfig.getIp(), nettyClientConfig.getPort())
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new FixedLengthFrameDecoder(1024)
                                    , new StringDecoder(Charset.defaultCharset())
                                    , new NettyClientHandler(client)
                            );
                        }
                    });
            ChannelFuture future = bootstrap.connect().syncUninterruptibly();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("fatal error: ", e);
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public void close() {

    }

    public void send(Object message) {

    }

    public Object receive() {
        return null;
    }
}
