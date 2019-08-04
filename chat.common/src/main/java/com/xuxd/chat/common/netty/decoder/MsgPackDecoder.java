package com.xuxd.chat.common.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @Auther: 许晓东
 * @Date: 19-8-4 21:14
 * @Description: messagepack 解码器
 */
public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {
    private MessagePack messagePack = new MessagePack();


    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final int length = msg.readableBytes();
        final byte[] bytes = new byte[length];

        msg.getBytes(msg.readerIndex(), bytes, 0, length);
        out.add(messagePack.read(bytes));
    }
}
