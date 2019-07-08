package com.xuxd.chat.client;

import com.xuxd.chat.client.netty.NettyClient;
import com.xuxd.chat.client.netty.NettyClientConfig;
import com.xuxd.chat.common.menu.MenuOptions;
import com.xuxd.chat.common.netty.AbstractEndpoint;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.xuxd.chat.common.Constants.CharsetName;

/**
 * Created by dong on 2019/6/19.
 */
public class ChatClient extends AbstractEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClient.class);

    private NettyClient nettyClient;
    private NettyClientConfig nettyClientConfig;
    private Boolean firstMessage = true;

    public ChatClient(NettyClientConfig nettyClientConfig) {
        this.nettyClientConfig = nettyClientConfig;
        this.nettyClient = new NettyClient(this.nettyClientConfig, this);
    }

    private static final int _1K = 1024, MAX_LENGTH = 65535;

    public void start() {
        LOGGER.info("start chat client");
        this.nettyClient.start();
        while (true) {
            try {
                // 第一条消息还未到来
                if (firstMessage) {
                    synchronized (this) {
                        if (firstMessage) {
                            this.wait();
                        }
                    }
                }
                inputPrompt("请输入你的选项（#+数字）: ");
                byte[] input = new byte[_1K];
                System.in.read(input);
                MenuOptions option = MenuOptions.valueOf2(new String(input, CharsetName.UTF_8));
                switch (option) {
                    case MENU:
                        System.out.println(">>>>回到主菜单");
                        break;
                    case CHAT_ROOM:
                        System.out.println(">>>>进入聊天室");
                        break;
                    case ERROR:
                    default:
                        System.out.println("无效选项");
                        break;
                }
            } catch (IOException e) {
                LOGGER.error("读取输入失败，退出", e);
                shutdown();
            } catch (InterruptedException ignore) {
                LOGGER.error("interrupt: ", ignore);
            }
        }

    }

    public void shutdown() {
        nettyClient.close();
        System.exit(0);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (firstMessage) {
            synchronized (this) {
                if (firstMessage) {
                    firstMessage = !firstMessage;
                    echo((String) msg);
                    this.notify();
                }
            }
        } else {
            echo((String) msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        LOGGER.error("ctx : {}, cause: {}", ctx, cause);
    }
}
