package wg.application.service.impl;

import org.springframework.stereotype.Service;
import wg.application.util.FileUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 17:16 2022/9/8
 * @updateTime: 17:16 2022/9/8
 ************************************************************************/
@Service
public class MyFileHandlerServiceImpl {

    /************************************************************************
     * @author: wg
     * @description: 读取一个文件夹下的所有文件, 去重后 保存, 意在去除文件夹 和 去重
     * @params:
     * @return:
     * @createTime: 17:16  2022/9/8
     * @updateTime: 17:16  2022/9/8
     ************************************************************************/
    public void distinctFile() throws Exception {
        // ↓↓******************* start <1. 复制文件夹下的所有文件> *******************↓↓
        String path = "H:\\test-copy";

        List<File> fileList = new ArrayList<>();

        fileList = FileUtil.getAllFile(path, fileList);

        // allFile.forEach(System.out::println);
        System.out.println(fileList.size());

        // 用 map 来去重
        HashMap<String, File> fileHashMap = new HashMap<>();
        String hexHash = "";
        for (File file : fileList) {
            hexHash = FileUtil.getHexHash(file);
            fileHashMap.put(hexHash, file);
        }

        System.out.println(fileHashMap.size());

        List<File> distinctFile = new ArrayList<>();
        fileHashMap.forEach((k, v) -> distinctFile.add(v));
        // ↑↑******************* end  <code>  *******************↑↑

        // 2. 清空这个文件夹
        FileUtil.deleteDir(path);

        // ↓↓******************* start <3. 把这个文件重新粘贴到这个文件夹下> *******************↓↓
        File newFile = null;

        for (File file1 : distinctFile) {
            // if (!file1.exists()) file1.createNewFile();
            FileInputStream fileInputStream = new FileInputStream(file1);
            newFile = new File(path + "\\" + file1.getName());
            if (!newFile.exists()) newFile.createNewFile();
            cn.hutool.core.io.FileUtil.writeFromStream(fileInputStream, newFile);
        }
        // ↑↑******************* end  <code>  *******************↑↑
    }

}
