package mappedByteBuffer;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class MappedByteBufferTest {
    static final long fileSize = 1024 * 1024;
    static FileChannel fileChannel;
    static MappedByteBuffer mappedByteBuffer ;
    static {
        try {
            fileChannel = new RandomAccessFile("test.txt", "rw").getChannel();
            mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void testSlice() {
        int pos = 10;
        int readPosition = 100;

        ByteBuffer byteBuffer = mappedByteBuffer.slice();
        byteBuffer.position(pos);
        int size = readPosition - pos;
        ByteBuffer byteBufferNew = byteBuffer.slice();
        byteBufferNew.limit(size);
        System.out.println();
    }

    public static void test2() throws Exception {
        String data = "test";
        ByteBuffer  byteBuffer = ByteBuffer.allocateDirect(1024);
        byteBuffer.put(data.getBytes(StandardCharsets.UTF_8));
        mappedByteBuffer.put(data.getBytes(StandardCharsets.UTF_8));

        Thread.sleep(500);
        mappedByteBuffer.force();

        Thread.sleep(500);
        fileChannel.write(byteBuffer);
    }

    public static void main(String[] args) throws Exception {
        //testSlice();
        test2();
    }

}
