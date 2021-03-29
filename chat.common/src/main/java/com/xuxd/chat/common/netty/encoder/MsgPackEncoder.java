package com.xuxd.chat.common.netty.encoder;

import com.xuxd.chat.common.beans.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: 许晓东
 * @Date: 19-8-4 21:13
 * @Description: messagepack编码器
 */
public class MsgPackEncoder extends MessageToByteEncoder<Message> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgPackEncoder.class);

    private MessagePack messagePack = new MessagePack();

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("error: ", cause);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        byte[] bytes = messagePack.write(msg);
        out.writeBytes(bytes);
    }
}
