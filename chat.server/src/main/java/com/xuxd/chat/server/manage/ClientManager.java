package com.xuxd.chat.server.manage;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 许晓东
 * @Date: 19-8-2 14:21
 * @Description:
 */
public class ClientManager {

    private Map<ClientState.State, ClientState> clientStateMap = new HashMap<ClientState.State, ClientState>();


    public void registerClient(Channel channel) {
        // 激活状态
        if (!clientStateMap.containsKey(ClientState.State.ACTIVE)) {
            synchronized (clientStateMap) {
                if (!clientStateMap.containsKey(ClientState.State.ACTIVE)){
                    clientStateMap.put(ClientState.State.ACTIVE, new ClientState());
                }
            }
        }
        ClientState clientState = clientStateMap.get(ClientState.State.ACTIVE);

        clientState.register(channel);
    }
}
