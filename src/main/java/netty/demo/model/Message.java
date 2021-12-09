package netty.demo.model;

import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Data
public class Message {

    private Header header;
    private String data;

    public Message(Header header) {
        this.header = header;
    }

    public Message(Header header, String data) {
        this.header = header;
        this.data = data;
    }

    public byte[] toByte() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(MessageDecoder.PACKAGE_TAG);
        out.write(header.getEncode());
        out.write(header.getEncrypt());
        out.write(header.getExtend1());
        out.write(header.getExtend2());

        byte[] sessionDest = new byte[32];
        byte[] sessionSrc = header.getSessionId().getBytes(StandardCharsets.UTF_8);
        System.arraycopy(sessionSrc, 0, sessionDest, 0, sessionSrc.length);

        out.write(sessionDest);

        byte[] dataByte = data.getBytes(StandardCharsets.UTF_8);

        out.write(intToBytes2(dataByte.length));
        out.write(intToBytes2(header.getCommand()));

        out.write(dataByte);
        out.write('\n');

        return out.toByteArray();
    }

    public static byte[] intToBytes2(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>18) & 0xFF);
        src[2] = (byte) ((value>>8) & 0xFF);
        src[3] = (byte) ((value) & 0xFF);
        return src;
    }

}
