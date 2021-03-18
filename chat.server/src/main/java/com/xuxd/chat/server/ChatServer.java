package com.xuxd.chat.server;

import com.xuxd.chat.common.Constants;
import com.xuxd.chat.common.beans.Message;
import com.xuxd.chat.common.menu.Menu;
import com.xuxd.chat.common.netty.AbstractEndpoint;
import com.xuxd.chat.server.manage.ClientManager;
import com.xuxd.chat.server.netty.NettyServer;
import com.xuxd.chat.server.netty.NettyServerConfig;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: 许晓东
 * @Date: 19-6-14 14:45
 * @Description: 聊天服务器初始化
 */
public class ChatServer extends AbstractEndpoint<Message> {

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
    public void write(Message message) throws Exception {

    }

    @Override
    public void receive(Message message) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        switch (message.getType()) {
            case Constants.MsgType.COMMAND: //指令类型的消息：回到菜单、进入聊天室等
                clientManager.switchState(ctx.channel(), message);
                break;
            case Constants.MsgType.MESSAGE:
            case Constants.MsgType.NOTIFY:
                // 转发消息
                clientManager.dispatcher(ctx.channel(), message);
                break;
            default:
                break;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String welcome = menu.welcome() + Constants.RETURN_NEW_LINE + menu.menu();
        Message message = new Message(Constants.MsgType.NOTIFY, welcome);
        ctx.write(message);
        ctx.flush();
        clientManager.registerClient(ctx.channel(), message.getInnerMap());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("ctx:{}, exception:{]", ctx, cause);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        clientManager.unregisterClient(ctx.channel());
    }
}
