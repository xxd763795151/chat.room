package com.xuxd.chat.common.beans;

import com.alibaba.fastjson.JSONObject;
import org.msgpack.annotation.Optional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 许晓东
 * @Date: 19-8-2 15:06
 * @Description:
 */
@org.msgpack.annotation.Message
public class Message extends JSONObject implements Serializable {

    private int type;
    private String body;
    private Map<String, String> attachment = new HashMap<>();

    public Message() {
    }

    public Message(int type) {
        this.type = type;
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

    public Map<String, String> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, String> attachment) {
        this.attachment = attachment;
    }

     @Override
    public Object put(String key, Object value) {
        return attachment.put(key, String.valueOf(value));
    }

    @Override
    public Map<String, Object> getInnerMap() {
        Map<String, Object> map = new HashMap<>(attachment);
        return map;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
