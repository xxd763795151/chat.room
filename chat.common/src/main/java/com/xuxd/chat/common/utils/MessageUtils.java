package com.xuxd.chat.common.utils;

import com.xuxd.chat.common.Constants;

/**
 * @Auther: 许晓东
 * @Date: 19-6-23 19:30
 * @Description:
 */
public class MessageUtils {

    public static String concatDefaultDelimiter(String message) {
        return message + Constants.Delimiter.DEFAULT;
    }
}
