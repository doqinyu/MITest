package netty.demo.model;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

/**
 * ByteToMessageDecoder :入站编码器，字节到消息
 */
public class MessageDecoder extends ByteToMessageDecoder {
    /**
     * 包长度
     */
    public static final int HEAD_LENGTH = 45;
    /**
     * 标志头
     */
    public static final byte PACKAGE_TAG = 0x01;

    //从ByteBuf中获取字节，转换成对象，写入到List中
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        //message的header字段不全
        if (byteBuf.readableBytes() < HEAD_LENGTH) {
            throw new CorruptedFrameException("包长度问题");
        }

        byte tag = byteBuf.readByte();
        if (tag != PACKAGE_TAG) {
            throw new CorruptedFrameException("标志错误");
        }

        byte encode = byteBuf.readByte();
        byte encrypt = byteBuf.readByte();
        byte extend1 = byteBuf.readByte();
        byte extend2 = byteBuf.readByte();

        byte[] sessionByte = new byte[32];
        byteBuf.readBytes(sessionByte);
        String sessionId = new String(sessionByte, "UTF-8");
        int length = byteBuf.readInt();
        int command = byteBuf.readInt();

        Header header = new Header(tag, encode, encrypt, extend1, extend2, sessionId, length, command);

        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        Message message = new Message(header, new String(data, "UTF-8"));

        list.add(message);

    }
}
