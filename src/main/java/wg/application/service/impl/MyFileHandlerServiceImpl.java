package wg.application.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import wg.application.util.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 17:16 2022/9/8
 * @updateTime: 17:16 2022/9/8
 ************************************************************************/
@Service
public class MyFileHandlerServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(MyFileHandlerServiceImpl.class);

    /************************************************************************
     * @author: wg
     * @description: 读取一个文件夹下的所有文件, 去重后 保存 到 临时文件夹中, 之后 清空这个文件夹下的所有内容, 但保留这个文件夹
     * @params:
     * @return:
     * @createTime: 17:16  2022/9/8
     * @updateTime: 17:16  2022/9/8
     ************************************************************************/
    public void distinctFile(String path, String tempPath) throws Exception {
        // ↓↓******************* start <1. 读取文件夹下的所有文件, 然后去重> *******************↓↓
        List<File> fileList = new ArrayList<>();
        FileUtil.getAllFile(path, fileList);

        System.out.println(fileList.size());

        // 用 map 来去重
        HashMap<String, File> fileHashMap = new HashMap<>();
        String hexHash = "";
        for (File file : fileList) {
            hexHash = FileUtil.getSha256Hex(file);
            fileHashMap.put(hexHash, file);
        }

        System.out.println(fileHashMap.size());

        List<File> distinctFile = new ArrayList<>();
        fileHashMap.forEach((k, v) -> distinctFile.add(v));
        // ↑↑******************* end  <code>  *******************↑↑


        // ↓↓******************* start <复制到临时文件夹, 流中文件删不掉, 另一个程序正在使用此文件> *******************↓↓
        FileInputStream fileInputStream = null;
        HashMap<FileInputStream, String> streamStringHashMap = new HashMap<>();

        for (File file1 : distinctFile) {
            fileInputStream = new FileInputStream(file1);
            String name = file1.getName();
            streamStringHashMap.put(fileInputStream, name);
        }

        File tempDir = null;
        for (Map.Entry<FileInputStream, String> entry : streamStringHashMap.entrySet()) {
            FileInputStream inputStream = entry.getKey();
            String name = entry.getValue();
            tempDir = new File(tempPath);
            if (!tempDir.exists()) tempDir.mkdir();
            cn.hutool.core.io.FileUtil.writeFromStream(inputStream, new File(tempPath, name));
        }
        // ↑↑******************* end  <code>  *******************↑↑

        // 清空这个文件夹
        FileUtil.clearDir(path);
    }

    /************************************************************************
     * @author: wg
     * @description: 测试删除 流中文件
     * 测试结果: 流中文件无法删除 ->  另一个程序正在使用此文件，进程无法访问。
     * @params:
     * @return:
     * @createTime: 10:54  2022/9/9
     * @updateTime: 10:54  2022/9/9
     ************************************************************************/
    public void testDeleteInputStream() throws IOException {
        final String path = "H:\\test-copy\\IDEA快捷键.txt";
        File file = new File(path);

        // ArrayList<File> files = new ArrayList<>();
        // List<File> allFile = FileUtil.getAllFile(path, files);

        FileInputStream fileInputStream = new FileInputStream(path);

        // boolean delete = file.delete();
        boolean delete = cn.hutool.core.io.FileUtil.del(file);
        if (!delete) {
            logger.error("MyFileHandlerServiceImpl 文件删除失败");
        }

        // FileUtil.deleteDir(path);

        cn.hutool.core.io.FileUtil.writeFromStream(fileInputStream, new File(path));

    }

}
