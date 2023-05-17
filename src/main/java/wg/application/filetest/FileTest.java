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

    /************************************************************************
     * @author: wg
     * @description: "\" -> "&#92;"
     * 正则表达式中, \表示将下一字符标记为特殊字符。如\d表示数字字符匹配，等效于 [0-9]。
     * \\中的第一个\表示java的转义字符\由编译器解析，第二个\是正则表达式\由正则表达式引擎解析。
     * @params:
     * @return:
     * @createTime: 9:48  2023/5/17
     * @updateTime: 9:48  2023/5/17
     ************************************************************************/
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
