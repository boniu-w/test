package wg.application.filetest;

import wg.application.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileTest {

    public static void main(String[] args) {
        try {
            fileTest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void fileTest() throws Exception {
        String path = "\\\\nas-wg\\video\\剧";
        ArrayList<File> files = new ArrayList<>();
        List<File> allFile = FileUtil.getAllFile(path, files);

        Map<String, List<File>> fileMap = allFile.stream()
                .collect(Collectors.groupingBy(e -> {
                    return e.getAbsolutePath().split("\\\\")[5];
                }));

        for (Map.Entry<String, List<File>> entry : fileMap.entrySet()) {
            String key = entry.getKey();
            List<File> fileList = entry.getValue();
            for (File file : fileList) {
                String sha256Hex = FileUtil.getSha256(file); // 大文件 消耗时间很长
                System.out.println(sha256Hex);
                // String md5 = FileUtil.getMD5(file); // 大文件 outofmemory
                // System.out.println(md5);
            }
        }

    }
}
