package com.xuxd.chat.common.netty.decoder;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author 许晓东
 * @date 2021-03-29 23:51
 * @Description: message frame decoder
 */
public class MsgFrameDecoder extends LengthFieldBasedFrameDecoder {

    public MsgFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
