package com.xuxd.chat.common.netty;

import io.netty.channel.ChannelDuplexHandler;

/**
 * @Auther: 许晓东
 * @Date: 19-6-23 19:41
 * @Description:
 */
public abstract class AbstractEndpoint extends ChannelDuplexHandler {

    public abstract void start();

    public abstract void shutdown();

    public void echo(String message) {
        System.out.println(message);
    }

    public void inputPrompt(String prompt) {
        System.out.print(prompt);
    }
}
