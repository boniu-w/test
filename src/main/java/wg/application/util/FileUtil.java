package wg.application.util;

import cn.hutool.core.io.resource.ClassPathResource;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 16:50 2022/3/14
 * @updateTime: 16:50 2022/3/14
 ************************************************************************/
public class FileUtil {
    /************************************************************************
     * @author: wg
     * @description: 把 map 写入 json 文件中
     * @params:
     * @return:
     * @createTime: 14:16  2022/3/14
     * @updateTime: 14:16  2022/3/14
     ************************************************************************/
    public static void writeToJson(Map map, ClassPathResource resource) throws IOException {
        String path = resource.getAbsolutePath();
        File file = new File(path);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, map);
    }

    /************************************************************************
     * @author: wg
     * @description: 读取 json 文件
     * @params:
     * @return:
     * @createTime: 14:12  2022/3/14
     * @updateTime: 14:12  2022/3/14
     ************************************************************************/
    public static JsonNode readJson(ClassPathResource resource) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        BufferedInputStream stream = new BufferedInputStream(resource.getStream());
        byte[] buff = new byte[1024];
        StringBuilder builder = new StringBuilder();

        int read;
        while ((read = stream.read(buff)) != -1) {
            builder.append(new String(buff, 0, read, StandardCharsets.US_ASCII));
        }
        stream.close();
        return mapper.readTree(builder.toString());
        //
        // StringBuilder originalFileContent = new StringBuilder();
        // BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));
        // String s = null;
        // while ((s = br.readLine()) != null) {
        //     originalFileContent.append(s).append("\n");
        // }
        // br.close();

        // return mapper.readTree(originalFileContent.toString());
    }

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /************************************************************************
     * @author: wg
     * @description: 获取文件名, 会去掉 类似这样的 `//12312/sdf.jpg` 之中的 斜杠, 只保留  `sdf.jpg`
     *   String path="12312/sdf.jpg";
     *   String s = FileUtil.reviseFileName(path);
     *   System.out.println(s); // sdf.jpg
     * @params:
     * @return:
     * @createTime: 10:20  2022/4/27
     * @updateTime: 10:20  2022/4/27
     ************************************************************************/
    public static String reviseFileName(String fileName) {
        //判断是否带有盘符信息
        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = (winSep > unixSep ? winSep : unixSep);
        if (pos != -1) {
            // Any sort of path separator found...
            fileName = fileName.substring(pos + 1);
        }
        //替换上传文件名字的特殊字符
        fileName = fileName.replace("=", "").replace(",", "").replace("&", "")
                .replace("#", "").replace("“", "").replace("”", "");
        //替换上传文件名字中的空格
        fileName = fileName.replaceAll("\\s", "");
        return fileName;
    }
}
