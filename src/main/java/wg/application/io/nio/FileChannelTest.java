package wg.application.io.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/************************************************************************
 * @author: wg
 * @description:
 * @params:
 * @return:
 * @createTime: 10:48  2022/5/9
 * @updateTime: 10:48  2022/5/9
 ************************************************************************/
public class FileChannelTest {

    // public static void main(String[] args) throws IOException {
    //     // 創建 fileChannel
    //     RandomAccessFile accessFile = new RandomAccessFile("d:\\wg\\nio-test.txt", "rw");
    //     FileChannel accessFileChannel = accessFile.getChannel();
    //
    //     // 創建buffer
    //     ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    //
    //     // 讀取數據到buffer中
    //     int read = accessFileChannel.read(byteBuffer);
    //     while (read != -1) {
    //         System.out.println("has content");
    //         byteBuffer.flip();
    //         while (byteBuffer.hasRemaining()) {
    //             byte b = byteBuffer.get();
    //             System.out.println((char) b);
    //         }
    //         byteBuffer.clear();
    //         read = accessFileChannel.read(byteBuffer);
    //     }
    //     accessFile.close();
    //
    // }

    // 204M
    private static String file = "H:/文档/电子书/《码出高效：Java开发手册》.pdf";

    public static void main(String[] args) throws IOException {

        // 普通 NIO 读取
        // 每次读取1024个字节
        // readByChannelTest(1024);	// 628 毫秒

        // 普通 NIO 读取
        // 每次读取1个字节,每次读取1个字节太慢了
        // readByChannelTest(2048); // 339 毫秒

        // 使用内存映射文件来读取
        // 从FileChannel拿到MappedByteBuffer,读取文件内容
        readByChannelTest3(1024);	// 61毫秒，甚至不到100毫秒

        // 对于一个只有204M的文件，验证使用FileChannel映射得到MappedByteBuffer
        // 就能大幅提交文件读取速度

        // 普通的缓冲流读取
        // readByBufferdStream();	// 127 毫秒
    }

    /**
     * 使用FileChannel读取文件,并打印在控制台
     *
     * @param 每次读取多少个字节
     * @throws IOException
     */
    public static void readByChannelTest(int allocate) throws IOException {
        long start = System.currentTimeMillis();
        FileInputStream fis = new FileInputStream(file);

        // 1.从FileInputStream对象获取文件通道FileChannel
        FileChannel channel = fis.getChannel();
        long size = channel.size();

        // 2.从通道读取文件内容
        byte[] bytes = new byte[2048];
        ByteBuffer byteBuffer = ByteBuffer.allocate(allocate);

        // channel.read(ByteBuffer) 方法就类似于 inputstream.read(byte)
        // 每次read都将读取 allocate 个字节到ByteBuffer
        int len;
        while ((len = channel.read(byteBuffer)) != -1) {
            // 注意先调用flip方法反转Buffer,再从Buffer读取数据
            byteBuffer.flip();

            // 有几种方式可以操作ByteBuffer
            // 1.可以将当前Buffer包含的字节数组全部读取出来
            //bytes = byteBuffer.array();
            // System.out.print(new String(bytes));

            // 2.类似与InputStrean的read(byte[],offset,len)方法读取
            byteBuffer.get(bytes, 0, len);
            // System.out.print(new String(bytes, 0 ,len));

            // 3.也可以遍历Buffer读取每个字节数据
            // 一个字节一个字节打印在控制台,但这种更慢且耗时
            // while(byteBuffer.hasRemaining()) {
            // System.out.print((char)byteBuffer.get());
            // }

            // 最后注意调用clear方法,将Buffer的位置回归到0
            byteBuffer.clear();

        }

        // 关闭通道和文件流
        channel.close();
        fis.close();

        long end = System.currentTimeMillis();
        System.out.println(String.format("\n===>文件大小：%s 字节", size));
        System.out.println(String.format("===>读取并打印文件耗时：%s毫秒", end - start));
    }

    /**
     * 仍然是根据FileChannel操作ByteBuffer,从ByteBuffer读取内容
     * 通道读取文件，速度比内存映射慢很多，甚至比普通缓冲流要慢
     *
     * @param allocate
     * @throws IOException
     */
    public static void readByChannelTest2(int allocate) throws IOException {
        long start = System.currentTimeMillis();
        FileInputStream fis = new FileInputStream(file);

        // 1.从FileInputStream对象获取文件通道FileChannel
        FileChannel channel = fis.getChannel();
        long size = channel.size();

        // 每次读取allocate个字节,计算要循环读取多少次
        long cycle = size / allocate;
        // 看是否能整数倍读完
        int mode = (int) (size % allocate);

        // 循环读取
        byte[] bytes;
        ByteBuffer byteBuffer = ByteBuffer.allocate(allocate);
        for (long i = 0; i < cycle; i++) {
            if (channel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                bytes = byteBuffer.array();
                // System.out.print(new String(bytes));
                byteBuffer.clear();
            }
        }

        // 读取最后mode个字节
        if (mode > 0) {
            byteBuffer = ByteBuffer.allocate(mode);
            if (channel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                bytes = byteBuffer.array();
                // System.out.print(new String(bytes));
                byteBuffer.clear();
            }
        }

        // 关闭通道和文件流
        channel.close();
        fis.close();

        long end = System.currentTimeMillis();
        System.out.println(String.format("\n===>文件大小：%s 字节", size));
        System.out.println(String.format("===>读取并打印文件耗时：%s毫秒", end - start));
    }

    /**
     * 通过 FileChannel.map()拿到 MappedByteBuffer
     * 使用内存文件映射，速度会快很多
     *
     * @throws IOException
     */
    public static void readByChannelTest3(int allocate) throws IOException {
        long start = System.currentTimeMillis();

        RandomAccessFile fis = new RandomAccessFile(new File(file), "rw");
        FileChannel channel = fis.getChannel();
        long size = channel.size();

        // 构建一个只读的MappedByteBuffer
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, size);

        // 如果文件不大,可以选择一次性读取到数组
        // byte[] all = new byte[(int)size];
        // mappedByteBuffer.get(all, 0, (int)size);
        // 打印文件内容
        // System.out.println(new String(all));

        // 如果文件内容很大,可以循环读取,计算应该读取多少次
        byte[] bytes = new byte[allocate];
        long cycle = size / allocate;
        int mode = (int)(size % allocate);
        //byte[] eachBytes = new byte[allocate];
        for (int i = 0; i < cycle; i++) {
            // 每次读取allocate个字节
            mappedByteBuffer.get(bytes);

            // 打印文件内容,关闭打印速度会很快
            // System.out.print(new String(eachBytes));
        }
        if(mode > 0) {
            bytes = new byte[mode];
            mappedByteBuffer.get(bytes);

            // 打印文件内容,关闭打印速度会很快
            // System.out.print(new String(eachBytes));
        }

        // 关闭通道和文件流
        channel.close();
        fis.close();

        long end = System.currentTimeMillis();
        System.out.println(String.format("\n===>文件大小：%s 字节", size));
        System.out.println(String.format("===>读取并打印文件耗时：%s毫秒", end - start));
    }


    /**
     * 普通Java IO 缓冲流读取
     * @throws IOException
     */
    public static void readByBufferdStream() throws IOException {
        long start = System.currentTimeMillis();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        long size = bis.available();

        int len = 0;
        int allocate = 1024;
        byte[] eachBytes = new byte[allocate];
        while((len = bis.read(eachBytes)) != -1) {
            // System.out.print(new String(eachBytes, 0, len));
        }

        bis.close();

        long end = System.currentTimeMillis();
        System.out.println(String.format("\n===>文件大小：%s 字节", size));
        System.out.println(String.format("===>读取并打印文件耗时：%s毫秒", end - start));
    }


}
