package com.xuxd.chat.client.processor;

import com.xuxd.chat.common.Constants;
import com.xuxd.chat.common.beans.Message;
import com.xuxd.chat.common.menu.MenuOptions;
import com.xuxd.chat.common.netty.AbstractEndpoint;

import java.util.Scanner;

/**
 * @Auther: 许晓东
 * @Date: 19-8-12 19:51
 * @Description:
 */
public class ChatRoomProcessor implements Processor {
    private AbstractEndpoint<Message> endpoint;
    private String clientName = null;
    private boolean isMenuState = false;

    public ChatRoomProcessor(AbstractEndpoint<Message> endpoint) {
        this.endpoint = endpoint;
    }

    public ChatRoomProcessor(AbstractEndpoint<Message> endpoint, String clientName) {
        this.endpoint = endpoint;
        this.clientName = clientName;
    }

    public void run() throws Exception {
        Message message = new Message(Constants.MsgType.COMMAND, Constants.Command.ENTER_CHAT_ROOM);
        message.put(Constants.MessageKeys.CLIENT_NAME, clientName);
        endpoint.write(message);
        String input = String.format(">>>>>>>>>>>>>>>>>>>>%s进入聊天室<<<<<<<<<<<<<<<<<<<", clientName);
        System.out.println(input);
        isMenuState = false;
        endpoint.write(new Message(Constants.MsgType.NOTIFY, input));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!isMenuState) {
                System.out.print(clientName + "：");
            }
            input = scanner.next();
            if (MenuOptions.MENU.equals(MenuOptions.valueOf2(input))) {
                System.out.println("请确认是否要回到主菜单>>>>>>>>>>>>>>>>>>>>>");
                isMenuState = true;
                break;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("{\"").append(clientName).append("\":");
            }
        }
    }
}
