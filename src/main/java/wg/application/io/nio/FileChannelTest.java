package wg.application.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {


    public static void main(String[] args) throws IOException {
        // 創建 fileChannel
        RandomAccessFile accessFile = new RandomAccessFile("d:\\wg\\nio-test.txt", "rw");
        FileChannel accessFileChannel = accessFile.getChannel();

        // 創建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 讀取數據到buffer中
        int read = accessFileChannel.read(byteBuffer);
        while (read != -1) {
            System.out.println("has content");
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                byte b = byteBuffer.get();
                System.out.println((char) b);
            }
            byteBuffer.clear();
            read = accessFileChannel.read(byteBuffer);
        }
        accessFile.close();

    }
}
