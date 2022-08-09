package wg.application.util;

import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 15:59 2022/8/5
 * @updateTime: 15:59 2022/8/5
 ************************************************************************/
public class MyIpUtil {

    static final Logger logger = LoggerFactory.getLogger(MyIpUtil.class);

    public static void main(String[] args) {
        // 1、创建 searcher 对象
        String dbPath = "src/main/resources/city/ip2region.xdb";
        Searcher searcher = null;

        try {
            searcher = Searcher.newWithFileOnly(dbPath);
        } catch (IOException e) {
            System.out.printf("failed to create searcher with `%s`: %s\n", dbPath, e);
            return;
        }

        // 2、查询
        String ip = null;
        try {
            // ip = "180.212.222.18";
            ip = "10.12.12.98";
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
        } catch (Exception e) {
            System.out.printf("failed to search(%s): %s\n", ip, e);
        }

        // 3、关闭资源
        try {
            searcher.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 备注：并发使用，每个线程需要创建一个独立的 searcher 对象单独使用。

        try {
            String myIP = getMyIP();
            System.out.println("myIP: "+myIP);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /************************************************************************
     * @author: wg
     * @description: 不管用
     * @params:
     * @return:
     * @createTime: 14:33  2022/8/9
     * @updateTime: 14:33  2022/8/9
     ************************************************************************/
    private static String getMyIP() throws IOException {
        String url = "http://ip.chinaz.com";
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String msg = sb.toString();
            String[] msgs = msg.split(",");
            String[] ips = msgs[0].split(":");
            return ips[1].substring(1, ips[1].length() - 1);
        } finally {
            is.close();
        }
    }
}
