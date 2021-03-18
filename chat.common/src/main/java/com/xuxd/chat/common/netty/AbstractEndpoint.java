package com.xuxd.chat.common.netty;

import io.netty.channel.ChannelDuplexHandler;

import java.util.Scanner;

/**
 * @Auther: 许晓东
 * @Date: 19-6-23 19:41
 * @Description: 通信端点基类
 */
public abstract class AbstractEndpoint<T> extends ChannelDuplexHandler {

    public abstract void start();

    public abstract void shutdown();

    public void echo(Object message) {
        System.out.println(message);
    }

    public void inputPrompt(String prompt) {
        System.out.print(prompt);
    }

    public abstract void write(T message) throws Exception;

    public abstract void receive(T message) throws Exception;

    // 从键盘读取输入
    protected String input() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}
