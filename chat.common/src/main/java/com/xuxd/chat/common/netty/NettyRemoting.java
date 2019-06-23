package com.xuxd.chat.common.netty;

import io.netty.channel.Channel;

/**
 * @Auther: 许晓东
 * @Date: 19-6-18 17:31
 * @Description: 基于netty的远程通信接口
 */
public interface NettyRemoting {

    void start();

    void close();

    Channel getChannel();

}
