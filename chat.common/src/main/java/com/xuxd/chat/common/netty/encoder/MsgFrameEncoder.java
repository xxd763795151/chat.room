package com.xuxd.chat.common.netty.encoder;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author 许晓东
 * @date 2021-03-29 23:56
 * @Description: msg frame encoder
 */
public class MsgFrameEncoder extends LengthFieldPrepender {

    public MsgFrameEncoder() {
        super(2);
    }
}
