package netty.demo.model;

import lombok.Data;

/**
 * 自定义协议包头
 */
@Data
public class Header {

    private byte tag;
    /**
     * 编码
     */
    private byte encode;
    /**
     * 加密
     */
    private byte encrypt;
    /**
     * 其他字段
     */
    private byte extend1;
    /**
     * 其他2
     */
    private byte extend2;

    private String sessionId;
    /**
     * 包的长度
     */
    private int length = 1024;
    /**
     * 命令
     */
    private int command;

    public Header(String sessionId) {
        this.encode = 0;
        this.encrypt = 0;
        this.sessionId = sessionId;
    }

    public Header(byte tag, byte encode, byte encrypt, byte extend1, byte extend2, String sessionId, int length, int command) {
        this.tag = tag;
        this.encode = encode;
        this.encrypt = encrypt;
        this.extend1 = extend1;
        this.extend2 = extend2;
        this.sessionId = sessionId;
        this.length = length;
        this.command = command;
    }
}
