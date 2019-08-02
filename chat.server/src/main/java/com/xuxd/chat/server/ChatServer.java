package com.xuxd.chat.server;

import com.xuxd.chat.common.Constants;
import com.xuxd.chat.common.MessageUtils;
import com.xuxd.chat.common.menu.Menu;
import com.xuxd.chat.common.netty.AbstractEndpoint;
import com.xuxd.chat.server.manage.ClientManager;
import com.xuxd.chat.server.netty.NettyServer;
import com.xuxd.chat.server.netty.NettyServerConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: 许晓东
 * @Date: 19-6-14 14:45
 * @Description: 聊天服务器初始化
 */
public class ChatServer extends AbstractEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServer.class);

    private NettyServer nettyServer;
    private NettyServerConfig nettyServerConfig;
    private Menu menu;
    private ClientManager clientManager = new ClientManager();

    ChatServer(NettyServerConfig config) {
        this.nettyServerConfig = config;
        this.nettyServer = new NettyServer(nettyServerConfig, this);
    }

    public void start() {
        LOGGER.info("start chat room");
        nettyServer.start();
        menu = Menu.create();
        LOGGER.info("start completed");
        try {
            nettyServer.getChannel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("error: " + e);
        } finally {
            shutdown();
        }
    }

    @Override
    public void shutdown() {
        nettyServer.close();

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String welcome = MessageUtils.concatDefaultDelimiter(menu.welcome() + "\r\n" + menu.menu());
        ByteBuf byteBuf = Unpooled.buffer(welcome.length());
        byteBuf.writeBytes(welcome.getBytes(Constants.CharsetName.UTF_8));
        ctx.writeAndFlush(byteBuf);
        // 注册客户端channel
        clientManager.registerClient(ctx.channel());
    }
}
