package wg.application.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CountCodeLines {
    private static long nullLines = 0; // 空行

    private static long annoLines = 0; // 注释行

    private static long codeLines = 0; // 代码行

    private static long configLines = 0; // 配置文件行行

    private static long allLines = 0; // 总行

    public static void main(String[] args) {
        CountCodeLines ccl = new CountCodeLines();
        ccl.listFile("D:\\java-project\\integrity-management");
        System.out.println("空行:" + nullLines);
        System.out.println("注释行:" + annoLines);
        System.out.println("代码行：" + codeLines);
        System.out.println("配置文件行：" + configLines);
        System.out.println("总行：" + allLines);
    }

    /**
     * 循环文件夹统计
     *
     * @param filePath
     */
    private void listFile(String filePath) {
        File f = new File(filePath);
        File[] childs = f.listFiles();

        for (int i = 0; i < childs.length; i++) {
            if (!childs[i].isDirectory()) {
                if (childs[i].getName().matches(".*\\.java$")
                        || childs[i].getName().endsWith(".yml")
                        || childs[i].getName().endsWith(".xml")
                        || childs[i].getName().endsWith(".properties")
                ) {
                    System.out.println(childs[i].getName());
                    sumCode(childs[i]);
                }
            } else {
                listFile(childs[i].getPath());
            }
        }
    }

    /**
     * 统计代码行数
     *
     * @param file
     */
    private void sumCode(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                allLines++;
                if (file.getName().endsWith(".yml")
                        || file.getName().endsWith(".xml")
                        || file.getName().endsWith(".properties")) {
                    //配置文件
                    configLines++;
                } else {
                    //java文件
                    String trimStr = line.trim();
                    if (trimStr.length() == 0) {
                        //空行
                        nullLines++;
                    } else if (trimStr.startsWith("//")
                            || trimStr.startsWith("/**")
                            || trimStr.startsWith("*")
                            || trimStr.startsWith("*/")
                            || trimStr.startsWith("/*")
                    ) {
                        annoLines++;
                    } else {
                        codeLines++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}