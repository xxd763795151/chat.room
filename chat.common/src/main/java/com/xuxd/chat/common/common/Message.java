package com.xuxd.chat.common.common;

import java.io.Serializable;

/**
 * @Auther: 许晓东
 * @Date: 19-8-2 15:06
 * @Description:
 */
@org.msgpack.annotation.Message
public class Message implements Serializable {

    private int type;
    private String body;

    public Message() {
    }

    public Message(int type, String body) {
        this.type = type;
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return body;
    }

}
