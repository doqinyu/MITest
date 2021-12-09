package netty.demo.model;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * MessageToByteEncoder: 出站解码器，消息到字节
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

    // 从Message中获取数据，解析成字节后，写入到ByteBuff中
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        Header header = message.getHeader();

        byteBuf.writeByte(MessageDecoder.PACKAGE_TAG);
        byteBuf.writeByte(header.getEncode());
        byteBuf.writeByte(header.getEncrypt());
        byteBuf.writeByte(header.getExtend1());
        byteBuf.writeByte(header.getExtend2());
        byteBuf.writeBytes(header.getSessionId().getBytes(StandardCharsets.UTF_8));
        byteBuf.writeInt(header.getLength());
        byteBuf.writeInt(header.getCommand());

        byteBuf.writeBytes(message.getData().getBytes(StandardCharsets.UTF_8));
    }
}
