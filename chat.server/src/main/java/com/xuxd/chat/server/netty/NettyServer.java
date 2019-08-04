package com.xuxd.chat.server.netty;

import com.xuxd.chat.common.netty.NettyRemoting;
import com.xuxd.chat.common.netty.decoder.MsgPackDecoder;
import com.xuxd.chat.common.netty.encoder.MsgPackEncoder;
import com.xuxd.chat.server.ChatServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Channel channel;
    private ChatServer chatServer;

    public NettyServer(NettyServerConfig config) {
        this.config = config;
    }

    public NettyServer(NettyServerConfig config, ChatServer chatServer) {
        this.config = config;
        this.chatServer = chatServer;
    }

    public void start() {

        bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(config.getBossThreads(), new DefaultThreadFactory("ChatServerBoss", true));
        workerGroup = new NioEventLoopGroup(config.getWorkerThreads(), new DefaultThreadFactory("ChatServerWorker", true));
        final ChannelHandler handler = new NettyServerHandler(chatServer);
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, config.getBacklog())
                .option(ChannelOption.SO_REUSEADDR, config.isReuseAddr())
                .localAddress(config.getIp(), config.getPort())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //ByteBuf delimiter = Unpooled.copiedBuffer(Constants.Delimiter.DEFAULT.getBytes(Constants.CharsetName.UTF_8));
                        ch.pipeline().addLast(
                                /*new DelimiterBasedFrameDecoder(65535, delimiter)
                                , new StringDecoder(Charset.forName(Constants.CharsetName.UTF_8))*/
                                new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2)
                                , new MsgPackDecoder()
                                , new LengthFieldPrepender(2)
                                , new MsgPackEncoder()
                                , handler
                        );
                    }
                });
        ChannelFuture future = bootstrap.bind().syncUninterruptibly();
        LOGGER.info("netty server started, bind ip:{},port:{}", config.getIp(), config.getPort());
        channel = future.channel();

    }

    public void close() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }


    public Channel getChannel() {
        return channel;
    }


}
