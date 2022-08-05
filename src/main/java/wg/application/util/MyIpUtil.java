package wg.application.util;

import cn.hutool.db.ds.pooled.DbConfig;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.*;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 15:59 2022/8/5
 * @updateTime: 15:59 2022/8/5
 ************************************************************************/
public class MyIpUtil {

    static final Logger logger = LoggerFactory.getLogger(MyIpUtil.class);

//     static DbConfig config;
//     static DbSearcher searcher;
//     static String dbPath;
//
//     static {
//         dbPath = createFtlFileByFtlArray() + "ip2region.db";
//         try {
//             config = new DbConfig();
//         } catch (DbMakerConfigException e) {
//             e.printStackTrace();
//         }
//         try {
//             searcher = new DbSearcher(config, dbPath);
//         } catch (FileNotFoundException e) {
//             e.printStackTrace();
//         }
//     }
//
//     private static String createFtlFileByFtlArray() {
//         String ftlPath = "city/";
//
//         return ftlPath;
//     }
//
//     public static String getCityInfo(String ip) {
//         if (StringUtils.isEmpty(dbPath)) {
//             logger.error("Error: Invalid ip2region.db file");
//             return null;
//         }
//         if (config == null || searcher == null) {
//             logger.error("Error: DbSearcher or DbConfig is null");
//             return null;
//         }
//
//         //查询算法
//         //B-tree, B树搜索（更快）
//         int algorithm = DbSearcher.BTREE_ALGORITHM;
//
//         //Binary,使用二分搜索
//         //DbSearcher.BINARY_ALGORITHM
//
//         //Memory,加载内存（最快）
//         //DbSearcher.MEMORY_ALGORITYM
//         try {
//             // 使用静态代码块，减少文件读取操作
// //            DbConfig config = new DbConfig();
// //            DbSearcher searcher = new DbSearcher(config, dbPath);
//
//             //define the method
//             Method method = null;
//             switch (algorithm) {
//                 case DbSearcher.BTREE_ALGORITHM:
//                     method = searcher.getClass().getMethod("btreeSearch", String.class);
//                     break;
//                 case DbSearcher.BINARY_ALGORITHM:
//                     method = searcher.getClass().getMethod("binarySearch", String.class);
//                     break;
//                 case DbSearcher.MEMORY_ALGORITYM:
//                     method = searcher.getClass().getMethod("memorySearch", String.class);
//                     break;
//                 default:
//             }
//
//             DataBlock dataBlock = null;
//             if (Util.isIpAddress(ip) == false) {
//                 System.out.println("Error: Invalid ip address");
//             }
//
//             dataBlock = (DataBlock) method.invoke(searcher, ip);
//             String ipInfo = dataBlock.getRegion();
//             if (!StringUtils.isEmpty(ipInfo)) {
//                 ipInfo = ipInfo.replace("|0", "");
//                 ipInfo = ipInfo.replace("0|", "");
//             }
//             return ipInfo;
//
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//
//         return null;
//     }

    // public static String getIpPossession(String ip) {
    //     String cityInfo = getCityInfo(ip);
    //     if (!StringUtils.isEmpty(cityInfo)) {
    //         cityInfo = cityInfo.replace("|", " ");
    //         String[] cityList = cityInfo.split(" ");
    //         if (cityList.length > 0) {
    //             // 国内的显示到具体的省
    //             if ("中国".equals(cityList[0])) {
    //                 if (cityList.length > 1) {
    //                     return cityList[1];
    //                 }
    //             }
    //             // 国外显示到国家
    //             return cityList[0];
    //         }
    //     }
    //     return "未知";
    // }

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
            ip = "180.212.222.18";
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
    }
}
