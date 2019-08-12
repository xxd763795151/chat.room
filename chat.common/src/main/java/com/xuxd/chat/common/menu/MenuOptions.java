package com.xuxd.chat.common.menu;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 许晓东
 * @Date: 19-7-8 17:47
 * @Description:
 */
public enum MenuOptions {

    CHAT_ROOM("#1"),
    MENU("#0"),
    QUIT1("#9"),
    QUIT2("quit"),
    ERROR("#9999") {
        {
            innerMap.put("#1", "CHAT_ROOM");
            innerMap.put("#0", "MENU");
            innerMap.put("#9999", "ERROR");
            innerMap.put("#9", "QUIT1");
            innerMap.put("quit", "QUIT2");
        }
    };

    private String value;
    protected Map<String, String> innerMap = new HashMap<>();

    MenuOptions(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    // 根据字符串的值返回枚举常量
    public static MenuOptions valueOf2(String value) {
        String name = MenuOptions.ERROR.innerMap.get(value.trim().intern());
        if (name == null) {
            return MenuOptions.ERROR;
        }
        MenuOptions option = valueOf(name);

        return option;
    }

}
