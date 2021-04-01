package com.xuxd.chat.client;

import com.xuxd.chat.client.netty.NettyClient;
import com.xuxd.chat.client.netty.NettyClientConfig;
import com.xuxd.chat.client.processor.ChatRoomProcessor;
import com.xuxd.chat.client.processor.Processor;
import com.xuxd.chat.common.Constants;
import com.xuxd.chat.common.Assert;
import com.xuxd.chat.common.beans.Message;
import com.xuxd.chat.common.menu.MenuOptions;
import com.xuxd.chat.common.netty.AbstractEndpoint;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.xuxd.chat.common.Constants.RETURN_NEW_LINE;

/**
 * Created by dong on 2019/6/19.
 */
public class ChatClient extends AbstractEndpoint<Message> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClient.class);

    private NettyClient nettyClient;
    private NettyClientConfig nettyClientConfig;
    private Boolean firstMessageUnReached = true;
    private boolean quit = false;
    private Channel channel;
    private String clientName;
    private Map<MenuOptions, Processor> processorMap = new HashMap<>();

    public ChatClient(NettyClientConfig nettyClientConfig) {
        this.nettyClientConfig = nettyClientConfig;
        this.nettyClient = new NettyClient(this.nettyClientConfig, this);
    }

    private static final int _1K = 1024, MAX_LENGTH = 65535;

    public void start() {
        LOGGER.info("start chat client");
        this.nettyClient.start();
        channel = nettyClient.getChannel();
        while (true) {
            try {
                // 第一条消息还未到来
                if (firstMessageUnReached) {
                    synchronized (this) {
                        if (firstMessageUnReached) {
                            this.wait();
                        }
                    }

                }
                inputPrompt("请输入你的选项（#+数字）: ");
                MenuOptions option = MenuOptions.valueOf2(input());
                switch (option) {
                    case MENU:
                        System.out.println(">>>>回到主菜单");
                        break;
                    case CHAT_ROOM:
                        processorMap.get(MenuOptions.CHAT_ROOM).run();
                        break;
                    case QUIT1:
                    case QUIT2:
                        quit = true;
                        break;
                    case ERROR:
                    default:
                        System.out.println("无效选项");
                        break;
                }
            } catch (InterruptedException ignore) {
                LOGGER.error("interrupt: ", ignore);
            } catch (Exception e) {
                LOGGER.error("error: ", e);
                quit = true;
            }
            if (quit) {
                break;
            }
        }
        System.out.println("退出客户端!!!");
        shutdown();
    }

    private void resetBytes(byte[] bytes) {
        //Arrays.fi
    }

    private void registerProcessors() {
        Assert.notNull(clientName);
        processorMap.put(MenuOptions.CHAT_ROOM, new ChatRoomProcessor(this, clientName));
    }

    public void shutdown() {
        nettyClient.close();
        System.exit(0);
    }

    @Override
    public void write(Message message) {
        channel.writeAndFlush(message);
    }

    @Override
    public void receive(Message message) {
        if (firstMessageUnReached) {
            synchronized (this) {
                if (firstMessageUnReached) {
                    firstMessageUnReached = !firstMessageUnReached;
                    inputPrompt("起个名字：");
                    String inputName = input();
                    if (RETURN_NEW_LINE.equals(inputName)) {
                        clientName = "用户_" + String.valueOf(System.currentTimeMillis()).substring(6);
                    } else {
                        clientName = inputName;
                    }
                    System.out.println("你好： " + clientName);
                    //
                    registerProcessors();

                    handleMessage(message);
                    this.notify();
                }
            }
        } else {
            handleMessage(message);
        }
    }

    private void handleMessage(Message message) {
        switch (message.getType()) {
            case Constants.MsgType.MESSAGE:

                break;
            case Constants.MsgType.BROADCAST:
                echo(message.getBody());
                break;
            default:
                break;
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        LOGGER.error("ctx : {}, cause: {}", ctx, cause);
    }
}
