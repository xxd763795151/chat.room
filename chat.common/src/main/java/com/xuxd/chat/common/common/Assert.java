package com.xuxd.chat.common.common;

import com.xuxd.chat.common.exception.AssertExcession;

/**
 * @Auther: 许晓东
 * @Date: 19-8-12 20:03
 * @Description:
 */
public class Assert {

    public static void notNull(Object o) throws AssertExcession {
        if (o == null) {
            throw new AssertExcession("不能为空", new NullPointerException());
        }
    }
}
