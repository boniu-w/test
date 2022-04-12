package wg.application.util;

import org.springframework.stereotype.Component;
import wg.application.exception.WgException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Component
public class PandocUtil {
    
    public static PandocUtil pandocUtil;

    @PostConstruct
    public void init() {
        pandocUtil = this;
    }

    /************************************************************************
     * @description: 先从数据库 读取相关数据, 写入 服务器指定位置
     * @author: wg
     * @date: 15:24  2021/11/24
     * @params:
     * @return:
     ************************************************************************/
    public static <T> File createDataFile(T entity) {
        String reportContent = null;
        if (CommonUtil.getter("reportContent", entity) != null) {
            reportContent = CommonUtil.getter("reportContent", entity).toString();
        }

        String path = System.getProperty("user.dir");
        String fileName = "report-" + System.currentTimeMillis() + ".md";

        File currentFile = new File(path, fileName);
        if (!currentFile.exists()) {
            currentFile.getParentFile().mkdir();
            try {
                currentFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        assert reportContent != null;
        StringReader stringReader = new StringReader(reportContent);
        // 把这个 写成 md
        try {
            BufferedReader bufferedReader = new BufferedReader(stringReader);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(currentFile));
            char[] chars = new char[1024];
            int len;
            while ((len = bufferedReader.read(chars)) != -1) {
                bufferedWriter.write(chars, 0, len);
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            bufferedReader.close();
            return currentFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /************************************************************************
     * @description: 把 md 转成 docx
     * @author: wg
     * @date: 15:49  2021/11/24
     * @params: file 为 .md 格式的文件
     * @return: pandoc 执行完成的 代码
     ************************************************************************/
    public static int parseToDocx(File file) {
        String fileNameOfMd = file.getAbsolutePath();
        String fileNameOfDocx = parseFileExtension(fileNameOfMd);
        if (fileNameOfDocx != null) {
            String[] command = new String[]{System.getProperty("user.dir") + "/pandoc", fileNameOfMd, "-o", fileNameOfDocx};
            File pandocFile = new File(System.getProperty("user.dir"));
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(command);
            processBuilder.directory(pandocFile); //设置工作目录
            processBuilder.redirectErrorStream(true);

            try {
                Process process = processBuilder.start();
                return process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return -1;
        }
        return -1;
    }

    /************************************************************************
     * @description: 下载
     * @author: wg
     * @date: 16:21  2021/11/24
     * @params:
     * @return:
     ************************************************************************/
    public static void downloadFile(HttpServletResponse response, File file) {
        String absolutePath = parseFileExtension(file.getAbsolutePath());
        OutputStream outputStream = null;
        try {
            /************ 设置响应header -> 开始  ************/
            absolutePath = new String(absolutePath.getBytes(), "ISO8859-1");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + parseFileExtension(file.getName()));
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            /************ 设置响应header -> 结束  ************/

            /************ 流 -> 开始 ************/
            outputStream = response.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(absolutePath);
            byte[] buffer = new byte[1024];

            int len;
            while ((len = fileInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            fileInputStream.close();
            /************ 流 -> 结束 ************/

            // 删除 md 和 docx
            boolean b = deleteFile(file.getName());
            if (!b) {
                throw new WgException("删除md docx 出错");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /************************************************************************
     * @description: 转换文件名后缀 .md -> .docx
     * "\u002E" = "."
     * @author: wg
     * @date: 11:25  2021/11/25
     * @params:
     * @return:
     ************************************************************************/
    private static String parseFileExtension(String fileName) {
        String[] split = fileName.split("\\u002E");
        if (split.length == 2) {
            if (split[1].equals("md")) {
                fileName = split[0] + ".docx";
                return fileName;
            }
        }
        return null;
    }

    private static boolean deleteFile(String fileName) {
        boolean deleted = false;
        File fileMd = new File(System.getProperty("user.dir"), fileName);
        File fileDocx = new File(System.getProperty("user.dir"), parseFileExtension(fileName));

        File[] files = new File[]{fileMd, fileDocx};
        for (File file2 : files) {
            if (file2.isDirectory()) {
                return false;
            }
            if (file2.isFile()) {
                deleted = file2.delete();
            }
            if (!deleted) {
                return false;
            }
        }
        return deleted;
    }
}
