package com.xuxd.chat.server.netty;

import com.xuxd.chat.common.netty.NettyRemoting;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * @Auther: 许晓东
 * @Date: 19-6-14 16:30
 * @Description: 基于netty实现服务端通信
 */
public class NettyServer extends NettyRemoting {

    private NettyServerConfig config;
    private ServerBootstrap bootstrap;
    private EventLoopGroup bossGroup, workerGroup;

    public NettyServer(NettyServerConfig config) {
        this.config = config;
    }

    public void start() {

        bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(config.getBossThreads(), new DefaultThreadFactory("ChatServerBoss", true));
        workerGroup = new NioEventLoopGroup(config.getWorkerThreads(), new DefaultThreadFactory("ChatServerWorker", true));
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, config.getBacklog())
                .option(ChannelOption.SO_REUSEADDR, config.isReuseAddr())
                .option(ChannelOption.SO_KEEPALIVE, config.isKeepAlive())
                .localAddress(config.getIp(), config.getPort())
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(

                        );
                    }
                });


    }
}
