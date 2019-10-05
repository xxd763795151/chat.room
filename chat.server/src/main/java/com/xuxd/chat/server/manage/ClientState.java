package com.xuxd.chat.server.manage;

import com.xuxd.chat.common.Constants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Auther: 许晓东
 * @Date: 19-8-2 14:21
 * @Description:
 */
public class ClientState {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientState.class);

    private ConcurrentMap<ChannelId, Channel> channelConcurrentMap = new ConcurrentHashMap<ChannelId, Channel>();
    private ConcurrentMap<ChannelId, Map<String, Object>> propertiesConcurrentMap = new ConcurrentHashMap<>();

    private State state;

    public ClientState(State state) {
        this.state = state;
    }

    static enum State {
        ACTIVE,
        CHAT_ROOM,
        UNKNOWN
    }


    public void register(Channel channel, Map<String, Object> properties) {
        switch (state) {
            case ACTIVE:
                //LOGGER.info("客户端{}进入激活状态", properties.get(Constants.MessageKeys.CLIENT_NAME));
                break;
            case CHAT_ROOM:
                LOGGER.info("客户端{}进入聊天室", properties.get(Constants.MessageKeys.CLIENT_NAME));
                break;
            default:
                break;
        }
        channelConcurrentMap.putIfAbsent(channel.id(), channel);
        propertiesConcurrentMap.putIfAbsent(channel.id(), properties);
    }

    public void unregister(Channel channel) {
        switch (state) {
            case ACTIVE:
                //LOGGER.info("客户端{}取消激活", propertiesConcurrentMap.get(channel.id()).get(Constants.MessageKeys.CLIENT_NAME));
                break;
            case CHAT_ROOM:
                LOGGER.info("客户端{}离开聊天室", propertiesConcurrentMap.get(channel.id()).get(Constants.MessageKeys.CLIENT_NAME));
                break;
            default:
                break;
        }
        channelConcurrentMap.remove(channel.id(), channel);
        propertiesConcurrentMap.remove(channel.id());
    }

    public boolean isCurrentState(Channel channel) {
        return channelConcurrentMap.containsKey(channel.id());
    }

    public ConcurrentMap<ChannelId, Channel> getChannels() {
        return channelConcurrentMap;
    }
}
