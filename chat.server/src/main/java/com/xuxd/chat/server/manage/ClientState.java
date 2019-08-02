package com.xuxd.chat.server.manage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Auther: 许晓东
 * @Date: 19-8-2 14:21
 * @Description:
 */
public class ClientState {

    private ConcurrentMap<ChannelId, Channel> channelConcurrentMap = new ConcurrentHashMap<>();

    static enum State {
        ACTIVE,
        CHAT_ROOM;
    }


    public void register(Channel channel) {
        channelConcurrentMap.putIfAbsent(channel.id(), channel);
    }



}
