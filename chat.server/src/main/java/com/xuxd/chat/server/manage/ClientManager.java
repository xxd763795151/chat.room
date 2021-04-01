package com.xuxd.chat.server.manage;

import com.xuxd.chat.common.Constants;
import com.xuxd.chat.common.beans.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @Auther: 许晓东
 * @Date: 19-8-2 14:21
 * @Description:
 */
public class ClientManager {

    private Map<ClientState.State, ClientState> clientStateMap = new HashMap<ClientState.State, ClientState>();


    public void registerClient(Channel channel, Map<String, Object> properties) {
        // 激活状态
        if (!clientStateMap.containsKey(ClientState.State.ACTIVE)) {
            synchronized (clientStateMap) {
                if (!clientStateMap.containsKey(ClientState.State.ACTIVE)) {
                    clientStateMap.put(ClientState.State.ACTIVE, new ClientState(ClientState.State.ACTIVE));
                }
            }
        }
        ClientState clientState = clientStateMap.get(ClientState.State.ACTIVE);

        clientState.register(channel, properties);
    }

    public void unregisterClient(Channel channel) {
        for (ClientState clientState : clientStateMap.values()) {
            clientState.unregister(channel);
        }
    }

    public void dispatcher(Channel src, Message message) {
        ClientState.State state = ClientState.State.UNKNOWN;
        ClientState clientState = null;
        for (Map.Entry<ClientState.State, ClientState> entry :
                clientStateMap.entrySet()) {
            if (entry.getValue().isCurrentState(src)) {
                state = entry.getKey();
                clientState = entry.getValue();
                break;
            }
        }
        switch (state) {
            case CHAT_ROOM:
                // 广播消息
                Map<ChannelId, Channel> channelMap = clientState.getChannels();
                for (Map.Entry<ChannelId, Channel> entry : channelMap.entrySet()) {
                    if (entry.getKey().equals(src.id())) {
                        continue;
                    }
                    entry.getValue().writeAndFlush(message);
                }
                break;
            case ACTIVE:
                break;
            default:
                break;
        }
    }

    public void switchState(Channel src, Message message) {

        for (Map.Entry<ClientState.State, ClientState> entry : clientStateMap.entrySet()) {
            if (entry.getValue().getChannels().containsKey(src.id())) {
                ConcurrentMap<ChannelId, Channel> concurrentMap = entry.getValue().getChannels();
                concurrentMap.remove(src.id(), src);
            }
        }
        String command = message.getBody();
        if (Constants.Command.ENTER_CHAT_ROOM.equals(command)) {
            if (!clientStateMap.containsKey(ClientState.State.CHAT_ROOM)) {
                synchronized (clientStateMap) {
                    if (!clientStateMap.containsKey(ClientState.State.CHAT_ROOM)) {
                        clientStateMap.putIfAbsent(ClientState.State.CHAT_ROOM, new ClientState(ClientState.State.CHAT_ROOM));
                    }
                }
            }
            ClientState clientState = clientStateMap.get(ClientState.State.CHAT_ROOM);
            clientState.register(src, message.getInnerMap());
        } else if (Constants.Command.RETURN_MENU.equals(command)) {
            registerClient(src, message.getInnerMap());
        }
    }

}
