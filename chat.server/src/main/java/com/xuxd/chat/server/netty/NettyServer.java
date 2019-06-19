package com.xuxd.chat.server.netty;

import com.xuxd.chat.common.netty.NettyRemoting;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @Auther: 许晓东
 * @Date: 19-6-14 16:30
 * @Description: 基于netty实现服务端通信
 */
public class NettyServer implements NettyRemoting {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    private NettyServerConfig config;
    private ServerBootstrap bootstrap;
    private EventLoopGroup bossGroup, workerGroup;

    public NettyServer(NettyServerConfig config) {
        this.config = config;
    }

    public void start() {

        try {
            bootstrap = new ServerBootstrap();
            bossGroup = new NioEventLoopGroup(config.getBossThreads(), new DefaultThreadFactory("ChatServerBoss", true));
            workerGroup = new NioEventLoopGroup(config.getWorkerThreads(), new DefaultThreadFactory("ChatServerWorker", true));
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, config.getBacklog())
                    .option(ChannelOption.SO_REUSEADDR, config.isReuseAddr())
                    .localAddress(config.getIp(), config.getPort())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new FixedLengthFrameDecoder(1024)
                                    , new StringDecoder(Charset.defaultCharset())
                                    , new NettyServerHandler()
                            );
                        }
                    });
            ChannelFuture future = bootstrap.bind().syncUninterruptibly();
            LOGGER.info("chat server started, bind ip:{},port:{}", config.getIp(), config.getPort());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("error: " + e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }


    }
}
