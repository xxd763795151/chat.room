package com.xuxd.chat.common;

/**
 * @Auther: 许晓东
 * @Date: 19-6-14 15:34
 * @Description: 常量类
 */
public class Constants {

    public interface Server {
        String HOST_L = "ip";
        String PORT_L = "port";
        String HOST_S = "i";
        String PORT_S = "p";
    }

    public interface CharsetName {
        String UTF_8 = "UTF-8";
    }

    public interface Delimiter {
        String DEFAULT = "$_$";
    }

    public interface MsgType {
        int COMMAND = 1 << 0;
        int MESSAGE = 1 << 1;
        int NOTIFY = 1 << 2;
    }

    public interface Command {
        String ENTER_CHAT_ROOM = "chat_room_command";
        String RETURN_MEUN = "return_menu_command";
    }

    // 回车换行符
    public static String RETURN_NEW_LINE = System.getProperty("line.separator");
}
